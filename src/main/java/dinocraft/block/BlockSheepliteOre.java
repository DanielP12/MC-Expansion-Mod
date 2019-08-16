package dinocraft.block;

import java.util.Random;

import dinocraft.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSheepliteOre extends Block
{	
	public BlockSheepliteOre(String unlocalizedName, String registryName) 
	{
		super(Material.ROCK);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
		this.setHardness(4);
		this.setResistance(15);
	}
	
	@Override
    public void harvestBlock(World worldIn, EntityPlayer playerIn, BlockPos pos, IBlockState stateIn, TileEntity tile, ItemStack stack)
	{
        super.harvestBlock(worldIn, playerIn, pos, stateIn, tile, stack);
        /*
        if (!playerIn.hasAchievement(DinocraftAchievements.SHEEPLITE))
		{
        	playerIn.addStat(DinocraftAchievements.SHEEPLITE);
		}
		*/
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}

