package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import com.mojang.authlib.GameProfile;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.UserListOps;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the OP level of the specified player to the specified permission level.
 * <br><br>
 * <b> Notes: </b> If the specified player is already opped, they will be deopped and opped to the specified permission level. Specifying the number 0 will deop the specified player.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandOpLevel extends CommandBase
{
	@Override
	public String getName()
	{
		return "oplevel";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.oplevel.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_OPLEVEL, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.oplevel.usage");
		}
		
		int level = parseInt(args[0]);

		if (level < 0)
		{
			throw new CommandException("commands.oplevel.failed.level");
		}
		
		GameProfile profile = args.length > 1 ? DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[1]) : getCommandSenderAsPlayer(sender).getGameProfile();

		//		if (level == 0)
		//		{
		//			server.getPlayerList().removeOp(profile);
		//			notifyCommandListener(sender, this, "commands.deop.success", new Object[] {profile.getName()});
		//		}
		//		else
		//		{
		PlayerList list = server.getPlayerList();
		UserListOps ops = list.getOppedPlayers();
		ops.addEntry(new UserListOpsEntry(profile, level, ops.bypassesPlayerLimit(profile)));
		EntityPlayerMP player = list.getPlayerByUUID(profile.getId());
		
		if (player != null && player.connection != null)
		{
			player.connection.sendPacket(new SPacketEntityStatus(player, level <= 0 ? 24 : level >= 4 ? 28 : (byte) (24 + level)));
		}

		notifyCommandListener(sender, this, "commands.oplevel.success", profile.getName(), level);
		//		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 1;
	}
}