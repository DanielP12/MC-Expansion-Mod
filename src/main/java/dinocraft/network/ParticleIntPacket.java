package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ParticleIntPacket<REQ extends IMessage> extends AbstractPacket<REQ>
{
    protected int x, y, z;
    
    public ParticleIntPacket(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void toBytes(ByteBuf buffer) 
    {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
    }
    
    public void fromBytes(ByteBuf buffer) 
    {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }
    
    public TargetPoint getTargetPoint(World world) 
    {
        return this.getTargetPoint(world, 400.0D);
    }
    
    public TargetPoint getTargetPoint(World world, double updateDistance) 
    {
        return new TargetPoint(world.provider.getDimension(), this.x, this.y, this.z, updateDistance);
    }
}