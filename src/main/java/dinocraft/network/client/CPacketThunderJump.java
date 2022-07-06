package dinocraft.network.client;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.network.AbstractPacket;
import dinocraft.util.server.DinocraftServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class CPacketThunderJump extends AbstractPacket<CPacketThunderJump>
{
	@Override
	public void fromBytes(ByteBuf buffer)
	{

	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		
	}
	
	@Override
	public void handleClientSide(CPacketThunderJump message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketThunderJump message, EntityPlayer player)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		dinoEntity.setFallDamageReductionAmount(5.0F);
		player.fallDistance = 0.0F;
		ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

		if (stack.getItem() == DinocraftItems.SPLICENTS_CHESTPLATE && player.world.rand.nextInt(2) == 0)
		{
			stack.damageItem(1, player);
		}

		float f0 = player.height / 2.0F;
		DinocraftServer.spawnElectricParticles(player.world, 30, 15, 25, player.posX, player.posY + f0, player.posZ, player.width, f0, player.width);
		EntityLightningBolt lightning = new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true);
		player.world.addWeatherEffect(lightning);
		List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().grow(2.5F),
				entity -> entity instanceof EntityLivingBase && entity.canBeCollidedWith() && entity.posY == player.posY);

		for (Entity entity : entities)
		{
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 5.0F);
			entity.setFire(8);
			double d = entity.posX - player.posX;
			double d1;
			
			for (d1 = entity.posZ - player.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
			{
				d = (Math.random() - Math.random()) * 0.01D;
			}
			
			float f = MathHelper.sqrt(d * d + d1 * d1);
			entity.motionX /= 2.0D;
			entity.motionY /= 2.0D;
			entity.motionZ /= 2.0D;
			entity.motionX += d / f;
			entity.motionY += 0.5D;
			entity.motionZ += d1 / f;
		}
	}
}