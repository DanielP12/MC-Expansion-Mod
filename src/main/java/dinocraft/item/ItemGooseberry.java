package dinocraft.item;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemGooseberry extends ItemFood implements IPlantable
{
	public ItemGooseberry()
	{
		super(3, 3.0F, false);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = world.getBlockState(pos);
		
		if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock() == Blocks.FARMLAND && world.isAirBlock(pos.up()))
		{
			world.setBlockState(pos.up(), DinocraftBlocks.GOOSEBERRY_BUSH.getDefaultState());

			if (!player.isCreative() && !world.isRemote)
			{
				stack.shrink(1);
			}
			
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return DinocraftBlocks.GOOSEBERRY_BUSH.getDefaultState();
	}
}
