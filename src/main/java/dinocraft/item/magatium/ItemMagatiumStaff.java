package dinocraft.item.magatium;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemMagatiumStaff extends Item
{
	public ItemMagatiumStaff()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(876);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 30;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
	{
		return oldStack != null;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		player.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (!world.isRemote && living instanceof EntityPlayer)
		{
			DinocraftEntity.getEntity(living).getActionsModule().shootMagatiumBolts(3);
			((EntityPlayer) living).getCooldownTracker().setCooldown(this, 60);
			stack.damageItem(1, living);
		}

		return super.onItemUseFinish(stack, world, living);
	}
}
