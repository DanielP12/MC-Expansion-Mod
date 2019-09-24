package dinocraft.worldgen;

import java.util.Random;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator 
{
	private static final WorldGenerator PEBBLES_BLOCK = new WorldGenMinable(DinocraftBlocks.PEBBLES_BLOCK.getDefaultState(), 9);
	private static final WorldGenerator SHEEPLITE_ORE = new WorldGenMinable(DinocraftBlocks.SHEEPLITE_ORE.getDefaultState(), 8);
	private static final WorldGenerator CHLOROPHYTE_ORE = new WorldGenMinable(DinocraftBlocks.CHLOROPHYTE_ORE.getDefaultState(), 8);
	private static final WorldGenerator RANIUM_ORE = new WorldGenMinable(DinocraftBlocks.RANIUM_ORE.getDefaultState(), 7);
	private static final WorldGenerator PEBBELONEUM_ORE = new WorldGenMinable(DinocraftBlocks.PEBBELONEUM_ORE.getDefaultState(), 5);

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
				this.runGenerator(PEBBLES_BLOCK, world, rand, chunkX, chunkZ, 18, 0, 64);
				this.runGenerator(SHEEPLITE_ORE, world, rand, chunkX, chunkZ, 1, 0, 16);
				this.runGenerator(CHLOROPHYTE_ORE, world, rand, chunkX, chunkZ, 1, 0, 18);
				this.runGenerator(RANIUM_ORE, world, rand, chunkX, chunkZ, 1, 0, 20);
				this.runGenerator(PEBBELONEUM_ORE, world, rand, chunkX, chunkZ, 1, 0, 20);
		}
	}
}