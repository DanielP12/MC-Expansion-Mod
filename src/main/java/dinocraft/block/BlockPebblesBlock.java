package dinocraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dinocraft.Reference;
import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPebblesBlock extends Block 
{
    public BlockPebblesBlock(String name) 
    {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("pickaxe", 2);
    }
	
    private int amount;

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) 
    {
        int i = rand.nextInt(2000);
        this.amount = 1;
        
        if (i < 1)
        {
            return DinocraftItems.TUSKERERS_AMULET;
        }
        else if (i < 3)
        {
            return DinocraftItems.TUSKERERS_GEMSTONE;
        } 
        else if (i < 7)
        {
            return DinocraftItems.BLEVENT_INGOT; 
        } 
        else if (i < 10) /* TODO: Remove; doesn't make any sense here. Replace with Tuskerer's Ruby */
        {
            return DinocraftItems.UMBRELLA_HAT;
        }
        else if (i < 13)
        {
            return DinocraftItems.LEAF;
        }
        else if (i < 18)
        {
            return DinocraftItems.RANIUM;
        } 
        else if (i < 24)
        {
            return Items.DIAMOND;
        } 
        else if (i < 39)
        {
            return DinocraftItems.SHEEPLITE_INGOT;
        } 
        else if (i < 39)
        {
        	this.amount = rand.nextInt(3) + 1;
            return Items.IRON_NUGGET;
        } 
        else if (i < 46)
        {
        	this.amount = rand.nextInt(16) + 1;
            return Items.GOLD_NUGGET;
        } 
        else if (i < 53)
        {
        	this.amount = rand.nextInt(2) + 1;
            return Items.FLINT;
        } 
        else if (i < 61)
        {
        	this.amount = rand.nextInt(4) + 1;
        	return Items.LEATHER;
        }
        else if (i < 85)
        {
        	return Items.COAL;
        }
        else if (i < 100)
        {
        	this.amount = rand.nextInt(1) + 1;
            return Item.getItemFromBlock(Blocks.GRAVEL);
        } 
        else if (i < 140)
        {
        	this.amount = rand.nextInt(2) + 1;
            return DinocraftItems.CHUNKY_FLESH;
        } 
        else if (i < 300)
        {
        	return Item.getItemFromBlock(Blocks.DIRT);
        }
        else if (i < 480)
        {
        	this.amount = rand.nextInt(4) + 1;
            return Items.CLAY_BALL;
        }
        else if (i < 740)
        {
        	this.amount = rand.nextInt(3) + 1;
            return Item.getItemFromBlock(Blocks.COBBLESTONE);
        }
        else if (i < 1080)
        {
        	this.amount = rand.nextInt(3) + 1;
            return DinocraftItems.CRACKED_PEBBLES;
        }
        else if (i < 1500)
        {
        	this.amount = rand.nextInt(3) + 1;
            return DinocraftItems.PEBBLES;
        }
        else if (i < 2000)
        {
            return Items.BONE;
        }
        
        return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int count = this.quantityDropped(state, fortune, rand);
        ArrayList<ItemStack> drops = new ArrayList<>();

        for (int i = 0; i < count; ++i) 
        {
            Item item = this.getItemDropped(state, rand, fortune);
            
            if (item != null)
            {
            	drops.add(new ItemStack(item, this.amount, 0));
            }
        }
        
        return drops;
    }
}