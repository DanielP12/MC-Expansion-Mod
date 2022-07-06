package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CPacketEntityEffect extends AbstractPacket<CPacketEntityEffect>
{
	private int potionID;
	private int duration, amplifier;
	private boolean ambient, showParticles;

	public CPacketEntityEffect()
	{

	}
	
	public CPacketEntityEffect(Potion effect, int duration, int amplifier, boolean ambient, boolean showParticles)
	{
		this.potionID = effect.getIdFromPotion(effect);
		this.duration = duration;
		this.amplifier = amplifier;
		this.ambient = ambient;
		this.showParticles = showParticles;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.potionID);
		buffer.writeInt(this.duration);
		buffer.writeInt(this.amplifier);
		buffer.writeBoolean(this.ambient);
		buffer.writeBoolean(this.showParticles);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.potionID = buffer.readInt();
		this.duration = buffer.readInt();
		this.amplifier = buffer.readInt();
		this.ambient = buffer.readBoolean();
		this.showParticles = buffer.readBoolean();
	}
	
	@Override
	public void handleClientSide(CPacketEntityEffect message, EntityPlayer player)
	{

	}
	
	@Override
	public void handleServerSide(CPacketEntityEffect message, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(message.potionID), message.duration, message.amplifier, message.ambient, message.showParticles));
	}
}