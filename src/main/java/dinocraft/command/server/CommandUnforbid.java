package dinocraft.command.server;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.server.DinocraftPlayerList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandUnforbid extends CommandBase
{
	@Override
	public String getName() 
	{
		return "unforbid";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("allow");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.unforbid.usage";
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
			throw new WrongUsageException("commands.unforbid.usage", new Object[0]);
		}
		else if (args[0].equals("@a"))
		{
			if (!DinocraftPlayerList.FORBIDDEN_WORDS.isEmpty())
			{
				DinocraftPlayerList.FORBIDDEN_WORDS.getValues().clear();
				
				try
				{
					DinocraftPlayerList.FORBIDDEN_WORDS.writeChanges();
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
				
				notifyCommandListener(sender, this, "commands.unforbid.success.all", new Object[0]);
			}
			else
			{
				throw new CommandException("commands.unforbid.failed.empty", new Object[0]);
			}
		}
		else
        {
			if (DinocraftPlayerList.FORBIDDEN_WORDS.isForbidden(args[0]))
			{   
				DinocraftPlayerList.FORBIDDEN_WORDS.removeEntry(args[0]);
				notifyCommandListener(sender, this, "commands.unforbid.success", new Object[] {args[0]});
			}
			else
			{
				throw new CommandException("commands.unforbid.failed", new Object[] {args[0]});
			}
        }
	}
}