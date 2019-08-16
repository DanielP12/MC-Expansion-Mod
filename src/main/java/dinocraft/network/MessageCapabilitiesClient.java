package dinocraft.network;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerActions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageCapabilitiesClient extends AbstractPacket<MessageCapabilitiesClient> 
{
	private Capability capability;
	
	private boolean state;
	
	public MessageCapabilitiesClient()
	{

	}

	public MessageCapabilitiesClient(Capability capability, boolean state)
	{
		this.capability = capability;
		this.state = state;
	}
	
	public MessageCapabilitiesClient(Capability capability)
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
		DOUBLE_JUMPED, DOUBLE_JUMP, FALL_DAMAGE, INVULNERABLE
	}

	@Override
	public void handleClientSide(MessageCapabilitiesClient message, EntityPlayer playerIn) 
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		DinocraftPlayerActions actions = player.getActions();
		
		if (player != null)
		{
			if (message.capability == Capability.FALL_DAMAGE)
			{
				player.setFallDamage(message.state);
			}
			else if (message.capability == Capability.INVULNERABLE)
			{
				player.setInvulnerable(100);
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

	@Override
	public void handleServerSide(MessageCapabilitiesClient message, EntityPlayer playerIn) 
	{
		
	}
}

