package dinocraft.network;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageRegenerate extends AbstractPacket<MessageRegenerate> 
{
	private int time;
	private float health, loopTime;
	
	public MessageRegenerate()
	{

	}

	public MessageRegenerate(int time, float loopTime, float health)
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
	public void handleClientSide(MessageRegenerate message, EntityPlayer player) 
	{
		
	}

	@Override
	public void handleServerSide(MessageRegenerate message, EntityPlayer player) 
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		DinocraftEntityActions actions = dinoEntity.getActionsModule();
		
		if (player != null)
		{
			dinoEntity.setRegenerating(message.time, message.loopTime, message.health);
		}
	}
}