package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports the specified living entity to the topmost block in their y-axis.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>top</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/top [entity]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandTop extends CommandBase
{
	@Override
	public String getName()
	{
		return "top";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.top.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TOP, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			for (int i = entity.getServer().getBuildLimit(); i > entity.posY; i--)
			{
				if (!entity.world.isAirBlock(new BlockPos(entity.posX, i, entity.posZ)))
				{
					entity.setPositionAndUpdate(entity.posX, i + 1.0D, entity.posZ);
					notifyCommandListener(sender, this, "commands.top.success", entity.getName());
					return;
				}
			}

			throw new CommandException("commands.top.failed");
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