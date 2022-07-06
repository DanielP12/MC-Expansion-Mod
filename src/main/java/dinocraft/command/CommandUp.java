package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports the specified living entity up/down the specified amount of blocks.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>up</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/up &lt;entity&gt; &lt;amount&gt;</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandUp extends CommandBase
{
	@Override
	public String getName()
	{
		return "up";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.up.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UP, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.up.usage");
		}

		Entity entity = getEntity(server, sender, args[0]);

		if (entity instanceof EntityLivingBase)
		{
			entity.dismountRidingEntity();
			int amount = parseInt(args[1]);

			if (entity instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entity).connection.setPlayerLocation(entity.posX, entity.posY + amount, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			}
			else
			{
				entity.setLocationAndAngles(entity.posX, entity.posY + amount, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			}

			if (amount < 0)
			{
				notifyCommandListener(sender, this, "commands.up.success.down", entity.getName(), -amount);
			}
			else
			{
				notifyCommandListener(sender, this, "commands.up.success.up", entity.getName(), amount);
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