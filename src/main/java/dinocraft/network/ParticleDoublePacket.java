package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ParticleDoublePacket<REQ extends IMessage> extends AbstractPacket<REQ>
{
    protected double x, y, z;

    public ParticleDoublePacket()
    {
    	
    }

    public ParticleDoublePacket(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
    	buffer.writeDouble(this.x);
    	buffer.writeDouble(this.y);
    	buffer.writeDouble(this.z);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    	this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public TargetPoint getTargetPoint(World world)
    {
        return this.getTargetPoint(world, 400.0D);
    }

    public TargetPoint getTargetPoint(World world, double updateDistance)
    {
        return new TargetPoint(world.provider.getDimension(), x, y, z, updateDistance);
    }
}