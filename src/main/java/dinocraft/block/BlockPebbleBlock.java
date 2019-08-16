package dinocraft.block;

import java.util.Random;

import dinocraft.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockPebbleBlock extends Block
{
	public BlockPebbleBlock(String unlocalizedName, String registryName) 
	{
		super(Material.ROCK);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
		this.setHardness(2);
		this.setResistance(30);
        this.setSoundType(SoundType.METAL);
	}
	
	@Override
	public Item getItemDropped(IBlockState stateIn, Random rand, int fortune) 
	{
		return Item.getItemFromBlock(this);
	}
}
