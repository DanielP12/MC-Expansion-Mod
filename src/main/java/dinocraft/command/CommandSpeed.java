package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSpeed extends CommandBase
{
	@Override
	public String getName() 
	{
		return "speed";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.speed.usage";
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
			throw new WrongUsageException("commands.speed.usage", new Object[0]);
		}
		else
        {
			Entity entity = args.length > 2 ? getEntity(server, sender, args[2]) : getCommandSenderAsPlayer(sender);

			if (entity instanceof EntityLivingBase)
			{
				EntityLivingBase entityliving = (EntityLivingBase) entity;
				double speed = parseDouble(args[1]) / 10.0D;
				
				if (args[0].equals("walk"))
				{
					DinocraftEntity.getEntity(entityliving).setMovementSpeed(speed);
					notifyCommandListener(sender, this, "commands.speed.walk.success", new Object[] {entityliving.getName(), args[0]});
				}
				else if (args[0].equals("fly"))
				{
					DinocraftEntity.getEntity(entityliving).setMovementSpeed(speed);
					notifyCommandListener(sender, this, "commands.speed.fly.success", new Object[] {entityliving.getName(), args[0]});
				}
			}
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"walk", "fly"}) : args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 2;
	}
}