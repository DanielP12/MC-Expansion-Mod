package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophyteShovel extends ItemSpade 
{
	public ItemChlorophyteShovel(ToolMaterial material, String name) 
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
}
