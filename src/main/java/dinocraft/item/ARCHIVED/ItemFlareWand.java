package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import dinocraft.entity.EntityFlareBolt;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//
//public class ItemFlareWand extends Item
//{
//	public ItemFlareWand()
//	{
//		this.setMaxStackSize(1);
//		this.setMaxDamage(876);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		ItemStack stack = player.getHeldItem(hand);
//
//		if (!world.isRemote)
//		{
//			player.getCooldownTracker().setCooldown(this, 16);
//			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1.0F, 0.5F);
//			world.playSound(null, player.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 1.0F, 2.5F);
//			EntityFlareBolt bolt = new EntityFlareBolt(player, 0.0F);
//			bolt.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.75F, 0.0F);
//			world.spawnEntity(bolt);
//			stack.damageItem(1, player);
//		}
//
//		return new ActionResult(EnumActionResult.SUCCESS, stack);
//	}
//}
