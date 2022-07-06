package dinocraft.network.client;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CPacketExecuteCommand extends AbstractPacket<CPacketExecuteCommand>
{
	private String rawCommand;

	public CPacketExecuteCommand()
	{

	}

	public CPacketExecuteCommand(String rawCommand)
	{
		this.rawCommand = rawCommand;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.rawCommand);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.rawCommand = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(CPacketExecuteCommand message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(CPacketExecuteCommand message, EntityPlayer player)
	{
		player.getServer().getCommandManager().executeCommand(player, message.rawCommand);
	}
}