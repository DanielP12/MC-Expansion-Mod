package dinocraft.command.server;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandListOps extends CommandBase
{
    @Override
	public String getName()
    {
        return "opslist";
    }

    @Override
	public String getUsage(ICommandSender sender)
    {
        return "commands.opslist.usage";
    }
    
    @Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

    @Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
    	UserListOps ops = server.getPlayerList().getOppedPlayers();
    	String[] list = ops.getKeys();
    	sender.sendMessage(new TextComponentTranslation("commands.opslist.players", new Object[] {list.length}));

    	for (String user : list)
    	{
    		GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(user);
    		sender.sendMessage(new TextComponentString(profile.getName() + " (permission level " + ops.getPermissionLevel(profile) + ")"));
    	}
    }
}