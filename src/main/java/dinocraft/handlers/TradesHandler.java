package dinocraft.handlers;

import java.util.Random;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.village.MerchantRecipeList;

public class TradesHandler implements ITradeList
{
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList list, Random rand)
	{
		//		list.add(new MerchantRecipe(new ItemStack(Items.EMERALD, rand.nextInt(16) + 80), new ItemStack(DinocraftItems.FLARE_GUN, 1)));
		//		list.add(new MerchantRecipe(new ItemStack(Items.EMERALD, rand.nextInt(2) + 3), new ItemStack(DinocraftItems.FLAME_BULLET, rand.nextInt(5) + 12)));
	}
}