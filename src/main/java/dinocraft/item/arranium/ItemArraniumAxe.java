package dinocraft.item.arranium;

import net.minecraft.item.ItemAxe;

public class ItemArraniumAxe extends ItemAxe
{
	public ItemArraniumAxe(ToolMaterial material)
	{
		this(material, 7.5F, -3.0F);
	}

	public ItemArraniumAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);
		this.setHarvestLevel("axe", material.getHarvestLevel());
	}
}