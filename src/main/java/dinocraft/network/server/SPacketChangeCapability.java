package dinocraft.network.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketChangeCapability extends AbstractPacket<SPacketChangeCapability>
{
	private Capability capability;
	private boolean state;
	private int value;

	public SPacketChangeCapability()
	{

	}

	public SPacketChangeCapability(Capability capability, int value)
	{
		this.capability = capability;
		this.value = value;
	}

	public SPacketChangeCapability(Capability capability, boolean state)
	{
		this.capability = capability;
		this.state = state;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeByte(this.capability.ordinal());
		buffer.writeBoolean(this.state);
		buffer.writeInt(this.value);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.capability = Capability.values()[buffer.readByte()];
		this.state = buffer.readBoolean();
		this.value = buffer.readInt();
	}

	public enum Capability
	{
		DA_DREADED_FLYING, DE_FROZEN;
	}

	@Override
	public void handleClientSide(SPacketChangeCapability message, EntityPlayer player)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);

		if (player != null)
		{
			if (message.capability == Capability.DA_DREADED_FLYING)
			{
				dinoEntity.getActionsModule().setDreadedFlying(message.state);
			}
			else if (message.capability == Capability.DE_FROZEN)
			{
				if (message.state)
				{
					dinoEntity.freeze();
				}
				else
				{
					dinoEntity.unfreeze();
				}
			}
		}
	}

	@Override
	public void handleServerSide(SPacketChangeCapability message, EntityPlayer player)
	{
		
	}
}