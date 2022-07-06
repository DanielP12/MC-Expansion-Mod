package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketChangeCapability extends AbstractPacket<CPacketChangeCapability>
{
	private Capability capability;
	private boolean state;
	private int value;
	
	public CPacketChangeCapability()
	{
		
	}
	
	public CPacketChangeCapability(Capability capability, int value)
	{
		this.capability = capability;
		this.value = value;
	}
	
	public CPacketChangeCapability(Capability capability, boolean state)
	{
		this.capability = capability;
		this.state = state;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.capability = Capability.values()[buffer.readByte()];
		this.state = buffer.readBoolean();
		this.value = buffer.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeByte(this.capability.ordinal());
		buffer.writeBoolean(this.state);
		buffer.writeInt(this.value);
	}
	
	public enum Capability
	{
		P_FALL_DISTANCE, DA_STANDING_STILL
	}
	
	@Override
	public void handleClientSide(CPacketChangeCapability message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketChangeCapability message, EntityPlayer player)
	{
		if (player != null)
		{
			if (message.capability == Capability.P_FALL_DISTANCE)
			{
				player.fallDistance = message.value;
			}
			else if (message.capability == Capability.DA_STANDING_STILL)
			{
				DinocraftEntityActions actions = DinocraftEntity.getEntity(player).getActionsModule();

				if (!message.state)
				{
					actions.resetStandingStill();
				}
				else
				{
					actions.setStandingStill(message.state);
				}
			}
		}
	}
}