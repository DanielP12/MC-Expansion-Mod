package dinocraft.item.jesters;

import dinocraft.entity.EntityJestersBolt;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemJestersSceptre extends Item
{
	public ItemJestersSceptre()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(876);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 35;
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
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		stack.damageItem(1, living);
		
		if (!world.isRemote && living instanceof EntityPlayer)
		{
			EntityJestersBolt bolt = new EntityJestersBolt(world, living);
			bolt.shoot(living, living.rotationPitch, living.rotationYaw, 0.0F, 1.25F, 0.0F);
			world.spawnEntity(bolt);
			world.playSound(null, living.getPosition(), DinocraftSoundEvents.HARP, SoundCategory.NEUTRAL, 0.3F, 0.5F);
			world.playSound(null, living.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			world.playSound(null, living.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
		}

		return super.onItemUseFinish(stack, world, living);
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		player.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}