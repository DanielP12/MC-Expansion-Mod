//package dinocraft.command.server;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//import com.mojang.authlib.GameProfile;
//
//import dinocraft.command.DinocraftCommandUtilities;
//import dinocraft.util.server.DinocraftPlayerList;
//import dinocraft.util.server.Group;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.command.WrongUsageException;
//import net.minecraft.network.datasync.EntityDataManager.DataEntry;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.Style;
//import net.minecraft.util.text.TextComponentString;
//import net.minecraft.util.text.TextComponentTranslation;
//import net.minecraft.util.text.TextFormatting;
//
//public class CommandGroup extends CommandBase
//{
//	@Override
//	public String getName()
//	{
//		return "group";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender)
//	{
//		return "commands.group.usage";
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
//	{
//		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FEED, sender);//TODO: add
//		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//	{
//		///group create <name>
//		///group <name> add player "playerName" "playerName" ...
//		///group <name> add command "commandName" "commandName" ...
//		///group <name> remove player "playerName" "playerName" ...
//		///group <name> remove command "commandName" "commandName" ...
//		///group <name> players
//		///group <name> commands
//		///group <name> name <new name>
//
//		if (args.length <= 1)
//		{
//			throw new WrongUsageException("commands.group.usage");
//		}
//
//		if (args[0].equalsIgnoreCase("create"))
//		{
//			DinocraftPlayerList.GROUPS.addEntry(new Group(args[1]));
//			notifyCommandListener(sender, this, "commands.group.success.create", new Object[] {args[1]});
//			DinocraftPlayerList.GROUPS.save();
//			return;
//		}
//
//		Group group = DinocraftPlayerList.GROUPS.getGroupByName(args[0]);
//
//		if (group == null)
//		{
//			throw new CommandException("commands.group.failed.invalidGroup", new Object[] {args[0]});
//		}
//
//		if (args[0].equalsIgnoreCase("delete"))
//		{
//			DinocraftPlayerList.GROUPS.removeEntry(group.getID());
//			notifyCommandListener(sender, this, "commands.group.success.create", new Object[] {args[1]});
//			DinocraftPlayerList.GROUPS.save();
//			return;
//		}
//
//		String name = group.getName();
//
//		if (args[1].equalsIgnoreCase("players"))
//		{
//			List<String> players = new ArrayList<>();
//			List<String> uuids = group.getPlayerUUIDs();
//
//			for (String uuid : uuids)
//			{
//				players.add(server.getPlayerProfileCache().getProfileByUUID(UUID.fromString(uuid)).getName());
//			}
//
//			sender.sendMessage(new TextComponentTranslation("commands.group.players", new Object[] {uuids.size(), name}));
//			sender.sendMessage(new TextComponentString(joinNiceString(players.toArray())));
//			return;
//		}
//		else if (args[1].equalsIgnoreCase("commands"))
//		{
//			List<String> commands = group.getCommands();
//			sender.sendMessage(new TextComponentTranslation("commands.group.commands", new Object[] {commands.size(), name}));
//			sender.sendMessage(new TextComponentString(joinNiceString(commands.toArray())));
//			return;
//		}
//
//		if (args.length < 3)
//		{
//			throw new WrongUsageException("commands.group.usage");
//		}
//
//		if (args[1].equalsIgnoreCase("name"))
//		{
//			group.setName(args[2]);
//			notifyCommandListener(sender, this, "commands.group.success.name", new Object[] {args[0], args[2]});
//			DinocraftPlayerList.GROUPS.save();
//			return;
//		}
//
//		if (args.length < 4)
//		{
//			throw new WrongUsageException("commands.group.usage");
//		}
//
//		if (args[1].equalsIgnoreCase("add"))
//		{
//			if (args[2].equalsIgnoreCase("player"))
//			{
//				List<String> failed = new ArrayList<>();
//
//				for (int i = 3; i < args.length; i++)
//				{
//					GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(args[i]);
//
//					if (profile == null)
//					{
//						failed.add(args[i] + "$");
//						continue;
//					}
//
//					List<String> uuids = group.getPlayerUUIDs();
//					String uuid = profile.getId().toString();
//
//					if (uuids.contains(uuid))
//					{
//						failed.add(profile.getName());
//						continue;
//					}
//
//					uuids.add(uuid);
//					DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(profile);
//
//					if (data != null)
//					{
//						data.setGroupID(group.getID());
//					}
//					else
//					{
//						DataEntry newEntry = new DataEntry(profile, 0, 0);
//						newEntry.setGroupID(group.getID());
//						DinocraftPlayerList.USER_DATA.addEntry(newEntry);
//					}
//
//					notifyCommandListener(sender, this, "commands.group.success.add.player", new Object[] {profile.getName(), name});
//				}
//
//				for (String username : failed)
//				{
//					if (username.charAt(username.length() - 1) == '$')
//					{
//						sender.sendMessage(new TextComponentTranslation("commands.generic.failed.invalidUsername", username.substring(0, username.length() - 1)).setStyle(new Style().setColor(TextFormatting.RED)));
//						continue;
//					}
//
//					sender.sendMessage(new TextComponentTranslation("commands.group.failed.player.alreadyInGroup", new Object[] {username, name}).setStyle(new Style().setColor(TextFormatting.RED)));
//				}
//			}
//			else if (args[2].equalsIgnoreCase("command"))
//			{
//				List<String> failed = new ArrayList<>();
//				List<String> commands = group.getCommands();
//				Set<String> allCommands = server.getCommandManager().getCommands().keySet();
//
//				if (args[3].equals("*"))
//				{
//					commands.addAll(allCommands);
//					notifyCommandListener(sender, this, "commands.group.success.add.command.all", new Object[] {name});
//					return;
//				}
//
//				for (int i = 3; i < args.length; i++)
//				{
//					if (!allCommands.contains(args[i]))
//					{
//						failed.add(args[i] + "$");
//						continue;
//					}
//
//					if (commands.contains(args[i]))
//					{
//						failed.add(args[i]);
//						continue;
//					}
//
//					commands.add(args[i]);
//					notifyCommandListener(sender, this, "commands.group.success.add.command", new Object[] {args[i], name});
//				}
//
//				for (String command : failed)
//				{
//					if (command.charAt(command.length() - 1) == '$')
//					{
//						sender.sendMessage(new TextComponentTranslation("commands.generic.failed.invalidCommand", command.substring(0, command.length() - 1)).setStyle(new Style().setColor(TextFormatting.RED)));
//						continue;
//					}
//
//					sender.sendMessage(new TextComponentTranslation("commands.group.failed.command.alreadyInGroup", new Object[] {command, name}).setStyle(new Style().setColor(TextFormatting.RED)));
//				}
//			}
//			else
//			{
//				throw new WrongUsageException("commands.group.usage");
//			}
//		}
//		else if (args[1].equalsIgnoreCase("remove"))
//		{
//			if (args[2].equalsIgnoreCase("player"))
//			{
//				List<String> failed = new ArrayList<>();
//
//				for (int i = 3; i < args.length; i++)
//				{
//					GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(args[i]);
//
//					if (profile == null)
//					{
//						failed.add(args[i] + "$");
//						continue;
//					}
//
//					List<String> uuids = group.getPlayerUUIDs();
//					String uuid = profile.getId().toString();
//
//					if (!uuids.contains(uuid))
//					{
//						failed.add(profile.getName());
//						continue;
//					}
//
//					uuids.remove(uuid);
//					DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(profile);
//
//					if (data != null)
//					{
//						data.setGroupID(null);
//					}
//					else
//					{
//						DataEntry newEntry = new DataEntry(profile, 0, 0);
//						newEntry.setGroupID(null);
//						DinocraftPlayerList.USER_DATA.addEntry(newEntry);
//					}
//
//					notifyCommandListener(sender, this, "commands.group.success.remove.player", new Object[] {profile.getName(), name});
//				}
//
//				for (String username : failed)
//				{
//					if (username.charAt(username.length() - 1) == '$')
//					{
//						sender.sendMessage(new TextComponentTranslation("commands.generic.failed.invalidUsername", username.substring(0, username.length() - 1)).setStyle(new Style().setColor(TextFormatting.RED)));
//						continue;
//					}
//
//					sender.sendMessage(new TextComponentTranslation("commands.group.failed.player.notInGroup", new Object[] {username, name}).setStyle(new Style().setColor(TextFormatting.RED)));
//				}
//			}
//			else if (args[2].equalsIgnoreCase("command"))
//			{
//				List<String> failed = new ArrayList<>();
//				List<String> commands = group.getCommands();
//				Set<String> allCommands = server.getCommandManager().getCommands().keySet();
//
//				if (args[3].equals("*"))
//				{
//					commands.clear();
//					notifyCommandListener(sender, this, "commands.group.success.remove.command.all", new Object[] {name});
//					return;
//				}
//
//				for (int i = 3; i < args.length; i++)
//				{
//					if (!allCommands.contains(args[i]))
//					{
//						failed.add(args[i] + "$");
//						continue;
//					}
//
//					if (!commands.contains(args[i]))
//					{
//						failed.add(args[i]);
//						continue;
//					}
//
//					commands.remove(args[i]);
//					notifyCommandListener(sender, this, "commands.group.success.remove.command", new Object[] {args[i], name});
//				}
//
//				for (String command : failed)
//				{
//					if (command.charAt(command.length() - 1) == '$')
//					{
//						sender.sendMessage(new TextComponentTranslation("commands.generic.failed.invalidCommand", command.substring(0, command.length() - 1)).setStyle(new Style().setColor(TextFormatting.RED)));
//						continue;
//					}
//
//					sender.sendMessage(new TextComponentTranslation("commands.group.failed.command.notInGroup", new Object[] {command, name}).setStyle(new Style().setColor(TextFormatting.RED)));
//				}
//			}
//			else
//			{
//				throw new WrongUsageException("commands.group.usage");
//			}
//		}
//		else
//		{
//			throw new WrongUsageException("commands.group.usage");
//		}
//
//		DinocraftPlayerList.GROUPS.save();
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
//	{
//		///group create <name>
//		///group <name> add player "playerName" "playerName" ...
//		///group <name> add command "commandName" "commandName" ...
//		///group <name> remove player "playerName" "playerName" ...
//		///group <name> remove command "commandName" "commandName" ...
//		///group <name> players
//		///group <name> commands
//		///group <name> name <new name>
//
//		if (args.length == 1)
//		{
//			List<String> list = new ArrayList<>();
//			list.add("create");
//			Collections.addAll(list, DinocraftPlayerList.GROUPS.getNames());
//			return getListOfStringsMatchingLastWord(args, list);
//		}
//
//		Group group = null;
//
//		if (!args[0].equalsIgnoreCase("create"))
//		{
//			group = DinocraftPlayerList.GROUPS.getGroupByName(args[0]);
//		}
//
//		if (group != null)
//		{
//			if (args.length == 2)
//			{
//				return getListOfStringsMatchingLastWord(args, new String[] {"add", "remove", "players", "commands", "name"});
//			}
//
//			if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))
//			{
//				if (args.length == 3)
//				{
//					return getListOfStringsMatchingLastWord(args, new String[] {"player", "command"});
//				}
//
//				if (args.length >= 4)
//				{
//					if (args[2].equalsIgnoreCase("player"))
//					{
//						if (args[1].equalsIgnoreCase("add"))
//						{
//							List<String> list = new ArrayList<>();
//							List<String> uuids = group.getPlayerUUIDs();
//
//							for (GameProfile profile : server.getPlayerList().getOnlinePlayerProfiles())
//							{
//								if (!uuids.contains(profile.getId().toString()))
//								{
//									list.add(profile.getName());
//								}
//							}
//
//							return getListOfStringsMatchingLastWord(args, list);
//						}
//						else if (args[1].equalsIgnoreCase("remove"))
//						{
//							List<String> list = new ArrayList<>();
//
//							for (String uuid : group.getPlayerUUIDs())
//							{
//								list.add(server.getPlayerProfileCache().getProfileByUUID(UUID.fromString(uuid)).getName());
//							}
//
//							return getListOfStringsMatchingLastWord(args, list);
//						}
//					}
//
//					if (args[2].equalsIgnoreCase("command"))
//					{
//						if (args[1].equalsIgnoreCase("add"))
//						{
//							List<String> list = new ArrayList<>();
//
//							for (String command : server.getCommandManager().getCommands().keySet())
//							{
//								if (!group.getCommands().contains(command))
//								{
//									list.add(command);
//								}
//							}
//
//							return getListOfStringsMatchingLastWord(args, list);
//						}
//						else if (args[1].equalsIgnoreCase("remove"))
//						{
//							return getListOfStringsMatchingLastWord(args, group.getCommands());//TODO: excluding the ones filled for args already
//						}
//					}
//				}
//			}
//		}
//
//		return Collections.emptyList();
//	}
//}