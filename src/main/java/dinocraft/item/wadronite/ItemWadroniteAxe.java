package dinocraft.item.wadronite;

import net.minecraft.item.ItemAxe;

public class ItemWadroniteAxe extends ItemAxe
{
	public ItemWadroniteAxe(ToolMaterial material)
	{
		this(material, 7.5F, -3.0F);
	}

	public ItemWadroniteAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);
		this.setHarvestLevel("axe", material.getHarvestLevel());
	}
}