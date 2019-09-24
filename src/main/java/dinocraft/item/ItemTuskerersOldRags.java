package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.init.DinocraftItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersOldRags extends ItemArmor
{
	public ItemTuskerersOldRags(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
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
	    
	    if (dinoEntity != null && !entity.world.isRemote)
	    {
	    	DinocraftEntityActions actions = dinoEntity.getActionsModule();
	        ItemStack leggings = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

	        if (leggings != null && leggings.getItem() == DinocraftItems.TUSKERERS_OLD_RAGS) 
	        {	
	            if (!actions.hasExtraMaxHealth())
	            {
	            	dinoEntity.addMaxHealth(2.0F);
	            	actions.setHasExtraMaxHealth(true);
	        	}
	        }
	        else if (actions.hasExtraMaxHealth())
	        {
	        	dinoEntity.addMaxHealth(-2.0F);
	            actions.setHasExtraMaxHealth(false);
	        }
	    }
    }
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() == this)
		{
			event.getToolTip().add(TextFormatting.BLUE + " +2 Max Health");
		}
	}
}