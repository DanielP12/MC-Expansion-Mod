package dinocraft.creativetabs;

import dinocraft.init.DinocraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDinocraftItems extends CreativeTabs
{
	public TabDinocraftItems()
	{
		super("dinocraftitems");
	}
	
	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(DinocraftItems.LEAF);
	}
}