package dinocraft.network.client;

import dinocraft.gui.PunishmentHistory;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CPacketDisplayContainer extends AbstractPacket<CPacketDisplayContainer>
{
	private String type;

	public CPacketDisplayContainer()
	{

	}

	public CPacketDisplayContainer(String type)
	{
		this.type = type;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.type);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.type = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(CPacketDisplayContainer message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(CPacketDisplayContainer message, EntityPlayer player)
	{
		if (player.openContainer instanceof PunishmentHistory)
		{
			((PunishmentHistory) player.openContainer).display(PunishmentHistory.Type.from(message.type));
			((PunishmentHistory) player.openContainer).detectAndSendChanges();
		}
	}
}