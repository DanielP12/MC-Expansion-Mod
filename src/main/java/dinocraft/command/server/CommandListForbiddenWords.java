package dinocraft.command.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.server.DinocraftPlayerList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandListForbiddenWords extends CommandBase
{
    @Override
	public String getName()
    {
        return "forbiddenlist";
    }

    @Override
	public String getUsage(ICommandSender sender)
    {
        return "commands.forbiddenlist.usage";
    }
    
    @Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

    @Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
    	sender.sendMessage(new TextComponentTranslation("commands.forbiddenlist.words", new Object[] {DinocraftPlayerList.FORBIDDEN_WORDS.getKeys().length}));
        sender.sendMessage(new TextComponentString(joinNiceString(DinocraftPlayerList.FORBIDDEN_WORDS.getKeys())));
    }
}