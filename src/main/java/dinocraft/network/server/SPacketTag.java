package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SPacketTag extends AbstractPacket<SPacketTag>
{
	private String tag;
	private String action;
	
	public SPacketTag()
	{

	}

	public SPacketTag(String tag, Action action)
	{
		this.tag = tag;
		this.action = action.toString();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, this.tag);
		ByteBufUtils.writeUTF8String(buffer, this.action);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.tag = ByteBufUtils.readUTF8String(buffer);
		this.action = ByteBufUtils.readUTF8String(buffer);
	}
	
	public enum Action
	{
		REMOVE, ADD;
		
		public static Action from(String str)
		{
			if (str.equalsIgnoreCase("add"))
			{
				return Action.ADD;
			}
			else if (str.equalsIgnoreCase("remove"))
			{
				return Action.REMOVE;
			}
			
			return null;
		}
		
		@Override
		public String toString()
		{
			return this == Action.ADD ? "add" : "remove";
		}
	}
	
	@Override
	public void handleClientSide(SPacketTag message, EntityPlayer player)
	{
		if (message.action.equalsIgnoreCase("add"))
		{
			if (!player.getTags().contains(message.tag))
			{
				player.addTag(message.tag);
			}
		}
		else if (message.action.equalsIgnoreCase("remove"))
		{
			if (player.getTags().contains(message.tag))
			{
				player.removeTag(message.tag);
			}
		}
	}
	
	@Override
	public void handleServerSide(SPacketTag message, EntityPlayer player)
	{
		
	}
}