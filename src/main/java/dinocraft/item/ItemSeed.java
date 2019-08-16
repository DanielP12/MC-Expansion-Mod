package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemSeed extends Item
{
	public ItemSeed(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
}
