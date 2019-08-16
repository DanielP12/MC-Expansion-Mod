package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CapFly extends AbstractPacket<CapFly> 
{
	private boolean flag;

    public CapFly()
    {
    
    }

    public CapFly(boolean flag)
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
	public void handleClientSide(CapFly message, EntityPlayer playerIn) 
	{
		playerIn.capabilities.allowFlying = message.flag;
		playerIn.capabilities.isFlying = message.flag;
	}

	@Override
	public void handleServerSide(CapFly message, EntityPlayer playerIn) 
	{

	}
}
