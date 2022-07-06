package dinocraft.network;

import dinocraft.Dinocraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
	@Override
	public REQ onMessage(REQ message, MessageContext context)
	{
		if (context.side == Side.SERVER)
		{
			EntityPlayerMP player = context.getServerHandler().player;
			player.getServer().addScheduledTask(() -> AbstractPacket.this.handleServerSide(message, player));
		}
		else
		{
			Minecraft.getMinecraft().addScheduledTask(() -> AbstractPacket.this.handleClientSide(message, Dinocraft.PROXY.getPlayer()));
		}

		return null;
	}
	
	public abstract void handleClientSide(REQ message, EntityPlayer player);
	
	public abstract void handleServerSide(REQ message, EntityPlayer player);
}