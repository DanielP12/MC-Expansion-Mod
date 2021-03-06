//package dinocraft.worldgen;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldType;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.IChunkGenerator;
//import net.minecraft.world.gen.feature.WorldGenerator;
//import net.minecraftforge.fml.common.IWorldGenerator;
//import scala.actors.threadpool.Arrays;
//
//public class WorldGenStructures implements IWorldGenerator
//{
//	public static final WorldGenStructure UNDERGROUND_HOUSE = new WorldGenStructure("underground_house");
//
//	@Override
//	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
//	{
//		switch (world.provider.getDimension())
//		{
//			case 0:
//				this.generateStructure(UNDERGROUND_HOUSE, world, rand, chunkX, chunkZ, 15, Blocks.GRASS, Biome.class);
//				break;
//		}
//	}
//
//	private void generateStructure(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
//	{
//		ArrayList<Class<?>> classesList = new ArrayList<>(Arrays.asList(classes));
//		int x = chunkX * 16 + rand.nextInt(16) + 8;
//		int z = chunkZ * 16 + rand.nextInt(16) + 8;
//		int y = calculateGenerationHeight(world, x, z, topBlock);
//		BlockPos pos = new BlockPos(x, y, z);
//		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
//
//		if (world.getWorldType() != WorldType.FLAT)
//		{
//			if (/*classesList.contains(biome) && */rand.nextInt(chance) == 0)
//			{
//				generator.generate(world, rand, pos);
//			}
//		}
//	}
//
//	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
//	{
//		int y = world.getHeight();
//		boolean foundGround = false;
//
//		while (!foundGround && y-- >= 0)
//		{
//			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
//			foundGround = block == topBlock;
//		}
//
//		return y;
//	}
//}
