package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

/**
 * Bounces the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>bounce</tt></span><br>
 * <b>Aliases:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>boing</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/bounce [entity]</tt></span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandBounce extends CommandBase
{
	@Override
	public String getName()
	{
		return "bounce";
	}

	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("boing");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.bounce.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_BOUNCE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			if (entity instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entity).connection.sendPacket(new SPacketEntityVelocity(entity.getEntityId(), 0.0D, entity.world.rand.nextInt(3) + 1.0D, 0.0D));
			}
			else
			{
				entity.motionY = entity.world.rand.nextInt(3) + 1.0D;
			}

			DinocraftEntity.getEntity((EntityLivingBase) entity).setFallDamage(false);
			entity.world.playSound(null, entity.getPosition(), DinocraftSoundEvents.BOUNCE, SoundCategory.NEUTRAL, 1.0F, entity.world.rand.nextFloat() + 0.5F);
			notifyCommandListener(sender, this, "commands.bounce.success", entity.getName());
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