package dinocraft.command.server;

import java.util.ArrayList;
import java.util.List;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandListPlayerPings extends CommandBase
{
	@Override
	public String getName()
	{
		return "pinglist";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/pinglist";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_PINGLIST, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		List<EntityPlayerMP> list = server.getPlayerList().getPlayers();
		List<String> players = new ArrayList<>();
		
		for (EntityPlayerMP player : list)
		{
			players.add(player.getName() + " (" + (player.ping < 100 ? TextFormatting.DARK_GREEN + Integer.toString(player.ping) : player.ping < 150 ? TextFormatting.GREEN + Integer.toString(player.ping) : player.ping < 200 ? TextFormatting.YELLOW + Integer.toString(player.ping) : player.ping < 250 ? TextFormatting.GOLD + Integer.toString(player.ping) : player.ping < 300 ? TextFormatting.RED + Integer.toString(player.ping) : TextFormatting.DARK_RED + Integer.toString(player.ping)) + TextFormatting.RESET + ")");
		}
		
		sender.sendMessage(new TextComponentTranslation("commands.pinglist.players"));
		sender.sendMessage(new TextComponentString(joinNiceString(players.toArray())));
		
	}
}