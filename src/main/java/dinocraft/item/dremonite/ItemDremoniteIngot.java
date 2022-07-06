package dinocraft.item.dremonite;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemDremoniteIngot extends Item
{
	public ItemDremoniteIngot()
	{
		
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
}