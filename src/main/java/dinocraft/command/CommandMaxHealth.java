package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
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
 * Sets the the specified living entity's max health to the specified amount.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>maxhealth</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/maxhealth &lt;entity&gt; &lt;amount&gt;</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandMaxHealth extends CommandBase
{
	@Override
	public String getName()
	{
		return "maxhealth";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.maxhealth.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_MAXHEALTH, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 1)
		{
			throw new WrongUsageException("commands.maxhealth.usage");
		}

		float amount = (float) parseDouble(args[1]);
		
		if (amount <= 0.0F)
		{
			throw new CommandException("commands.maxhealth.failed.level");
		}

		Entity entity = getEntity(server, sender, args[0]);

		if (entity instanceof EntityLivingBase)
		{
			DinocraftEntity.setMaxHealth((EntityLivingBase) entity, amount);
			notifyCommandListener(sender, this, "commands.maxhealth.success", entity.getName(), amount);
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