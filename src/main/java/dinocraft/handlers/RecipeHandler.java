package dinocraft.handlers;

import dinocraft.Reference;
import dinocraft.init.DinocraftArmour;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
import dinocraft.util.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler
{	
	public static void registerCraftingRecipes()
	{
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DinocraftItems.POTATOSHROOM_PIE), new Object[] {"R", "B", "P", 'R', Blocks.RED_MUSHROOM, 'B', Blocks.BROWN_MUSHROOM, 'P', Items.POTATO}));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DinocraftArmour.BLEVENT_BOOTS), new Object[] {"B B","B B",'B', DinocraftItems.BLEVENT_INGOT}));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DinocraftBlocks.PEBBLE_BLOCK, 2), new Object[] {"PP", "PP", 'P', DinocraftItems.PEBBLES}));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DinocraftBlocks.PEBBLE_BRICKS, 4), new Object[] {"PP", "PP", 'P', DinocraftBlocks.PEBBLE_BLOCK}));
//		GameRegistry.addRecipe(new ItemStack(DinocraftBlocks.CRACKED_PEBBLE_BRICKS, 4), new Object[] {"CCC", "CCC", "CCC", 'C', DinocraftItems.CRACKED_PEBBLES});
		
//		GameRegistry.addRecipe(new ItemStack(DinocraftItems.VINE_BALL, 1), new Object[] {"VVV", "VVV", "VVV", 'V', Blocks.VINE});
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DinocraftItems.VINE_BALL_BUNDLE, 1), new Object[] {"VV", "VV", 'V', DinocraftItems.VINE_BALL}));

		registerTools(DinocraftItems.CHLOROPHYTE_INGOT, DinocraftTools.CHLOROPHYTE_PICKAXE, DinocraftTools.CHLOROPHYTE_HOE, DinocraftTools.CHLOROPHYTE_SHOVEL, DinocraftTools.CHLOROPHYTE_SWORD);
		registerChlorophyteArmour(DinocraftItems.CHLOROPHYTE_INGOT, DinocraftArmour.CHLOROPHYTE_HELMET, DinocraftArmour.CHLOROPHYTE_CHESTPLATE, DinocraftArmour.CHLOROPHYTE_LEGGINGS, DinocraftArmour.CHLOROPHYTE_BOOTS);
		registerRaniumArmour(DinocraftItems.RANIUM, DinocraftArmour.RANIUM_HELMET, DinocraftArmour.RANIUM_CHESTPLATE, DinocraftArmour.RANIUM_LEGGINGS, DinocraftArmour.RANIUM_BOOTS);
		Utils.getLogger().info("Registered Crafting Recipes");
	}

	public static void registerFurnaceRecipes()
	{
		GameRegistry.addSmelting(DinocraftBlocks.SHEEPLITE_ORE, new ItemStack(DinocraftItems.SHEEPLITE_INGOT), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.CHLOROPHYTE_ORE, new ItemStack(DinocraftItems.CHLOROPHYTE_INGOT), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.RANIUM_ORE, new ItemStack(DinocraftItems.RANIUM), 0.7F);
		Utils.getLogger().info("Registered Furnace Recipes");
	}
	
	public static void registerTools(Item ingot, Item pickaxe, Item hoe, Item shovel, Item sword)
	{
		//GameRegistry.addShapedRecipe(null, null, new ItemStack(pickaxe), new Object[] {"III"," S "," S ", 'I', ingot, 'S', Items.STICK });
//		GameRegistry.addShapedRecipe(null, null, new ItemStack(hoe), new Object[] {"II "," S "," S ", 'I', ingot, 'S', Items.STICK });
//		GameRegistry.addShapedRecipe(null, null, new ItemStack(hoe), new Object[] {" II"," S "," S ", 'I', ingot, 'S', Items.STICK });
//		GameRegistry.addShapedRecipe(new ShapedOreRecipe(new ItemStack(shovel), new Object[] {"I", "S", "S", 'I', ingot, 'S', Items.STICK })); 
//		GameRegistry.addShapedRecipe(new ShapedOreRecipe(new ItemStack(sword), new Object[] {"I", "I", "S", 'I', ingot, 'S', Items.STICK })); //ShapedOreRecipe in this case means "I", "I", "S" together in any row and any column
	}
	
	public static void registerChlorophyteArmour(Item i, Item h, Item c, Item l, Item b) 
	{
//		GameRegistry.addRecipe(new ItemStack(h), new Object[] { "III","I I",'I',i});
//		GameRegistry.addRecipe(new ItemStack(c), new Object[] { "I I","III","III",'I',i});
//		GameRegistry.addRecipe(new ItemStack(l), new Object[] { "III","I I","I I",'I',i});
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(b), new Object[] { "I I","I I",'I',i}));
	}
	
	public static void registerRaniumArmour(Item i, Item h, Item c, Item l, Item b) 
	{
//		GameRegistry.addRecipe(new ItemStack(h), new Object[] { "III","I I",'I',i});
//		GameRegistry.addRecipe(new ItemStack(c), new Object[] { "I I","III","III",'I',i});
	//	GameRegistry.addRecipe(new ItemStack(l), new Object[] { "III","I I","I I",'I',i});
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(b), new Object[] { "I I","I I",'I',i}));
	}
	
	@EventBusSubscriber
	public static class RegistrationHandler
	{	
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<IRecipe> event)
		{	
			NonNullList<Ingredient> ingredients = null;
 
			if (DinocraftArmour.LEAFY_BOOTS.getRegistryName() != null)
			{
	            Ingredient L = Ingredient.fromItem(DinocraftItems.LEAF);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		L, Ingredient.EMPTY, L, 
	            		L, Ingredient.EMPTY, L
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.LEAFY_BOOTS)).setRegistryName(DinocraftArmour.LEAFY_BOOTS.getRegistryName()));
	        }
 
			if (DinocraftBlocks.CRACKED_PEBBLE_BRICKS.getRegistryName() != null) 
			{
				Ingredient C = Ingredient.fromItem(DinocraftItems.CRACKED_PEBBLES);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, C, C,
						C, C, C,
						C, C, C
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftBlocks.CRACKED_PEBBLE_BRICKS, 4)).setRegistryName(DinocraftBlocks.CRACKED_PEBBLE_BRICKS.getRegistryName()));
			}
 
			if (DinocraftItems.RAY_BULLET.getRegistryName() != null) 
			{
	            Ingredient R = Ingredient.fromItem(Items.REDSTONE);
	            Ingredient I = Ingredient.fromItem(Items.IRON_INGOT);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		R,
	            		I
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 2, ingredients, new ItemStack(DinocraftItems.RAY_BULLET, 6)).setRegistryName(DinocraftItems.RAY_BULLET.getRegistryName()));
	        }
			
			if (DinocraftItems.POTATOSHROOM_PIE.getRegistryName() != null)
			{
			    Ingredient R = Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_MUSHROOM));
			    Ingredient B = Ingredient.fromItem(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM));
			    Ingredient P = Ingredient.fromItem(Items.POTATO);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
						R,
						B,
						P
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftItems.POTATOSHROOM_PIE)).setRegistryName(DinocraftItems.POTATOSHROOM_PIE.getRegistryName()));
			}
			
			if (DinocraftItems.SEED_PIPE.getRegistryName() != null)
			{
			    Ingredient R = Ingredient.fromItem(Items.REEDS);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
			    		Ingredient.EMPTY, Ingredient.EMPTY, R,
			    		Ingredient.EMPTY, R, Ingredient.EMPTY,
						R, Ingredient.EMPTY, Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.SEED_PIPE)).setRegistryName(DinocraftItems.SEED_PIPE.getRegistryName()));
			}
 
			if (DinocraftArmour.BLEVENT_BOOTS.getRegistryName() != null)
			{
			    Ingredient B = Ingredient.fromItem(DinocraftItems.BLEVENT_INGOT);
 
			    ingredients = NonNullList.from(Ingredient.EMPTY,
						B, Ingredient.EMPTY, B,
						B, Ingredient.EMPTY, B
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.BLEVENT_BOOTS)).setRegistryName(DinocraftArmour.BLEVENT_BOOTS.getRegistryName()));
			}
 
			if (DinocraftTools.TUSKERERS_SWORD.getRegistryName() != null)
			{
	            Ingredient G = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEM);
	            Ingredient T = Ingredient.fromItem(DinocraftItems.TUSK);
	            Ingredient S = Ingredient.fromItem(Items.STICK);
	            
	            ingredients = NonNullList.from(Ingredient.EMPTY, 
	            		Ingredient.EMPTY, Ingredient.EMPTY, T, 
	            		Ingredient.EMPTY, G, Ingredient.EMPTY, 
	            		S, Ingredient.EMPTY, Ingredient.EMPTY
	            	);
	            ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftTools.TUSKERERS_SWORD)).setRegistryName(DinocraftTools.TUSKERERS_SWORD.getRegistryName()));
	        }
			
			if (DinocraftBlocks.PEBBLE_BLOCK.getRegistryName() != null)
			{
			    Ingredient P = Ingredient.fromItem(DinocraftItems.PEBBLES);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						P, P,
						P, P
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftBlocks.PEBBLE_BLOCK, 2)).setRegistryName(DinocraftBlocks.PEBBLE_BLOCK.getRegistryName()));
			}
 
            if (DinocraftBlocks.PEBBLE_BRICKS.getRegistryName() != null)
			{
			    Ingredient P = Ingredient.fromItem(Item.getItemFromBlock(DinocraftBlocks.PEBBLE_BLOCK));
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						P, P,
						P, P
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftBlocks.PEBBLE_BRICKS, 4)).setRegistryName(DinocraftBlocks.PEBBLE_BRICKS.getRegistryName()));
			}
 
            if (DinocraftItems.VINE_BALL.getRegistryName() != null)
			{
			    Ingredient V = Ingredient.fromItem(Item.getItemFromBlock(Blocks.VINE));
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						V, V, V,
						V, V, V,
						V, V, V
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftItems.VINE_BALL)).setRegistryName(DinocraftItems.VINE_BALL.getRegistryName()));
			}
            
            if (DinocraftArmour.TUSKERERS_OLD_RAGS.getRegistryName() != null)
			{
			    Ingredient L = Ingredient.fromItem(Items.LEATHER);
			    Ingredient S = Ingredient.fromItem(DinocraftItems.SHEEPLITE_INGOT);
			    Ingredient T = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEM);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						L, T, L,
						S, Ingredient.EMPTY, S,
						S, Ingredient.EMPTY, S
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftArmour.TUSKERERS_OLD_RAGS)).setRegistryName(DinocraftArmour.TUSKERERS_OLD_RAGS.getRegistryName()));
			}
 
			if (DinocraftItems.VINE_BALL_BUNDLE.getRegistryName() != null)
			{
			    Ingredient V = Ingredient.fromItem(DinocraftItems.VINE_BALL);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						V, V,
						V, V
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 2, ingredients, new ItemStack(DinocraftItems.VINE_BALL_BUNDLE)).setRegistryName(DinocraftItems.VINE_BALL_BUNDLE.getRegistryName()));
			}
			
			if (DinocraftTools.LEAFERANG.getRegistryName() != null)
			{
			    Ingredient T = Ingredient.fromItem(DinocraftItems.TUSK);
			    Ingredient L = Ingredient.fromItem(DinocraftItems.LEAF);
			    Ingredient V = Ingredient.fromItem(Item.getItemFromBlock(Blocks.VINE));
			    Ingredient G = Ingredient.fromItem(DinocraftItems.TUSKERERS_GEM);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						T, L, T,
						V, G, V,
						T, L, T
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftTools.LEAFERANG)).setRegistryName(DinocraftTools.LEAFERANG.getRegistryName()));
			}
			
			if (DinocraftTools.SHEEPLITE_SWORD.getRegistryName() != null)
			{
			    Ingredient S = Ingredient.fromItem(Items.STICK);
			    Ingredient H = Ingredient.fromItem(DinocraftItems.SHEEPLITE_INGOT);
			    Ingredient B = Ingredient.fromItem(DinocraftItems.BLEVENT_INGOT);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						H,
						B,
						S
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftTools.LEAFERANG)).setRegistryName(DinocraftTools.LEAFERANG.getRegistryName()));
			}
			
			if (DinocraftArmour.RANIUM_HELMET.getRegistryName() != null)
			{
				Ingredient R = Ingredient.fromItem(DinocraftItems.RANIUM);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						R, R, R,
						R, Ingredient.EMPTY, R
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.RANIUM_HELMET)).setRegistryName(DinocraftArmour.RANIUM_HELMET.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						R, Ingredient.EMPTY, R,
						R, R, R,
						R, R, R
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftArmour.RANIUM_CHESTPLATE)).setRegistryName(DinocraftArmour.RANIUM_CHESTPLATE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						R, R, R,
						R, Ingredient.EMPTY, R,
						R, Ingredient.EMPTY, R
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftArmour.RANIUM_LEGGINGS)).setRegistryName(DinocraftArmour.RANIUM_LEGGINGS.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						R, Ingredient.EMPTY, R,
					    R, Ingredient.EMPTY, R
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.RANIUM_BOOTS)).setRegistryName(DinocraftArmour.RANIUM_BOOTS.getRegistryName()));
			}
 
			if (DinocraftTools.CHLOROPHYTE_PICKAXE.getRegistryName() != null)
			{
				Ingredient C = Ingredient.fromItem(DinocraftItems.CHLOROPHYTE_INGOT);
				Ingredient S = Ingredient.fromItem(Items.STICK);

				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, C, C,
						Ingredient.EMPTY, S, Ingredient.EMPTY,
						Ingredient.EMPTY, S, Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftTools.CHLOROPHYTE_PICKAXE)).setRegistryName(DinocraftTools.CHLOROPHYTE_PICKAXE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C,
						S,
						S
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftTools.CHLOROPHYTE_SHOVEL)).setRegistryName(DinocraftTools.CHLOROPHYTE_SHOVEL.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, C,
						S, Ingredient.EMPTY,
						S, Ingredient.EMPTY
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 2, 3, ingredients, new ItemStack(DinocraftTools.CHLOROPHYTE_HOE)).setRegistryName(DinocraftTools.CHLOROPHYTE_HOE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C,
					    C,
					    S
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 1, 3, ingredients, new ItemStack(DinocraftTools.CHLOROPHYTE_SWORD)).setRegistryName(DinocraftTools.CHLOROPHYTE_SWORD.getRegistryName()));
			}
			
			if (DinocraftArmour.CHLOROPHYTE_HELMET.getRegistryName() != null)
			{
				Ingredient C = Ingredient.fromItem(DinocraftItems.CHLOROPHYTE_INGOT);
 
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, C, C,
						C, Ingredient.EMPTY, C
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.CHLOROPHYTE_HELMET)).setRegistryName(DinocraftArmour.CHLOROPHYTE_HELMET.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, Ingredient.EMPTY, C,
						C, C, C,
						C, C, C
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftArmour.CHLOROPHYTE_CHESTPLATE)).setRegistryName(DinocraftArmour.CHLOROPHYTE_CHESTPLATE.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, C, C,
						C, Ingredient.EMPTY, C,
						C, Ingredient.EMPTY, C
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 3, ingredients, new ItemStack(DinocraftArmour.CHLOROPHYTE_LEGGINGS)).setRegistryName(DinocraftArmour.CHLOROPHYTE_LEGGINGS.getRegistryName()));
				ingredients = NonNullList.from(Ingredient.EMPTY,
						C, Ingredient.EMPTY, C,
					    C, Ingredient.EMPTY, C
					);
				ForgeRegistries.RECIPES.register(new ShapedMatchingRecipes(Reference.NAME, 3, 2, ingredients, new ItemStack(DinocraftArmour.CHLOROPHYTE_BOOTS)).setRegistryName(DinocraftArmour.CHLOROPHYTE_BOOTS.getRegistryName()));
			}
		}
	}
}
