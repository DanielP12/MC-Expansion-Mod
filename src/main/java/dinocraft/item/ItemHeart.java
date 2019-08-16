package dinocraft.item;

import dinocraft.Reference;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHeart extends Item
{
	public ItemHeart(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/* Event fired when an item is picked up */
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityItem entityitem = event.getItem();
	    ItemStack stack = entityitem.getItem();
	    Item item = stack.getItem();
	    
	    if (entityitem != null && stack != null && item != null && item == DinocraftItems.HEART) 
	    {
	        EntityPlayer playerIn = event.getEntityPlayer();  
	        playerIn.heal(2.0F);
	        stack.shrink(1);
    		playerIn.world.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}
}
