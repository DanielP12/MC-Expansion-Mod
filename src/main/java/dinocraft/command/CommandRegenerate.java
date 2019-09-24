package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the specified regenerating the specified amount of half-hearts every specified amount of seconds for the specified amount of seconds
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandRegenerate extends CommandBase
{
	@Override
	public String getName() 
	{
		return "regenerate";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.regenerate.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length <= 2)
		{
			throw new WrongUsageException("commands.regenerate.usage", new Object[0]);
		}
		else
        {
	        int time = parseInt(args[0]);
	        float loopTime = Float.parseFloat(args[1]);
	        float health = Float.parseFloat(args[2]);
	        
	        if (time <= 0 || loopTime <= 0)
	        {
				throw new CommandException("commands.regenerate.failed.time", new Object[0]);
	        }
	        
	        EntityPlayer player = args.length > 3 ? getPlayer(server, sender, args[3]) : getCommandSenderAsPlayer(sender);
	        DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	        
	        if (health == 0)
	        {
				throw new CommandException("commands.regenerate.failed.health", new Object[0]);
	        }
	        else if (health < 0)
	        {
	        	dinoEntity.setDegenerating(time, loopTime, health * -1);
	        }
	        else
	        {
	        	dinoEntity.setRegenerating(time, loopTime, health);
	        }

            notifyCommandListener(sender, this, "commands.regenerate.success", new Object[] {player.getName(), health, loopTime, time});
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
        return args.length == 4 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 3;
	}
}