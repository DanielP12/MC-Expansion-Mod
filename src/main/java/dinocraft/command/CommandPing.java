package dinocraft.command;

import java.util.Collections;
import java.util.List;

import org.jline.utils.Log;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.network.MessagePing;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

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
		return "commands.ping.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayerMP playerMP = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);            
        NetworkHandler.sendTo(new MessagePing(), playerMP);
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}
