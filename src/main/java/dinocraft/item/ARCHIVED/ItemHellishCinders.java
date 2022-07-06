package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.item.ItemSpellBook;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.world.World;
//
//public class ItemHellishCinders extends ItemSpellBook
//{
//	public ItemHellishCinders()
//	{
//		super();
//	}
//
//	@Override
//	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
//	{
//		if (!world.isRemote && living instanceof EntityPlayer)
//		{
//			DinocraftEntity.getEntity(living).getActionsModule().shootFlameBalls(3);
//			((EntityPlayer) living).getCooldownTracker().setCooldown(this, 60);
//			stack.damageItem(1, living);
//		}
//
//		return super.onItemUseFinish(stack, world, living);
//	}
//}
