//package dinocraft.worldgen;
//
//import java.util.Random;
//
//import dinocraft.Dinocraft;
//import dinocraft.util.IStructure;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.WorldGenerator;
//import net.minecraft.world.gen.structure.template.Template;
//
//public class WorldGenStructure extends WorldGenerator implements IStructure
//{
//	private static String STRUCTURE_NAME;
//
//	public WorldGenStructure(String name)
//	{
//		this.STRUCTURE_NAME = name;
//	}
//
//	@Override
//	public boolean generate(World world, Random rand, BlockPos pos)
//	{
//		this.generateStructure(world, pos);
//		return true;
//	}
//
//	public static void generateStructure(World world, BlockPos pos)
//	{
//		Template template = WORLD_SERVER.getStructureTemplateManager().get(world.getMinecraftServer(), new ResourceLocation(Dinocraft.MODID, STRUCTURE_NAME));
//
//		if (template != null)
//		{
//			IBlockState state = world.getBlockState(pos);
//			world.notifyBlockUpdate(pos, state, state, 3);
//			template.addBlocksToWorldChunk(world, pos, SETTINGS);
//		}
//	}
//}
