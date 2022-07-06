package dinocraft.item.olitropy;

import net.minecraft.item.ItemAxe;

public class ItemOlitropyAxe extends ItemAxe
{
	public ItemOlitropyAxe(ToolMaterial material)
	{
		this(material, 7.5F, -3.0F);
	}

	public ItemOlitropyAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);
		this.setHarvestLevel("axe", material.getHarvestLevel());
	}
}