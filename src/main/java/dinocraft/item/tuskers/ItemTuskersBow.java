package dinocraft.item.tuskers;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemTuskersBow extends ItemBow
{
	public ItemTuskersBow()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(876);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		EntityPlayer player = event.getEntity();
		Item item = player.getActiveItemStack().getItem();

		if (item == this)
		{
			event.setNewfov(event.getFov() * ((ItemTuskersBow) item).getZoom(player));
		}
	}

	protected float getZoom(EntityLivingBase entity)
	{
		return 1.0F - MathHelper.clamp(72000 - entity.getItemInUseCount(), 0, 20) / 256F;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int timeLeft)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) living;
			boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(player);
			int i = this.getMaxItemUseDuration(stack) - timeLeft;
			i = ForgeEventFactory.onArrowLoose(stack, world, player, i, !itemstack.isEmpty() || flag);
			
			if (i < 0)
			{
				return;
			}
			
			if (!itemstack.isEmpty() || flag)
			{
				if (itemstack.isEmpty())
				{
					itemstack = new ItemStack(Items.ARROW);
				}
				
				float f = getArrowVelocity(i);
				
				if (f >= 0.1D)
				{
					boolean flag1 = player.capabilities.isCreativeMode || itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, player);
					
					if (!world.isRemote)
					{
						ItemArrow itemarrow = (ItemArrow) (itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
						EntityArrow arrow = itemarrow.createArrow(world, itemstack, player);
						arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.5F, 1.0F);
						arrow.setDamage(arrow.getDamage() + 0.25D);
						arrow.addTag("HeartArrow");
						
						if (f == 1.0F)
						{
							arrow.setIsCritical(true);
						}
						
						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
						
						if (j > 0)
						{
							arrow.setDamage(arrow.getDamage() + j * 0.5D + 0.5D);
						}
						
						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
						
						if (k > 0)
						{
							arrow.setKnockbackStrength(k);
						}
						
						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
						{
							arrow.setFire(100);
						}
						
						stack.damageItem(1, player);
						
						if (flag1 || player.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
						{
							arrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
						}

						world.spawnEntity(arrow);
					}
					
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					
					if (!flag1 && !player.capabilities.isCreativeMode)
					{
						itemstack.shrink(1);
						
						if (itemstack.isEmpty())
						{
							player.inventory.deleteStack(itemstack);
						}
					}
					
					player.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}