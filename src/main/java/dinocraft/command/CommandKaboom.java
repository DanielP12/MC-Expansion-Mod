package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Launches the specified living entity to the air.
 * <br><br>
 * <b> Copyright © 2019 Danfinite </b>
 */
/**
 * Launches the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>kaboom</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/kaboom [entity]</tt></span><p>
 * <b>Copyright © 2019 - 2020 Danfinite</b>
 */
//TODO: make sure horses and llamas don't get hurt when falling (maybe cancel for them)
public class CommandKaboom extends CommandBase
{
	@Override
	public String getName()
	{
		return "kaboom";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.kaboom.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_KABOOM, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		
		if (entity instanceof EntityLivingBase)
		{
			entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, entity.posX, entity.posY, entity.posZ, true));

			if (entity instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entity).connection.sendPacket(new SPacketEntityVelocity(entity.getEntityId(), 0.0D, 5.0D, 0.0D));
			}
			else
			{
				entity.motionY = 5.0D;
			}

			DinocraftEntity.getEntity((EntityLivingBase) entity).setFallDamage(false);
			notifyCommandListener(sender, this, "commands.kaboom.success", entity.getName());
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