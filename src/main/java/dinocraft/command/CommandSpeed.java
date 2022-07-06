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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the specified speed type of the specified entity to the specified amount. Input "reset" to the first argument to reset the speed.
 * <br><br>
 * <b> Copyright © 2019 - 2020 Danfinite </b>
 */
/**
 * Sets the the specified living entity's walk/fly speed to the specified amount.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>speed</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/speed &lt;entity&gt; &lt;&lt;amount&gt;|reset&gt; &lt;walk|fly|*&gt;</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
public class CommandSpeed extends CommandBase
{
	@Override
	public String getName()
	{
		return "speed";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.speed.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_SPEED, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 2)
		{
			throw new WrongUsageException("commands.speed.usage");
		}
		
		Entity entity = getEntity(server, sender, args[0]);
		
		if (entity instanceof EntityLivingBase)
		{
			String name = entity.getName();
			
			if (args[2].equals("walk"))
			{
				if (args[1].equals("reset"))
				{
					DinocraftEntity.setMovementSpeed((EntityLivingBase) entity, 0.0D);
					notifyCommandListener(sender, this, "commands.speed.success.walk", name, "normal");
				}
				else
				{
					double amount = parseDouble(args[1]);
					DinocraftEntity.setMovementSpeed((EntityLivingBase) entity, amount <= 0.0D ? -1.0D : (amount - 1.0D) / 20.0D);
					notifyCommandListener(sender, this, "commands.speed.success.walk", name, amount);
				}
			}
			else if (args[2].equals("fly"))
			{
				if (args[1].equals("reset"))
				{
					if (entity instanceof EntityPlayer)
					{
						DinocraftEntity.setFlySpeed((EntityPlayer) entity, 0.05D);
					}

					notifyCommandListener(sender, this, "commands.speed.success.fly", name, "normal");
				}
				else
				{
					double amount = parseDouble(args[1]);

					if (entity instanceof EntityPlayer)
					{
						DinocraftEntity.setFlySpeed((EntityPlayer) entity, amount / 20.0D);
					}

					notifyCommandListener(sender, this, "commands.speed.success.fly", name, amount);
				}
			}
			else if (args[2].equals("*"))
			{
				if (args[1].equals("reset"))
				{
					if (entity instanceof EntityPlayer)
					{
						DinocraftEntity.setFlySpeed((EntityPlayer) entity, 0.05D);
					}

					DinocraftEntity.setMovementSpeed((EntityLivingBase) entity, 0.0D);
					notifyCommandListener(sender, this, "commands.speed.success.walk", name, "normal");
					notifyCommandListener(sender, this, "commands.speed.success.fly", name, "normal");
				}
				else
				{
					double amount = parseDouble(args[1]);

					if (entity instanceof EntityPlayer)
					{
						DinocraftEntity.setFlySpeed((EntityPlayer) entity, amount / 20.0D);
					}

					DinocraftEntity.setMovementSpeed((EntityLivingBase) entity, amount <= 0.0D ? -1.0D : (amount - 1.0D) / 20.0D);
					notifyCommandListener(sender, this, "commands.speed.success.walk", name, amount);
					notifyCommandListener(sender, this, "commands.speed.success.fly", name, amount);
				}
			}
			else
			{
				throw new CommandException("commands.speed.failed.invalid", args[2]);
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if (args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		else if (args.length == 2)
		{
			return getListOfStringsMatchingLastWord(args, "reset");
		}
		else if (args.length == 3)
		{
			return getListOfStringsMatchingLastWord(args, "walk", "fly", "*");
		}
		
		return Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}