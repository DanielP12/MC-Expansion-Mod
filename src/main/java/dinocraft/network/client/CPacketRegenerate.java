package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketRegenerate extends AbstractPacket<CPacketRegenerate>
{
	private int time;
	private float health, loopTime;
	
	public CPacketRegenerate()
	{

	}

	public CPacketRegenerate(int time, float loopTime, float health)
	{
		this.time = time;
		this.loopTime = loopTime;
		this.health = health;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.time = buffer.readInt();
		this.loopTime = buffer.readFloat();
		this.health = buffer.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.time);
		buffer.writeFloat(this.loopTime);
		buffer.writeFloat(this.health);
	}

	@Override
	public void handleClientSide(CPacketRegenerate message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(CPacketRegenerate message, EntityPlayer player)
	{
		DinocraftEntity.getEntity(player).setRegenerating(message.time, message.loopTime, message.health);
	}
}