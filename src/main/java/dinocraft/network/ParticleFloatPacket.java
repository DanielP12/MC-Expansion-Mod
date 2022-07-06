package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ParticleFloatPacket<REQ extends IMessage> extends AbstractPacket<REQ>
{
	protected float x, y, z;

	public ParticleFloatPacket()
	{
		
	}

	public ParticleFloatPacket(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeFloat(this.x);
		buffer.writeFloat(this.y);
		buffer.writeFloat(this.z);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.x = buffer.readFloat();
		this.y = buffer.readFloat();
		this.z = buffer.readFloat();
	}

	public TargetPoint getTargetPoint(World world, double range)
	{
		return new TargetPoint(world.provider.getDimension(), this.x, this.y, this.z, range);
	}
}