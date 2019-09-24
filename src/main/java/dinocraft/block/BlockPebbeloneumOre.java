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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPebbeloneumOre extends Block 
{
    public BlockPebbeloneumOre(String name) 
    {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
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
    
    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = 0;
            
            if (this == DinocraftBlocks.PEBBELONEUM_ORE)
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