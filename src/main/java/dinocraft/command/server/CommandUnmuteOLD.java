package dinocraft.command.server;
//package dinocraft.command.server;
//
//import java.util.Collections;
//import java.util.List;
//
//import com.mojang.authlib.GameProfile;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.command.DinocraftCommandUtilities;
//import dinocraft.util.DinocraftConfig;
//import dinocraft.util.server.DataEntry;
//import dinocraft.util.server.DinocraftPlayerList;
//import dinocraft.util.server.MuteEntry;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.command.WrongUsageException;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.Style;
//import net.minecraft.util.text.TextComponentTranslation;
//import net.minecraft.util.text.TextFormatting;
//
///**
// * Removes the specified user from the muted players list.
// * <br><br>
// * <b> Notes: </b> Can also unmute offline players by specifying their username. Unmute all players with "@a" for first argument (must have command level specified in config file).
// * <br><br>
// * <b> Copyright © 2019 - 2020 Danfinite </b>
// */
//public class CommandUnmute extends CommandBase
//{
//	@Override
//	public String getName()
//	{
//		return "unmute";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender)
//	{
//		return DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_UNMUTE_FULL, sender.getServer()) ? "commands.unmute.fullUsage" : "commands.unmute.usage";
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
//	{
//		//return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UNMUTE, sender);
//		return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//	{
//		boolean isLevel = DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_UNMUTE_FULL, server);
//
//		if (args.length == 0)
//		{
//			throw new WrongUsageException(isLevel ? "commands.unmute.fullUsage" : "commands.unmute.usage");
//		}
//
//		if (args[0].equals("@a") && isLevel)
//		{
//			if (!DinocraftPlayerList.MUTED_PLAYERS.isEmpty())
//			{
//				String reason = "";
//
//				if (args.length >= 2)
//				{
//					reason = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
//				}
//
//				for (String username : DinocraftPlayerList.MUTED_PLAYERS.getKeys())
//				{
//					EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(username);
//
//					if (player != null)
//					{
//						player.sendMessage(new TextComponentTranslation("commands.unmute.unmutePlayer").setStyle(new Style().setColor(TextFormatting.GREEN)));
//					}
//
//					GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(username);
//					MuteEntry entry = DinocraftPlayerList.MUTED_PLAYERS.getEntry(profile);
//					DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(profile);
//					String category = entry.getCategory();
//
//					if (category != null)
//					{
//						for (int i = 0; i < DinocraftConfig.MUTE_CATEGORIES.length; i++)
//						{
//							if (category.equals(DinocraftConfig.MUTE_CATEGORIES[i]))
//							{
//								data.muteLevel -= Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);
//							}
//						}
//					}
//
//					data.previousPunishments--;
//
//					if (args.length >= 2)
//					{
//						notifyCommandListener(sender, this, "commands.unmute.success", username, reason);
//					}
//					else
//					{
//						notifyCommandListener(sender, this, "commands.unmute.success.noReason", username);
//					}
//				}
//
//				DinocraftPlayerList.MUTED_PLAYERS.getValues().clear();
//			}
//			else
//			{
//				throw new CommandException("commands.unmute.failed.empty");
//			}
//		}
//		else if (args.length >= 2 || isLevel)
//		{
//			GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[0]);
//			DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(profile);
//			MuteEntry entry = DinocraftPlayerList.MUTED_PLAYERS.getEntry(profile);
//			if (DinocraftPlayerList.MUTED_PLAYERS.isMuted(profile) && entry != null)
//			{
//				String category = entry.getCategory();
//
//				if (category != null)
//				{
//					for (int i = 0; i < DinocraftConfig.MUTE_CATEGORIES.length; i++)
//					{
//						if (category.equals(DinocraftConfig.MUTE_CATEGORIES[i]))
//						{
//							data.muteLevel -= Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);
//						}
//					}
//
//					data.revertLatestMute();
//					data.decayAndUpdate();
//				}
//
//				data.previousPunishments--;
//				DinocraftPlayerList.MUTED_PLAYERS.removeEntry(profile);
//				EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(profile.getId());
//
//				if (player != null)
//				{
//					player.sendMessage(new TextComponentTranslation("commands.unmute.unmutePlayer").setStyle(new Style().setColor(TextFormatting.GREEN)));
//				}
//
//				if (args.length >= 2)
//				{
//					notifyCommandListener(sender, this, "commands.unmute.success", profile.getName(), getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
//				}
//				else
//				{
//					notifyCommandListener(sender, this, "commands.unmute.success.noReason", profile.getName());
//				}
//			}
//			else if (data != null && data.warningReasons.size() > 0)
//			{
//				for (int i = 0; i < DinocraftConfig.MUTE_REASONS.length; i++)
//				{
//					if (data.warningReasons.get(0).equals(DinocraftConfig.MUTE_REASONS[i]))
//					{
//						data.muteLevel -= Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);
//					}
//				}
//
//				data.timeWarned = null;
//				data.revertLatestMute();
//				data.decayAndUpdate();
//				data.toWarn = false;
//				data.revertLatestWarning();
//				data.previousPunishments--;
//				DinocraftPlayerList.WARNED_PLAYERS.remove(profile.getName());
//
//				if (args.length >= 2)
//				{
//					notifyCommandListener(sender, this, "commands.unmute.success.warning", profile.getName(), getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
//				}
//				else
//				{
//					notifyCommandListener(sender, this, "commands.unmute.success.warning.noReason", profile.getName());
//				}
//			}
//			else
//			{
//				throw new CommandException("commands.unmute.failed", profile.getName());
//			}
//		}
//		else
//		{
//			throw new WrongUsageException("commands.unmute.usage");
//		}
//
//		//DinocraftPlayerList.writeDataListChanges();
//		DinocraftPlayerList.MUTED_PLAYERS.save();
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
//	{
//		String[] mutedPlayers = DinocraftPlayerList.MUTED_PLAYERS.getKeys();
//		Object[] warnedPlayers = DinocraftPlayerList.WARNED_PLAYERS.toArray();
//		int mutedLength = mutedPlayers.length;
//		int warnedLength = warnedPlayers.length;
//		String[] result = new String[mutedLength + warnedLength];
//		System.arraycopy(mutedPlayers, 0, result, 0, mutedLength);
//		System.arraycopy(warnedPlayers, 0, result, mutedLength, warnedLength);
//		return args.length == 1 ? getListOfStringsMatchingLastWord(args, result) : Collections.emptyList();
//	}
//}