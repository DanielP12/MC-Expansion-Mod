package dinocraft.network;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerActions;
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
		DOUBLE_JUMPED, DOUBLE_JUMP, FALL_DAMAGE
	}

	@Override
	public void handleClientSide(PacketCapabilities message, EntityPlayer playerIn) 
	{
		
	}

	@Override
	public void handleServerSide(PacketCapabilities message, EntityPlayer playerIn) 
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		DinocraftPlayerActions actions = player.getActions();
		
		if (player != null)
		{
			if (message.capability == Capability.FALL_DAMAGE)
			{
				player.setFallDamage(message.state);
			}
			else if (message.capability == Capability.DOUBLE_JUMP)
			{
				actions.setCanDoubleJumpAgain(message.state);
			}
			else if (message.capability == Capability.DOUBLE_JUMPED)
			{
				actions.setHasDoubleJumped(message.state);
			}
		}
	}
}
