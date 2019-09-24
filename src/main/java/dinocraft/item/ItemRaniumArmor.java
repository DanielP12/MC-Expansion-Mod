package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemRaniumArmor extends ItemArmor
{
	public ItemRaniumArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
	{
		super(material, renderIndex, equipmentSlot);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
	    DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
	    
		if (dinoEntity.isWearing(DinocraftItems.RANIUM_HELMET, DinocraftItems.RANIUM_CHESTPLATE, DinocraftItems.RANIUM_LEGGINGS, DinocraftItems.RANIUM_BOOTS) && entity.getHealth() <= 5.0F)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1, 1, false, false));
		}
	}
}