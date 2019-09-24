package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the OP level of the specified player to the specified permission level.
 * <br><br>
 * <b> Notes: </b> If the specified player is already opped, they will be deopped and opped to the specified permission level. Specifying the number 0 will deop the specified player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
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
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(4) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.oplevel.usage", new Object[0]);
		}
		else
        {
            int level = parseInt(args[0]);
            
            if (level < 0)
            {
            	throw new CommandException("commands.oplevel.failed.level", new Object[0]);
            }
            else
            {
                GameProfile profile = args.length > 1 ? server.getPlayerProfileCache().getGameProfileForUsername(args[1]) : getCommandSenderAsPlayer(sender).getGameProfile();

                if (profile == null)
                {
                    throw new CommandException("commands.oplevel.failed", new Object[] {args[0]});
                }
                else
                {
                	if (level == 0)
                    {
                    	server.getPlayerList().removeOp(profile);
                    	notifyCommandListener(sender, this, "commands.deop.success", new Object[] {profile.getName()});
                    }
                    else
                    {
                        DinocraftEntity.getEntity((EntityPlayerMP) sender).op(level, profile);
                    	notifyCommandListener(sender, this, "commands.oplevel.success", new Object[] {profile.getName(), level});
                    }
                }
            }
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 1;
	}
}