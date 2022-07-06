package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Teleports all online players to the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>tpall</tt></span><br>
 * <b>Aliases:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>tpa</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/tpall [entity|UUID]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandTPAll extends CommandBase
{
	@Override
	public String getName()
	{
		return "tpall";
	}

	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("tpa");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.tpall.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_TPALL, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length == 0 ? getCommandSenderAsPlayer(sender) : DinocraftCommandUtilities.getEntityFromUsernameOrUUID(server, sender, args[0]);

		for (EntityPlayerMP player : server.getPlayerList().getPlayers())
		{
			if (entity.world != player.world)
			{
				throw new CommandException("commands.tp.notSameDimension");
			}
			
			player.dismountRidingEntity();
			player.connection.setPlayerLocation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
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