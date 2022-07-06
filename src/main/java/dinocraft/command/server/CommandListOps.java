package dinocraft.command.server;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Displays a list containing all opped users and their permission levels.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandListOps extends CommandBase
{
	@Override
	public String getName()
	{
		return "oplist";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/oplist";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_OPLIST, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		UserListOps ops = server.getPlayerList().getOppedPlayers();
		String[] list = ops.getKeys();
		sender.sendMessage(new TextComponentTranslation("commands.oplist.players", list.length));
		
		for (int i = 0; i < list.length; i++)
		{
			sender.sendMessage(new TextComponentString(list[i] + " (permission level " + ops.getPermissionLevel(server.getPlayerProfileCache().getGameProfileForUsername(list[i])) + ")" + (list.length == 1 ? "" : i == list.length - 2 ? " and" : i == list.length - 1 ? "" : ",")));
		}
	}
}