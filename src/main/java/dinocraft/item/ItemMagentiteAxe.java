package dinocraft.item;

import net.minecraft.item.ItemAxe;

public class ItemMagentiteAxe extends ItemAxe
{
	public ItemMagentiteAxe(ToolMaterial material)
	{
		this(material, 7.5F, -3.0F);
	}
	
	public ItemMagentiteAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);
		this.setHarvestLevel("axe", material.getHarvestLevel());
	}
}