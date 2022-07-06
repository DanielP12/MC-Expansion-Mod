package dinocraft.command.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import dinocraft.util.server.EntityPlayerOP;
import dinocraft.util.server.MuteEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Displays a list containing all muted users.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandListMutes extends CommandBase
{
	@Override
	public String getName()
	{
		return "mutelist";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_MUTE_FULL, sender.getServer()) ? "commands.mutelist.fullUsage" : "/mutelist";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_MUTELIST, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_MUTE_FULL, server) && args.length >= 1 && !args[0].equalsIgnoreCase("online"))
		{
			File file = new File("./player_data");
			ArrayList<EntityPlayerOP> mutedPlayers = new ArrayList<>();
			String[] list = file.list((dir, name) -> name.endsWith(".json"));
			
			for (String name : list)
			{
				EntityPlayerOP offlinePlayer = EntityPlayerOP.getEntityPlayerOP(UUID.fromString(name.substring(0, name.lastIndexOf('.'))));
				
				if (offlinePlayer.isMuted() && (args[0].equals("*") || args[0].equalsIgnoreCase("offline") && !offlinePlayer.isOnline()))
				{
					mutedPlayers.add(offlinePlayer);
				}
			}
			
			int size = mutedPlayers.size();
			sender.sendMessage(new TextComponentTranslation("commands.mutelist.players", size));

			for (int i = 0; i < size; i++)
			{
				EntityPlayerOP offlinePlayer = mutedPlayers.get(i);
				MuteEntry entry = offlinePlayer.getCurrentMute();
				sender.sendMessage(new TextComponentString(offlinePlayer.getUsername() + " (type: " + entry.getCategory() + "; " + "remaining time: " + entry.getRemainingTime(true) + ")" + (size == 1 ? "" : i == size - 2 ? " and" : i == size - 1 ? "" : ",")));
			}
		}
		else
		{
			Map<UUID, MuteEntry> playerMutes = DinocraftServer.getPlayerMutes();
			int size = playerMutes.size();
			sender.sendMessage(new TextComponentTranslation("commands.mutelist.players", size));
			List<UUID> keyList = new ArrayList<>(playerMutes.keySet());

			for (int i = 0; i < size; i++)
			{
				UUID UUID = keyList.get(i);
				MuteEntry entry = playerMutes.get(UUID);
				sender.sendMessage(new TextComponentString(server.getPlayerProfileCache().getProfileByUUID(UUID).getName() + " (type: " + entry.getCategory() + "; " + "remaining time: " + entry.getRemainingTime(true) + ")" + (size == 1 ? "" : i == size - 2 ? " and" : i == size - 1 ? "" : ",")));
			}
		}
	}
}