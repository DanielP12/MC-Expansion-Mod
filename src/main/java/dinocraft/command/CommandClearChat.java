package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketClearChat;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Clears the chat for the specified player.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>clearchat</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/clearchat [player]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
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
		return "commands.clearchat.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_CLEARCHAT, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = args.length == 1 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		PacketHandler.sendTo(new SPacketClearChat(), player);
		notifyCommandListener(sender, this, "commands.clearchat.success", player.getName());
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