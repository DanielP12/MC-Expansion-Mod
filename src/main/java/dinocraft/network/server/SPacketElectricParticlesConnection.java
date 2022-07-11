package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import dinocraft.util.VectorHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;

public class SPacketElectricParticlesConnection extends AbstractPacket<SPacketElectricParticlesConnection>
{
	private int entityId1, entityId2;
	private byte degreeOfElectricity;

	public SPacketElectricParticlesConnection()
	{

	}
	
	public SPacketElectricParticlesConnection(EntityLivingBase entity1, EntityLivingBase entity2, byte degreeOfElectricity)
	{
		this.entityId1 = entity1.getEntityId();
		this.entityId2 = entity2.getEntityId();
		this.degreeOfElectricity = degreeOfElectricity < 1 ? 1 : degreeOfElectricity > 100 ? 100 : degreeOfElectricity;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.entityId1);
		buffer.writeInt(this.entityId2);
		buffer.writeByte(this.degreeOfElectricity);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.entityId1 = buffer.readInt();
		this.entityId2 = buffer.readInt();
		this.degreeOfElectricity = buffer.readByte();
	}
	
	@Override
	public void handleClientSide(SPacketElectricParticlesConnection message, EntityPlayer player)
	{
		Vec3d vec = VectorHelper.fromEntityCenter(player.world.getEntityByID(message.entityId1)).toVec3D();
		Vec3d vec1 = VectorHelper.fromEntityCenter(player.world.getEntityByID(message.entityId2)).toVec3D();
		Vec3d vec2 = vec.subtract(vec1).normalize();

		for (double i = 0.0D; i < vec1.distanceTo(vec); i += 0.25D)
		{
			if (i % 0.5D == 0)
			{
				player.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, vec1.x + vec2.x * i, vec1.y + vec2.y * i,
						vec1.z + vec2.z * i, 20, message.degreeOfElectricity + 60 * player.world.rand.nextInt(3), 0);
			}
			else
			{
				if (message.degreeOfElectricity < 25)
				{
					player.world.spawnParticle(player.world.rand.nextBoolean() ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.FIREWORKS_SPARK,
							true, vec1.x + vec2.x * i, vec1.y + vec2.y * i, vec1.z + vec2.z * i, 0, 0, 0);
				}
				else
				{
					player.world.spawnParticle(player.world.rand.nextBoolean() ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.SMOKE_LARGE,
							true, vec1.x + vec2.x * i, vec1.y + vec2.y * i, vec1.z + vec2.z * i,
							player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F);

					if (message.degreeOfElectricity < 75)
					{
						player.world.spawnParticle(EnumParticleTypes.FLAME, true, vec1.x + vec2.x * i, vec1.y + vec2.y * i, vec1.z + vec2.z * i,
								player.world.rand.nextGaussian() * 0.025F, player.world.rand.nextGaussian() * 0.025F, player.world.rand.nextGaussian() * 0.025F);
					}
					else
					{
						player.world.spawnParticle(EnumParticleTypes.LAVA, true, vec1.x + vec2.x * i, vec1.y + vec2.y * i, vec1.z + vec2.z * i,
								player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F, player.world.rand.nextGaussian() * 0.0025F);
					}
				}
			}
		}
	}
	
	@Override
	public void handleServerSide(SPacketElectricParticlesConnection message, EntityPlayer player)
	{

	}
}