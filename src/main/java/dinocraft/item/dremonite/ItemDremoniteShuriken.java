package dinocraft.item.dremonite;

import dinocraft.entity.EntityDremoniteShuriken;
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

public class ItemDremoniteShuriken extends Item
{
	public ItemDremoniteShuriken()
	{
		this.setMaxStackSize(3);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 1.5F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote)
		{
			EntityDremoniteShuriken shuriken = new EntityDremoniteShuriken(world, player, stack, 0.025F, 3.0F);
			shuriken.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 0.5F);
			world.spawnEntity(shuriken);
		}
		
		if (!player.capabilities.isCreativeMode)
		{
			stack.shrink(1);
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
