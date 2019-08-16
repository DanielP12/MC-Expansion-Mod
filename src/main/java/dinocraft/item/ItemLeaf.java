package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemLeaf extends Item
{
	public ItemLeaf(String unlocalizedName, String registryName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
	}
}
