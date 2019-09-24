package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.MessageCapabilitiesClient;
import dinocraft.network.MessageCapabilitiesClient.Capability;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandVanish extends CommandBase
{
	@Override
	public String getName() 
	{
		return "vanish";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.vanish.usage";
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
			
		if (!dinoEntity.isVanished())
		{
			dinoEntity.vanish();
			NetworkHandler.sendTo(new MessageCapabilitiesClient(Capability.VANISH, true), (EntityPlayerMP) player);
			notifyCommandListener(sender, this, "commands.vanish.success", new Object[] {player.getName()});
		}
		else
		{
			throw new CommandException("commands.vanish.failed", new Object[] {player.getName()});
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