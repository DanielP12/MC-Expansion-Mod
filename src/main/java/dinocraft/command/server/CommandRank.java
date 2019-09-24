package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the rank of the specified player to the specified rank.
 * <br><br>
 * <b> Notes: </b> Ranks include "moderator" (OP level 2), "administrator" (OP level 3), and "owner" (OP level 4).
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandRank extends CommandBase
{
	@Override
	public String getName() 
	{
		return "rank";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.rank.usage";
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
			throw new WrongUsageException("commands.rank.usage", new Object[0]);
		}
		else
		{
			EntityPlayerMP player = getPlayer(server, sender, args[1]);
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	        dinoEntity.op(args[0].equalsIgnoreCase("moderator") ? 2 : args[0].equalsIgnoreCase("administrator") ? 3 : args[0].equalsIgnoreCase("owner") ? 4 : dinoEntity.getOpLevel());
			notifyCommandListener(sender, this, "commands.rank.success", new Object[] {player.getName(), args[0].toLowerCase()});
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 2 ? getListOfStringsMatchingLastWord(args, getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames())) : args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"moderator", "administrator", "owner"}) : Collections.emptyList();
	}
}