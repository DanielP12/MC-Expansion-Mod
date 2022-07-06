package dinocraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTuskTorch extends Block
{
	protected static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.35D, 0.19D, 0.64D, 0.65D, 0.81D, 1.0D);
	protected static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.35D, 0.19D, 0.0D, 0.65D, 0.81D, 0.36D);
	protected static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.64D, 0.19D, 0.35D, 1.0D, 0.81D, 0.65D);
	protected static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, 0.19D, 0.35D, 0.36D, 0.81D, 0.65D);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", (EnumFacing facing) -> facing != EnumFacing.DOWN && facing != EnumFacing.UP);

	public BlockTuskTorch()
	{
		super(Material.CIRCUITS);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setTickRandomly(true);
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return 12;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		double x = pos.getX() + 0.5D;
		double y = pos.getY() + 0.675D;
		double z = pos.getZ() + 0.5D;

		if (rand.nextInt(20) == 0)
		{
			world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.75F + 0.5F, false);
		}
		
		EnumFacing facing = state.getValue(FACING).getOpposite();
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.27D * facing.getFrontOffsetX(), y + 0.22D, z + 0.27D * facing.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.FLAME, x + 0.27D * facing.getFrontOffsetX(), y + 0.22D, z + 0.27D * facing.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
		
		if (rand.nextInt(4) == 0)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + 0.27D * facing.getFrontOffsetX(), y + 0.22D, z + 0.27D * facing.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	private boolean canPlaceOn(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().canPlaceTorchOnTop(state, world, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		for (EnumFacing facing : FACING.getAllowedValues())
		{
			if (this.canPlaceAt(world, pos, facing))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean canPlaceAt(World world, BlockPos pos, EnumFacing facing)
	{
		BlockPos blockpos = pos.offset(facing.getOpposite());
		IBlockState state = world.getBlockState(blockpos);
		Block block = state.getBlock();
		BlockFaceShape shape = state.getBlockFaceShape(world, blockpos, facing);
		
		if (facing.getAxis().isVertical())
		{
			return false;
		}

		if (this.canPlaceOn(world, blockpos))
		{
			return true;
		}
		
		return !isExceptBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (this.canPlaceAt(world, pos, facing))
		{
			return this.getDefaultState().withProperty(FACING, facing);
		}
		else
		{
			for (EnumFacing enumfacing : Plane.HORIZONTAL)
			{
				if (this.canPlaceAt(world, pos, enumfacing))
				{
					return this.getDefaultState().withProperty(FACING, enumfacing);
				}
			}
			
			return this.getDefaultState();
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		this.checkForDrop(world, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor)
	{
		this.onNeighborChangeInternal(world, pos, state);
	}
	
	protected boolean onNeighborChangeInternal(World world, BlockPos pos, IBlockState state)
	{
		if (!this.checkForDrop(world, pos, state))
		{
			return true;
		}
		else
		{
			EnumFacing facing = state.getValue(FACING);
			Axis axis = facing.getAxis();
			BlockPos blockpos = pos.offset(facing.getOpposite());
			boolean flag = false;
			
			if (axis.isHorizontal() && world.getBlockState(blockpos).getBlockFaceShape(world, blockpos, facing) != BlockFaceShape.SOLID)
			{
				flag = true;
			}
			else if (axis.isVertical() && !this.canPlaceOn(world, blockpos))
			{
				flag = true;
			}
			
			if (flag)
			{
				this.dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	protected boolean checkForDrop(World world, BlockPos pos, IBlockState state)
	{
		if (state.getBlock() == this && this.canPlaceAt(world, pos, state.getValue(FACING)))
		{
			return true;
		}
		else
		{
			if (world.getBlockState(pos).getBlock() == this)
			{
				this.dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
			}
			
			return false;
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
		IBlockState state = this.getDefaultState();
		
		switch (meta)
		{
			case 1:
				state = state.withProperty(FACING, EnumFacing.EAST);
				break;
			case 2:
				state = state.withProperty(FACING, EnumFacing.WEST);
				break;
			case 3:
				state = state.withProperty(FACING, EnumFacing.SOUTH);
				break;
			case 4:
				state = state.withProperty(FACING, EnumFacing.NORTH);
				break;
			case 5:
			default:
				state = state.withProperty(FACING, EnumFacing.NORTH);
		}
		
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		switch (state.getValue(FACING))
		{
			case EAST:
				i = i | 1;
				break;
			case WEST:
				i = i | 2;
				break;
			case SOUTH:
				i = i | 3;
				break;
			case NORTH:
				i = i | 4;
				break;
			case DOWN:
			case UP:
			default:
				i = i | 5;
		}

		return i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(FACING))
		{
			case EAST:
				return TORCH_EAST_AABB;
			case WEST:
				return TORCH_WEST_AABB;
			case SOUTH:
				return TORCH_SOUTH_AABB;
			case NORTH:
				return TORCH_NORTH_AABB;
			default:
				return TORCH_NORTH_AABB;
		}
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return NULL_AABB;
	}
}