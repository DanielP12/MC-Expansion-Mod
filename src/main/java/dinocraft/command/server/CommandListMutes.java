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

/**
 * Displays a list containing all the muted users.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandListMutes extends CommandBase
{
    @Override
	public String getName()
    {
        return "mutelist";
    }

    @Override
	public String getUsage(ICommandSender sender)
    {
        return "commands.mutelist.usage";
    }
    
    @Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(2) : true;
	}

    @Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
    	sender.sendMessage(new TextComponentTranslation("commands.mutelist.players", new Object[] {DinocraftPlayerList.MUTED_PLAYERS.getKeys().length}));
        sender.sendMessage(new TextComponentString(joinNiceString(DinocraftPlayerList.MUTED_PLAYERS.getKeys())));
    }
}