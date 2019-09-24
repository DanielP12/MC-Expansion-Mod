package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.MessageBounce;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sends the specified living entity to the sky.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
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
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{	
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase) entity;
			entityliving.world.addWeatherEffect(new EntityLightningBolt(entityliving.world, entityliving.posX, entityliving.posY, entityliving.posZ, true));
			entityliving.motionY = 5.0D;
			
			if (entityliving instanceof EntityPlayer)
			{
		        NetworkHandler.sendTo(new MessageBounce(5.0D), (EntityPlayerMP) entityliving);
			}
			
	        DinocraftEntity.getEntity(entityliving).setFallDamage(false);
	        notifyCommandListener(sender, this, "commands.kaboom.success", new Object[] {entityliving.getName()});
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}