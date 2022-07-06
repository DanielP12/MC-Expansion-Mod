package dinocraft.command.server;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import dinocraft.util.server.ForbiddenWord;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

/**
 * Adds the specified word to the forbidden words list.
 * <br><br>
 * <b> Notes: </b> Can also forbid multiple words, putting each word in a separate argument.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandForbid extends CommandBase
{
	@Override
	public String getName()
	{
		return "forbid";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.forbid.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FORBID, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.forbid.usage", new Object[0]);
		}

		for (String arg : args)
		{
			if (arg.contains("/"))
			{
				int posColon = arg.indexOf('/');
				String word = arg.substring(0, posColon);
				String replacement = arg.substring(posColon + 1);
				DinocraftServer.FORBIDDEN_WORDS.addEntry(new ForbiddenWord(word, sender.getName(), replacement));
				notifyCommandListener(sender, this, "commands.forbid.success", new Object[] {word, replacement});
			}
			else
			{
				DinocraftServer.FORBIDDEN_WORDS.addEntry(new ForbiddenWord(arg, sender.getName(), null));
				notifyCommandListener(sender, this, "commands.forbid.success.noReplacement", new Object[] {arg});
			}
		}
	}
}