package dinocraft.command;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Displays the coordinates the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>find</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/find &lt;entity&gt;</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandFind extends CommandBase
{
	@Override
	public String getName()
	{
		return "find";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.find.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_FIND, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.find.usage");
		}

		Entity entity = getEntity(server, sender, args[0]);

		if (entity instanceof EntityLivingBase)
		{
			DecimalFormat format = new DecimalFormat("0.00");
			format.setRoundingMode(RoundingMode.HALF_UP);
			String pos = format.format(entity.posX) + ", " + format.format(entity.posY) + ", " + format.format(entity.posZ);
			sender.sendMessage(new TextComponentTranslation("commands.find.findEntity", entity.getName(), pos));
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