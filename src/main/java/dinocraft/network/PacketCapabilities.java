package dinocraft.network;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketCapabilities extends AbstractPacket<PacketCapabilities> 
{
	private Capability capability;
	
	private boolean state;
	
	public PacketCapabilities()
	{

	}

	public PacketCapabilities(Capability capability, boolean state)
	{
		this.capability = capability;
		this.state = state;
	}
	
	public PacketCapabilities(Capability capability)
	{
		this.capability = capability;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
        this.state = buffer.readBoolean();
		this.capability = Capability.values()[buffer.readByte()];
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
        buffer.writeBoolean(this.state);
		buffer.writeByte(this.capability.ordinal());
	}

	public enum Capability
	{
		DOUBLE_JUMPED, DOUBLE_JUMP, FALL_DAMAGE, FALL_DISTANCE, REDUCTION
	}

	@Override
	public void handleClientSide(PacketCapabilities message, EntityPlayer player) 
	{
		
	}

	@Override
	public void handleServerSide(PacketCapabilities message, EntityPlayer player) 
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		DinocraftEntityActions actions = dinoEntity.getActionsModule();
		
		if (player != null)
		{
			if (message.capability == Capability.FALL_DAMAGE)
			{
				dinoEntity.setFallDamage(message.state);
			}
			else if (message.capability == Capability.DOUBLE_JUMP)
			{
				actions.setCanDoubleJumpAgain(message.state);
			}
			else if (message.capability == Capability.DOUBLE_JUMPED)
			{
				actions.setHasDoubleJumped(message.state);
			}
			else if (message.capability == Capability.FALL_DISTANCE)
			{
				player.fallDistance = 0.0F;
			}
			else if (message.capability == Capability.REDUCTION)
			{
				dinoEntity.setFallDamageReductionAmount(4.0F);
			}
		}
	}
}