package dinocraft.network.reach;

import dinocraft.capabilities.entity.DinocraftEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExtendedReachAttack implements IMessage 
{
    private int entityId;

    public MessageExtendedReachAttack() 
    { 

    }

	public MessageExtendedReachAttack(int entityId) 
    {
    	this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buffer) 
    {
    	this.entityId = ByteBufUtils.readVarInt(buffer, 4);
    }

    @Override
    public void toBytes(ByteBuf buffer) 
    {
    	ByteBufUtils.writeVarInt(buffer, this.entityId, 4);
    }

    public static class Handler implements IMessageHandler<MessageExtendedReachAttack, IMessage> 
    {
        @Override
        public IMessage onMessage(final MessageExtendedReachAttack message, MessageContext context) 
        {
            EntityPlayerMP player = context.getServerHandler().player;
            player.getServer().addScheduledTask(
            		new Runnable()
            		{
            			@Override
            			public void run() 
            			{
            				Entity entity = player.world.getEntityByID(message.entityId);

            				/*
            				if (player.getHeldItemMainhand() == null)
            				{
            					return;
                         	}
            				
            				if (player.getHeldItemMainhand().getItem() instanceof IExtendedReach)
            				{
            					IExtendedReach item = (IExtendedReach) player.getHeldItemMainhand().getItem();
                                    
            					if (item.getReach() * item.getReach() >= player.getDistanceSq(entity))
            					{
            						player.attackTargetEntityWithCurrentItem(entity);
            					}
            				}
            				*/
            				
            				//Item item = player.getHeldItemMainhand().getItem();
                            DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
                            double reach = dinoEntity.getAttackReach();
                            
            				if (reach * reach >= player.getDistanceSq(entity))
            				{
            					player.attackTargetEntityWithCurrentItem(entity);
            				}
            				
            				return;
            			}
            		}
            );
            return null;
        }
    }
}