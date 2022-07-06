package dinocraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPebblePath extends Block
{
	private static final AxisAlignedBB PATH_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	
	public BlockPebblePath()
	{
		super(Material.CLAY);
		this.setHardness(0.25F);
		this.setResistance(0.5F);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		IBlockState under = world.getBlockState(pos.down());
		Block underneath = under.getBlock();

		try
		{
			return super.canPlaceBlockAt(world, pos) && underneath.canPlaceTorchOnTop(under, world, pos);// && underneath.isFullCube(under);
		}
		catch (Exception exception)
		{
			return false;
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor)
	{
		if (pos.down().equals(neighbor))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getFront(meta);

		if (facing.getAxis() == Axis.Y)
		{
			facing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation)
	{
		return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror)
	{
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PATH_BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return PATH_BOX;
	}
}