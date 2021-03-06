package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.event.DinocraftFunctionEvents;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * Shuts the server down after the specified amount of time.
 * <br><br>
 * <b> Notes: </b> Cancel shutdown with "cancel" for first argument. Pause shutdown with "pause" for first argument. Resume shutdown with "resume" for first argument.
 * <br><br>
 * <b> Copyright ? 2019 Danfinite </b>
 */
public class CommandShutDown extends CommandBase
{
	@Override
	public String getName()
	{
		return "shutdown";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.shutdown.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_SHUTDOWN, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	private void initiateShutdown(int time)
	{
		DinocraftFunctionEvents.toggle = true;
		DinocraftFunctionEvents.flag = true;
		DinocraftFunctionEvents.flag2 = true;
		DinocraftFunctionEvents.time = time;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.shutdown.usage");
		}

		if (args[0].equals("cancel"))
		{
			if (!DinocraftFunctionEvents.flag2)
			{
				DinocraftFunctionEvents.flag = false;
				DinocraftFunctionEvents.flag2 = true;
				server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.RED + "SHUTDOWN CANCELED"));
				notifyCommandListener(sender, this, "commands.shutdown.cancel");
			}
			else
			{
				throw new CommandException("commands.shutdown.failed", "cancel");
			}
		}
		else if (args[0].equals("pause"))
		{
			if (DinocraftFunctionEvents.toggle && DinocraftFunctionEvents.flag && !DinocraftFunctionEvents.flag2)
			{
				DinocraftFunctionEvents.toggle = false;
				DinocraftFunctionEvents.flag = false;
				server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.RED + "SHUTDOWN PAUSED"));
				notifyCommandListener(sender, this, "commands.shutdown.pause", DinocraftFunctionEvents.time / 20);
			}
			else
			{
				throw new CommandException("commands.shutdown.failed", "pause");
			}
		}
		else if (args[0].equals("resume"))
		{
			if (!DinocraftFunctionEvents.toggle && DinocraftFunctionEvents.time != 0 && !DinocraftFunctionEvents.flag2)
			{
				DinocraftFunctionEvents.toggle = true;
				DinocraftFunctionEvents.flag = true;
				server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.RED + "SHUTDOWN RESUMED"));
				notifyCommandListener(sender, this, "commands.shutdown.resume");
			}
			else
			{
				throw new CommandException("commands.shutdown.failed", "resume");
			}
		}
		else
		{
			int time = parseInt(args[0]);

			if (time <= 0)
			{
				throw new CommandException("commands.shutdown.failed.time");
			}
			
			this.initiateShutdown(time * 20);
			notifyCommandListener(sender, this, "commands.shutdown.success", time);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "cancel", "pause", "resume") : Collections.emptyList();
	}
}