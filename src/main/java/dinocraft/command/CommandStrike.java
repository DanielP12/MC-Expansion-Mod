package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Strikes the specified living entity.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
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
		return Lists.newArrayList("smite");
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.strike.usage";
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
            EntityPlayer player = getCommandSenderAsPlayer(sender);
            World world = player.world;
     	    RayTraceResult result = DinocraftEntity.getEntity(player).getTrace(1000.0D);
     	    
     	    if (result == null || result.typeOfHit == RayTraceResult.Type.MISS)
     	    {
     	    	throw new CommandException("commands.strike.failed", new Object[0]);
     	    }
    	    else
    	    {
    	    	switch (result.typeOfHit)
    	    	{
    	    		case ENTITY:
    	    		{
    	    			Entity entity = result.entityHit;
    	    			world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ, false));
    	    			notifyCommandListener(sender, this, "commands.strike.success", new Object[] {entity.getName()});
    	    			break;
    	    		}
    	    	
    	    		case BLOCK:
    	    		{
    	         	    BlockPos pos = result.getBlockPos();
    					Block block = world.getBlockState(pos).getBlock();
    					world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
    		            notifyCommandListener(sender, this, "commands.strike.success", new Object[] {block.getLocalizedName()});
    					break;
    	    		}
    	    		
    	    		default: break;
    	    	}
    	    }
        }
		else
        {
			Entity entity = getEntity(server, sender, args[0]);
			
			if (entity instanceof EntityLivingBase)
			{
				EntityLivingBase entityliving = (EntityLivingBase) entity;
				World world = entityliving.world;
				world.addWeatherEffect(new EntityLightningBolt(world, entityliving.posX, entityliving.posY, entityliving.posZ, false));
			
				notifyCommandListener(sender, this, "commands.strike.success", new Object[] {entityliving.getName()});
			}
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