package dinocraft.command.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.EntityPlayerOP;
import dinocraft.util.server.HistoryEntry;
import dinocraft.util.server.MuteEntry;
import dinocraft.util.server.MuteHistoryEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.UserListBans;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.event.HoverEvent;

public class CommandUserInformation extends CommandBase
{
	@Override
	public String getName()
	{
		return "userinfo";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.userinfo.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_USER_INFORMATION, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.userinfo.usage");
		}

		GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[0]);
		UUID UUID = profile.getId();
		PlayerList list = server.getPlayerList();
		EntityPlayerMP player = list.getPlayerByUUID(UUID);
		UserListOps ops = list.getOppedPlayers();
		UserListBans bans = list.getBannedPlayers();
		String name = profile.getName();
		String status = TextFormatting.RED + "Offline";
		String ping = null;

		if (player != null)
		{
			status = TextFormatting.GREEN + "Online";
			ping = player.ping < 100 ? TextFormatting.DARK_GREEN + Integer.toString(player.ping) : player.ping < 150 ? TextFormatting.GREEN + Integer.toString(player.ping) : player.ping < 200 ? TextFormatting.YELLOW + Integer.toString(player.ping) : player.ping < 250 ? TextFormatting.GOLD + Integer.toString(player.ping) : player.ping < 300 ? TextFormatting.RED + Integer.toString(player.ping) : TextFormatting.DARK_RED + Integer.toString(player.ping);
		}

		int permissionLevel = 0;

		if (ops.getEntry(profile) != null)
		{
			permissionLevel = ops.getPermissionLevel(profile);
		}

		int previousPunishments = 0;
		int muteStandingLevel = 0;
		//int banStandingLevel = 0;
		EntityPlayerOP offlinePlayer = null;
		
		if (EntityPlayerOP.hasSavedData(UUID))
		{
			offlinePlayer = EntityPlayerOP.getEntityPlayerOP(UUID);
		}
		
		String currentMute = null;
		String currentBans = null;
		MuteEntry muteEntry = null;

		if (offlinePlayer != null)
		{
			previousPunishments = offlinePlayer.getNumPunishmentsReceived();
			muteStandingLevel = offlinePlayer.getMuteStandingLevel();

			if (offlinePlayer.isMuted())
			{
				muteEntry = offlinePlayer.getCurrentMute();
				String reason = muteEntry.getReason();
				currentMute = reason != null ? reason + " (time left: " + muteEntry.getRemainingTime(true) + ")" : " time left: " + muteEntry.getRemainingTime(true);
			}
		}

		List<String> categories = new ArrayList<>(), replacements = new ArrayList<>();
		List<Integer> standings = new ArrayList<>();

		for (int i = 0; i < DinocraftConfig.MUTE_CODES.length; i++)
		{
			String replacement = DinocraftConfig.MUTE_CODES[i];
			String category = DinocraftConfig.MUTE_CATEGORIES[i];
			int standing = Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);

			if (!replacement.equals("") && !category.equals(""))
			{
				replacements.add("'" + replacement + "'");
				categories.add("'" + category + "'");
				standings.add(standing);
			}
		}

		Style unavailable = new Style().setColor(TextFormatting.GRAY).setStrikethrough(true);
		Style muteButtonStyle;

		if (muteEntry == null)
		{
			String text = "";
			int size = replacements.size();
			
			for (int i = 0; i < size; i++)
			{
				int level = standings.get(i) + muteStandingLevel;
				String time = "";
				
				if (level <= 0)
				{
					time = DinocraftConfig.MUTE_TIMES[0];
				}
				else if (level >= DinocraftConfig.MUTE_TIMES.length)
				{
					time = DinocraftConfig.MUTE_TIMES[DinocraftConfig.MUTE_TIMES.length - 1];
				}
				else
				{
					time = DinocraftConfig.MUTE_TIMES[level - 1];
				}

				text += replacements.get(i) + " for " + categories.get(i) + " (" + time + ")" + (i == size - 1 ? "" : "\n");
			}

			muteButtonStyle = new Style().setColor(TextFormatting.RED).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/mute " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(text)));
		}
		else
		{
			muteButtonStyle = unavailable;
		}

		ITextComponent muteButton = new TextComponentString("[MUTE]").setStyle(muteButtonStyle);
		Style kickButtonStyle;

		if (player != null)
		{
			kickButtonStyle = new Style().setColor(TextFormatting.GOLD).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/kick " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Kick " + name)));
		}
		else
		{
			kickButtonStyle = unavailable;
		}

		ITextComponent kickButton = new TextComponentString("[KICK]").setStyle(kickButtonStyle);
		Style unmuteButtonStyle;

		if (muteEntry == null)
		{
			HistoryEntry[] punishmentHistory = offlinePlayer.getPunishmentHistory();
			
			if (offlinePlayer == null || !offlinePlayer.hasQueuedWarnings() || punishmentHistory.length > 0 && punishmentHistory[0] instanceof MuteHistoryEntry)
			{
				unmuteButtonStyle = unavailable;
			}
			else
			{
				unmuteButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/unmute " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Unmute " + name)));
			}
		}
		else
		{
			unmuteButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/unmute " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Unmute " + name)));
		}

		ITextComponent unmuteButton = new TextComponentString("[UNMUTE]").setStyle(unmuteButtonStyle);
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity((EntityLivingBase) sender);

		if (dinoEntity.hasOpLevel(3))
		{
			Style banButtonStyle;
			Style unbanButtonStyle;
			Style freezeButtonStyle;
			Style unfreezeButtonStyle;
			Style portButtonStyle;
			Style pullButtonStyle;

			if (server.getPlayerList().getBannedPlayers().getEntry(profile) == null)
			{
				banButtonStyle = new Style().setColor(TextFormatting.RED).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/ban " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Ban " + name)));
				unbanButtonStyle = unavailable;
			}
			else
			{
				unbanButtonStyle = new Style().setColor(TextFormatting.RED).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/unban " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Unban " + name)));
				banButtonStyle = unavailable;
			}

			if (player != null)
			{
				portButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tpto " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Teleport to " + name)));
				pullButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tphere " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Teleport " + name + " here")));

				if (!DinocraftEntity.getEntity(player).isFrozen())
				{
					unfreezeButtonStyle = unavailable;
					freezeButtonStyle = new Style().setColor(TextFormatting.AQUA).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/freeze " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Freeze " + name)));
				}
				else
				{
					freezeButtonStyle = unavailable;
					unfreezeButtonStyle = new Style().setColor(TextFormatting.GOLD).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/unfreeze " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Unfreeze " + name)));
				}
			}
			else
			{
				portButtonStyle = unavailable;
				pullButtonStyle = unavailable;
				unfreezeButtonStyle = unavailable;
				freezeButtonStyle = unavailable;
			}

			ITextComponent portButton = new TextComponentString("[PORT]").setStyle(portButtonStyle);
			ITextComponent pullButton = new TextComponentString("[PULL]").setStyle(pullButtonStyle);
			ITextComponent freezeButton = new TextComponentString("[FREEZE]").setStyle(freezeButtonStyle);
			ITextComponent unfreezeButton = new TextComponentString("[UNFREEZE]").setStyle(unfreezeButtonStyle);
			ITextComponent banButton = new TextComponentString("[BAN]").setStyle(banButtonStyle);
			ITextComponent unbanButton = new TextComponentString("[UNBAN]").setStyle(unbanButtonStyle);

			if (dinoEntity.hasOpLevel(4))
			{
				Style healButtonStyle;
				Style feedButtonStyle;
				Style setPermissionLevelButtonStyle;
				Style setMuteLevelButtonStyle;
				Style setMaxHealthButtonStyle;
				Style strikeButtonStyle;
				Style killButtonStyle;
				Style bounceButtonStyle;
				Style inventoryButtonStyle;
				setPermissionLevelButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/oplevel ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Change " + name + "'s permission level")));
				setMuteLevelButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/mute level " + name + " ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Set or get " + name + "'s mute level")));

				if (player != null)
				{
					healButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/heal " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Heal " + name)));
					feedButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/feed " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Feed " + name)));
					setMaxHealthButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/maxhealth ")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Change " + name + "'s max health")));
					strikeButtonStyle = new Style().setColor(TextFormatting.RED).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/strike " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Strike " + name)));
					killButtonStyle = new Style().setColor(TextFormatting.RED).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/kill " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Kill " + name)));
					inventoryButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/inventory " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("See " + name + "'s inventory")));
					bounceButtonStyle = new Style().setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/bounce " + name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Bounce " + name)));
				}
				else
				{
					healButtonStyle = unavailable;
					feedButtonStyle = unavailable;
					setMaxHealthButtonStyle = unavailable;
					strikeButtonStyle = unavailable;
					killButtonStyle = unavailable;
					inventoryButtonStyle = unavailable;
					bounceButtonStyle = unavailable;
				}

				ITextComponent healButton = new TextComponentString("[HEAL]").setStyle(healButtonStyle);
				ITextComponent feedButton = new TextComponentString("[FEED]").setStyle(feedButtonStyle);
				ITextComponent setPermissionLevelButton = new TextComponentString("[SET PERMISSION LEVEL]").setStyle(setPermissionLevelButtonStyle);
				ITextComponent setMuteLevelButton = new TextComponentString("[SET MUTE LEVEL]").setStyle(setMuteLevelButtonStyle);
				ITextComponent setMaxHealthButton = new TextComponentString("[SET MAX HEALTH]").setStyle(setMaxHealthButtonStyle);
				ITextComponent strikeButton = new TextComponentString("[STRIKE]").setStyle(strikeButtonStyle);
				ITextComponent killButton = new TextComponentString("[KILL]").setStyle(killButtonStyle);
				ITextComponent bounceButton = new TextComponentString("[BOUNCE]").setStyle(bounceButtonStyle);
				ITextComponent inventoryButton = new TextComponentString("[INVENTORY]").setStyle(inventoryButtonStyle);

				sender.sendMessage(new TextComponentString(TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------\n" + TextFormatting.GRAY + "Username: " + TextFormatting.WHITE + name + "\n" + TextFormatting.GRAY + "UUID: " + TextFormatting.WHITE + UUID
						+ "\n" + TextFormatting.GRAY + "Status: " + status + "\n" + (ping != null ? TextFormatting.GRAY + "Ping: " + ping + "\n" : "") + TextFormatting.GRAY + "Permission Level: " + TextFormatting.WHITE + permissionLevel + "\n" + TextFormatting.GRAY + "Previous Punishments: " + TextFormatting.WHITE + previousPunishments
						+ "\n" + TextFormatting.GRAY + "Mute Standing Level: " + TextFormatting.WHITE + muteStandingLevel + "\n" + (currentMute != null ? TextFormatting.GRAY + "Current Mute: " + TextFormatting.WHITE + currentMute + "\n" : "")
						+ TextFormatting.GRAY + "Actions: ").appendSibling(muteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(unmuteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(kickButton).appendText("\n")
						.appendSibling(portButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(pullButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(freezeButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ")
						.appendSibling(unfreezeButton).appendText("\n").appendSibling(banButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(unbanButton).appendText("\n").appendSibling(healButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ")
						.appendSibling(feedButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(setMaxHealthButton).appendText("\n").appendSibling(setPermissionLevelButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(setMuteLevelButton).appendText("\n")
						.appendSibling(strikeButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(killButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(bounceButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ")
						.appendSibling(inventoryButton).appendText("\n" + TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------"));
				return;
			}

			sender.sendMessage(new TextComponentString(TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------\n" + TextFormatting.GRAY + "Username: " + TextFormatting.WHITE + name + "\n" + TextFormatting.GRAY + "UUID: " + TextFormatting.WHITE + UUID
					+ "\n" + TextFormatting.GRAY + "Status: " + status + "\n" + (ping != null ? TextFormatting.GRAY + "Ping: " + ping + "\n" : "") + TextFormatting.GRAY + "Permission Level: " + TextFormatting.WHITE + permissionLevel + "\n" + TextFormatting.GRAY + "Previous Punishments: " + TextFormatting.WHITE + previousPunishments
					+ "\n" + TextFormatting.GRAY + "Mute Standing Level: " + TextFormatting.WHITE + muteStandingLevel + "\n" + (currentMute != null ? TextFormatting.GRAY + "Current Mute: " + TextFormatting.WHITE + currentMute + "\n" : "")
					+ TextFormatting.GRAY + "Actions: ").appendSibling(muteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(unmuteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(kickButton).appendText("\n")
					.appendSibling(portButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(pullButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(freezeButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ")
					.appendSibling(unfreezeButton).appendText("\n").appendSibling(banButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(unbanButton).appendText("\n" + TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------"));
			return;
		}

		if (dinoEntity.hasOpLevel(2))
		{
			sender.sendMessage(new TextComponentString(TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------\n" + TextFormatting.GRAY + "Username: " + TextFormatting.WHITE + name + "\n" + TextFormatting.GRAY + "UUID: " + TextFormatting.WHITE + UUID
					+ "\n" + TextFormatting.GRAY + "Status: " + status + "\n" + (ping != null ? TextFormatting.GRAY + "Ping: " + ping + "\n" : "") + TextFormatting.GRAY + "Permission Level: " + TextFormatting.WHITE + permissionLevel + "\n" + TextFormatting.GRAY + "Previous Punishments: " + TextFormatting.WHITE + previousPunishments
					+ "\n" + TextFormatting.GRAY + "Mute Standing Level: " + TextFormatting.WHITE + muteStandingLevel + "\n" + (currentMute != null ? TextFormatting.GRAY + "Current Mute: " + TextFormatting.WHITE + currentMute + "\n" : "")
					+ TextFormatting.GRAY + "Actions: ").appendSibling(muteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(unmuteButton).appendText(" " + TextFormatting.STRIKETHROUGH + "--" + TextFormatting.RESET + " ").appendSibling(kickButton).appendText("\n" + TextFormatting.WHITE + "" + TextFormatting.STRIKETHROUGH + "-----------------------------------------------------"));
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
}