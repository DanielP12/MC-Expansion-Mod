package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports the specified player to their last death location.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>back</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/back [player]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandBack extends CommandBase
{
	@Override
	public String getName()
	{
		return "back";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.back.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_BACK, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		BlockPos pos = dinoEntity.getPosBeforeLastDeath();

		if (pos == null)
		{
			throw new CommandException("commands.back.failed");
		}
		
		player.dismountRidingEntity();
		player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
		notifyCommandListener(sender, this, "commands.back.success", player.getName());
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