package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SPacketClearChat extends AbstractPacket<SPacketClearChat>
{
	public SPacketClearChat()
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
	public void handleClientSide(SPacketClearChat message, EntityPlayer player)
	{
		Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(true);
	}
	
	@Override
	public void handleServerSide(SPacketClearChat message, EntityPlayer player)
	{

	}
}