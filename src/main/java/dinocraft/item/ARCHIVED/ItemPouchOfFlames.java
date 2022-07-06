package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import dinocraft.entity.EntitySmolderingCharcoal;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//
//public class ItemPouchOfFlames extends Item
//{
//	public ItemPouchOfFlames()
//	{
//		this.setMaxStackSize(1);
//		this.setMaxDamage(742);
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
//			if (!player.world.isRemote && player.getCooldownTracker().getCooldown(this, 0.0F) == 0)
//			{
//				EntitySmolderingCharcoal bullet = new EntitySmolderingCharcoal(player, 0.05F);
//				bullet.setPosition(player.posX, player.posY + player.height / 2.0F, player.posZ);
//				Vec3d vec = player.getLookVec();
//				double x = bullet.world.rand.nextDouble() * 0.5D * vec.x;
//				double y = bullet.world.rand.nextDouble() * 0.25D + 0.25D;
//				double z = bullet.world.rand.nextDouble() * 0.5D * vec.z;
//				bullet.motionX = x;
//				bullet.motionY = y;
//				bullet.motionZ = z;
//				bullet.velocityChanged = true;
//				player.world.spawnEntity(bullet);
//				player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.75F, player.world.rand.nextFloat() + 0.5F);
//				player.getCooldownTracker().setCooldown(this, 4);
//
//				if (itemRand.nextInt(4) == 0)
//				{
//					stack.damageItem(1, player);
//				}
//			}
//
//			super.onUsingTick(stack, entity, count);
//		}
//	}
//
//	@Override
//	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
//	{
//		entityLiving.resetActiveHand();
//		return super.onItemUseFinish(stack, world, entityLiving);
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
//		player.setActiveHand(hand);
//		return super.onItemRightClick(world, player, hand);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//}