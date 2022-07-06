package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

public class CPacketFallingCrystalEntity extends AbstractPacket<CPacketFallingCrystalEntity>
{
	private int entityId, shooterId, seconds;
	
	public CPacketFallingCrystalEntity()
	{
		
	}
	
	public CPacketFallingCrystalEntity(Entity shooter, Entity entity, int seconds)
	{
		this.entityId = entity.getEntityId();
		this.shooterId = shooter.getEntityId();
		this.seconds = seconds;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.entityId);
		buffer.writeInt(this.shooterId);
		buffer.writeInt(this.seconds);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.entityId = buffer.readInt();
		this.shooterId = buffer.readInt();
		this.seconds = buffer.readInt();
	}
	
	@Override
	public void handleClientSide(CPacketFallingCrystalEntity message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketFallingCrystalEntity message, EntityPlayer player)
	{
		Entity entity = player.world.getEntityByID(message.entityId);
		Entity shooter = player.world.getEntityByID(message.shooterId);

		if (entity instanceof EntityLivingBase && shooter instanceof EntityLivingBase)
		{
			if (shooter instanceof EntityPlayer)
			{
				((EntityPlayer) shooter).getCooldownTracker().setCooldown(DinocraftItems.FALLEN_CRYSTALS, 100);
			}

			shooter.world.playSound(null, shooter.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, shooter.world.rand.nextFloat() + 0.5F);
			DinocraftEntity.getEntity((EntityLivingBase) entity).getActionsModule().setFallingCrystals((EntityLivingBase) shooter, message.seconds);
		}
	}
}