package dinocraft.item.tuskers;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemTuskersGemstone extends Item
{
	public ItemTuskersGemstone()
	{

	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
}