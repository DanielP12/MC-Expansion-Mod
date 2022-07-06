package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import dinocraft.util.server.DinocraftServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class CPacketSpawnParticle extends AbstractPacket<CPacketSpawnParticle>
{
	private double x, y, z, dx, dy, dz;
	private int particleId, parameters;
	private boolean longRange;

	public CPacketSpawnParticle()
	{
		
	}

	public CPacketSpawnParticle(EnumParticleTypes particle, boolean longRange, double x, double y, double z, double dx, double dy, double dz, int parameters)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.longRange = longRange;
		this.parameters = parameters;
		this.particleId = particle.ordinal();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeDouble(this.x);
		buffer.writeDouble(this.y);
		buffer.writeDouble(this.z);
		buffer.writeBoolean(this.longRange);
		buffer.writeInt(this.particleId);
		buffer.writeInt(this.parameters);
		buffer.writeDouble(this.dx);
		buffer.writeDouble(this.dy);
		buffer.writeDouble(this.dz);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.x = buffer.readDouble();
		this.y = buffer.readDouble();
		this.z = buffer.readDouble();
		this.longRange = buffer.readBoolean();
		this.particleId = buffer.readInt();
		this.parameters = buffer.readInt();
		this.dx = buffer.readDouble();
		this.dy = buffer.readDouble();
		this.dz = buffer.readDouble();
	}

	@Override
	public void handleClientSide(CPacketSpawnParticle message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(CPacketSpawnParticle message, EntityPlayer player)
	{
		DinocraftServer.spawnParticle(EnumParticleTypes.values()[message.particleId], message.longRange, player.world, (float) message.x, (float) message.y, (float) message.z, (float) message.dx, (float) message.dy, (float) message.dz, message.parameters);
	}
}