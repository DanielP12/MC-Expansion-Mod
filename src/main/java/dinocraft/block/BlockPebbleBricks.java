package dinocraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockPebbleBricks extends Block
{
	public BlockPebbleBricks()
	{
		super(Material.ROCK);
		this.setHardness(1.5F);
		this.setResistance(30.0F);
		this.setSoundType(SoundType.METAL);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}