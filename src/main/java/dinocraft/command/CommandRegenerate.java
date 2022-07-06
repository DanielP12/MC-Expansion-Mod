package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the specified living entity regenerating/degenerating the specified amount of health every specified amount of seconds for a total of specified amount of seconds.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>regenerate</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/regenerate &lt;entity&gt; &lt;health&gt; &lt;loop time&gt; &lt;time&gt;</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandRegenerate extends CommandBase
{
	@Override
	public String getName()
	{
		return "regenerate";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.regenerate.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_REGENERATE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 3)
		{
			throw new WrongUsageException("commands.regenerate.usage");
		}

		Entity entity = getEntity(server, sender, args[0]);
		float health = (float) parseDouble(args[1]);
		float loopTime = (float) parseDouble(args[2]);
		int time = parseInt(args[3]);
		
		if (health == 0)
		{
			throw new CommandException("commands.regenerate.failed.health");
		}
		
		if (time <= 0 || loopTime <= 0)
		{
			throw new CommandException("commands.regenerate.failed.time");
		}
		
		if (entity instanceof EntityLivingBase)
		{
			DinocraftEntity.getEntity((EntityLivingBase) entity).setRegenerating(time, loopTime, health);

			if (health < 0)
			{
				notifyCommandListener(sender, this, "commands.regenerate.success", entity.getName(), "degenerating", -health, loopTime, time);
			}
			else
			{
				notifyCommandListener(sender, this, "commands.regenerate.success", entity.getName(), "regenerating", health, loopTime, time);
			}
		}
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