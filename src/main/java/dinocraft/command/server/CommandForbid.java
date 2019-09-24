package dinocraft.command.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.server.DinocraftPlayerList;
import dinocraft.util.server.ListForbiddenWordsEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandForbid extends CommandBase
{
	@Override
	public String getName() 
	{
		return "forbid";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.forbid.usage";
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
			throw new WrongUsageException("commands.forbid.usage", new Object[0]);
		}
		else
        {
			/*
			if (args[0].length() == 0)
			{
				throw new CommandException("commands.forbid.failed.invalid", new Object[] {args[0]});
			}
			else
			{*/
				for (int i = 0; i < args.length; ++i)
				{
					if (!args[i].equals(" ") && !DinocraftPlayerList.FORBIDDEN_WORDS.isForbidden(args[i]))
					{
						ListForbiddenWordsEntry entry = new ListForbiddenWordsEntry(args[i]);
						DinocraftPlayerList.FORBIDDEN_WORDS.addEntry(entry);
						notifyCommandListener(sender, this, "commands.forbid.success", new Object[] {args[i]});
					}
					else
					{
						sender.sendMessage(new TextComponentTranslation("commands.forbid.failed", new Object[] {args[i]}));
					}
				//}
				/*
				if (!DinocraftPlayerList.FORBIDDEN_WORDS.isForbidden(args[0]))
				{   
					ListForbiddenWordsEntry entry = new ListForbiddenWordsEntry(args[0]);
					DinocraftPlayerList.FORBIDDEN_WORDS.addEntry(entry);
					notifyCommandListener(sender, this, "commands.forbid.success", new Object[] {args[0]});
				}
				else
				{
					throw new CommandException("commands.forbid.failed", new Object[] {args[0]});
				}
				*/
			}
        }
	}
}