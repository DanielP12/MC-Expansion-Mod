package dinocraft.command;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

/**
 * Breaks the block that the sender is looking at.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>break</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/break</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandBreak extends CommandBase
{
	@Override
	public String getName()
	{
		return "break";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/break";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_BREAK, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		RayTraceResult trace = DinocraftEntity.getBlockTrace(player, 1024.0D);
		
		if (trace == null || trace.typeOfHit == Type.MISS)
		{
			throw new CommandException("commands.break.failed");
		}

		BlockPos pos = trace.getBlockPos();
		notifyCommandListener(sender, this, "commands.break.success", player.world.getBlockState(pos).getBlock().getLocalizedName());
		player.world.setBlockToAir(pos);
	}
}