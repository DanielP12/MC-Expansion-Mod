package dinocraft.block;

import dinocraft.init.DinocraftBlocks;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockPebbleWall extends BlockWall
{
	public BlockPebbleWall()
	{
		super(DinocraftBlocks.PEBBLE_BLOCK);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
	{
		items.add(new ItemStack(this));
	}
}
