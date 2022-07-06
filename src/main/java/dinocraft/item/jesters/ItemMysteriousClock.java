package dinocraft.item.jesters;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.capabilities.itemstack.DinocraftItemStack;
import dinocraft.capabilities.itemstack.DinocraftItemStackTicks;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMysteriousClock extends Item
{
	public ItemMysteriousClock()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();

		if (!entity.world.isRemote && entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isFlying && !entity.isElytraFlying())
		{
			DinocraftEntityActions actions = DinocraftEntity.getEntity(entity).getActionsModule();
			ItemStack mainhandItem = entity.getHeldItemMainhand(), offhandItem = entity.getHeldItemOffhand();
			ItemStack stack = mainhandItem.getItem() == this ? mainhandItem : offhandItem.getItem() == this ? offhandItem : null;
			
			if (stack != null && entity.isSprinting())
			{
				if (entity.ticksExisted % 20 == 0)
				{
					entity.world.playSound(null, entity.getPosition(), DinocraftSoundEvents.CLOCK, SoundCategory.PLAYERS, 0.25F,
							entity.world.rand.nextFloat() * 0.05F + (entity.ticksExisted % 40 == 0 ? 1.25F : 1.75F));
				}
				
				DinocraftItemStackTicks ticks = DinocraftItemStack.getItemStack(stack).getTicksModule();
				int mysteriousClockTick = ticks.getMysteriousClockTick();
				
				if (mysteriousClockTick % 100 == 0)
				{
					stack.damageItem(1, entity);
					
					if (mysteriousClockTick % 200 == 0)
					{
						((EntityPlayerMP) entity).connection.sendPacket(new SPacketSoundEffect(SoundEvents.AMBIENT_CAVE, SoundCategory.PLAYERS, entity.posX, entity.posY, entity.posZ, 2.5F, entity.world.rand.nextFloat() * 0.5F + 1.25F));
					}
				}

				if (mysteriousClockTick % 2 == 0)
				{
					float f0 = entity.height * 0.1F;
					DinocraftServer.spawnJesterParticles(entity.world, 1, 1, entity.posX, entity.posY + f0, entity.posZ, entity.width * 0.75F, f0, entity.width * 0.75F);
				}
				
				ticks.incrementMysteriousClockTick();

				if (!actions.isJesterSpeeding())
				{
					DinocraftEntity.setMovementSpeed(entity, 0.025D);
					actions.setJesterSpeeding(true);
				}
			}
			else if (actions.isJesterSpeeding())
			{
				actions.setJesterSpeeding(false);
				DinocraftEntity.setMovementSpeed(entity, 0.0D);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;

		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "When held:");
			tooltip.add(TextFormatting.GRAY + " The player sprints faster while hearing hallucinations");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}
