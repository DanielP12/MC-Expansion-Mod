package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophyteHoe extends ItemHoe
{
	public ItemChlorophyteHoe(ToolMaterial material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
}
