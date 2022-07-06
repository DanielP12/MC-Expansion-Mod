package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Removes the specified word from the forbidden words list.
 * <br><br>
 * <b> Notes: </b> Can also unforbid multiple words, putting each word in a separate argument. Unforbid all words with "*" for first argument. <br>
 * <b> Aliases: </b> /allow
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandUnforbid extends CommandBase
{
	@Override
	public String getName()
	{
		return "unforbid";
	}

	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("allow");
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.unforbid.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UNFORBID, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.unforbid.usage");
		}

		if (args[0].equals("*"))
		{
			if (!DinocraftServer.FORBIDDEN_WORDS.isEmpty())
			{
				DinocraftServer.FORBIDDEN_WORDS.getValues().clear();
				DinocraftServer.FORBIDDEN_WORDS.save();
				notifyCommandListener(sender, this, "commands.unforbid.success.all");
			}
			else
			{
				throw new CommandException("commands.unforbid.failed.empty");
			}
		}
		else
		{
			for (String arg : args)
			{
				if (!arg.equals("") && DinocraftServer.FORBIDDEN_WORDS.isForbidden(arg))
				{
					DinocraftServer.FORBIDDEN_WORDS.removeEntry(arg);
					notifyCommandListener(sender, this, "commands.unforbid.success", arg);
				}
				else
				{
					throw new CommandException("commands.unforbid.failed", arg);
				}
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		String[] forbiddenWords = DinocraftServer.FORBIDDEN_WORDS.getKeys();
		String[] all = new String[] {"*"};
		String[] result = new String[forbiddenWords.length + all.length];
		System.arraycopy(forbiddenWords, 0, result, 0, forbiddenWords.length);
		System.arraycopy(all, 0, result, forbiddenWords.length, all.length);
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, result) : Collections.emptyList();
	}
}