package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
    	buffer.writeDouble(x);
    	buffer.writeDouble(y);
    	buffer.writeDouble(z);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
    }

    public NetworkRegistry.TargetPoint getTargetPoint(World worldIn)
    {
        return getTargetPoint(worldIn, 64);
    }

    public NetworkRegistry.TargetPoint getTargetPoint(World worldIn, double updateDistance)
    {
        return new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), x, y, z, updateDistance);
    }
}
