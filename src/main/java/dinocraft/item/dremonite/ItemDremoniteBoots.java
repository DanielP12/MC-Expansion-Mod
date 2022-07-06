package dinocraft.item.dremonite;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketDreadedFlight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDremoniteBoots extends ItemArmor
{
	public ItemDremoniteBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
	}
	
	//	@SubscribeEvent(priority = EventPriority.LOWEST)
	//	public void onLivingFall(LivingFallEvent event)
	//	{
	//		EntityLivingBase entityliving = event.getEntityLiving();
	//
	//		if (entityliving instanceof EntityPlayer && entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this && entityliving.isSneaking() && entityliving.fallDistance >= 4.0F)
	//		{
	//			event.setCanceled(true);
	//			EntityPlayer player = (EntityPlayer) entityliving;
	//			List<Entity> entities = Lists.newArrayList(player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(2.5D, 1.5D, 2.5D)));
	//			Random rand = player.world.rand;
	//			player.getItemStackFromSlot(EntityEquipmentSlot.FEET).attemptDamageItem(1, rand, null);
	//
	//
	//			float f = 2.0F;
	//			player.setAbsorptionAmount(player.getAbsorptionAmount() - 2.0F);
	//			player.setHealth(player.getHealth() - 2.0F);
	//			player.getCombatTracker().trackDamage(DamageSource.FALL, player.getHealth(), 2.0F);
	//			player.performHurtAnimation();
	//
	//
	//
	//			if (!player.world.isRemote)
	//			{
	//				player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 5.0F, 0.25F);
	//				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	//
	//
	//				for (int i = 0; i < 50; i++)
	//				{
	//					dinoEntity.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + player.world.rand.nextFloat() * player.width * 2.5F - player.width,
	//							player.posY + 0.5D + player.world.rand.nextFloat() * player.height - 1.25D, player.posZ + player.world.rand.nextFloat() * player.width * 2.5F - player.width,
	//							player.world.rand.nextGaussian() * 0.50D, player.world.rand.nextGaussian() * 0.010D, player.world.rand.nextGaussian() * 0.50D, 1);
	//				}
	//			}
	//
	//			for (Entity entity : entities)
	//			{
	//				if (!player.world.isRemote)
	//				{
	//					float damage = 2.5F * player.fallDistance - 2.0F;
	//					entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
	//				}
	//			}
	//		}
	//	}
	//
	//	@Override
	//	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	//	{
	//		if (player.fallDistance > 2.0F && player.isSneaking() && !player.onGround && player.motionY < 0.0D && !player.isCreative() && !player.capabilities.isFlying && !player.isInWater() && !player.isOnLadder() && !player.isInLava())
	//		{
	//			DinocraftEntity.getEntity(player).setFallDamageReductionAmount(player.fallDistance / 2.0F);
	//			player.motionY -= 0.20D;
	//		}
	//
	//		super.onArmorTick(world, player, stack);
	//	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (player.getCooldownTracker().getCooldown(this, 0.0F) == 0 && world.isRemote && !player.capabilities.allowFlying && !player.isCreative() && !player.isElytraFlying())
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
			DinocraftEntityActions actions = dinoEntity.getActionsModule();
			
			if (player.onGround || player.isInWater() || player.isInLava() || player.collided || player.isRiding())
			{
				if (actions.isDreadedFlying())
				{
					PacketHandler.sendToServer(new CPacketDreadedFlight(false));
				}
				
				actions.setHasJumpedInAir(true);
				actions.setCanJumpInAir(false);
				actions.setDreadedFlying(false);
			}
			
			if (!player.onGround)
			{
				boolean jumping = dinoEntity.isJumping();
				boolean sprinting = player.isSprinting();
				
				if (sprinting && !jumping && !actions.canJumpInAir())
				{
					actions.setHasJumpedInAir(false);
					actions.setCanJumpInAir(true);
				}
				
				if (sprinting && jumping && !actions.hasJumpedInAir())
				{
					actions.setHasJumpedInAir(true);
					player.motionX *= 2.5D;
					player.motionZ *= 2.5D;
					player.motionY = player.getLookVec().normalize().y + 0.75D;
					PacketHandler.sendToServer(new CPacketDreadedFlight(true));
					actions.setDreadedFlying(true);
				}

				if (actions.isDreadedFlying())
				{
					if (player.isSneaking())
					{
						KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
					}
					
					Vec3d vec = player.getLookVec().normalize();

					if (player.motionY < -0.33D && vec.y > 0.0D)
					{
						player.motionY += vec.y * 0.033D;
					}
					
					if (player.motionY < 0.01D)
					{
						player.motionY += 0.075D;
					}

					player.motionY += vec.y < -0.5D ? vec.y * 0.025D : 0.0D;

					if (sprinting)
					{
						player.motionX *= 1.0025D;
						player.motionZ *= 1.0025D;
					}
					
					player.motionX *= 1.05D;
					player.motionZ *= 1.05D;
				}
			}
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
		
		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "When worn:");
			tooltip.add(TextFormatting.DARK_AQUA + " Dreaded Flight (" + TextFormatting.DARK_GRAY + "[SPRINT] " + TextFormatting.DARK_AQUA + "+" + TextFormatting.DARK_GRAY + " [SPACE] " + TextFormatting.DARK_AQUA + "+" + TextFormatting.DARK_GRAY + " [SPACE]" + TextFormatting.DARK_AQUA + ")");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}