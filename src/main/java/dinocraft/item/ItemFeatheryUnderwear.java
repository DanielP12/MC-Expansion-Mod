package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

public class ItemFeatheryUnderwear extends ItemArmor
{
	public ItemFeatheryUnderwear(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
    {
        super(material, renderIndex, equipmentSlot);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setMaxDamage(300);
    }
}