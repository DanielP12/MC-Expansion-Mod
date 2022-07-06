package dinocraft.block;

import java.util.Random;

import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

public class BlockGooseberryBush extends BlockBush implements IGrowable
{
	private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 6);
	private static final AxisAlignedBB[] GOOSEBERRY_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.4D, 0.0D, 0.4D, 0.6D, 0.35D, 0.6D),
			new AxisAlignedBB(0.35D, 0.0D, 0.35D, 0.65D, 0.5D, 0.65D),
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.7D, 0.75D),
			new AxisAlignedBB(0.225D, 0.0D, 0.225D, 0.775D, 0.8D, 0.775D),
			new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.9D, 0.8D),
			new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.9D, 0.8D)};

	public BlockGooseberryBush()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
		this.setTickRandomly(true);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return GOOSEBERRY_AABB[state.getValue(this.getAgeProperty())];
	}

	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return state.getBlock() == Blocks.FARMLAND;
	}

	protected PropertyInteger getAgeProperty()
	{
		return AGE;
	}

	public int getMaxAge()
	{
		return 6;
	}

	protected int getAge(IBlockState state)
	{
		return state.getValue(this.getAgeProperty());
	}

	public IBlockState withAge(int age)
	{
		return this.getDefaultState().withProperty(this.getAgeProperty(), age);
	}

	public boolean isMaxAge(IBlockState state)
	{
		return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);

		if (!world.isAreaLoaded(pos, 1))
		{
			return;
		}
		
		if (world.getLightFromNeighbors(pos.up()) >= 9)
		{
			int i = this.getAge(state);

			if (i < this.getMaxAge())
			{
				float f = getGrowthChance(this, world, pos);

				if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0))
				{
					world.setBlockState(pos, this.withAge(i + 1), 2);
					ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
				}
			}
		}
	}

	public void grow(World world, BlockPos pos, IBlockState state)
	{
		int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
		int j = this.getMaxAge();

		if (i > j)
		{
			i = j;
		}

		world.setBlockState(pos, this.withAge(i), 2);
	}

	protected int getBonemealAgeIncrease(World world)
	{
		return MathHelper.getInt(world.rand, 2, 5);
	}

	protected static float getGrowthChance(Block block, World world, BlockPos pos)
	{
		float f = 1.0F;
		BlockPos blockpos = pos.down();

		for (int i = -1; i <= 1; ++i)
		{
			for (int j = -1; j <= 1; ++j)
			{
				float f1 = 0.0F;
				IBlockState iblockstate = world.getBlockState(blockpos.add(i, 0, j));

				if (iblockstate.getBlock().canSustainPlant(iblockstate, world, blockpos.add(i, 0, j), EnumFacing.UP, (IPlantable) block))
				{
					f1 = 1.0F;

					if (iblockstate.getBlock().isFertile(world, blockpos.add(i, 0, j)))
					{
						f1 = 3.0F;
					}
				}

				if (i != 0 || j != 0)
				{
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos blockpos1 = pos.north();
		BlockPos blockpos2 = pos.south();
		BlockPos blockpos3 = pos.west();
		BlockPos blockpos4 = pos.east();
		boolean flag = block == world.getBlockState(blockpos3).getBlock() || block == world.getBlockState(blockpos4).getBlock();
		boolean flag1 = block == world.getBlockState(blockpos1).getBlock() || block == world.getBlockState(blockpos2).getBlock();

		if (flag && flag1)
		{
			f /= 2.0F;
		}
		else
		{
			boolean flag2 = block == world.getBlockState(blockpos3.north()).getBlock() || block == world.getBlockState(blockpos4.north()).getBlock() || block == world.getBlockState(blockpos4.south()).getBlock() || block == world.getBlockState(blockpos3.south()).getBlock();

			if (flag2)
			{
				f /= 2.0F;
			}
		}

		return f;
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		IBlockState soil = world.getBlockState(pos.down());
		return (world.getLight(pos) >= 8 || world.canSeeSky(pos)) && this.canSustainBush(soil);
	}

	protected Item getSeed()
	{
		return DinocraftItems.GOOSEBERRY;
	}

	protected Item getCrop()
	{
		return DinocraftItems.GOOSEBERRY;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		super.getDrops(drops, world, pos, state, 0);
		int age = this.getAge(state);
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		if (age >= this.getMaxAge())
		{
			for (int i = 0; i < 2 + fortune; ++i)
			{
				if (rand.nextInt(2 * this.getMaxAge()) <= age)
				{
					drops.add(new ItemStack(this.getSeed(), 1, 0));
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this.getSeed());
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return !this.isMaxAge(state);
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		this.grow(world, pos, state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.withAge(meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return this.getAge(state);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {AGE});
	}
}