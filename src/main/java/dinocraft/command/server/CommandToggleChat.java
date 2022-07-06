package dinocraft.command.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandToggleChat extends CommandBase
{
	@Override
	public String getName()
	{
		return "togglechat";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/togglechat";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TOGGLECHAT, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (DinocraftServer.isChatEnabled())
		{
			DinocraftServer.disableChat();

			for (EntityPlayerMP player : server.getPlayerList().getPlayers())
			{
				if (!DinocraftEntity.getEntity(player).hasOpLevel(2))
				{
					player.sendMessage(new TextComponentTranslation("commands.togglechat.togglePlayer", "disabled").setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}

			notifyCommandListener(sender, this, "commands.togglechat.success", "Disabled");
		}
		else
		{
			DinocraftServer.enableChat();

			for (EntityPlayerMP player : server.getPlayerList().getPlayers())
			{
				if (!DinocraftEntity.getEntity(player).hasOpLevel(2))
				{
					player.sendMessage(new TextComponentTranslation("commands.togglechat.togglePlayer", "enabled").setStyle(new Style().setColor(TextFormatting.GREEN)));
				}
			}

			notifyCommandListener(sender, this, "commands.togglechat.success", "Enabled");
		}
	}
}