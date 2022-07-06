package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Makes the specified living entity invulnerable for the specified amount of seconds.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>god</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/god [entity] [time]</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">If the time is not specified, the entity is made invulnerable permanently.</span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandGod extends CommandBase
{
	@Override
	public String getName()
	{
		return "god";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.god.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_GOD, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		int time = -1;

		if (args.length > 1)
		{
			time = parseInt(args[1]);

			if (time <= 0)
			{
				throw new CommandException("commands.god.failed.time");
			}
		}

		if (entity instanceof EntityLivingBase)
		{
			if (time == -1)
			{
				entity.setEntityInvulnerable(true);
				notifyCommandListener(sender, this, "commands.god.success.permanent", entity.getName());
			}
			else
			{
				DinocraftEntity.getEntity((EntityLivingBase) entity).setInvulnerable(time);
				notifyCommandListener(sender, this, "commands.god.success", entity.getName(), time);
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