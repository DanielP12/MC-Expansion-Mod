package dinocraft.init;

import java.util.ArrayList;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.block.BlockChlorophyteOre;
import dinocraft.block.BlockCrackedPebbleBricks;
import dinocraft.block.BlockPebbeloneumOre;
import dinocraft.block.BlockPebbleBlock;
import dinocraft.block.BlockPebbleBricks;
import dinocraft.block.BlockPebblesBlock;
import dinocraft.block.BlockRaniumOre;
import dinocraft.block.BlockSheepliteOre;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftBlocks 
{
	public static final ArrayList<Block> BLOCKS = new ArrayList<Block>();
	public static final Block PEBBLE_BLOCK;
	public static final Block PEBBLE_BRICKS;
	public static final Block CRACKED_PEBBLE_BRICKS;
	public static final Block PEBBLES_BLOCK;
	public static final Block SHEEPLITE_ORE;
	public static final Block CHLOROPHYTE_ORE;
	public static final Block RANIUM_ORE;
	public static final Block PEBBELONEUM_ORE;

	static
	{
		PEBBLE_BLOCK = new BlockPebbleBlock("pebble_block");
		PEBBLE_BRICKS = new BlockPebbleBricks("pebble_bricks");
		CRACKED_PEBBLE_BRICKS = new BlockCrackedPebbleBricks("cracked_pebble_bricks");
		PEBBLES_BLOCK = new BlockPebblesBlock("pebbles_block");
		SHEEPLITE_ORE = new BlockSheepliteOre("sheeplite_ore");
		CHLOROPHYTE_ORE = new BlockChlorophyteOre("chlorophyte_ore");
		RANIUM_ORE = new BlockRaniumOre("ranium_ore");
		PEBBELONEUM_ORE = new BlockPebbeloneumOre("pebbeloneum_ore");
	}
	
	
	public static void register() 
	{
		registerBlock(PEBBLE_BLOCK);
		registerBlock(PEBBLE_BRICKS);
		registerBlock(CRACKED_PEBBLE_BRICKS);
		registerBlock(PEBBLES_BLOCK);
		registerBlock(SHEEPLITE_ORE);
		registerBlock(CHLOROPHYTE_ORE);
		registerBlock(RANIUM_ORE);
		registerBlock(PEBBELONEUM_ORE);
	}
	
	public static void registerRenders() 
	{
		for (Block block : BLOCKS)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
		}
	}
	
	public static void registerBlock(Block block) 
	{
		BLOCKS.add(block);
		block.setCreativeTab(Dinocraft.BLOCKS);
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
}