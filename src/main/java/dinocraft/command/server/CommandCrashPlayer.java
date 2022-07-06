package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketCrashPlayer;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandCrashPlayer extends CommandBase
{
	@Override
	public String getName()
	{
		return "crash";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.crash.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_ADD, sender);//TODO:add
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.crash.usage");
		}
		
		EntityPlayerMP player = getPlayer(server, sender, args[0]);
		String reason = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
		PacketHandler.sendTo(new SPacketCrashPlayer(reason), player);

		if (args.length > 1)
		{
			notifyCommandListener(sender, this, "commands.crash.success", player.getName(), reason);
		}
		else
		{
			notifyCommandListener(sender, this, "commands.crash.success.noReason", player.getName());
		}
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