package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;

/**
 * Feeds the specified player.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>feed</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/feed [player]</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">Removes any hunger effect active on the player.</span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
//TODO: add flags: -h: restores hunger; -s: restores saturation (to current player food level); -e: restores exhaustion (to 4)
//TODO: allow to control whether or not it takes away the hunger effect with -n
public class CommandFeed extends CommandBase
{
	@Override
	public String getName()
	{
		return "feed";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.feed.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FEED, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player;
		int start = 0;
		
		if (args.length > 0 && !args[0].startsWith("-"))
		{
			player = getPlayer(server, sender, args[0]);
			start = 1;
		}
		else
		{
			player = getCommandSenderAsPlayer(sender);
		}
		
		FoodStats stats = player.getFoodStats();
		
		if (args.length == start)
		{
			stats.setFoodLevel(20);
			stats.setFoodSaturationLevel(20.0F);

			if (player.isPotionActive(MobEffects.HUNGER))
			{
				player.removePotionEffect(MobEffects.HUNGER);
			}
			
			notifyCommandListener(sender, this, "commands.feed.success.all", player.getName());
			return;
		}
		
		boolean food = false;
		boolean saturation = false;
		boolean hungerEffect = false;
		
		for (int i = start; i < (args.length > start + 3 ? start + 3 : args.length); i++)
		{
			if (args[i].equals("-f") && (i == 0 || !args[i - 1].equals("-f")))
			{
				food = true;
			}
			else if (args[i].equals("-s") && (i == 0 || !args[i - 1].equals("-s")))
			{
				saturation = true;
			}
			else if (args[i].equals("-h") && (i == 0 || !args[i - 1].equals("-h")))
			{
				hungerEffect = true;
			}
			else
			{
				throw new CommandException("commands.generic.invalidFlag");
			}
		}

		String[] finale = new String[3];
		int index = 0;

		if (food)
		{
			finale[index++] = "food";
			stats.setFoodLevel(20);
		}

		if (saturation)
		{
			finale[index++] = "saturation";
			stats.setFoodSaturationLevel(stats.getFoodLevel());
		}

		if (hungerEffect)
		{
			finale[index] = "hunger effect";

			if (player.isPotionActive(MobEffects.HUNGER))
			{
				player.removePotionEffect(MobEffects.HUNGER);
			}
		}
		
		notifyCommandListener(sender, this, "commands.feed.success", player.getName(), DinocraftCommandUtilities.joinNicely(finale));
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}