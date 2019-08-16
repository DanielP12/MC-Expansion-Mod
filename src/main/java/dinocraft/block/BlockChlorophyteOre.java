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

public class BlockChlorophyteOre extends Block 
{
	public BlockChlorophyteOre(String unlocalizedName, String registryName) 
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
        if (!playerIn.hasAchievement(DinocraftAchievements.CHLOROPHYLL))
		{
        	playerIn.addStat(DinocraftAchievements.CHLOROPHYLL);
		}
		*/
	}
	
	@Override
	public Item getItemDropped(IBlockState stateIn, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}
