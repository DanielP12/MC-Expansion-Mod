package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SPacketCrashPlayer extends AbstractPacket<SPacketCrashPlayer>
{
	private String message;
	
	public SPacketCrashPlayer()
	{
		
	}
	
	public SPacketCrashPlayer(String msg)
	{
		this.message = msg;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.message);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.message = ByteBufUtils.readUTF8String(buffer);
	}
	
	@Override
	public void handleClientSide(SPacketCrashPlayer message, EntityPlayer player)
	{
		Minecraft.getMinecraft().displayCrashReport(new CrashReport(message.message, new Exception(message.message)));
	}
	
	@Override
	public void handleServerSide(SPacketCrashPlayer message, EntityPlayer player)
	{

	}
}