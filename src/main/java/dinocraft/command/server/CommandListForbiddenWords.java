package dinocraft.command.server;

import java.util.ArrayList;
import java.util.List;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import dinocraft.util.server.ForbiddenWord;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Displays a list containing all the forbidden words (words that will always be blocked from messages).
 * <br><br>
 * <b> Notes: </b> Can also forbid multiple words, putting each word in a separate argument.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandListForbiddenWords extends CommandBase
{
	@Override
	public String getName()
	{
		return "forbiddenlist";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/forbiddenlist";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FORBIDDENLIST, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		String[] words = DinocraftServer.FORBIDDEN_WORDS.getKeys();
		sender.sendMessage(new TextComponentTranslation("commands.forbiddenlist.words", new Object[] {words.length}));
		List<String> list = new ArrayList<>();

		for (String word : words)
		{
			ForbiddenWord entry = DinocraftServer.FORBIDDEN_WORDS.getEntry(word);
			String replacement = entry.getReplacement();

			if (entry.getStarReplacement().equals(replacement))
			{
				list.add(word);
			}
			else
			{
				list.add(word + " (replacement: " + replacement + ")");
			}
		}

		sender.sendMessage(new TextComponentString(joinNiceString(list.toArray())));
	}
}