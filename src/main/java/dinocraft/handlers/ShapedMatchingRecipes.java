package dinocraft.handlers;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

public class ShapedMatchingRecipes extends ShapedRecipes
{
	private ItemStack recipeOutput;

	public ShapedMatchingRecipes(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result)
	{
		super(group, width, height, ingredients, result);
		this.recipeOutput = result;
	}

	@Override
	@Nullable
	public ItemStack getCraftingResult(InventoryCrafting inventory) 
	{
		ItemStack result = this.recipeOutput.copy();
		
		for (int i = 0; i < inventory.getHeight(); ++i)
		{
			for (int j = 0; j < inventory.getWidth(); ++j) 
			{
				ItemStack stack = inventory.getStackInRowAndColumn(j, i);
				
				if (stack != null && stack.getItem() instanceof ItemArmor) 
				{
					result.setTagCompound(stack.getTagCompound());
					result.setItemDamage(stack.getItemDamage());
				}
			}
		}
		
		return result;
	}
}