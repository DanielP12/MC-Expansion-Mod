package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketAllowFlying;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Toggles flight on or off for the specified player.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>fly</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/fly [player]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
//TODO: allow on and off specifications
public class CommandFly extends CommandBase
{
	@Override
	public String getName()
	{
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.fly.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FLY, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);

		if (!dinoEntity.canFly())
		{
			dinoEntity.setAllowFlight(true);
			notifyCommandListener(sender, this, "commands.fly.success", "on", player.getName());
			PacketHandler.sendTo(new SPacketAllowFlying(true, !player.onGround), player);
			dinoEntity.setFlight(true);
		}
		else
		{
			dinoEntity.setAllowFlight(false);
			notifyCommandListener(sender, this, "commands.fly.success", "off", player.getName());
			PacketHandler.sendTo(new SPacketAllowFlying(false, false), player);
			dinoEntity.setFlight(false);

			if (!player.onGround)
			{
				dinoEntity.setFallDamage(false);
			}
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