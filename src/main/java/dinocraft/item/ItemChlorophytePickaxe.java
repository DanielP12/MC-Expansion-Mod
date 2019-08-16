package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophytePickaxe extends ItemPickaxe
{
	public ItemChlorophytePickaxe(ToolMaterial material, String unlocalizedName)
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
}
