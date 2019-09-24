package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;

public class MessageClearChat extends AbstractPacket<MessageClearChat> 
{
	public MessageClearChat()
	{

	}
	
    @Override
    public void toBytes(ByteBuf buffer)
    {
    	
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    	
    }

	@Override
	public void handleClientSide(MessageClearChat message, EntityPlayer player) 
	{
		GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
		chat.clearChatMessages(true);
	}

	@Override
	public void handleServerSide(MessageClearChat message, EntityPlayer player) 
	{
		
	}
}