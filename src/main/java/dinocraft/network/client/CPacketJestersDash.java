package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.network.AbstractPacket;
import dinocraft.util.server.DinocraftServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public class CPacketJestersDash extends AbstractPacket<CPacketJestersDash>
{
	@Override
	public void fromBytes(ByteBuf buffer)
	{

	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		
	}
	
	@Override
	public void handleClientSide(CPacketJestersDash message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketJestersDash message, EntityPlayer player)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		dinoEntity.setFallDamageReductionAmount(2.0F);
		player.fallDistance = 0.0F;
		player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat() + 0.25F);
		ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

		if (stack.getItem() == DinocraftItems.JESTERS_HAT)
		{
			if (player.world.rand.nextInt(3) == 0)
			{
				stack.damageItem(1, player);
			}

			player.getCooldownTracker().setCooldown(DinocraftItems.JESTERS_HAT, 60);
		}
		
		float f0 = player.height / 2.0F;
		DinocraftServer.spawnJesterParticles(player.world, 25, 10, player.posX, player.posY + f0, player.posZ, player.width, f0, player.width);
	}
}