package dinocraft.command.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketChatMsgAsPlayer;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import dinocraft.util.server.EntityPlayerOP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * 1. Forces the specified player to say the specified message. <br>
 * 2. Forces the specified player to execute the specified command.
 * <br><br>
 * <b> Copyright © 2019 - 2020 Danfinite </b>
 */
public class CommandAsPlayer extends CommandBase
{
	@Override
	public String getName()
	{
		return "asPlayer";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("sudo");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.asPlayer.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_ASPLAYER, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.asPlayer.usage");
		}

		EntityPlayerMP player = getPlayer(server, sender, args[0]);
		String name = player.getName();
		String text = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
		
		if (args[1].charAt(0) == '/')
		{
			String commandName = args[1].substring(1);
			ICommand command = null;
			ICommandManager manager = server.getCommandManager();

			for (ICommand command1 : manager.getPossibleCommands(player))
			{
				if (command1.getName().equals(commandName))
				{
					command = command1;
					break;
				}
			}

			if (command != null)
			{
				if (command instanceof CommandBroadcast || command instanceof CommandEmote || command instanceof CommandMessage)
				{
					if (!DinocraftServer.isChatEnabled() && !DinocraftEntity.getEntity(player).hasOpLevel(2))
					{
						throw new CommandException("commands.asPlayer.failed.chatDisabled", name);
					}

					if (EntityPlayerOP.getEntityPlayerOP(player.getUniqueID()).isMuted())
					{
						throw new CommandException("commands.asPlayer.failed.playerMuted", name);
					}
				}

				notifyCommandListener(sender, this, "commands.asPlayer.success.command", name, text);
			}
			else
			{
				for (String commandName1 : manager.getCommands().keySet())
				{
					if (commandName1.equals(commandName))
					{
						throw new CommandException("commands.asPlayer.failed.command.noPermission", name);
					}
				}

				throw new CommandException("commands.generic.failed.invalidCommand", args[1]);
			}

			PacketHandler.sendTo(new SPacketChatMsgAsPlayer(text, true), player);
		}
		else
		{
			if (!DinocraftServer.isChatEnabled() && !DinocraftEntity.getEntity(player).hasOpLevel(2))
			{
				throw new CommandException("commands.asPlayer.failed.chatDisabled", name);
			}
			
			if (EntityPlayerOP.getEntityPlayerOP(player.getUniqueID()).isMuted())
			{
				throw new CommandException("commands.asPlayer.failed.playerMuted", name);
			}

			PacketHandler.sendTo(new SPacketChatMsgAsPlayer(text, true), player);
			notifyCommandListener(sender, this, "commands.asPlayer.success.text", player.getName(), text);
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if (args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}

		if (args.length == 2)
		{
			EntityPlayerMP player = null;
			
			try
			{
				player = getPlayer(server, sender, args[0]);
			}
			catch (Exception exception)
			{
				return Collections.emptyList();
			}
			
			if (player != null)
			{
				List<String> commands = new ArrayList<>();
				
				for (ICommand command : server.getCommandManager().getPossibleCommands(player))
				{
					commands.add("/" + command.getName());
				}
				
				return getListOfStringsMatchingLastWord(args, commands);
			}
		}
		else if (args.length > 2)
		{
			ICommand command = null;
			
			for (ICommand command0 : server.getCommandManager().getPossibleCommands(sender))
			{
				if (command0.getName().equals(args[1].substring(1)))
				{
					command = command0;
					break;
				}
			}
			
			if (command != null)
			{
				List<String> output = new ArrayList<>();
				
				for (int i = 0; i < args.length; i++)
				{
					if (!Arrays.asList(0, 1).contains(i))
					{
						output.add(args[i]);
					}
				}
				
				return command.getTabCompletions(server, sender, output.toArray(new String[output.size()]), pos);
			}
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}