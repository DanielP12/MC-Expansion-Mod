package dinocraft.command;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandAsServer extends CommandBase
{
	@Override
	public String getName() 
	{
		return "asServer";
	}

	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.asServer.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length <= 0) throw new WrongUsageException("commands.asServer.usage", new Object[0]);
		else
		{
			server.getCommandManager().executeCommand(server, args[0] + (args.length >= 2 ? " " + args[1] : "") 
					+ (args.length >= 3 ? " " + args[2] : "") + (args.length >= 4 ? " " + args[3] : "") 
					+ (args.length >= 5 ? " " + args[4] : "") + (args.length >= 6 ? " " + args[5] : "")
					+ (args.length >= 7 ? " " + args[6] : "") + (args.length >= 8 ? " " + args[7] : "")
					+ (args.length >= 9 ? " " + args[8] : "") + (args.length >= 10 ? " " + args[9] : "")
					+ (args.length >= 11 ? " " + args[10] : "") + (args.length >= 12 ? " " + args[11] : ""));
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.commandManager.getTabCompletions(sender, args[0], pos)) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 1;
	}
}
