package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CPacketReachAttack extends AbstractPacket<CPacketReachAttack>
{
	private int entityId;
	
	public CPacketReachAttack()
	{
		
	}
	
	public CPacketReachAttack(Entity entity)
	{
		this.entityId = entity.getEntityId();
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.entityId);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.entityId = buffer.readInt();
	}
	
	@Override
	public void handleClientSide(CPacketReachAttack message, EntityPlayer player)
	{
		
	}
	
	@Override
	public void handleServerSide(CPacketReachAttack message, EntityPlayer player)
	{
		Entity entity = player.world.getEntityByID(message.entityId);
		//
		//		if (entity instanceof EntityLivingBase)
		//		{
		//			double reach = DinocraftEntity.getEntity(player).getAttackReach();
		//
		//			if (reach * reach >= player.getDistanceSq(entity))
		//			{
		player.attackTargetEntityWithCurrentItem(entity);
		//			}
		//		}
	}
}