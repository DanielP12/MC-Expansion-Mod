package dinocraft.network.server;

import dinocraft.network.ParticlePositionPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class SPacketElectricParticles extends ParticlePositionPacket<SPacketElectricParticles>
{
	private int particleCount1, particleCount2;
	private byte degreeOfElectricity;

	public SPacketElectricParticles()
	{
		
	}

	public SPacketElectricParticles(int count1, int count2, byte degreeOfElectricity, float xCoord, float yCoord, float zCoord, float xOffset, float yOffset, float zOffset)
	{
		super(xCoord, yCoord, zCoord, xOffset, yOffset, zOffset);
		this.particleCount1 = count1;
		this.particleCount2 = count2;
		this.degreeOfElectricity = degreeOfElectricity < 1 ? 1 : degreeOfElectricity > 100 ? 100 : degreeOfElectricity;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeInt(this.particleCount1);
		buffer.writeInt(this.particleCount2);
		buffer.writeByte(this.degreeOfElectricity);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.particleCount1 = buffer.readInt();
		this.particleCount2 = buffer.readInt();
		this.degreeOfElectricity = buffer.readByte();
	}

	@Override
	public void handleClientSide(SPacketElectricParticles message, EntityPlayer player)
	{
		for (int i = 0; i < message.particleCount1; ++i)
		{
			player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					20, message.degreeOfElectricity, 0);
			player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					20, 60 + message.degreeOfElectricity, 0);
			player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
					message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
					message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
					20, 120 + message.degreeOfElectricity, 0);
		}
		
		for (int i = 0; i < message.particleCount2; ++i)
		{
			if (message.degreeOfElectricity < 25)
			{
				player.world.spawnParticle(player.world.rand.nextBoolean() ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.FIREWORKS_SPARK, true,
						message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
						message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
						message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
						0, 0, 0);
			}
			else
			{
				player.world.spawnParticle(player.world.rand.nextBoolean() ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.SMOKE_LARGE, true,
						message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
						message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
						message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
						player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F);
				
				if (message.degreeOfElectricity >= 50)
				{
					player.world.spawnParticle(EnumParticleTypes.FLAME, true,
							message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
							message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
							message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
							player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F);
					
					if (message.degreeOfElectricity >= 75)
					{
						player.world.spawnParticle(EnumParticleTypes.LAVA, true,
								message.xCoord - message.xOffset + 2.0F * message.xOffset * player.world.rand.nextFloat(),
								message.yCoord - message.yOffset + 2.0F * message.yOffset * player.world.rand.nextFloat(),
								message.zCoord - message.zOffset + 2.0F * message.zOffset * player.world.rand.nextFloat(),
								player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F);
					}
				}
			}
		}
	}

	@Override
	public void handleServerSide(SPacketElectricParticles message, EntityPlayer player)
	{
		
	}
}