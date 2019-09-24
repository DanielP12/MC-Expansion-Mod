package dinocraft.network;

import dinocraft.capabilities.entity.DinocraftEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageReach extends AbstractPacket<MessageReach> 
{
	private double reach;
	
	public MessageReach()
	{

	}
	
	public MessageReach(double reach)
	{
		this.reach = reach;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeDouble(this.reach);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
        this.reach = buffer.readDouble();
	}

	@Override
	public void handleClientSide(MessageReach message, EntityPlayer player) 
	{
		DinocraftEntity.getEntity(player).setAttackReach(message.reach);
	}

	@Override
	public void handleServerSide(MessageReach message, EntityPlayer player) 
	{
		
	}
}