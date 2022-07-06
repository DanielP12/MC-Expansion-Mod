package dinocraft.capabilities.itemstack;

import dinocraft.capabilities.DinocraftCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DinocraftItemStack implements IDinocraftItemStack
{
	private final ItemStack stack;
	protected DinocraftItemStackTicks ticks;
	private int ticksHeld;
	
	@SubscribeEvent
	public static void onHolding(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		ItemStack stack1 = entity.getHeldItemMainhand();
		ItemStack stack2 = entity.getHeldItemOffhand();

		if (stack1 != null && stack1 != ItemStack.EMPTY)
		{
			DinocraftItemStack dinoStack = getItemStack(stack1);

			if (dinoStack != null)
			{
				dinoStack.ticksHeld = (dinoStack.ticksHeld + 1) % 10000;
			}
		}

		if (stack2 != null && stack2 != ItemStack.EMPTY)
		{
			DinocraftItemStack dinoStack = getItemStack(stack2);

			if (dinoStack != null)
			{
				dinoStack.ticksHeld = (dinoStack.ticksHeld + 1) % 10000;
			}
		}
	}
	
	public DinocraftItemStack(ItemStack stack)
	{
		this.stack = stack;
		this.ticks = new DinocraftItemStackTicks(this);
	}

	public DinocraftItemStack()
	{
		this.stack = null;
	}

	/**
	 * Gets this capability data's corresponding entity
	 */
	@Override
	public ItemStack getItemStack()
	{
		return this.stack;
	}

	/**
	 * Gets the capability data for specified entity
	 */
	public static DinocraftItemStack getItemStack(ItemStack stack)
	{
		return !DinocraftItemStack.hasCapability(stack) ? null : (DinocraftItemStack) stack.getCapability(DinocraftCapabilities.DINOCRAFT_ITEM_STACK, null);
	}

	public DinocraftItemStackTicks getTicksModule()
	{
		return this.ticks;
	}

	/**
	 * Returns if this entity has this capability
	 */
	public static boolean hasCapability(ItemStack stack)
	{
		return stack.hasCapability(DinocraftCapabilities.DINOCRAFT_ITEM_STACK, null);
	}
	
	/**
	 * Returns the number of ticks this ItemStack has been held for (mod 10000)
	 */
	public int getTicksHeld()
	{
		return this.ticksHeld;
	}

	public static class Storage implements IStorage<IDinocraftItemStack>
	{
		@Override
		public NBTBase writeNBT(Capability<IDinocraftItemStack> capability, IDinocraftItemStack instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();
			instance.write(compound);
			return compound;
		}

		@Override
		public void readNBT(Capability<IDinocraftItemStack> capability, IDinocraftItemStack instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;
			instance.read(compound);
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		this.ticks.write(tag);
	}
	
	@Override
	public void read(NBTTagCompound tag)
	{
		this.ticks.read(tag);
	}
}