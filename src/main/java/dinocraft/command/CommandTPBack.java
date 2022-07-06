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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports the specified living entity back to their last location before teleporting.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>tpback</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/tpback [entity|UUID]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandTPBack extends CommandBase
{
	@Override
	public String getName()
	{
		return "tpback";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.tpback.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TPBACK, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length == 0 ? getCommandSenderAsPlayer(sender) : DinocraftCommandUtilities.getEntityFromUsernameOrUUID(server, sender, args[0]);
		
		if (entity instanceof EntityLivingBase)
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity((EntityLivingBase) entity);
			BlockPos pos = dinoEntity.getPosBeforeLastTeleport();

			if (pos == null)
			{
				throw new CommandException("commands.tpback.failed", entity.getName());
			}

			if (entity.world != null)
			{
				entity.dismountRidingEntity();

				if (entity instanceof EntityPlayerMP)
				{
					((EntityPlayerMP) entity).connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				}
				else
				{
					entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				}

				notifyCommandListener(sender, this, "commands.tpback.success", entity.getName());
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