package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageNoFall extends AbstractPacket<MessageNoFall> 
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
	public void handleClientSide(MessageNoFall message, EntityPlayer playerIn) 
	{

	}

	@Override
	public void handleServerSide(MessageNoFall message, EntityPlayer playerIn) 
	{
		playerIn.fallDistance = 0.0F;
	}
}

