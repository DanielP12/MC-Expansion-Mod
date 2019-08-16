package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;

public class MessageAbsorption extends AbstractPacket<MessageAbsorption> 
{
	private float amount;

    public MessageAbsorption()
    {
    
    }

    public MessageAbsorption(float amount)
    {
        this.amount = amount;
    }
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
        buffer.writeFloat(this.amount);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
        this.amount = buffer.readFloat();
	}

	@Override
	public void handleClientSide(MessageAbsorption message, EntityPlayer playerIn)
	{
		
	}

	@Override
	public void handleServerSide(MessageAbsorption message, EntityPlayer playerIn)
	{
		playerIn.setAbsorptionAmount(message.amount);
	}
}
