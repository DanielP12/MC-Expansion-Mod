package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.MessageClearChat;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Clears the chat for the specified player.
 * <br><br>
 * <b> Notes: </b> Clear chat for all players with "@a" for first argument.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandClearChat extends CommandBase
{
	@Override
	public String getName() 
	{
		return "clearchat";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/clearchat";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 1 && args[0].equals("@a"))
		{
	 	    NetworkHandler.sendToAll(new MessageClearChat());
			notifyCommandListener(sender, this, "commands.clearchat.success", new Object[] {"all players"});
		}
		else
		{
			EntityPlayerMP player = args.length == 1 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
			NetworkHandler.sendTo(new MessageClearChat(), player);
			notifyCommandListener(sender, this, "commands.clearchat.success", new Object[] {player.getName()});
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
}