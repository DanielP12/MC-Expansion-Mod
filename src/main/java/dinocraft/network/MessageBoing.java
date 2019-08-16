package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageBoing extends AbstractPacket<MessageBoing> 
{
	private double amount;
	
	public MessageBoing()
	{
		
	}
	
	public MessageBoing(double amount)
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
	public void handleClientSide(MessageBoing message, EntityPlayer playerIn) 
	{
		playerIn.motionY = message.amount;
	}

	@Override
	public void handleServerSide(MessageBoing message, EntityPlayer playerIn) 
	{

	}
}

