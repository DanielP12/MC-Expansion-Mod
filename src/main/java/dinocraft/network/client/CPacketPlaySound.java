package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class CPacketPlaySound extends AbstractPacket<CPacketPlaySound>
{
	private double x, y, z;
	private int soundID, categoryID;
	private float volume, pitch;

	public CPacketPlaySound()
	{

	}
	
	public CPacketPlaySound(double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.soundID = SoundEvent.REGISTRY.getIDForObject(sound);
		this.categoryID = category.ordinal();
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeDouble(this.x);
		buffer.writeDouble(this.y);
		buffer.writeDouble(this.z);
		buffer.writeInt(this.soundID);
		buffer.writeInt(this.categoryID);
		buffer.writeFloat(this.volume);
		buffer.writeFloat(this.pitch);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.x = buffer.readDouble();
		this.y = buffer.readDouble();
		this.z = buffer.readDouble();
		this.soundID = buffer.readInt();
		this.categoryID = buffer.readInt();
		this.volume = buffer.readFloat();
		this.pitch = buffer.readFloat();
	}
	
	@Override
	public void handleClientSide(CPacketPlaySound message, EntityPlayer player)
	{

	}
	
	@Override
	public void handleServerSide(CPacketPlaySound message, EntityPlayer player)
	{
		player.world.playSound(null, message.x, message.y, message.z, SoundEvent.REGISTRY.getObjectById(message.soundID), SoundCategory.values()[message.categoryID], message.volume, message.pitch);
	}
}