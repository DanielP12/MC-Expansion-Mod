package dinocraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import dinocraft.Reference;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockPebbeloneumOre extends Block 
{
    public BlockPebbeloneumOre(String unlocalizedName, String registryName) 
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
        this.setHardness(3.25F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("pickaxe", 2);
    }
	
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return DinocraftItems.PEBBELONEUM;
    }

    public int quantityDroppedWithBonus(int fortune, Random rand)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), rand, fortune))
        {
            int i = rand.nextInt(fortune + 2) - 1;
			if (i < 0) i = 0;
			
            return this.quantityDropped(rand) * (i + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState stateIn, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, stateIn, chance, fortune);
    }
    
    @Override
    public int getExpDrop(IBlockState stateIn, net.minecraft.world.IBlockAccess worldIn, BlockPos pos, int fortune)
    {
        Random rand = worldIn instanceof World ? ((World) worldIn).rand : new Random();
        
        if (this.getItemDropped(stateIn, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = 0;
            if (this == DinocraftBlocks.PEBBELONEUM_ORE) i = MathHelper.getInt(rand, 3, 7);

            return i;
        }
        
        return 0;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState stateIn)
    {
        return new ItemStack(this);
    }
}