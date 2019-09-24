package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageBounce extends AbstractPacket<MessageBounce> 
{
	private double amount;
	
	public MessageBounce()
	{
		
	}
	
	public MessageBounce(double amount)
	{
		this.amount = amount;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) 
	{
        buffer.writeDouble(this.amount);
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		this.amount = buffer.readDouble();
	}

	@Override
	public void handleClientSide(MessageBounce message, EntityPlayer player) 
	{
		player.motionY = message.amount;
	}

	@Override
	public void handleServerSide(MessageBounce message, EntityPlayer player) 
	{

	}
}