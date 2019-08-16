package dinocraft.init;

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
import dinocraft.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftBlocks 
{
	//Decoration
	public static final BlockPebbleBlock PEBBLE_BLOCK = new BlockPebbleBlock("pebble_block", "pebble_block");
	public static final BlockPebbleBricks PEBBLE_BRICKS = new BlockPebbleBricks("pebble_bricks", "pebble_bricks");
	public static final BlockCrackedPebbleBricks CRACKED_PEBBLE_BRICKS = new BlockCrackedPebbleBricks("cracked_pebble_bricks", "cracked_pebble_bricks");

	//Generated
	public static final BlockPebblesBlock PEBBLES_BLOCK = new BlockPebblesBlock("pebbles_block", "pebbles_block");
	public static final BlockSheepliteOre SHEEPLITE_ORE = new BlockSheepliteOre("sheeplite_ore", "sheeplite_ore");
	public static final BlockChlorophyteOre CHLOROPHYTE_ORE = new BlockChlorophyteOre("chlorophyte_ore", "chlorophyte_ore");
	public static final BlockRaniumOre RANIUM_ORE = new BlockRaniumOre("ranium_ore", "ranium_ore");
	public static final BlockPebbeloneumOre PEBBELONEUM_ORE = new BlockPebbeloneumOre("pebbeloneum_ore", "pebbeloneum_ore");
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		
	}
	
	public static void register() 
	{
		//Decoration
		registerBlock(PEBBLE_BLOCK);
		registerBlock(PEBBLE_BRICKS);
		registerBlock(CRACKED_PEBBLE_BRICKS);
		//Generated
		registerBlock(PEBBLES_BLOCK);
		registerBlock(SHEEPLITE_ORE);
		registerBlock(CHLOROPHYTE_ORE);
		registerBlock(RANIUM_ORE);
		registerBlock(PEBBELONEUM_ORE);
	}
	
	public static void registerRenders() 
	{
		//Decoration
		registerRender(PEBBLE_BLOCK);
		registerRender(PEBBLE_BRICKS);
		registerRender(CRACKED_PEBBLE_BRICKS);
		//Generated
		registerRender(PEBBLES_BLOCK);
		registerRender(SHEEPLITE_ORE);
		registerRender(CHLOROPHYTE_ORE);
		registerRender(RANIUM_ORE);
		registerRender(PEBBELONEUM_ORE);
	}
	
	public static void registerBlock(Block block) 
	{
		block.setCreativeTab(Dinocraft.BLOCKS);
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		Utils.getLogger().info("Registered block: " + block.getUnlocalizedName().substring(5));
	}
	
	public static void registerRender(Block block) 
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Registered render for " + block.getUnlocalizedName().substring(5));
	}
	
}
