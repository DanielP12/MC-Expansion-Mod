package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessagePing extends AbstractPacket<MessagePing> 
{	
	public MessagePing()
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
	public void handleClientSide(MessagePing message, EntityPlayer playerIn) 
	{
		Minecraft mc = Minecraft.getMinecraft();
		NetworkPlayerInfo playerinfo = mc.getConnection().getPlayerInfo(playerIn.getUniqueID());
		int ping = playerinfo.getResponseTime();

		if (mc.getConnection() != null && playerinfo != null)
		{
			playerIn.sendMessage(new TextComponentString("Your connection is " + (ping <= 100 ? TextFormatting.GREEN : ping <= 200 ? TextFormatting.DARK_GREEN : ping <= 300 ? TextFormatting.GOLD : TextFormatting.RED) + Integer.toString(ping) + TextFormatting.RESET + " ms ping!"));
		}
	}

	@Override
	public void handleServerSide(MessagePing message, EntityPlayer playerIn) 
	{

	}
}