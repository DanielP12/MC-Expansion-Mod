package dinocraft.block;

import java.util.Random;

import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWadroniteOre extends Block
{
	public BlockWadroniteOre()
	{
		super(Material.ROCK);
		this.setHardness(4.0F);
		this.setResistance(15.0F);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return DinocraftItems.MAGENTITE;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand)
	{
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), rand, fortune))
		{
			int i = rand.nextInt(fortune + 2) - 1;

			if (i < 0)
			{
				i = 0;
			}
			
			return this.quantityDropped(rand) * (i + 1);
		}
		else
		{
			return this.quantityDropped(rand);
		}
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			return MathHelper.getInt(rand, 3, 7);
		}

		return 0;
	}
}