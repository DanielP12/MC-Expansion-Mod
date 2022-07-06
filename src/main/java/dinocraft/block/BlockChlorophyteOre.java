package dinocraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockChlorophyteOre extends Block
{
	public BlockChlorophyteOre()
	{
		super(Material.ROCK);
		this.setHardness(4.0F);
		this.setResistance(15.0F);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}