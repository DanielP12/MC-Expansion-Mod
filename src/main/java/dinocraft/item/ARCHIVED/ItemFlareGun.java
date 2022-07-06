package dinocraft.item.ARCHIVED;
//package dinocraft.item;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.entity.EntityFlameBullet;
//import dinocraft.init.DinocraftItems;
//import dinocraft.init.DinocraftSoundEvents;
//import dinocraft.network.Packet;
//import dinocraft.network.server.SPacketRecoilPlayer;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//
//public class ItemFlareGun extends Item
//{
//	public ItemFlareGun()
//	{
//		this.setMaxStackSize(1);
//		this.setMaxDamage(1248);
//	}
//
//	@Override
//	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft)
//	{
//		entity.resetActiveHand();
//		super.onPlayerStoppedUsing(stack, world, entity, timeLeft);
//	}
//
//	@Override
//	public int getMaxItemUseDuration(ItemStack stack)
//	{
//		return 72000;
//	}
//
//	@Override
//	public EnumAction getItemUseAction(ItemStack stack)
//	{
//		return EnumAction.BOW;
//	}
//
//	@Override
//	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count)
//	{
//		if (entity instanceof EntityPlayer)
//		{
//			EntityPlayer player = (EntityPlayer) entity;
//
//			if (player.getCooldownTracker().getCooldown(this, 0.0F) == 0)
//			{
//				if (player.isCreative() || DinocraftEntity.hasAmmo(entity, DinocraftItems.FLAME_BULLET))
//				{
//					if (!player.isCreative())
//					{
//						DinocraftEntity.consumeAmmo(entity, DinocraftItems.FLAME_BULLET, 1);
//						stack.damageItem(1, player);
//					}
//
//					if (!player.world.isRemote)
//					{
//						EntityFlameBullet bullet = new EntityFlameBullet(player, 0.001F);
//						bullet.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 4.0F, 0.5F);
//						player.world.spawnEntity(bullet);
//						player.world.playSound(null, player.getPosition(), DinocraftSoundEvents.SHOT, SoundCategory.NEUTRAL, 4.0F, player.world.rand.nextFloat() + 0.5F);
//						Packet.sendTo(new SPacketRecoilPlayer(0.05F, 1.5F, true), (EntityPlayerMP) player);
//						DinocraftEntity.recoil(entity, 0.05F, 1.5F, true);
//						player.getCooldownTracker().setCooldown(this, 5);
//					}
//				}
//				else
//				{
//					player.resetActiveHand();
//				}
//			}
//
//			super.onUsingTick(stack, entity, count);
//		}
//		else
//		{
//			if (!entity.world.isRemote)
//			{
//				EntityFlameBullet bullet = new EntityFlameBullet(entity, 0.001F);
//				bullet.shoot(entity, entity.rotationPitch, entity.rotationYawHead, 0.0F, 4.0F, 0.25F);
//				entity.world.spawnEntity(bullet);
//				entity.world.playSound(null, entity.getPosition(), DinocraftSoundEvents.SHOT, SoundCategory.NEUTRAL, 4.0F, entity.world.rand.nextFloat() + 0.5F);
//				stack.damageItem(1, entity);
//			}
//
//			entity.resetActiveHand();
//			super.onUsingTick(stack, entity, count);
//		}
//	}
//
//	@Override
//	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
//	{
//		return oldStack != null;
//	}
//
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		if (player.isCreative() || DinocraftEntity.hasAmmo(player, DinocraftItems.FLAME_BULLET))
//		{
//			player.setActiveHand(hand);
//		}
//		else if (!world.isRemote)
//		{
//			DinocraftEntity.sendActionbarMessage(player, TextFormatting.RED + "Out of ammo!");
//			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 0.25F);
//		}
//
//		return super.onItemRightClick(world, player, hand);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//
//	/*
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		ItemStack stack = player.getHeldItem(hand);
//		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
//
//		if (player.isCreative() || dinoEntity.hasAmmo(DinocraftItems.RAY_BULLET))
//		{
//			if (!player.isCreative())
//			{
//				dinoEntity.consumeAmmo(DinocraftItems.RAY_BULLET, 1);
//				stack.damageItem(1, player);
//			}
//
//			if (!world.isRemote)
//			{
//				EntityRayBullet ball = new EntityRayBullet(player, 0.001F);
//				Vec3d vector = player.getLookVec();
//				double x = vector.x;
//				double y = vector.y;
//	        	double z = vector.z;
//				player.setActiveHand(hand);
//				ball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.33F, 0.0F);
//				ball.setRotationYawHead(player.rotationYawHead);
//	        	ball.setPositionAndUpdate(player.posX - (x * 0.75D), player.posY + player.eyeHeight, player.posZ - (z * 0.75D));
//	        	world.spawnEntity(ball);
//	        	world.playSound(null, player.getPosition(), DinocraftSoundEvents.RAY_GUN_SHOT, SoundCategory.NEUTRAL, 3.0F, world.rand.nextFloat() + 0.5F);
//			}
//
//			DinocraftEntity.getEntity(player).recoil(0.1F, 1.25F, true);
//			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
//		}
//		else if (!world.isRemote)
//		{
//			dinoEntity.sendActionbarMessage(TextFormatting.RED + "Out of ammo!");
//			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
//			return ActionResult.newResult(EnumActionResult.FAIL, stack);
//		}
//
//		return ActionResult.newResult(EnumActionResult.FAIL, stack);
//	}
//	 */
//}