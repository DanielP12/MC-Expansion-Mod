package dinocraft.command;

import java.util.Collections;
import java.util.List;

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
 * Teleports the specified living entity to the spawnpoint.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>spawn</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/spawn [entity]</tt></span><p>
 * <b>Copyright © 2020 Danfinite</b>
 */
public class CommandSpawn extends CommandBase
{
	@Override
	public String getName()
	{
		return "spawn";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.spawn.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_SPAWN, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			entity.dismountRidingEntity();
			BlockPos spawn = entity.world.getSpawnPoint();

			if (entity instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entity).connection.setPlayerLocation(spawn.getX(), spawn.getY(), spawn.getZ(), entity.rotationYaw, entity.rotationPitch);
			}
			else
			{
				entity.setLocationAndAngles(spawn.getX(), spawn.getY(), spawn.getZ(), entity.rotationYaw, entity.rotationPitch);
			}

			notifyCommandListener(sender, this, "commands.spawn.success", entity.getName());
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