package dinocraft.command.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Forces the server to execute the specified command
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandAsServer extends CommandBase
{
	@Override
	public String getName()
	{
		return "asServer";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.asServer.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_ASSERVER, sender);
		//		return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.asServer.usage");
		}
		
		String command = getChatComponentFromNthArg(sender, args, 0).getUnformattedText();

		if (command.charAt(0) == '/')
		{
			if (server.getCommandManager().executeCommand(server, command.substring(1)) > 0)
			{
				notifyCommandListener(sender, this, "commands.asServer.success", command);
			}
			else
			{
				throw new CommandException("commands.asServer.failed", command);
			}
		}
		else
		{
			throw new CommandException("commands.asServer.failed.noCommand", command);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if (args.length == 1)
		{
			List<String> commands = new ArrayList<>();
			
			for (ICommand command : server.getCommandManager().getPossibleCommands(server))
			{
				commands.add("/" + command.getName());
			}
			
			return getListOfStringsMatchingLastWord(args, commands);
		}
		
		ICommand command = null;
		
		for (ICommand command0 : server.getCommandManager().getPossibleCommands(server))
		{
			if (command0.getName().equals(args[0].substring(1)))
			{
				command = command0;
				break;
			}
		}
		
		if (command != null)
		{
			List<String> output = new ArrayList<>();
			
			for (int i = 0; i < args.length; i++)
			{
				if (!Arrays.asList(0).contains(i))
				{
					output.add(args[i]);
				}
			}
			
			return command.getTabCompletions(server, sender, output.toArray(new String[output.size()]), pos);
		}
		
		return Collections.emptyList();
	}
}