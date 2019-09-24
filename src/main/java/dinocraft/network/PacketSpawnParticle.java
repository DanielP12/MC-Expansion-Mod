package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class PacketSpawnParticle extends ParticleDoublePacket<PacketSpawnParticle>
{
    private double dx, dy, dz;
    private int particleId, parameters;
    private boolean ignoreRange;

    public PacketSpawnParticle()
    {
    	
    }

    public PacketSpawnParticle(EnumParticleTypes particle, boolean ignoreRange, double x, double y, double z, double dx, double dy, double dz, int parameters) 
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
        buffer.writeDouble(this.dx);
        buffer.writeDouble(this.dy);
        buffer.writeDouble(this.dz);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        super.fromBytes(buffer);
        this.ignoreRange = buffer.readBoolean();
        this.particleId = buffer.readInt();
        this.parameters = buffer.readInt();
        this.dx = buffer.readDouble();
        this.dy = buffer.readDouble();
        this.dz = buffer.readDouble();
    }

    @Override
    public void handleClientSide(PacketSpawnParticle message, EntityPlayer player) 
    {
    	player.world.spawnParticle(EnumParticleTypes.values()[message.particleId], message.ignoreRange, message.x, message.y, message.z, message.dx, message.dy, message.dz, message.parameters);
    }

    @Override
    public void handleServerSide(PacketSpawnParticle message, EntityPlayer player)
    {
    	
    }
}