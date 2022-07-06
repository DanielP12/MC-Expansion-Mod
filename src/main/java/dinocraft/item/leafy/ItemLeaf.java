package dinocraft.item.leafy;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemLeaf extends Item
{
	public ItemLeaf()
	{
		
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
}