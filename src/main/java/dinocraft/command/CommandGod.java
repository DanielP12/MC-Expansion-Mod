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
 * Makes the specified living entity invulnerable for the specified amount of time.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandGod extends CommandBase
{
	@Override
	public String getName() 
	{
		return "god";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.god.usage";
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
			throw new WrongUsageException("commands.god.usage", new Object[0]);
		}
		else
        {
	        int time = parseInt(args[0]);
	        
	        if (time <= 0)
	        {
	        	throw new CommandException("commands.god.failed.time", new Object[0]);
	        }
            else
            {
            	Entity entity = args.length > 1 ? getEntity(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

    			if (entity instanceof EntityLivingBase)
    			{
    				EntityLivingBase entityliving = (EntityLivingBase) entity;
    				DinocraftEntity.getEntity(entityliving).setInvulnerable(time);
    				
    				/*
                    BlockPos pos = entityliving.getPosition();
                    entityliving.world.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.NEUTRAL, 10.0F, 0.25F);
                    entityliving.world.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.NEUTRAL, 10.0F, 0.7F);
                    entityliving.world.playSound(null, pos, SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.NEUTRAL, 5.0F, 3.0F);
                    
                    Random rand = entity.world.rand;

                    for (int i = 0; i < 25; ++i)
                    {
                    	DinocraftServer.spawnParticle(EnumParticleTypes.CRIT_MAGIC, false, entityliving.world, entityliving.posX + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width, 
                    			entityliving.posY + 0.5D + (rand.nextFloat() * entityliving.height), entityliving.posZ + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width, 
                    			rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, 1);
                    }
                    */
                    notifyCommandListener(sender, this, "commands.god.success", new Object[] {entityliving.getName(), time});
    			}
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