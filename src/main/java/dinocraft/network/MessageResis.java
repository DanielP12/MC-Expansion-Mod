package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class MessageResis extends AbstractPacket<MessageResis> 
{
	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		
	}

	@Override
	public void toBytes(ByteBuf buffer) 
	{

	}

	@Override
	public void handleClientSide(MessageResis message, EntityPlayer playerIn) 
	{

	}

	@Override
	public void handleServerSide(MessageResis message, EntityPlayer playerIn) 
	{
		if (playerIn.getHealth() <= 5) playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4, 0, false, false));
	}
}

