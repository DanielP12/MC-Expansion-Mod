package dinocraft.creativetabs;

import dinocraft.init.DinocraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDinocraftCombat extends CreativeTabs
{
	public TabDinocraftCombat()
	{
		super("dinocraftcombat");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(DinocraftItems.TUSKERS_SWORD);
	}
}
