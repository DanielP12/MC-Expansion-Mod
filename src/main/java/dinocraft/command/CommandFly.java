package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.network.CapFly;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

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
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer playerIn = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);            
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
        EntityPlayerMP playerMP = (EntityPlayerMP) playerIn;

        if (!player.canFly())
        {
        	player.sendChatMessage(TextFormatting.GREEN + "Turned on flight!");
			player.setAllowFlight(true);
            notifyCommandListener(sender, this, "commands.fly.success.on", new Object[] {"on".toString(), playerIn.getName()});
        	NetworkHandler.sendTo(new CapFly(true), playerMP);
			player.setFlight(true);
        }
        else
        {
        	player.sendChatMessage(TextFormatting.RED + "Turned off flight!");
			player.setAllowFlight(false);
            notifyCommandListener(sender, this, "commands.fly.success.off", new Object[] {"off".toString(), playerIn.getName()});
        	NetworkHandler.sendTo(new CapFly(false), playerMP);
			player.setFlight(false);
			DinocraftPlayer.getPlayer(playerIn).setFallDamage(false);
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