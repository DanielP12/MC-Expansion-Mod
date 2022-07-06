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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the the specified living entity's health to the specified amount.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>health</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/health &lt;entity&gt; &lt;amount&gt;</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">If the amount is less than or equal to 0, the entity will die.</span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandHealth extends CommandBase
{
	@Override
	public String getName()
	{
		return "health";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.health.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_HEALTH, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.health.usage");
		}
		
		Entity entity = getEntity(server, sender, args[0]);
		
		if (entity instanceof EntityLivingBase)
		{
			((EntityLivingBase) entity).setHealth(parseInt(args[1]));
			notifyCommandListener(sender, this, "commands.health.success", entity.getName(), args[1]);
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