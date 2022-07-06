package dinocraft.command.server;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Displays the inventory of the specified player.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
public class CommandSeeInventory extends CommandBase
{
	@Override
	public String getName()
	{
		return "inventory";
	}

	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("invsee");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.inventory.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_INVENTORY, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.inventory.usage");
		}

		EntityPlayerMP player = getPlayer(server, sender, args[0]);

		if (args.length >= 2 && args[1].equalsIgnoreCase("enderchest"))
		{
			getCommandSenderAsPlayer(sender).displayGUIChest(player.getInventoryEnderChest());
			sender.sendMessage(new TextComponentTranslation("commands.inventory.displayingEnderchest", player.getName()));
		}
		else
		{
			getCommandSenderAsPlayer(sender).displayGUIChest(player.inventory);
			sender.sendMessage(new TextComponentTranslation("commands.inventory.displayingInventory", player.getName()));
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"enderchest"}) : Collections.emptyList();
	}
}