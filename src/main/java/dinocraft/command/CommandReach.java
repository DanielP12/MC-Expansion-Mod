package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.MessageReach;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the attack reach distance to the specified amount for the specified player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandReach extends CommandBase
{
	@Override
	public String getName() 
	{
		return "reach";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.reach.usage";
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
			throw new WrongUsageException("commands.reach.usage", new Object[0]);
		}
		else
        {
            double distance = parseDouble(args[0]);
            
            if (distance <= 1.0D)
            {
            	throw new CommandException("commands.reach.failed.distance", new Object[0]);
            }
            else
            {
                EntityPlayerMP player = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
                DinocraftEntity.getEntity(player).setAttackReach(distance);
    			NetworkHandler.sendTo(new MessageReach(distance), player);
            	notifyCommandListener(sender, this, "commands.reach.success", new Object[] {player.getName(), distance});
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