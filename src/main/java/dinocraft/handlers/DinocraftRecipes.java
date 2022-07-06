package dinocraft.handlers;

import dinocraft.Dinocraft;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DinocraftRecipes
{
	public static void registerFurnaceRecipes()
	{
		GameRegistry.addSmelting(DinocraftBlocks.DREMONITE_ORE, new ItemStack(DinocraftItems.DREMONITE_INGOT), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.MAGENTITE_ORE, new ItemStack(DinocraftItems.MAGENTITE), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.OLITROPY_ORE, new ItemStack(DinocraftItems.OLITROPY), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.WADRONITE_ORE, new ItemStack(DinocraftItems.WADRONITE), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.DRACOLITE_ORE, new ItemStack(DinocraftItems.DRACOLITE), 0.7F);
		GameRegistry.addSmelting(DinocraftBlocks.ARRANIUM_ORE, new ItemStack(DinocraftItems.ARRANIUM), 0.7F);
		GameRegistry.addSmelting(Items.LEATHER, new ItemStack(DinocraftItems.DRY_HIDE), 0.75F);
		GameRegistry.addSmelting(Items.RABBIT_HIDE, new ItemStack(DinocraftItems.DRY_HIDE), 0.75F);
		//		GameRegistry.addSmelting(new ItemStack(Items.COAL, 1, 1).getItem(), new ItemStack(DinocraftItems.SMOLDERING_CHARCOAL), 0.7F);
		Dinocraft.LOGGER.info("Registered furnace recipes");
	}

	@EventBusSubscriber
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void registerRecipes(Register<IRecipe> event)
		{
			NonNullList<Ingredient> ingredients = null;

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
				event.getRegistry().register(new ShapedMatchingRecipes(Dinocraft.NAME, 3, 2, ingredients, new ItemStack(DinocraftItems.POTATOSHROOM_PIE)).setRegistryName(DinocraftItems.POTATOSHROOM_PIE.getRegistryName()));
			}

			Dinocraft.LOGGER.info("Registered crafting recipes");
		}
	}
}
