package dinocraft.network.server;

import dinocraft.network.ParticleFloatPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class SPacketSpawnParticle extends ParticleFloatPacket<SPacketSpawnParticle>
{
	private float dx, dy, dz;
	private int particleId, parameters;
	private boolean ignoreRange;
	
	public SPacketSpawnParticle()
	{
		
	}

	public SPacketSpawnParticle(EnumParticleTypes particle, boolean ignoreRange, float x, float y, float z, float dx, float dy, float dz, int parameters)
	{
		super(x, y, z);
		this.ignoreRange = ignoreRange;
		this.parameters = parameters;
		this.particleId = particle.ordinal();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeBoolean(this.ignoreRange);
		buffer.writeInt(this.particleId);
		buffer.writeInt(this.parameters);
		buffer.writeFloat(this.dx);
		buffer.writeFloat(this.dy);
		buffer.writeFloat(this.dz);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.ignoreRange = buffer.readBoolean();
		this.particleId = buffer.readInt();
		this.parameters = buffer.readInt();
		this.dx = buffer.readFloat();
		this.dy = buffer.readFloat();
		this.dz = buffer.readFloat();
	}

	@Override
	public void handleClientSide(SPacketSpawnParticle message, EntityPlayer player)
	{
		player.world.spawnParticle(EnumParticleTypes.values()[message.particleId], message.ignoreRange, message.x, message.y, message.z, message.dx, message.dy, message.dz, message.parameters);
	}

	@Override
	public void handleServerSide(SPacketSpawnParticle message, EntityPlayer player)
	{
		
	}
}