package dinocraft.command.shortcuts;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandHard extends CommandBase
{
	@Override
	public String getName()
	{
		return "hard";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/hard";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_ADD, sender);//TODO: change perm level
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		server.getCommandManager().executeCommand(sender, "difficulty hard");
	}
}