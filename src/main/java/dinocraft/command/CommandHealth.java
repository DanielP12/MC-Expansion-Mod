package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the health of the specified living entity to the specified amount.
 * <br><br>
 * <b> Note: </b> If the specified amount is less than 0, the player will die.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
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
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.health.usage", new Object[0]);
		}
		else
        {
			Entity entity = args.length > 1 ? getEntity(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

			if (entity instanceof EntityLivingBase)
			{
				EntityLivingBase entityliving = (EntityLivingBase) entity;
				int health = parseInt(args[0]);
				entityliving.setHealth(health);
	            
				/*
	            Random rand = entityliving.world.rand;
	            
	        	for (int i = 0; i < 16; ++i)
	        	{
	    			DinocraftServer.spawnParticle(EnumParticleTypes.HEART, false, entityliving.world, entityliving.posX + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width,
	    					entityliving.posY + 0.5D + (rand.nextFloat() * entityliving.height), entityliving.posZ + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width,
	    					rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, 1);
	            }
	        	
	            entityliving.world.playSound(null, entityliving.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.75F, 1.0F);            	
	            */
	            notifyCommandListener(sender, this, "commands.health.success", new Object[] {entityliving.getName(), health});
			}
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 1;
	}
}