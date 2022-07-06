package dinocraft.network.server;

import dinocraft.network.ParticlePositionPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class SPacketSpawnParticles extends ParticlePositionPacket<SPacketSpawnParticles>
{
	protected float xSpeed, ySpeed, zSpeed;
	protected EnumParticleTypes particleType;
	protected int[] particleArguments;
	protected int particleCount;
	
	public SPacketSpawnParticles()
	{

	}
	
	public SPacketSpawnParticles(EnumParticleTypes particle, int particleCount, float xCoord, float yCoord, float zCoord,
			float xOffset, float yOffset, float zOffset, float xSpeed, float ySpeed, float zSpeed, int... parameters)
	{
		super(xCoord, yCoord, zCoord, xOffset, yOffset, zOffset);
		this.particleType = particle;
		this.particleCount = particleCount;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		this.particleArguments = parameters;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeInt(this.particleType.getParticleID());
		buffer.writeInt(this.particleCount);
		buffer.writeFloat(this.xSpeed);
		buffer.writeFloat(this.ySpeed);
		buffer.writeFloat(this.zSpeed);
		int j = this.particleType.getArgumentCount();

		for (int i = 0; i < j; ++i)
		{
			int arg = this.particleArguments[i];
			
			while ((arg & -128) != 0)
			{
				buffer.writeByte(arg & 127 | 128);
				arg >>>= 7;
			}

			buffer.writeByte(arg);
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.particleType = EnumParticleTypes.getParticleFromId(buffer.readInt());
		this.particleCount = buffer.readInt();
		this.xSpeed = buffer.readFloat();
		this.ySpeed = buffer.readFloat();
		this.zSpeed = buffer.readFloat();
		int k = this.particleType.getArgumentCount();
		this.particleArguments = new int[k];

		for (int l = 0; l < k; ++l)
		{
			int i = 0;
			int j = 0;

			while (true)
			{
				byte b0 = buffer.readByte();
				i |= (b0 & 127) << j++ * 7;

				if (j > 5)
				{
					throw new RuntimeException("Integer too big");
				}

				if ((b0 & 128) != 128)
				{
					break;
				}
			}
			
			this.particleArguments[j] = i;
		}
	}
	
	@Override
	public void handleClientSide(SPacketSpawnParticles message, EntityPlayer player)
	{
		if (message.particleType == EnumParticleTypes.SPELL_MOB)
		{
			for (int i = 0; i < message.particleCount; i++)
			{
				player.world.spawnParticle(message.particleType, true,
						message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
						message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
						message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
						message.xSpeed, message.ySpeed, message.zSpeed, message.particleArguments);
			}
		}
		else
		{
			for (int i = 0; i < message.particleCount; i++)
			{
				player.world.spawnParticle(message.particleType, true,
						message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
						message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
						message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
						player.world.rand.nextGaussian() * message.xSpeed, player.world.rand.nextGaussian() * message.ySpeed,
						player.world.rand.nextGaussian() * message.zSpeed, message.particleArguments);
			}
		}
	}
	
	@Override
	public void handleServerSide(SPacketSpawnParticles message, EntityPlayer player)
	{

	}
}