package dinocraft.creativetabs;

import dinocraft.init.DinocraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDinocraftMaterials extends CreativeTabs
{
	public TabDinocraftMaterials()
	{
		super("dinocraftmiscellaneous");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(DinocraftItems.LEAF);
	}
}
