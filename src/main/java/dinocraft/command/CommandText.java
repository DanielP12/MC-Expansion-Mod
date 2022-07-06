package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sends the specified formatted message to the specified player.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>text</tt></span><br>
 * <b>Aliases:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>raw</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/text &lt;player&gt; &lt;formatted message ...&gt;</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">Use "<tt>&</tt>" instead of "<tt>§</tt>" for formatting codes. Use <tt>:n:</tt> for new line, <tt>:line:</tt> for a big line, and <tt>:t:</tt> for a tab.</span><p>
 * <b>Copyright © 2020 Danfinite</b>
 */
public class CommandText extends CommandBase
{
	@Override
	public String getName()
	{
		return "text";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("raw");
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.text.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TEXT, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.text.usage");
		}
		
		getPlayer(server, sender, args[0]).sendMessage(DinocraftCommandUtilities.generateSimpleComponentFromString(getChatComponentFromNthArg(sender, args, 1).getUnformattedText()));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}