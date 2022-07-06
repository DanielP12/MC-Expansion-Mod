package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketChatMsgAsPlayer extends AbstractPacket<SPacketChatMsgAsPlayer>
{
	private String message;
	private boolean addToChat;
	
	public SPacketChatMsgAsPlayer()
	{
		
	}
	
	public SPacketChatMsgAsPlayer(String message, boolean addToChat)
	{
		this.message = message;
		this.addToChat = addToChat;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.message);
		buffer.writeBoolean(this.addToChat);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.message = ByteBufUtils.readUTF8String(buffer);
		this.addToChat = buffer.readBoolean();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(SPacketChatMsgAsPlayer message, EntityPlayer player)
	{
		message.message = ForgeEventFactory.onClientSendMessage(message.message);
		
		if (!message.message.isEmpty())
		{
			Minecraft minecraft = Minecraft.getMinecraft();

			if (message.addToChat)
			{
				minecraft.ingameGUI.getChatGUI().addToSentMessages(message.message);
			}
			
			if (ClientCommandHandler.instance.executeCommand(player, message.message) == 0)
			{
				minecraft.player.sendChatMessage(message.message);
			}
		}
	}
	
	@Override
	public void handleServerSide(SPacketChatMsgAsPlayer message, EntityPlayer player)
	{
		
	}
}