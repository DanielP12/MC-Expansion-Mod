package dinocraft.init.archived;

//public class DinocraftBlocksOLD
//{
//	public static final List<Block> BLOCKS = new ArrayList<>();
//	public static final Block PEBBLE_BLOCK;
//	public static final Block PEBBLE_BRICKS;
//	public static final Block PEBBLE_PATH;
//	public static final BlockWall PEBBLE_WALL;
//	public static final BlockStairs PEBBLE_STAIRS;
//	public static final BlockPebbleHalfSlab PEBBLE_SLAB_HALF;
//	public static final BlockPebbleDoubleSlab PEBBLE_SLAB_DOUBLE;
//	public static final Block PEBBLES_BLOCK;
//	public static final Block DREADED_ORE;
//	public static final Block RANIUM_ORE;
//	public static final Block PEBLONEUM_ORE;
//	public static final Block PYRITE_ORE;
//	public static final Block MAGENTITE_ORE;
//	public static final Block OLITROPY_ORE;
//	public static final Block DRACOLITE_ORE;
//	public static final Block WADRONITE_ORE;
//	public static final Block ARRANIUM_ORE;
//	public static final BlockBush GOOSEBERRY_BUSH;
//	public static final Block SCATTERED_BONES;
//	public static final Block TUSK_TORCH;
//
//	static
//	{
//		PEBBLE_BLOCK = new BlockPebbleBlock("pebble_block");
//		PEBBLE_BRICKS = new BlockPebbleBricks("pebble_bricks");
//		PEBBLE_PATH = new BlockPebblePath("pebble_path");
//		PEBBLE_WALL = new BlockPebbleWall("pebble_wall");
//		PEBBLE_STAIRS = new BlockPebbleStairs("pebble_stairs");
//		PEBBLE_SLAB_HALF = new BlockPebbleHalfSlab("pebble_slab_half");
//		PEBBLE_SLAB_DOUBLE = new BlockPebbleDoubleSlab("pebble_slab_double");
//		PEBBLES_BLOCK = new BlockPebblesBlock("pebbles_block");
//		DREADED_ORE = new BlockDreadedOre("dreaded_ore");
//		RANIUM_ORE = new BlockRaniumOre("ranium_ore");
//		PEBLONEUM_ORE = new BlockPebloneumOre("pebloneum_ore");
//		PYRITE_ORE = new BlockPyriteOre("pyrite_ore");
//		MAGENTITE_ORE = new BlockMagentiteOre("magentite_ore");
//		OLITROPY_ORE = new BlockOlitropyOre("olitropy_ore");
//		DRACOLITE_ORE = new BlockDracoliteOre("dracolite_ore");
//		WADRONITE_ORE = new BlockWadroniteOre("wadronite_ore");
//		ARRANIUM_ORE = new BlockArraniumOre("arranium_ore");
//		GOOSEBERRY_BUSH = new BlockGooseberryBush("gooseberry_bush");
//		SCATTERED_BONES = new BlockScatteredBones("scattered_bones");
//		TUSK_TORCH = new BlockTuskTorch("tusk_torch");
//	}
//
//
//	public static void register()
//	{
//		registerBlock(PEBBLE_BLOCK).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBBLE_BRICKS).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBBLE_PATH).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBBLE_WALL).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBBLE_STAIRS).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBBLE_SLAB_HALF, new ItemSlab(PEBBLE_SLAB_HALF, PEBBLE_SLAB_HALF, PEBBLE_SLAB_DOUBLE)).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		ForgeRegistries.BLOCKS.register(PEBBLE_SLAB_DOUBLE);
//		registerBlock(PEBBLES_BLOCK).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(DREADED_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(RANIUM_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PEBLONEUM_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(PYRITE_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(MAGENTITE_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(OLITROPY_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(DRACOLITE_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(WADRONITE_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(ARRANIUM_ORE).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(GOOSEBERRY_BUSH);
//		registerBlock(SCATTERED_BONES).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		registerBlock(TUSK_TORCH).setCreativeTab(Dinocraft.CreativeTabs.BLOCKS);
//		Dinocraft.log().info("Registered all blocks");
//	}
//
//	public static void registerRenders()
//	{
//		for (Block block : BLOCKS)
//		{
//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
//		}
//	}
//
//	public static Block registerBlock(Block block)
//	{
//		BLOCKS.add(block);
//		ForgeRegistries.BLOCKS.register(block);
//		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
//		return block;
//	}
//
//	public static Block registerBlock(Block block, ItemBlock itemblock)
//	{
//		BLOCKS.add(block);
//		ForgeRegistries.BLOCKS.register(block);
//		ForgeRegistries.ITEMS.register(itemblock.setRegistryName(block.getRegistryName()));
//		return block;
//	}
//}
