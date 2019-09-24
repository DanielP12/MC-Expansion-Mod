package dinocraft.item;

import dinocraft.Reference;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHeart extends Item
{
	public ItemHeart(String name)
	{
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));

		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onEntityItemPickup(EntityItemPickupEvent event)
	{
    	EntityPlayer player = event.getEntityPlayer();  
		EntityItem entityitem = event.getItem();
	    String owner = entityitem.getOwner();
	    
	    if (entityitem.getItem().getItem() == DinocraftItems.HEART && owner != null && owner.equals(player.getUniqueID().toString()))
	    {
	    	player.heal(2.0F);
	    	player.world.playSound(null, player.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.NEUTRAL, 0.5F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
	    	entityitem.setDead();
	    }
	}
}