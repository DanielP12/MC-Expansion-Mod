package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketAllowFlying extends AbstractPacket<SPacketAllowFlying>
{
	private boolean allowFlying, isFlying;
	
	public SPacketAllowFlying()
	{

	}

	public SPacketAllowFlying(boolean allowFlying, boolean isFlying)
	{
		this.allowFlying = allowFlying;
		this.isFlying = isFlying;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(this.allowFlying);
		buffer.writeBoolean(this.isFlying);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.allowFlying = buffer.readBoolean();
		this.isFlying = buffer.readBoolean();
	}
	
	@Override
	public void handleClientSide(SPacketAllowFlying message, EntityPlayer player)
	{
		player.capabilities.allowFlying = message.allowFlying;
		player.capabilities.isFlying = message.isFlying;
	}
	
	@Override
	public void handleServerSide(SPacketAllowFlying message, EntityPlayer player)
	{
		
	}
}