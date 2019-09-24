package dinocraft.network;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
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

	@Override
	public void toBytes(ByteBuf buffer)
	{
        buffer.writeBoolean(this.state);
		buffer.writeByte(this.capability.ordinal());
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
        this.state = buffer.readBoolean();
		this.capability = Capability.values()[buffer.readByte()];
	}

	public enum Capability
	{
		DOUBLE_JUMPED, DOUBLE_JUMP, FALL_DAMAGE, INVULNERABLE, SPEED, VANISH
	}

	@Override
	public void handleClientSide(MessageCapabilitiesClient message, EntityPlayer player) 
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		DinocraftEntityActions actions = dinoEntity.getActionsModule();
		
		if (player != null)
		{
			if (message.capability == Capability.FALL_DAMAGE)
			{
				dinoEntity.setFallDamage(message.state);
			}
			else if (message.capability == Capability.INVULNERABLE)
			{
				dinoEntity.setInvulnerable(100);
			}
			else if (message.capability == Capability.DOUBLE_JUMP)
			{
				actions.setCanDoubleJumpAgain(message.state);
			}
			else if (message.capability == Capability.DOUBLE_JUMPED)
			{
				actions.setHasDoubleJumped(message.state);
			}
			else if (message.capability == Capability.SPEED)
			{
				if (message.state)
				{
					dinoEntity.freeze();
				}
				else
				{
					dinoEntity.unFreeze();
				}
			}
			else if (message.capability == Capability.VANISH)
			{
				dinoEntity.vanish();
			}
		}
	}

	@Override
	public void handleServerSide(MessageCapabilitiesClient message, EntityPlayer player) 
	{
		
	}
}