package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.server.DinocraftPlayerList;
import dinocraft.util.server.UserListMutesEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Mutes the specified player.
 * <br><br>
 * <b> Notes: </b> Can also mute offline players by specifying their username.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
//TODO: DATE IMPLEMENTATION
public class CommandMute extends CommandBase
{
	@Override
	public String getName() 
	{
		return "mute";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.mute.usage";
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
			throw new WrongUsageException("commands.mute.usage", new Object[0]);
		}
		else
        {
			GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(args[0]);
			
			if (profile == null)
			{
				throw new CommandException("commands.mute.failed.invalid", new Object[] {args[0]});
			}
			else
			{	
				if (!DinocraftPlayerList.MUTED_PLAYERS.isMuted(profile))
				{
					String reason = null;

	                if (args.length >= 2)
	                {
	                	reason = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
	                }
	                
					UserListMutesEntry entry = new UserListMutesEntry(profile, null, sender.getName(), null, reason);
					DinocraftPlayerList.MUTED_PLAYERS.addEntry(entry);
					notifyCommandListener(sender, this, "commands.mute.success", new Object[] {profile.getName()});
				}
				else
				{
					throw new CommandException("commands.mute.failed", new Object[] {profile.getName()});
				}
			}
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
}