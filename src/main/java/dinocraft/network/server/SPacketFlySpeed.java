package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketFlySpeed extends AbstractPacket<SPacketFlySpeed>
{
	private double speed;

	public SPacketFlySpeed()
	{

	}

	public SPacketFlySpeed(double amount)
	{
		this.speed = amount;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeDouble(this.speed);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.speed = buffer.readDouble();
	}
	
	@Override
	public void handleClientSide(SPacketFlySpeed message, EntityPlayer player)
	{
		player.capabilities.setFlySpeed((float) message.speed);
	}

	@Override
	public void handleServerSide(SPacketFlySpeed message, EntityPlayer player)
	{

	}
}
