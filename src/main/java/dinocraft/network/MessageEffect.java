package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class MessageEffect extends AbstractPacket<MessageEffect> 
{
	private int duration, amplifier;
	private Potion effect;
	
    public MessageEffect()
    {
    	
    }

    public MessageEffect(Potion effect, int duration, int amplifier) 
    {
    	this.effect = effect;
    	this.duration = duration;
        this.amplifier = amplifier;
    }
	
	@Override
	public void toBytes(ByteBuf buffer) 
	{
        buffer.writeInt(this.duration);
        buffer.writeInt(this.amplifier);
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		this.duration = buffer.readInt();
		this.amplifier = buffer.readInt();
	}

	@Override
	public void handleClientSide(MessageEffect message, EntityPlayer player) 
	{
		
	}

	@Override
	public void handleServerSide(MessageEffect message, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(message.effect, message.duration, message.amplifier, false, false));

		//player.addPotionEffect(new PotionEffect(Potion.getPotionById(message.potionID), message.duration, message.amplifier, false, false));
	}
}