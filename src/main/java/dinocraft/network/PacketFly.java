package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketFly extends AbstractPacket<PacketFly> 
{
	private boolean flag;

	public PacketFly()
	{
		
	}
	
    public PacketFly(boolean flag)
    {
        this.flag = flag;
    }
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
        buffer.writeBoolean(this.flag);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
        this.flag = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(PacketFly message, EntityPlayer player) 
	{
		player.capabilities.allowFlying = message.flag;
		player.capabilities.isFlying = message.flag;
	}

	@Override
	public void handleServerSide(PacketFly message, EntityPlayer player) 
	{

	}
}