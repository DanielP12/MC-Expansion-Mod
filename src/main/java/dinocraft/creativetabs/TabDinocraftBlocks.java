package dinocraft.creativetabs;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabDinocraftBlocks extends CreativeTabs
{
	public TabDinocraftBlocks() 
	{
		super("dinocraftblocks");
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(DinocraftBlocks.CHLOROPHYTE_ORE);
	}
}
