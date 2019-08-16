package dinocraft.network;

import dinocraft.Dinocraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(REQ message, MessageContext ctx)
    {
        if (ctx.side == Side.SERVER)
        {
            this.handleServerSide(message, ctx.getServerHandler().player);
        } 
        else
        {
        	this.handleClientSide(message, Dinocraft.PROXY.getPlayer());
        }
        
        return null;
    }

    public abstract void handleClientSide(REQ message, EntityPlayer playerIn);

    public abstract void handleServerSide(REQ message, EntityPlayer playerIn);
}
