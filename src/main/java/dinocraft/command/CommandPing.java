package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Imparts to the sender the amount of ping they have.
 * <br><br>
 * <b> Notes: </b> Only players with OP permission level of 2 or higher can view other players' ping.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
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
		return sender instanceof MinecraftServer || (sender instanceof EntityPlayerMP && DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(2)) ? "commands.ping.fullUsage" : "commands.ping.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
		{
			if (sender instanceof EntityPlayer)
			{
				sender.sendMessage(new TextComponentTranslation("commands.ping.pingSender", new Object[] {getCommandSenderAsPlayer(sender).ping}));
			}
			else
			{
				throw new CommandException("commands.generic.player.unspecified", new Object[0]);
			}
		}
		else if (sender instanceof MinecraftServer || args[0].equalsIgnoreCase(sender.getName()) || (sender instanceof EntityPlayer && DinocraftEntity.getEntity((EntityPlayer) sender).hasOpLevel(2)))
		{
			EntityPlayerMP player = getPlayer(server, sender, args[0]);
			sender.sendMessage(new TextComponentTranslation("commands.ping.pingPlayer", new Object[] {player.getName(), player.ping}));
		}
		else
		{
			throw new CommandException("commands.ping.failed.players", new Object[0]);
		}
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		if (args.length == 1)
		{
	        return sender instanceof MinecraftServer || (sender instanceof EntityPlayer && DinocraftEntity.getEntity((EntityPlayer) sender).hasOpLevel(2)) ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : getListOfStringsMatchingLastWord(args, sender.getName());
		}
		
		return  Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}