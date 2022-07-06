package dinocraft.init;

import java.util.ArrayList;
import java.util.List;

import dinocraft.Dinocraft;
import dinocraft.block.BlockArraniumOre;
import dinocraft.block.BlockDracoliteOre;
import dinocraft.block.BlockDremoniteOre;
import dinocraft.block.BlockGooseberryBush;
import dinocraft.block.BlockLeafySpore;
import dinocraft.block.BlockMagentiteOre;
import dinocraft.block.BlockOlitropyOre;
import dinocraft.block.BlockPebbleBlock;
import dinocraft.block.BlockPebbleBricks;
import dinocraft.block.BlockPebbleDoubleSlab;
import dinocraft.block.BlockPebbleHalfSlab;
import dinocraft.block.BlockPebblePath;
import dinocraft.block.BlockPebbleStairs;
import dinocraft.block.BlockPebbleWall;
import dinocraft.block.BlockPebblesBlock;
import dinocraft.block.BlockPebloneumOre;
import dinocraft.block.BlockScatteredBones;
import dinocraft.block.BlockSplicentsOre;
import dinocraft.block.BlockTuskTorch;
import dinocraft.block.BlockWadroniteOre;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class DinocraftBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final Block PEBBLE_BLOCK = new BlockPebbleBlock();
	public static final BlockWall PEBBLE_WALL = new BlockPebbleWall();
	public static final BlockStairs PEBBLE_STAIRS = new BlockPebbleStairs();
	public static final BlockSlab PEBBLE_SLAB_HALF = new BlockPebbleHalfSlab();
	public static final BlockSlab PEBBLE_SLAB_DOUBLE = (BlockSlab) new BlockPebbleDoubleSlab().setUnlocalizedName("pebble_slab_double").setRegistryName(new ResourceLocation(Dinocraft.MODID, "pebble_slab_double"));
	public static final Block PEBBLE_PATH = new BlockPebblePath();
	public static final Block PEBBLE_BRICKS = new BlockPebbleBricks();
	public static final Block PEBBLES_BLOCK = new BlockPebblesBlock();
	public static final Block DREMONITE_ORE = new BlockDremoniteOre();
	//	public static final Block RANIUM_ORE = new BlockRaniumOre();
	public static final Block PEBLONEUM_ORE = new BlockPebloneumOre();
	//	public static final Block FLARE_ORE = new BlockFlareOre();
	public static final Block MAGENTITE_ORE = new BlockMagentiteOre();
	public static final Block OLITROPY_ORE = new BlockOlitropyOre();
	public static final Block DRACOLITE_ORE = new BlockDracoliteOre();
	public static final Block WADRONITE_ORE = new BlockWadroniteOre();
	public static final Block ARRANIUM_ORE = new BlockArraniumOre();
	public static final Block SPLICENTS_ORE = new BlockSplicentsOre();
	public static final BlockBush GOOSEBERRY_BUSH = new BlockGooseberryBush();
	public static final Block SCATTERED_BONES = new BlockScatteredBones();
	public static final Block TUSK_TORCH = new BlockTuskTorch();
	public static final Block LEAFY_SPORE = new BlockLeafySpore();

	@EventBusSubscriber
	public static class RegistrationHandler
	{
		public static Block getUnregisteredBlock(Block block, String name, CreativeTabs tab)
		{
			block.setUnlocalizedName(name);
			block.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
			block.setCreativeTab(tab);
			BLOCKS.add(block);
			return block;
		}
		
		public static Block getUnregisteredBlock(Block block, String name)
		{
			block.setUnlocalizedName(name);
			block.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
			BLOCKS.add(block);
			return block;
		}
		
		public static Item getUnregisteredItem(Item item, String name)
		{
			item.setUnlocalizedName(name);
			item.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
			return item;
		}
		
		@SubscribeEvent
		public static void onEvent(Register<Block> event)
		{
			IForgeRegistry<Block> registry = event.getRegistry();
			registry.register(getUnregisteredBlock(PEBBLE_BLOCK, "pebble_block", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBBLE_WALL, "pebble_wall", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBBLE_STAIRS, "pebble_stairs", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBBLE_SLAB_HALF, "pebble_slab_half", Dinocraft.CreativeTab.BLOCKS));
			registry.register(PEBBLE_SLAB_DOUBLE);
			registry.register(getUnregisteredBlock(PEBBLE_PATH, "pebble_path", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBBLE_BRICKS, "pebble_bricks", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBBLES_BLOCK, "pebbles_block", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(DREMONITE_ORE, "dremonite_ore", Dinocraft.CreativeTab.BLOCKS));
			//			registry.register(getUnregisteredBlock(RANIUM_ORE, "ranium_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(PEBLONEUM_ORE, "pebloneum_ore", Dinocraft.CreativeTab.BLOCKS));
			//			registry.register(getUnregisteredBlock(FLARE_ORE, "flare_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(MAGENTITE_ORE, "magentite_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(OLITROPY_ORE, "olitropy_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(DRACOLITE_ORE, "dracolite_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(WADRONITE_ORE, "wadronite_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(ARRANIUM_ORE, "arranium_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(SPLICENTS_ORE, "splicents_ore", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(GOOSEBERRY_BUSH, "gooseberry_bush", null));
			registry.register(getUnregisteredBlock(SCATTERED_BONES, "scattered_bones", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(TUSK_TORCH, "tusk_torch", Dinocraft.CreativeTab.BLOCKS));
			registry.register(getUnregisteredBlock(LEAFY_SPORE, "leafy_spore", Dinocraft.CreativeTab.BLOCKS));
			Dinocraft.LOGGER.info("Registered all blocks");
		}

		@SubscribeEvent
		public static void onRegister(Register<Item> event)
		{
			IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLE_BLOCK), "pebble_block"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLE_WALL), "pebble_wall"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLE_STAIRS), "pebble_stairs"));
			registry.register(getUnregisteredItem(new ItemSlab(PEBBLE_SLAB_HALF, PEBBLE_SLAB_HALF, PEBBLE_SLAB_DOUBLE), "pebble_slab_half"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLE_PATH), "pebble_path"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLE_BRICKS), "pebble_bricks"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBBLES_BLOCK), "pebbles_block"));
			registry.register(getUnregisteredItem(new ItemBlock(DREMONITE_ORE), "dremonite_ore"));
			//			registry.register(getUnregisteredItem(new ItemBlock(RANIUM_ORE), "ranium_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(PEBLONEUM_ORE), "pebloneum_ore"));
			//			registry.register(getUnregisteredItem(new ItemBlock(FLARE_ORE), "flare_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(OLITROPY_ORE), "olitropy_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(DRACOLITE_ORE), "dracolite_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(WADRONITE_ORE), "wadronite_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(ARRANIUM_ORE), "arranium_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(SPLICENTS_ORE), "splicents_ore"));
			registry.register(getUnregisteredItem(new ItemBlock(GOOSEBERRY_BUSH), "gooseberry_bush"));
			registry.register(getUnregisteredItem(new ItemBlock(SCATTERED_BONES), "scattered_bones"));
			registry.register(getUnregisteredItem(new ItemBlock(TUSK_TORCH), "tusk_torch"));
			registry.register(getUnregisteredItem(new ItemBlock(LEAFY_SPORE), "leafy_spore"));
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onModelRegistry(ModelRegistryEvent event)
		{
			for (Block block : BLOCKS)
			{
				registerBlockModel(block);
				registerItemBlockModel(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}

			Dinocraft.LOGGER.info("Registered all block models");
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block)
	{
		registerBlockModel(block, 0);
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block, int meta)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));

	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockModel(Item item)
	{
		registerItemBlockModel(item, 0);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockModel(Item item, int meta)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}