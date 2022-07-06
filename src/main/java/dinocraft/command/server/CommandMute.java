package dinocraft.command.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.EntityPlayerOP;
import dinocraft.util.server.Entry;
import dinocraft.util.server.MuteEntry;
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
 * Mutes the specified player for a calculated time based on the specified mute code for a calculated reason based on the mute code.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>mute</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/mute &lt;username|UUID&gt; &lt;mute code&gt;<br>/mute &lt;username|UUID&gt; &lt;&lt;time&gt;|permanent|warning&gt; [reason ...]<br>/mute level &lt;username|UUID&gt; [level]</tt></span><p>
 * <b>Types of mutes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">
 * 1. Issued Regular (the player is online and the mute expires someday)<br>
 * 2. Issued Permanent (the player is online and the mute is permanent)<br>
 * 3. Scheduled Regular (the player is offline and the mute expires someday)<br>
 * 4. Scheduled Permanent (the player is offline and the mute is permanent)</span><p>
 * Scheduled mutes will only start when the player logs on, not when the player was muted. You can only mute for permanent if you have permission to do so.<p><br>
 * <u><b>GUIDELINES SYSTEM</b></u><br>
 * The guidelines system looks at the player's behavior as a whole.<br>
 * Each player will have a punishment level that they are currently on (starts at 0).<br>
 * Every time that a player breaks the rules their punishment level will increase by a given amount (higher mute level results in a higher increase).<br>
 * A player's punishment level will decay (decrease by 1) for every 30 days that pass after the latest punishment ended without breaking any rules.<p>
 * <b>After a player is muted:</b><br>
 * <span style="margin-left: 40px; display: inline-block">If their standing level becomes 1, they are warned.<br>
 * If their standing level becomes 2, they are muted for 1 day.<br>
 * If their standing level becomes 3, they are muted for 3 days.<br>
 * If their standing level becomes 4, they are muted for 7 days.<br>
 * If their standing level becomes 5, they are muted for 14 days.<br>
 * If their standing level becomes 6, they are muted for 30 days.<br>
 * If their standing level becomes 7 or higher, they are muted for 90 days (the highest possible punishment time).<p><br></span>
 * <u><b>THE NORMAL MUTE COMMAND</b></u><br>
 * The normal mute command allows you to mute using a set of mute codes which automatically calculate the time (based on the standing level), reason, and category of the punishment.<p>
 * <b>Mute levels and mute codes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">
 * There are four levels of mutes: Level 1 mutes, Level 2 mutes, Level 3 mutes, and Level 4 mutes.<br>
 * For level 1 mutes, the player's standing level increases by 1. For level 2 mutes, the standing level increases by 2. For level 3 mutes, the standing level increases by 3. For level 4 mutes, the standing level increases by 4.<p>
 *
 * <span style="margin-left: 40px; display: inline-block">Level 1 mutes: MA (Media Advertising), RU (Rude), PS (Public Shaming), SP (Excessive Spamming), MI (Misleading Information), and US (Unnecessary Spoilers).<br>
 * Example: {@code /mute Danfinite RU}</span><br>
 * <span style="margin-left: 90px; display: inline-block">{@code Warned Danfinite. Reason: 'Being rude or inappropriate.'}</span><p>
 *
 * <span style="margin-left: 40px; display: inline-block">Level 2 mutes: EC1 (Encouraging Cheating 1), ES (Excessive/Targeted Swearing), and UI1 (Unintentional/Intentional Distress).<br>
 * Example: {@code /mute Danfinite ES}</span><br>
 * <span style="margin-left: 90px; display: inline-block">{@code Muted Danfinite for 1d. Reason: 'Excessive use of swearing in chat.'}</span><p>
 *
 * <span style="margin-left: 40px; display: inline-block">Level 3 mutes: IC1 (Inappropriate Content 1) and DI (Discrimination).<br>
 * Example: {@code /mute Danfinite IC1}</span><br>
 * <span style="margin-left: 90px; display: inline-block">{@code Muted Danfinite for 3d. Reason: 'Using inappropriate concepts on the server.'}</span><p>
 *
 * <span style="margin-left: 40px; display: inline-block">Level 4 mutes: UD (User Disrespect) and NR (Negative Reference).<br>
 * Example: {@code /mute Danfinite UD}</span><br>
 * <span style="margin-left: 90px; display: inline-block">{@code Muted Danfinite for 7d. Reason: 'Acting in a manner that is disrespectful to members within the community.'}</span><p><br>
 *
 * <u><b>THE ENHANCED MUTE COMMAND</b></u><br>
 * The enhanced mute command allows you to mute by specifying the time and reason of the punishment manually. The player's standing level is neither affected by nor affects the punishment in any way when muting a player this way. The reason is not required to mute.<p>
 *
 * Example 1: {@code /mute Danfinite 1y30d20m45s Conducting an extremely inappropriate chat.}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Muted Danfinite for 1y7d12m300s. Reason: 'Conducting an extremely inappropriate chat.'}</span><p>
 *
 * Example 2: {@code /mute Danfinite warning Displaying negative behavior towards others.}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Warned Danfinite. Reason: 'Displaying negative behavior towards others.'}</span><p>
 *
 * Example 3: {@code /mute Danfinite permanent Continuously interfering with server ideals and conducting rule-breaking.}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Permanently muted Danfinite. Reason: 'Continuously interfering with server ideals and conducting rule-breaking.'}</span><p><br>
 *
 * <u><b>MODIFYING STANDING LEVELS</b></u><br>
 * You can also set and get the standing level of a specific player.<p>
 *
 * Example 1: {@code /mute level Danfinite 6}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Set Danfinite's mute level to 6}</span><p>
 *
 * Example 2: {@code /mute level Danfinite}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Danfinite's mute level is 6}</span><p>
 *
 * Example 3: {@code /mute Danfinite NR}<br>
 * <span style="margin-left: 60px; display: inline-block">{@code Muted Danfinite for 90d. Reason: 'Discussing important people or world events in a negative way.'}</span><p>
 *
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandMute extends CommandBase
{

	//	————————————————————————————————————————————————————————
	//	 *
	//	 * THE UNMUTE COMMAND:
	//	 * The unmute command reverts the active punishment and reduces the standing level accordingly based on the level increase of the punishment. If you have an elevated permission level, the reason is not required to unmute.
	//	 *
	//	 * Example: /unmute Danfinite Wrong Reason
	//	 * Reverted latest mute on Danfinite. Reason: 'Wrong Reason.'
	//	 *
	//	————————————————————————————————————————————————————————

	@Override
	public String getName()
	{
		return "mute";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_MUTE_FULL, sender.getServer()) ? "commands.mute.fullUsage" : "commands.mute.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_MUTE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	/**
	 * Parses the specified <code>String</code> into time components if and only if the <code>String</code> is in the format:<br>
	 * <code>[years]y[days]d[hours]h[minutes]m[seconds]s</code><br><br>
	 *
	 * <b>Examples:</b><br>
	 * <pre>
	 * getTimeComponents("1y170d20h30m10s"); → [1, 170, 20, 30, 10]
	 * getTimeComponents("5m23s");           → [0, 0, 0, 5, 23]
	 * getTimeComponents("5h8mm4s");         → null
	 * getTimeComponents("10k2m");           → null
	 * getTimeComponents("10h2m3");          → null
	 * </pre>
	 */
	public int[] getTimeComponents(String str)
	{
		String time = str.toLowerCase();
		
		if (!time.replaceFirst("y", "").replaceFirst("d", "").replaceFirst("h", "").replaceFirst("m", "").replaceFirst("s", "").matches("\\d+"))
		{
			return null;
		}
		
		char curr;
		String value = "";
		int years = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
		
		for (int i = 0; i < time.length(); i++)
		{
			curr = time.charAt(i);

			if (Character.isDigit(curr))
			{
				value += Character.toString(curr);

				if (i == time.length() - 1)
				{
					return null;
				}
			}
			else if (value.length() != 0)
			{
				int val = Integer.valueOf(value);
				
				switch (curr)
				{
					case 'y':
					{
						years = val;
						break;
					}
					case 'd':
					{
						days = val;
						break;
					}
					case 'h':
					{
						hours = val;
						break;
					}
					case 'm':
					{
						minutes = val;
						break;
					}
					case 's':
					{
						seconds = val;
						break;
					}
					default: return null;
				}
				
				value = "";
			}
			else
			{
				return null;
			}
		}

		return new int[] {years, days, hours, minutes, seconds};
	}

	private boolean parse(String str)
	{
		String time = str.toLowerCase();

		if (!time.equals("permanent") && !time.equals("warning"))
		{
			String number = time.replaceFirst("y", "").replaceFirst("d", "").replaceFirst("h", "").replaceFirst("m", "").replaceFirst("s", "");

			if (number.matches("\\d+") && !time.matches("\\d+") && time.substring(0, 1).matches("\\d+") && !time.substring(time.length() - 1).matches("\\d+") && !number.matches("^[0]+$"))
			{
				String[] parts = StringUtils.splitByCharacterType(time);

				for (int i = 1; i < parts.length; i += 2)
				{
					if (parts[i].length() != 1)
					{
						return false;
					}
				}
				
				return true;
			}
			else
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		boolean isLevel = DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_MUTE_FULL, server);

		if (args.length <= 1)
		{
			throw new WrongUsageException(isLevel ? "commands.mute.fullUsage" : "commands.mute.usage");
		}

		if (isLevel && args[0].equalsIgnoreCase("level"))
		{
			GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[1]);
			UUID uuid = profile.getId();
			sender.sendMessage(new TextComponentTranslation("commands.mute.success.levelPlayer", profile.getName(), EntityPlayerOP.hasSavedData(uuid) ? EntityPlayerOP.getEntityPlayerOP(uuid).getMuteStandingLevel() : 0));
			return;
		}
		
		GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[0]);
		UUID UUID = profile.getId();
		EntityPlayerOP offlinePlayer = EntityPlayerOP.getEntityPlayerOP(UUID);
		String name = profile.getName();
		
		if (offlinePlayer.isMuted())
		{
			throw new CommandException("commands.mute.failed", name);
		}
		
		boolean flag = false;
		
		for (String element : DinocraftConfig.MUTE_CODES)
		{
			if (element.equalsIgnoreCase(args[1]))
			{
				flag = true;
				break;
			}
		}
		
		EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(UUID);
		
		if (!flag)
		{
			StringBuilder codes = new StringBuilder();

			for (int i = 0; i < DinocraftConfig.MUTE_CODES.length; i++)
			{
				String code = DinocraftConfig.MUTE_CODES[i];

				if (i > 0)
				{
					if (i == DinocraftConfig.MUTE_CODES.length - 1)
					{
						if (DinocraftConfig.MUTE_CODES.length == 2)
						{
							codes.append(" and ");
						}
						else
						{
							codes.append(", or ");
						}
					}
					else
					{
						codes.append(", ");
					}
				}
				
				codes.append("'" + code + "'");
			}

			if (!isLevel)
			{
				throw new CommandException("commands.mute.failed.code", args[1], codes);
			}

			String reason = "";

			if (args.length >= 3)
			{
				reason = getChatComponentFromNthArg(sender, args, 2).getUnformattedText();
			}

			if (!args[1].equalsIgnoreCase("permanent"))
			{
				if (args[1].length() <= 1)
				{
					throw new CommandException("commands.mute.failed.invalidTime", args[1], codes);
				}

				if (args[1].equalsIgnoreCase("warning"))
				{
					offlinePlayer.warn(sender.getName(), reason);
				}
				else
				{
					int[] components = this.getTimeComponents(args[1]);
					
					if (components == null)
					{
						throw new CommandException("commands.mute.failed.invalidTime", args[1], codes);
					}
					
					offlinePlayer.mute(sender.getName(), components[0], components[1], components[2], components[3], components[4], reason);
				}
			}
			else
			{
				offlinePlayer.mutePermanently(sender.getName(), reason);
			}

			if (player != null)
			{
				if (args[1].equalsIgnoreCase("warning"))
				{
					if (!reason.equals(""))
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.warnPlayer2", reason));
						notifyCommandListener(sender, this, "commands.mute.success.issued.warning", name, reason);
					}
					else
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.warnPlayer.noReason2"));
						notifyCommandListener(sender, this, "commands.mute.success.issued.warning.noReason", name);
					}
				}
				else
				{
					MuteEntry mute = offlinePlayer.getCurrentMute();
					int[] times = mute.getTimeComponents();
					int years = times[0], days = times[1], hours = times[2], minutes = times[3], seconds = times[4];
					String time = (years == 0 ? "" : years + "y") + (days == 0 ? "" : days + "d") + (hours == 0 ? "" : hours + "h") + (minutes == 0 ? "" : minutes + "m") + (seconds == 0 ? "" : seconds + "s");

					if (args[1].equalsIgnoreCase("permanent"))
					{
						if (!reason.equals(""))
						{
							player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer.permanent2", TextFormatting.RED + reason, mute.getID()));
							notifyCommandListener(sender, this, "commands.mute.success.issued.permanent", name, reason);
						}
						else
						{
							player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer.permanent.noReason2", mute.getID()));
							notifyCommandListener(sender, this, "commands.mute.success.issued.permanent.noReason", name);
						}
					}
					else
					{
						if (!reason.equals(""))
						{
							player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer2", TextFormatting.RED + reason, TextFormatting.RED + mute.getRemainingTime(true), mute.getID()));
							notifyCommandListener(sender, this, "commands.mute.success.issued", name, time, reason);
						}
						else
						{
							player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer.noReason2", TextFormatting.RED + mute.getRemainingTime(true), mute.getID()));
							notifyCommandListener(sender, this, "commands.mute.success.issued.noReason", name, time);
						}
					}
				}
			}
			else
			{
				if (args[1].equalsIgnoreCase("permanent"))
				{
					if (!reason.equals(""))
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.permanent", name, reason);
					}
					else
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.permanent.noReason", name);
					}
				}
				else if (args[1].equalsIgnoreCase("warning"))
				{
					if (!reason.equals(""))
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.warning", name, reason);
					}
					else
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.warning.noReason", name);
					}
				}
				else
				{
					MuteEntry mute = offlinePlayer.getCurrentMute();
					int[] times = mute.getTimeComponents();
					int years = times[0], days = times[1], hours = times[2], minutes = times[3], seconds = times[4];
					String time = (years == 0 ? "" : years + "y") + (days == 0 ? "" : days + "d") + (hours == 0 ? "" : hours + "h") + (minutes == 0 ? "" : minutes + "m") + (seconds == 0 ? "" : seconds + "s");

					if (!reason.equals(""))
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled", name, time, reason);
					}
					else
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.noReason", name, time);
					}
				}
			}

			offlinePlayer.save();
			return;
		}

		try
		{
			Entry latestWarning = offlinePlayer.getLatestWarning();

			if (latestWarning != null)
			{
				Date date = new Date();
				date.setMinutes(date.getMinutes() + 5);

				if (latestWarning.getDateCreated().before(date))
				{
					sender.sendMessage(new TextComponentTranslation("commands.mute.warn.alreadyWarned", name).setStyle(new Style().setColor(TextFormatting.GOLD)));
				}
			}

			String reason = "", category = "";
			
			for (int i = 0; i < DinocraftConfig.MUTE_CODES.length; i++)
			{
				if (args[1].equalsIgnoreCase(DinocraftConfig.MUTE_CODES[i]) && !DinocraftConfig.MUTE_CODES[i].equals(""))
				{
					reason = DinocraftConfig.MUTE_REASONS[i];
					category = DinocraftConfig.MUTE_CATEGORIES[i];
					break;
				}
			}
			
			int level = offlinePlayer.getMuteStandingLevel();
			
			for (int i = 0; i < DinocraftConfig.MUTE_CODES.length; i++)
			{
				if (args[1].equalsIgnoreCase(DinocraftConfig.MUTE_CODES[i]) && !DinocraftConfig.MUTE_CODES[i].equals(""))
				{
					level += Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);
					break;
				}
			}
			
			int levelThreshold = level;

			if (level >= DinocraftConfig.MUTE_TIMES.length)
			{
				String str = DinocraftConfig.MUTE_TIMES[DinocraftConfig.MUTE_TIMES.length - 1];
				
				if (!this.parse(str))
				{
					throw new CommandException("The mute operation was aborted due to an error. The config file has a mute time that may have errors ('" + str + "'). Make sure it is written in one of the following formats: '[years]y[days]d[hours]h[minutes]m[seconds]s' OR 'WARNING' OR 'PERMANENT'!");
				}

				levelThreshold = DinocraftConfig.MUTE_TIMES.length;
			}
			else if (!this.parse(DinocraftConfig.MUTE_TIMES[level - 1]))
			{
				throw new CommandException("The mute operation was aborted due to an error. The config file has a mute time that may have errors ('" + DinocraftConfig.MUTE_TIMES[level - 1] + "'). Make sure it is written in one of the following formats: '[years]y[days]d[hours]h[minutes]m[seconds]s' OR 'WARNING' OR 'PERMANENT'!");
			}

			if (DinocraftConfig.MUTE_TIMES[levelThreshold - 1].equalsIgnoreCase("WARNING"))
			{
				offlinePlayer.warn(sender.getName(), level, category, reason);
				
				if (player != null)
				{
					player.sendMessage(new TextComponentTranslation("commands.mute.warnPlayer2", reason));
					notifyCommandListener(sender, this, "commands.mute.success.issued.warning", name, reason);
				}
				else
				{
					notifyCommandListener(sender, this, "commands.mute.success.scheduled.warning", name, reason);
				}
			}
			else
			{
				offlinePlayer.mute(sender.getName(), level, category, reason);
				MuteEntry mute = offlinePlayer.getCurrentMute();

				if (DinocraftConfig.MUTE_TIMES[levelThreshold - 1].equalsIgnoreCase("PERMANENT"))
				{
					if (player != null)
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer.permanent2", TextFormatting.RED + reason, mute.getID()));
						notifyCommandListener(sender, this, "commands.mute.success.issued.permanent", name, reason);
					}
					else
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled.permanent", name, reason);
					}
				}
				else
				{
					int[] times = mute.getTimeComponents();
					int years = times[0], days = times[1], hours = times[2], minutes = times[3], seconds = times[4];
					String time = (years == 0 ? "" : years + "y") + (days == 0 ? "" : days + "d") + (hours == 0 ? "" : hours + "h") + (minutes == 0 ? "" : minutes + "m") + (seconds == 0 ? "" : seconds + "s");

					if (player != null)
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.mutePlayer2", TextFormatting.RED + reason, TextFormatting.RED + mute.getRemainingTime(true), mute.getID()));
						notifyCommandListener(sender, this, "commands.mute.success.issued", name, time, reason);
					}
					else
					{
						notifyCommandListener(sender, this, "commands.mute.success.scheduled", name, time, reason);
					}
				}
			}

			offlinePlayer.save();
		}
		catch (ArrayIndexOutOfBoundsException exception)
		{
			throw new CommandException("The mute process was aborted due to a fatal error. The mute component lists in the config file are not of equal length! Please make sure they are!");
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		List<String> list = getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		boolean isLevel = DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_MUTE_FULL, server);

		if (args.length == 1)
		{
			if (isLevel)
			{
				list.add("level");
				return getListOfStringsMatchingLastWord(args, list);
			}

			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		
		if (args.length == 2)
		{
			if (args[0].equalsIgnoreCase("level") && isLevel)
			{
				return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
			}

			List<String> real = new ArrayList<>();

			if (isLevel)
			{
				real.add("PERMANENT");
				real.add("WARNING");
			}

			for (String current : DinocraftConfig.MUTE_CODES)
			{
				if (!current.equals(""))
				{
					real.add(current);
				}
			}

			return getListOfStringsMatchingLastWord(args, real);
		}

		return Collections.emptyList();
	}
}