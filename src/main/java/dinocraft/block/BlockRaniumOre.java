package dinocraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import dinocraft.Reference;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRaniumOre extends Block
{
	public BlockRaniumOre(String name) 
	{
		super(Material.ROCK);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setHardness(4.0F);
		this.setResistance(15.0F);
	}

	@Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return DinocraftItems.RANIUM;
    }

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

    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }
    
    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = 0;
            
            if (this == DinocraftBlocks.RANIUM_ORE)
            {
            	i = MathHelper.getInt(rand, 3, 7);
            }

            return i;
        }
        
        return 0;
    }

    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }
}