package dinocraft.command.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import dinocraft.util.server.EntityPlayerOP;
import dinocraft.util.server.Entry;
import dinocraft.util.server.MuteEntry;
import dinocraft.util.server.MuteHistoryEntry;
import dinocraft.util.server.WarningEntry;
import dinocraft.util.server.WarningHistoryEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/**
 * Removes the specified user from the muted players list.
 * <br><br>
 * <b> Notes: </b> Can also unmute offline players by specifying their username. Unmute all players with "@a" for first argument (must have command level specified in config file).
 * <br><br>
 * <b> Copyright © 2019 - 2020 Danfinite </b>
 */
public class CommandUnmute extends CommandBase
{
	@Override
	public String getName()
	{
		return "unmute";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.unmute.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UNMUTE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.unmute.usage");
		}
		
		if (args.length >= 2)
		{
			GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[0]);
			UUID UUID = profile.getId();
			EntityPlayerOP offlinePlayer = EntityPlayerOP.getEntityPlayerOP(UUID);
			Entry entry = offlinePlayer.unmute();
			
			if (entry == null)
			{
				throw new CommandException("commands.unmute.failed", profile.getName());
			}

			if (entry instanceof MuteHistoryEntry || entry instanceof MuteEntry)
			{
				EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(UUID);
				
				if (player != null)
				{
					player.sendMessage(new TextComponentTranslation("commands.unmute.unmutePlayer").setStyle(new Style().setColor(TextFormatting.GREEN)));
				}
				
				if (args.length >= 2)
				{
					notifyCommandListener(sender, this, "commands.unmute.success", profile.getName(), getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
				}
				else
				{
					notifyCommandListener(sender, this, "commands.unmute.success.noReason", profile.getName());
				}
			}
			else if (entry instanceof WarningHistoryEntry || entry instanceof WarningEntry)
			{
				if (args.length >= 2)
				{
					notifyCommandListener(sender, this, "commands.unmute.success.warning", profile.getName(), getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
				}
				else
				{
					notifyCommandListener(sender, this, "commands.unmute.success.warning.noReason", profile.getName());
				}
			}
			
			offlinePlayer.save();
		}
		else
		{
			throw new WrongUsageException("commands.unmute.usage");
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		//Set<UUID> mutedPlayers = DinocraftServer.MUTED_PLAYERS.keySet(); //TODO
		Set<UUID> mutedPlayers = DinocraftServer.getPlayerMutes().keySet();
		ArrayList<String> playerNames = new ArrayList<>();

		for (UUID UUID : mutedPlayers)
		{
			playerNames.add(server.getPlayerList().getPlayerByUUID(UUID).getName());
		}

		return args.length == 1 ? getListOfStringsMatchingLastWord(args, playerNames) : Collections.emptyList();
	}
}