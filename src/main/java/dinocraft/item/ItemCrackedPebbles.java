package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCrackedPebbles extends Item
{
	public ItemCrackedPebbles(String unlocalizedName, String registryName) 
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
	}
}
