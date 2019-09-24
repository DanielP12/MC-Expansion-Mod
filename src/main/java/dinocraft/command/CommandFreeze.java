package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandFreeze extends CommandBase
{
	@Override
	public String getName() 
	{
		return "freeze";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.freeze.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase) entity;
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
			
			if (!dinoEntity.isFrozen())
			{
				dinoEntity.freeze();
				notifyCommandListener(sender, this, "commands.freeze.success", new Object[] {entityliving.getName()});
			}
			else
			{
				throw new CommandException("commands.freeze.failed", new Object[] {entityliving.getName()});
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}