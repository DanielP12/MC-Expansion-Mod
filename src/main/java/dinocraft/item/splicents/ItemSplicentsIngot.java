package dinocraft.item.splicents;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemSplicentsIngot extends Item
{
	public ItemSplicentsIngot()
	{

	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
}
