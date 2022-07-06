package dinocraft.worldgen;

import java.util.Random;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator
{
	private static final WorldGenerator PEBBLES_BLOCK = new WorldGenMinable(DinocraftBlocks.PEBBLES_BLOCK.getDefaultState(), 9);
	private static final WorldGenerator DREADED_ORE = new WorldGenMinable(DinocraftBlocks.DREMONITE_ORE.getDefaultState(), 1);
	//	private static final WorldGenerator FLARE_ORE = new WorldGenMinable(DinocraftBlocks.FLARE_ORE.getDefaultState(), 1);
	//	private static final WorldGenerator RANIUM_ORE = new WorldGenMinable(DinocraftBlocks.RANIUM_ORE.getDefaultState(), 7);
	private static final WorldGenerator PEBLONEUM_ORE = new WorldGenMinable(DinocraftBlocks.PEBLONEUM_ORE.getDefaultState(), 5);
	private static final WorldGenerator OLITROPY_ORE = new WorldGenMinable(DinocraftBlocks.OLITROPY_ORE.getDefaultState(), 6);
	private static final WorldGenerator DRACOLITE_ORE = new WorldGenMinable(DinocraftBlocks.DRACOLITE_ORE.getDefaultState(), 6);
	private static final WorldGenerator MARONITE_ORE = new WorldGenMinable(DinocraftBlocks.WADRONITE_ORE.getDefaultState(), 6);
	private static final WorldGenerator ARENIUM_ORE = new WorldGenMinable(DinocraftBlocks.ARRANIUM_ORE.getDefaultState(), 6);


	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight)
	{
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
		{
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		}
		
		int heightDiff = maxHeight - minHeight + 1;

		for (int i = 0; i < chancesToSpawn; ++i)
		{
			int x = chunk_X * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunk_Z * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.getDimension())
		{
			case 0: //Overworld
				Biome biome = world.getBiome(new BlockPos(chunkX * 16 + 8, 62, chunkZ * 16 + 8));
				
				if (biome == Biomes.SWAMPLAND || biome == Biomes.JUNGLE || biome == Biomes.JUNGLE_EDGE || biome == Biomes.JUNGLE_HILLS
						|| biome == Biomes.ROOFED_FOREST || biome == Biomes.REDWOOD_TAIGA || biome == Biomes.REDWOOD_TAIGA_HILLS)
				{
					this.runGenerator(ARENIUM_ORE, world, rand, chunkX, chunkZ, 1, 0, 24);
				}
				
				if (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.SAVANNA || biome == Biomes.SAVANNA_PLATEAU
						|| biome == Biomes.MESA || biome == Biomes.MESA_ROCK)
				{
					this.runGenerator(MARONITE_ORE, world, rand, chunkX, chunkZ, 1, 0, 24);
				}
				
				if (biome == Biomes.FOREST || biome == Biomes.FOREST_HILLS || biome == Biomes.BIRCH_FOREST || biome == Biomes.RIVER
						|| biome == Biomes.BIRCH_FOREST_HILLS || biome == Biomes.DEFAULT || biome == Biomes.PLAINS || biome == Biomes.BEACH
						|| biome == Biomes.DEEP_OCEAN || biome == Biomes.STONE_BEACH)
				{
					this.runGenerator(OLITROPY_ORE, world, rand, chunkX, chunkZ, 1, 0, 24);
				}
				
				if (biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.COLD_TAIGA || biome == Biomes.COLD_TAIGA_HILLS
						|| biome == Biomes.FROZEN_OCEAN || biome == Biomes.FROZEN_RIVER || biome == Biomes.COLD_BEACH
						|| biome == Biomes.ICE_MOUNTAINS || biome == Biomes.ICE_PLAINS)
				{
					this.runGenerator(DRACOLITE_ORE, world, rand, chunkX, chunkZ, 1, 0, 24);
				}
				
				this.runGenerator(PEBBLES_BLOCK, world, rand, chunkX, chunkZ, 18, 0, 64);
				this.runGenerator(DREADED_ORE, world, rand, chunkX, chunkZ, 1, 0, 16);
				//				this.runGenerator(FLARE_ORE, world, rand, chunkX, chunkZ, 1, 0, 16);
				//				this.runGenerator(RANIUM_ORE, world, rand, chunkX, chunkZ, 1, 0, 20);
				this.runGenerator(PEBLONEUM_ORE, world, rand, chunkX, chunkZ, 1, 0, 20);
		}
	}
}