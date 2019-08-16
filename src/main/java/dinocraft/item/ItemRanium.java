package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemRanium extends Item
{
	public ItemRanium(String unlocalizedName, String registryName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
	}
}