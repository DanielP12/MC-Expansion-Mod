package dinocraft.handlers;

import dinocraft.Reference;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import dinocraft.util.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DinocraftRecipes
{
	public static void registerFurnaceRecipes()
	{
		GameRegistry.addSmelting(DinocraftBlocks.SHEEPLITE_ORE, new ItemStack(DinocraftItems.SHEEPLITE_INGOT), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.CHLOROPHYTE_ORE, new ItemStack(DinocraftItems.CHLOROPHYTE_INGOT), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.RANIUM_ORE, new ItemStack(DinocraftItems.RANIUM), 0.7F);
		Utils.getLogger().info("Registered furnace recipes");
	}
	
	@EventBusSubscriber
	public static class RegistrationHandler
	{	
		@SubscribeEvent
		public static void registerRecipes(Register<IRecipe> event)
		{
			NonNullList<Ingredient> ingredients = null;
 
			if (DinocraftItems.TUSK.getRegistryName() != null)
			{
	            Ingredient TUSK = Ingredient.fromItem(DinocraftItems.TUSK);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		TUSK
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 1, ingredients, new ItemStack(Items.DYE, 2, 15)).setRegistryName(Items.DYE.getRegistryName()));
	        }
			
			if (DinocraftItems.LEAFY_BOOTS.getRegistryName() != null)
			{
	            Ingredient LEAF = Ingredient.fromItem(DinocraftItems.LEAF);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		LEAF, Ingredient.EMPTY, LEAF, 
	            		LEAF, Ingredient.EMPTY, LEAF
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.LEAFY_BOOTS)).setRegistryName(DinocraftItems.LEAFY_BOOTS.getRegistryName()));
	        }
 
			if (DinocraftBlocks.CRACKED_PEBBLE_BRICKS.getRegistryName() != null) 
			{
				Ingredient CRACKED_PEBBLES = Ingredient.fromItem(DinocraftItems.CRACKED_PEBBLES);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CRACKED_PEBBLES, CRACKED_PEBBLES, CRACKED_PEBBLES,
						CRACKED_PEBBLES, CRACKED_PEBBLES, CRACKED_PEBBLES,
						CRACKED_PEBBLES, CRACKED_PEBBLES, CRACKED_PEBBLES
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftBlocks.CRACKED_PEBBLE_BRICKS, 4)).setRegistryName(DinocraftBlocks.CRACKED_PEBBLE_BRICKS.getRegistryName()));
			}
 
			if (DinocraftItems.RAY_BULLET.getRegistryName() != null) 
			{
	            Ingredient GUNPOWDER = Ingredient.fromItem(Items.GUNPOWDER);
	            Ingredient IRON_NUGGET = Ingredient.fromItem(Items.IRON_NUGGET);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		Ingredient.EMPTY, IRON_NUGGET, Ingredient.EMPTY,
	            		IRON_NUGGET, 	  GUNPOWDER,   IRON_NUGGET,
	            		Ingredient.EMPTY, IRON_NUGGET, Ingredient.EMPTY
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.RAY_BULLET, 6)).setRegistryName(DinocraftItems.RAY_BULLET.getRegistryName()));
	        }
			
			if (DinocraftItems.POTATOSHROOM_PIE.getRegistryName() != null)
			{
			    Ingredient SUGAR = Ingredient.fromItem(Items.SUGAR);
			    Ingredient EGG = Ingredient.fromItem(Items.EGG);
			    Ingredient BROWN_MUSHROOM = Ingredient.fromItem(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM));
			    Ingredient BAKED_POTATO = Ingredient.fromItem(Items.BAKED_POTATO);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
			    		BROWN_MUSHROOM, BROWN_MUSHROOM, BROWN_MUSHROOM,
			    		SUGAR, 			BAKED_POTATO, 	EGG
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.POTATOSHROOM_PIE)).setRegistryName(DinocraftItems.POTATOSHROOM_PIE.getRegistryName()));
			}
			
			if (DinocraftItems.SEED_PIPE.getRegistryName() != null)
			{
			    Ingredient SUGAR_CANE = Ingredient.fromItem(Items.REEDS);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
			    		Ingredient.EMPTY, Ingredient.EMPTY, SUGAR_CANE,
			    		Ingredient.EMPTY, SUGAR_CANE, 		Ingredient.EMPTY,
			    		SUGAR_CANE, 	  Ingredient.EMPTY,	Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.SEED_PIPE)).setRegistryName(DinocraftItems.SEED_PIPE.getRegistryName()));
			}
 
			if (DinocraftItems.BLEVENT_BOOTS.getRegistryName() != null)
			{
			    Ingredient BLEVENT_INGOT = Ingredient.fromItem(DinocraftItems.BLEVENT_INGOT);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
			    		BLEVENT_INGOT, Ingredient.EMPTY, BLEVENT_INGOT,
			    		BLEVENT_INGOT, Ingredient.EMPTY, BLEVENT_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.BLEVENT_BOOTS)).setRegistryName(DinocraftItems.BLEVENT_BOOTS.getRegistryName()));
			}
 
			if (DinocraftItems.TUSKERERS_SWORD.getRegistryName() != null)
			{
	            Ingredient TUSKERERS_GEMSTONE = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEMSTONE);
	            Ingredient TUSK = Ingredient.fromItem(DinocraftItems.TUSK);
	            Ingredient STICK = Ingredient.fromItem(Items.STICK);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		Ingredient.EMPTY, Ingredient.EMPTY,   TUSK, 
	            		Ingredient.EMPTY, TUSKERERS_GEMSTONE, Ingredient.EMPTY, 
	            		STICK, 			  Ingredient.EMPTY,   Ingredient.EMPTY
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.TUSKERERS_SWORD)).setRegistryName(DinocraftItems.TUSKERERS_SWORD.getRegistryName()));
	        }
			
			if (DinocraftItems.TUSKERERS_CHARM.getRegistryName() != null)
			{
	            Ingredient TUSKERERS_GEMSTONE = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEMSTONE);
	            Ingredient REDSTONE_DUST = Ingredient.fromItem(Items.REDSTONE);
	            Ingredient GOLD_INGOT = Ingredient.fromItem(Items.GOLD_INGOT);
	            Ingredient DIAMOND = Ingredient.fromItem(Items.DIAMOND);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		Ingredient.EMPTY, 	REDSTONE_DUST, Ingredient.EMPTY, 
	            		Ingredient.EMPTY,	GOLD_INGOT,	   Ingredient.EMPTY, 
	            		TUSKERERS_GEMSTONE, DIAMOND,	   TUSKERERS_GEMSTONE
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.TUSKERERS_CHARM)).setRegistryName(DinocraftItems.TUSKERERS_CHARM.getRegistryName()));
	        }
			
			if (DinocraftBlocks.PEBBLE_BLOCK.getRegistryName() != null)
			{
			    Ingredient PEBBLES = Ingredient.fromItem(DinocraftItems.PEBBLES);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						PEBBLES, PEBBLES,
						PEBBLES, PEBBLES
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftBlocks.PEBBLE_BLOCK, 2)).setRegistryName(DinocraftBlocks.PEBBLE_BLOCK.getRegistryName()));
			}
 
            if (DinocraftBlocks.PEBBLE_BRICKS.getRegistryName() != null)
			{
			    Ingredient PEBBLE_BLOCK = Ingredient.fromItem(Item.getItemFromBlock(DinocraftBlocks.PEBBLE_BLOCK));
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						PEBBLE_BLOCK, PEBBLE_BLOCK,
						PEBBLE_BLOCK, PEBBLE_BLOCK
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftBlocks.PEBBLE_BRICKS, 4)).setRegistryName(DinocraftBlocks.PEBBLE_BRICKS.getRegistryName()));
			}
 
            if (DinocraftItems.VINE_BALL.getRegistryName() != null)
			{
			    Ingredient VINE = Ingredient.fromItem(Item.getItemFromBlock(Blocks.VINE));
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						VINE, VINE, VINE,
						VINE, VINE, VINE,
						VINE, VINE, VINE
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.VINE_BALL)).setRegistryName(DinocraftItems.VINE_BALL.getRegistryName()));
			}
            
            if (DinocraftItems.TUSKERERS_OLD_RAGS.getRegistryName() != null)
			{
			    Ingredient LEATHER = Ingredient.fromItem(Items.LEATHER);
			    Ingredient SHEEPLITE_INGOT = Ingredient.fromItem(DinocraftItems.SHEEPLITE_INGOT);
			    Ingredient TUSKERERS_GEMSTONE = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEMSTONE);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						LEATHER, 		 TUSKERERS_GEMSTONE, LEATHER,
						SHEEPLITE_INGOT, Ingredient.EMPTY,   SHEEPLITE_INGOT,
						SHEEPLITE_INGOT, Ingredient.EMPTY,   SHEEPLITE_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.TUSKERERS_OLD_RAGS)).setRegistryName(DinocraftItems.TUSKERERS_OLD_RAGS.getRegistryName()));
			}
            
            if (DinocraftItems.FEATHERY_UNDERWEAR.getRegistryName() != null)
			{
			    Ingredient FEATHER = Ingredient.fromItem(Items.FEATHER);
			    Ingredient LEAF = Ingredient.fromItem(DinocraftItems.LEAF);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						FEATHER,		  LEAF,    FEATHER,
						Ingredient.EMPTY, FEATHER, Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.FEATHERY_UNDERWEAR)).setRegistryName(DinocraftItems.FEATHERY_UNDERWEAR.getRegistryName()));
			}
 
			if (DinocraftItems.VINE_BALL_BUNDLE.getRegistryName() != null)
			{
			    Ingredient VINE_BALL = Ingredient.fromItem(DinocraftItems.VINE_BALL);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						VINE_BALL, VINE_BALL,
						VINE_BALL, VINE_BALL
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftItems.VINE_BALL_BUNDLE)).setRegistryName(DinocraftItems.VINE_BALL_BUNDLE.getRegistryName()));
			}
			
			if (DinocraftItems.LEAFERANG.getRegistryName() != null)
			{
			    Ingredient TUSK = Ingredient.fromItem(DinocraftItems.TUSK);
			    Ingredient LEAF = Ingredient.fromItem(DinocraftItems.LEAF);
			    Ingredient VINE = Ingredient.fromItem(Item.getItemFromBlock(Blocks.VINE));
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						TUSK, LEAF, 			TUSK,
						VINE, Ingredient.EMPTY, VINE,
						TUSK, LEAF, 			TUSK
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.LEAFERANG)).setRegistryName(DinocraftItems.LEAFERANG.getRegistryName()));
			}
			
			if (DinocraftItems.SHEEPLITE_SWORD.getRegistryName() != null)
			{
			    Ingredient STICK = Ingredient.fromItem(Items.STICK);
			    Ingredient SHEEPLITE_INGOT = Ingredient.fromItem(DinocraftItems.SHEEPLITE_INGOT);
			    Ingredient BLEVENT_INGOT = Ingredient.fromItem(DinocraftItems.BLEVENT_INGOT);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						SHEEPLITE_INGOT,
						BLEVENT_INGOT,
						STICK
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftItems.SHEEPLITE_SWORD)).setRegistryName(DinocraftItems.SHEEPLITE_SWORD.getRegistryName()));
			}
			
			if (DinocraftItems.RANIUM_HELMET.getRegistryName() != null)
			{
				Ingredient RANIUM = Ingredient.fromItem(DinocraftItems.RANIUM);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						RANIUM, RANIUM, 		  RANIUM,
						RANIUM, Ingredient.EMPTY, RANIUM
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.RANIUM_HELMET)).setRegistryName(DinocraftItems.RANIUM_HELMET.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						RANIUM, Ingredient.EMPTY, RANIUM,
						RANIUM, RANIUM, 		  RANIUM,
						RANIUM, RANIUM, 		  RANIUM
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.RANIUM_CHESTPLATE)).setRegistryName(DinocraftItems.RANIUM_CHESTPLATE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						RANIUM, RANIUM, 		  RANIUM,
						RANIUM, Ingredient.EMPTY, RANIUM,
						RANIUM, Ingredient.EMPTY, RANIUM
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.RANIUM_LEGGINGS)).setRegistryName(DinocraftItems.RANIUM_LEGGINGS.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						RANIUM, Ingredient.EMPTY, RANIUM,
					    RANIUM, Ingredient.EMPTY, RANIUM
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.RANIUM_BOOTS)).setRegistryName(DinocraftItems.RANIUM_BOOTS.getRegistryName()));
			}
 
			if (DinocraftItems.CHLOROPHYTE_PICKAXE.getRegistryName() != null)
			{
				Ingredient CHLOROPHYTE_INGOT = Ingredient.fromItem(DinocraftItems.CHLOROPHYTE_INGOT);
				Ingredient STICK = Ingredient.fromItem(Items.STICK);

				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT,
						Ingredient.EMPTY,  STICK, 			  Ingredient.EMPTY,
						Ingredient.EMPTY,  STICK, 			  Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_PICKAXE)).setRegistryName(DinocraftItems.CHLOROPHYTE_PICKAXE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT,
						STICK,
						STICK
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_SHOVEL)).setRegistryName(DinocraftItems.CHLOROPHYTE_SHOVEL.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT,
						STICK, 			   Ingredient.EMPTY,
						STICK, 			   Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_HOE)).setRegistryName(DinocraftItems.CHLOROPHYTE_HOE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT,
					    CHLOROPHYTE_INGOT,
					    STICK
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_SWORD)).setRegistryName(DinocraftItems.CHLOROPHYTE_SWORD.getRegistryName()));
			}
			
			if (DinocraftItems.CHLOROPHYTE_HELMET.getRegistryName() != null)
			{
				Ingredient CHLOROPHYTE_INGOT = Ingredient.fromItem(DinocraftItems.CHLOROPHYTE_INGOT);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT,
						CHLOROPHYTE_INGOT, Ingredient.EMPTY,  CHLOROPHYTE_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_HELMET)).setRegistryName(DinocraftItems.CHLOROPHYTE_HELMET.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, Ingredient.EMPTY,  CHLOROPHYTE_INGOT,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_CHESTPLATE)).setRegistryName(DinocraftItems.CHLOROPHYTE_CHESTPLATE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT, CHLOROPHYTE_INGOT,
						CHLOROPHYTE_INGOT, Ingredient.EMPTY,  CHLOROPHYTE_INGOT,
						CHLOROPHYTE_INGOT, Ingredient.EMPTY,  CHLOROPHYTE_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_LEGGINGS)).setRegistryName(DinocraftItems.CHLOROPHYTE_LEGGINGS.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						CHLOROPHYTE_INGOT, Ingredient.EMPTY, CHLOROPHYTE_INGOT,
					    CHLOROPHYTE_INGOT, Ingredient.EMPTY, CHLOROPHYTE_INGOT
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.CHLOROPHYTE_BOOTS)).setRegistryName(DinocraftItems.CHLOROPHYTE_BOOTS.getRegistryName()));
			}
			
			Utils.getLogger().info("Registered crafting recipes");
		}
	}
}
