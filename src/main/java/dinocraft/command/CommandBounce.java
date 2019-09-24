package dinocraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.network.MessageBounce;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

/**
 * Bounces the specified living entity.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
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
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		
		if (entity instanceof EntityLivingBase)
		{
			Random rand = entity.world.rand;
			double motion = rand.nextInt(3) + 1.0D;
			EntityLivingBase entityliving = (EntityLivingBase) entity;
			entityliving.motionY = motion;
			
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
	        dinoEntity.setFallDamage(false);
	        
	        if (entityliving instanceof EntityPlayer)
	        {
		        NetworkHandler.sendTo(new MessageBounce(motion), (EntityPlayerMP) entityliving);
	        }
	        
	        entityliving.world.playSound(null, entityliving.getPosition(), DinocraftSoundEvents.BOING, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 0.5F);
	        notifyCommandListener(sender, this, "commands.bounce.success", new Object[] {entityliving.getName()});
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