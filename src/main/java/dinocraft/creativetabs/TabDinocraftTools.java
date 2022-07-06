package dinocraft.creativetabs;

import dinocraft.init.DinocraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDinocraftTools extends CreativeTabs
{
	public TabDinocraftTools()
	{
		super("dinocrafttools");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(DinocraftItems.WADRONITE_AXE);
	}
}
