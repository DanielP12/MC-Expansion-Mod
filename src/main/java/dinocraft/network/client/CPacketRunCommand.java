package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CPacketRunCommand extends AbstractPacket<CPacketRunCommand>
{
	private String command;

	public CPacketRunCommand()
	{

	}

	public CPacketRunCommand(String command)
	{
		this.command = command;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.command);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.command = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(CPacketRunCommand message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(CPacketRunCommand message, EntityPlayer player)
	{
		player.getServer().getCommandManager().executeCommand(player, message.command);
	}
}