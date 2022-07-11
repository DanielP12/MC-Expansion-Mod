package dinocraft.network.server;

import dinocraft.network.ParticlePositionPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class SPacketDreadedParticles extends ParticlePositionPacket<SPacketDreadedParticles>
{
	private int particleCount1, particleCount2;

	public SPacketDreadedParticles()
	{
		
	}

	public SPacketDreadedParticles(int count1, int count2, float xCoord, float yCoord, float zCoord, float xOffset, float yOffset, float zOffset)
	{
		super(xCoord, yCoord, zCoord, xOffset, yOffset, zOffset);
		this.particleCount1 = count1;
		this.particleCount2 = count2;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeInt(this.particleCount1);
		buffer.writeInt(this.particleCount2);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.particleCount1 = buffer.readInt();
		this.particleCount2 = buffer.readInt();
	}

	@Override
	public void handleClientSide(SPacketDreadedParticles message, EntityPlayer player)
	{
		for (int i = 0; i < message.particleCount1; ++i)
		{
			player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					150, player.world.rand.nextInt(51) + 150, 100);
			player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					player.world.rand.nextInt(21) + 130, 200, 200);
		}
		
		for (int i = 0; i < message.particleCount2; ++i)
		{
			player.world.spawnParticle(player.world.rand.nextBoolean() ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.SMOKE_LARGE, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					player.world.rand.nextGaussian() * 0.001F, player.world.rand.nextGaussian() * 0.001F, player.world.rand.nextGaussian() * 0.001F, 0);
			player.world.spawnParticle(EnumParticleTypes.FALLING_DUST, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					0, 0, 0, 0);
		}
	}

	@Override
	public void handleServerSide(SPacketDreadedParticles message, EntityPlayer player)
	{
		
	}
}