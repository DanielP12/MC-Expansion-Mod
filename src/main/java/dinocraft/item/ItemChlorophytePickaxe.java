package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophytePickaxe extends ItemPickaxe
{
	public ItemChlorophytePickaxe(ToolMaterial material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
}
