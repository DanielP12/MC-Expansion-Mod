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
 * Unfreezes the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>unfreeze</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/unfreeze [entity]</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandUnfreeze extends CommandBase
{
	@Override
	public String getName()
	{
		return "unfreeze";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.unfreeze.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UNFREEZE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		
		if (entity instanceof EntityLivingBase)
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity((EntityLivingBase) entity);
			String name = entity.getName();

			if (dinoEntity.isFrozen())
			{
				dinoEntity.unfreeze();
				notifyCommandListener(sender, this, "commands.unfreeze.success", name);
			}
			else
			{
				throw new CommandException("commands.unfreeze.failed", name);
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