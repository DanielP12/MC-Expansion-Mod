package dinocraft.item;

import dinocraft.Reference;
import dinocraft.entity.EntityVineBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemVineBallBundle extends Item
{
	public ItemVineBallBundle(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);	
		if (!playerIn.isCreative()) stack.shrink(1);

		if (!worldIn.isRemote)
		{
			for (int i = 0; i < 6; ++i)
			{
				EntityVineBall ball = new EntityVineBall(playerIn, 0.1F);
				ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 1.0F, 10F);
				worldIn.spawnEntity(ball);
			}
		}
		
		worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 1F, 0.05F);
		playerIn.addStat(StatList.getObjectUseStats(this));	
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
}