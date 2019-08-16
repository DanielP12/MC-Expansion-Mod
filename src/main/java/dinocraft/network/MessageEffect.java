package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class MessageEffect extends AbstractPacket<MessageEffect> 
{
	private int potionID, duration, amplifier;

    public MessageEffect()
    {
    	
    }

    public MessageEffect(int potionID, int duration, int amplifier) 
    {
        this.potionID = potionID;
        this.duration = duration;
        this.amplifier = amplifier;
    }
	
	@Override
	public void toBytes(ByteBuf buffer) 
	{
        buffer.writeInt(this.potionID);
        buffer.writeInt(this.duration);
        buffer.writeInt(this.amplifier);
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		this.potionID = buffer.readInt();
		this.duration = buffer.readInt();
		this.amplifier = buffer.readInt();
	}

	@Override
	public void handleClientSide(MessageEffect message, EntityPlayer playerIn) 
	{
		
	}

	@Override
	public void handleServerSide(MessageEffect message, EntityPlayer playerIn)
	{
		playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(message.potionID), message.duration, message.amplifier, false, false));
	}
}