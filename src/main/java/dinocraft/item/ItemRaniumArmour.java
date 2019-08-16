package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.init.DinocraftArmour;
import dinocraft.network.MessageResis;
import dinocraft.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemRaniumArmour extends ItemArmor
{
	public ItemRaniumArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRaniumArmor(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END) return;
		
	    EntityPlayer playerIn = event.player;
	    DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
	    
		if (player != null && playerIn.world.isRemote && player.isWearingItems(
				DinocraftArmour.RANIUM_HELMET, DinocraftArmour.RANIUM_CHESTPLATE,
				DinocraftArmour.RANIUM_LEGGINGS, DinocraftArmour.RANIUM_BOOTS))
		{
			NetworkHandler.sendToServer(new MessageResis());
		}	
	}
}