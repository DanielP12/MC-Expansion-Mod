package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Runs a command as the specified player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandSudo extends CommandBase
{
	@Override
	public String getName() 
	{
		return "sudo";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.sudo.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(4) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.sudo.usage", new Object[0]);
		}
		else
		{
			EntityPlayer player = getPlayer(server, sender, args[0]);
			server.getCommandManager().executeCommand(player, args[1] + (args.length >= 3 ? " " + args[2] : "") 
					+ (args.length >= 4 ? " " + args[3] : "") + (args.length >= 5 ? " " + args[4] : "") 
					+ (args.length >= 6 ? " " + args[5] : "") + (args.length >= 7 ? " " + args[6] : "")
					+ (args.length >= 8 ? " " + args[7] : "") + (args.length >= 9 ? " " + args[8] : "")
					+ (args.length >= 10 ? " " + args[9] : "") + (args.length >= 11 ? " " + args[10] : "")
					+ (args.length >= 12 ? " " + args[11] : "") + (args.length >= 13 ? " " + args[12] : ""));
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : args.length == 2 ? getListOfStringsMatchingLastWord(args, server.commandManager.getTabCompletions(sender, args[1], pos)) : Collections.<String>emptyList();
	}
}