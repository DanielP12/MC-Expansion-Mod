package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ParticlePositionPacket<REQ extends IMessage> extends AbstractPacket<REQ>
{
	protected float xCoord, yCoord, zCoord;
	protected float xOffset, yOffset, zOffset;

	public ParticlePositionPacket()
	{

	}
	
	public ParticlePositionPacket(float xCoord, float yCoord, float zCoord, float xOffset, float yOffset, float zOffset)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeFloat(this.xCoord);
		buffer.writeFloat(this.yCoord);
		buffer.writeFloat(this.zCoord);
		buffer.writeFloat(this.xOffset);
		buffer.writeFloat(this.yOffset);
		buffer.writeFloat(this.zOffset);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.xCoord = buffer.readFloat();
		this.yCoord = buffer.readFloat();
		this.zCoord = buffer.readFloat();
		this.xOffset = buffer.readFloat();
		this.yOffset = buffer.readFloat();
		this.zOffset = buffer.readFloat();
	}
	
	public TargetPoint getTargetPoint(World world, double range)
	{
		return new TargetPoint(world.provider.getDimension(), this.xCoord, this.yCoord, this.zCoord, range);
	}
}