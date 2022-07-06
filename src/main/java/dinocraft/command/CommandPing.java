package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/**
 * Displays the ping of the specified player.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>ping</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/ping [player]</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
//TODO: WIP
public class CommandPing extends CommandBase
{
	@Override
	public String getName()
	{
		return "ping";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return sender instanceof MinecraftServer || sender instanceof EntityPlayerMP && DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(DinocraftConfig.PERMISSION_LEVEL_PING) ? "commands.ping.fullUsage" : "commands.ping.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			if (sender instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = getCommandSenderAsPlayer(sender);
				sender.sendMessage(new TextComponentTranslation("commands.ping.pingSender", player.ping < 100 ? TextFormatting.DARK_GREEN + Integer.toString(player.ping) : player.ping < 150 ? TextFormatting.GREEN + Integer.toString(player.ping) : player.ping < 200 ? TextFormatting.YELLOW + Integer.toString(player.ping) : player.ping < 250 ? TextFormatting.GOLD + Integer.toString(player.ping) : player.ping < 300 ? TextFormatting.RED + Integer.toString(player.ping) : TextFormatting.DARK_RED + Integer.toString(player.ping)));
			}
			else
			{
				throw new CommandException("commands.generic.player.unspecified");
			}
		}
		else if (sender instanceof MinecraftServer || args[0].equalsIgnoreCase(sender.getName()) || sender instanceof EntityPlayerMP && DinocraftEntity.getEntity((EntityPlayer) sender).hasOpLevel(DinocraftConfig.PERMISSION_LEVEL_PING))
		{
			EntityPlayerMP player = getPlayer(server, sender, args[0]);
			sender.sendMessage(new TextComponentTranslation("commands.ping.pingPlayer", player.getName(), player.ping < 100 ? TextFormatting.DARK_GREEN + Integer.toString(player.ping) : player.ping < 150 ? TextFormatting.GREEN + Integer.toString(player.ping) : player.ping < 200 ? TextFormatting.YELLOW + Integer.toString(player.ping) : player.ping < 250 ? TextFormatting.GOLD + Integer.toString(player.ping) : player.ping < 300 ? TextFormatting.RED + Integer.toString(player.ping) : TextFormatting.DARK_RED + Integer.toString(player.ping)));
		}
		else
		{
			throw new CommandException("commands.ping.failed.players");
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if (args.length == 1)
		{
			return sender instanceof MinecraftServer || sender instanceof EntityPlayer && DinocraftEntity.getEntity((EntityPlayer) sender).hasOpLevel(DinocraftConfig.PERMISSION_LEVEL_PING) ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : getListOfStringsMatchingLastWord(args, sender.getName());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}