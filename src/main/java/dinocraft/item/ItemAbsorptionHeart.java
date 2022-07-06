package dinocraft.item;

import dinocraft.init.DinocraftSoundEvents;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketItemPickupEffect;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class ItemAbsorptionHeart extends Item
{
	public ItemAbsorptionHeart()
	{
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onEntityItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		EntityItem item = event.getItem();
		
		if (item.getItem().getItem() == this && player.getUniqueID().toString().equals(item.getOwner()))
		{
			PacketHandler.sendToAllAround(new SPacketItemPickupEffect(item, player), new TargetPoint(player.world.provider.getDimension(), player.posX, player.posY, player.posZ, 64.0D));
			float amount = player.getAbsorptionAmount();

			if (amount < 8.0F)
			{
				player.setAbsorptionAmount(amount + 1.0F);
			}

			player.world.playSound(null, player.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.AMBIENT, 1.0F, ((player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			item.setDead();
		}
	}
}