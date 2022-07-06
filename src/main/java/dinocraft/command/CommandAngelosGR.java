package dinocraft.command;

import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketTag;
import dinocraft.network.server.SPacketTag.Action;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandAngelosGR extends CommandBase
{
	@Override
	public String getName()
	{
		return "AngelosGR";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.AngelosGR.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 1)
		{
			getCommandSenderAsPlayer(sender).addTag("invisible");
			PacketHandler.sendTo(new SPacketTag("invisible", Action.REMOVE), getCommandSenderAsPlayer(sender));
			
		}
		else
		{
			getCommandSenderAsPlayer(sender).removeTag("invisible");
			PacketHandler.sendTo(new SPacketTag("invisible", Action.ADD), getCommandSenderAsPlayer(sender));
			
		}
	}
}