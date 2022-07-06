//package dinocraft.item.ARCHIVED;
//
//import dinocraft.entity.EntityShiningStar;
//import dinocraft.item.ItemSpellBook;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//
//public class ItemSoulStar extends ItemSpellBook
//{
//	@Override
//	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
//	{
//		if (!world.isRemote && living instanceof EntityPlayer)
//		{
//			EntityShiningStar bolt = new EntityShiningStar(world, living);
//			bolt.shoot(living, living.rotationPitch, living.rotationYaw, 0.0F, 1.5F, 0.0F);
//			world.spawnEntity(bolt);
//			world.playSound(null, living.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
//			((EntityPlayer) living).getCooldownTracker().setCooldown(this, 80);
//			stack.damageItem(1, living);
//		}
//
//		return super.onItemUseFinish(stack, world, living);
//	}
//}
