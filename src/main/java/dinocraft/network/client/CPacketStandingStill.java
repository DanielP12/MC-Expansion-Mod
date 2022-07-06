package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class CPacketStandingStill extends AbstractPacket<CPacketStandingStill>
{
	private int handId;
	
	public CPacketStandingStill()
	{
		
	}
	
	public CPacketStandingStill(EnumHand hand)
	{
		this.handId = hand.ordinal();
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.handId);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.handId = buffer.readInt();
	}
	
	@Override
	public void handleClientSide(CPacketStandingStill message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketStandingStill message, EntityPlayer player)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (dinoEntity != null)
		{
			dinoEntity.getActionsModule().setStandingStill(true);
			player.getHeldItem(EnumHand.values()[message.handId]).damageItem(1, player);
		}
	}
}