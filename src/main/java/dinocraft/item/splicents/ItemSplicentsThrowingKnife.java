package dinocraft.item.splicents;

import dinocraft.entity.EntitySplicentsThrowingKnife;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemSplicentsThrowingKnife extends Item
{
	public ItemSplicentsThrowingKnife()
	{
		this.setMaxStackSize(16);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (!player.capabilities.isCreativeMode)
		{
			stack.shrink(1);
		}
		
		world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.9F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote)
		{
			EntitySplicentsThrowingKnife knife = new EntitySplicentsThrowingKnife(player, 0.025F);
			knife.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, itemRand.nextFloat() * 0.5F + 1.5F, 0.5F);
			world.spawnEntity(knife);
		}

		player.swingArm(hand);
		player.addStat(StatList.getObjectUseStats(this));
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}
