package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMerchantsLuckyBoots extends ItemArmor
{
	public ItemMerchantsLuckyBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
    {
        super(material, renderIndex, equipmentSlot);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setMaxDamage(500);
    }
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() == this)
		{
			event.getToolTip().add(TextFormatting.BLUE + " +1 Max Health");
		}
	}
}