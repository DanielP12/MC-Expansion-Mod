package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketStrikeEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Strikes the specified living entity.<p>
 * <b>Name:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>strike</tt></span><br>
 * <b>Aliases:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>smite<br>lightning<br>thor</tt></span><br>
 * <b>Usage:</b><br>
 * <span style="margin-left: 40px; display: inline-block"><tt>/strike [entity]</tt></span><br>
 * <b>Notes:</b><br>
 * <span style="margin-left: 40px; display: inline-block">If the entity is not specified, strikes the block that the sender is looking at.</span><p>
 * <b>Copyright © 2019 Danfinite</b>
 */
public class CommandStrike extends CommandBase
{
	@Override
	public String getName()
	{
		return "strike";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("smite", "lightning", "thor");
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.strike.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_STRIKE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			PacketHandler.sendTo(new SPacketStrikeEntity(), (EntityPlayerMP) sender);
		}
		else
		{
			Entity entity = getEntity(server, sender, args[0]);
			
			if (entity instanceof EntityLivingBase)
			{
				entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, entity.posX, entity.posY, entity.posZ, false));
				notifyCommandListener(sender, this, "commands.strike.success", entity.getName());
			}
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