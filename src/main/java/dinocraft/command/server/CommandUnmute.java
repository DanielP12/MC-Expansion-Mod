package dinocraft.command.server;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.server.DinocraftPlayerList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Unmutes the specified player.
 * <br><br>
 * <b> Notes: </b> Can also unmute offline players by specifying their username. Unmute all players with "@a" for first argument.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
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
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(2) : true;
	}
		
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.unmute.usage", new Object[0]);
		}
		else if (args[0].equals("@a"))
		{
			if (!DinocraftPlayerList.MUTED_PLAYERS.isEmpty())
			{
				DinocraftPlayerList.MUTED_PLAYERS.getValues().clear();
			
				try
				{
					DinocraftPlayerList.MUTED_PLAYERS.writeChanges();
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
			
				notifyCommandListener(sender, this, "commands.unmute.success", new Object[] {"all muted profiles"});
			}
			else
			{
				throw new CommandException("commands.unmute.failed.empty", new Object[0]);
			}
		}
		else
        {
			GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(args[0]);
			
			if (profile == null)
			{
				throw new CommandException("commands.unmute.failed.invalid", new Object[] {args[0]});
			}
			else
			{				
				if (DinocraftPlayerList.MUTED_PLAYERS.isMuted(profile))
				{
					DinocraftPlayerList.MUTED_PLAYERS.removeEntry(profile);
					notifyCommandListener(sender, this, "commands.unmute.success", new Object[] {profile.getName()});
				}
				else
				{
					throw new CommandException("commands.unmute.failed", new Object[] {profile.getName()});
				}
			}
        }
    }

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, DinocraftPlayerList.MUTED_PLAYERS.getKeys()) : Collections.<String>emptyList();
	}
}