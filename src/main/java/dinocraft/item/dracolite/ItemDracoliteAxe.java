package dinocraft.item.dracolite;

import net.minecraft.item.ItemAxe;

public class ItemDracoliteAxe extends ItemAxe
{
	public ItemDracoliteAxe(ToolMaterial material)
	{
		this(material, 7.5F, -3.0F);
	}

	public ItemDracoliteAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);
		this.setHarvestLevel("axe", material.getHarvestLevel());
	}
}