package dinocraft.worldgen;

import java.util.Random;

import javax.annotation.Nullable;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BushGen implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		final int x = chunkX * 16 + 8;
		final int z = chunkZ * 16 + 8;
		final Biome biome = world.getBiomeForCoordsBody(new BlockPos(x, 62, z));

		if (BiomeDictionary.hasType(biome, Type.DRY) || BiomeDictionary.hasType(biome, Type.COLD)
				|| BiomeDictionary.hasType(biome, Type.OCEAN) || BiomeDictionary.hasType(biome, Type.DEAD))
		{
			return;
		}
		
		this.generateGarden(DinocraftBlocks.GOOSEBERRY_BUSH, world, random, x, z);
	}
	
	private void generateGarden(BlockBush bush, World world, Random random, int x, int z)
	{
		if (random.nextInt(64) == 0)
		{
			for (int i = 0; i < 4; i++)
			{
				int posX = x + random.nextInt(16);
				int posZ = z + random.nextInt(16);
				BlockPos newPos = getGroundPos(world, posX, posZ);
				
				if (newPos != null)
				{
					BlockPos under = newPos.down();
					
					if (world.getBlockState(under) == Blocks.GRASS.getDefaultState())
					{
						world.setBlockState(under, Blocks.FARMLAND.getDefaultState());

						if (bush.canPlaceBlockAt(world, newPos))
						{
							world.setBlockState(newPos, bush.getDefaultState(), 2);
						}
					}
				}
				
				//				if (newPos != null && bush.canPlaceBlockAt(world, newPos))
				//				{
				//					world.setBlockState(newPos, bush.getDefaultState(), 2);
				//				}
			}

			for (int i = 0; i < 16; i++)
			{
				int posX = x + random.nextInt(16);
				int posZ = z + random.nextInt(16);
				BlockPos newPos = getGroundPos(world, posX, posZ);

				if (newPos != null && world.getBlockState(newPos) != bush.getDefaultState())
				{
					BlockPos down = newPos.down();
					
					if (!world.isAirBlock(down.east()) && (world.isBlockFullCube(down.east()) || world.getBlockState(down.east()) == Blocks.WATER.getDefaultState()) && !world.isAirBlock(down.west()) && (world.isBlockFullCube(down.west()) || world.getBlockState(down.west()) == Blocks.WATER.getDefaultState()) && !world.isAirBlock(down.south()) && (world.isBlockFullCube(down.south()) || world.getBlockState(down.south()) == Blocks.WATER.getDefaultState()) && !world.isAirBlock(down.north()) && (world.isBlockFullCube(down.north()) || world.getBlockState(down.north()) == Blocks.WATER.getDefaultState()))
					{
						world.setBlockState(down, Blocks.WATER.getDefaultState());
					}
				}
			}
		}
	}

	@Nullable
	public static BlockPos getGroundPos(World world, int x, int z)
	{
		BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));

		if (topPos.getY() == 0)
		{
			return null;
		}
		
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);
		IBlockState blockState = world.getBlockState(pos);

		while (isTreeBlock(blockState, world, pos) || canReplace(blockState, world, pos))
		{
			pos.move(EnumFacing.DOWN);

			if (pos.getY() <= 0)
			{
				return null;
			}

			blockState = world.getBlockState(pos);
		}
		
		return pos.up();
	}
	
	public static boolean isTreeBlock(IBlockState state, World world, BlockPos pos)
	{
		Block block = state.getBlock();
		return block.isLeaves(state, world, pos) || block.isWood(world, pos);
	}

	public static boolean canReplace(IBlockState state, World world, BlockPos pos)
	{
		return state.getBlock().isReplaceable(world, pos) && !state.getMaterial().isLiquid();
	}
}