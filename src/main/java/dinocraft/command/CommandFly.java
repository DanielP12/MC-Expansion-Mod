package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketFly;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Toggles fly on or off for specified player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandFly extends CommandBase
{
	@Override
	public String getName() 
	{
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.fly.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer player = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);            
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        if (!dinoEntity.canFly())
        {
        	//dinoEntity.sendChatMessage(TextFormatting.GREEN + "Turned on flight!");
        	dinoEntity.setAllowFlight(true);
            notifyCommandListener(sender, this, "commands.fly.success", new Object[] {"on", player.getName()});
        	NetworkHandler.sendTo(new PacketFly(true), playerMP);
        	dinoEntity.setFlight(true);
        }
        else
        {
        	//dinoEntity.sendChatMessage(TextFormatting.RED + "Turned off flight!");
        	dinoEntity.setAllowFlight(false);
            notifyCommandListener(sender, this, "commands.fly.success", new Object[] {"off", player.getName()});
        	NetworkHandler.sendTo(new PacketFly(false), playerMP);
        	dinoEntity.setFlight(false);
			dinoEntity.setFallDamage(false);
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