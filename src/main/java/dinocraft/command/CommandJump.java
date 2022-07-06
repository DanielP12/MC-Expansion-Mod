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
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Teleports the sender to the block they are looking at.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>jump</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/jump</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandJump extends CommandBase
{
	@Override
	public String getName()
	{
		return "jump";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/jump";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_JUMP, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		RayTraceResult result = DinocraftEntity.getBlockTrace(player, 1024.0D);

		if (result == null || result.typeOfHit == Type.MISS)
		{
			throw new CommandException("commands.jump.failed");
		}
		
		BlockPos pos = result.getBlockPos();
		player.setPositionAndUpdate(pos.getX(), pos.getY() + 1.0D, pos.getZ());
		sender.sendMessage(new TextComponentTranslation("commands.jump.jumpPlayer"));
	}
}