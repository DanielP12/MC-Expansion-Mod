package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Heals the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>heal</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/heal [entity]</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">Removes any poison, wither, and fire effects active on the entity.</span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
//TODO: allow to control whether it takes out active harmful effects with flag -n
public class CommandHeal extends CommandBase
{
	@Override
	public String getName()
	{
		return "heal";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.heal.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_HEAL, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
			living.heal(living.getMaxHealth());
			
			if (living.isBurning())
			{
				living.extinguish();
			}
			
			if (living.isPotionActive(MobEffects.POISON))
			{
				living.removePotionEffect(MobEffects.POISON);
			}
			
			if (living.isPotionActive(MobEffects.WITHER))
			{
				living.removePotionEffect(MobEffects.WITHER);
			}

			notifyCommandListener(sender, this, "commands.heal.success", living.getName());
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