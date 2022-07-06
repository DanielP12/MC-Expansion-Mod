package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports the sender to the specified living entity.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
/**
 * Teleports the sender to the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>tpto</tt></span><br>
 * <b>Aliases:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>port</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/tpto &lt;entity|UUID&gt;</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandTPTo extends CommandBase
{
	@Override
	public String getName()
	{
		return "tpto";
	}

	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("port");
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.tpto.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TPTO, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.tpto.usage");
		}
		
		Entity entity = DinocraftCommandUtilities.getEntityFromUsernameOrUUID(server, sender, args[0]);
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		if (entity.world != player.world)
		{
			throw new CommandException("commands.tp.notSameDimension");
		}
		
		if (entity instanceof EntityLivingBase)
		{
			player.dismountRidingEntity();

			if (player instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) player).connection.setPlayerLocation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			}
			else
			{
				player.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			}

			notifyCommandListener(sender, this, "commands.tp.success", player.getName(), entity.getName());
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