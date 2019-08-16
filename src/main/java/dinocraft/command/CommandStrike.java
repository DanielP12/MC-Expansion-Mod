package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.player.DinocraftPlayer;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CommandStrike extends CommandBase
{
	@Override
	public String getName() 
	{
		return "strike";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.strike.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
        {
            EntityPlayer playerIn = getCommandSenderAsPlayer(sender);
            World worldIn = playerIn.world;
     	    RayTraceResult trace = DinocraftPlayer.getPlayer(playerIn).getTrace(1000.0D);
     	    
     	    if (trace == null || trace.typeOfHit == RayTraceResult.Type.MISS) throw new CommandException("commands.strike.failure", new Object[0]);
    	    else
    	    {
    	    	switch (trace.typeOfHit)
    	    	{
    	    		case BLOCK:
    	    		{
    	         	    BlockPos pos = trace.getBlockPos();
    					Block block = worldIn.getBlockState(pos).getBlock();
    		            notifyCommandListener(sender, this, "commands.strike.success", new Object[] {block.getLocalizedName()});
    					worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), false));
    					break;
    	    		}
    	    		
    	    		case ENTITY:
    	    		{
    	    			Entity entity = trace.entityHit;
    	                notifyCommandListener(sender, this, "commands.strike.success", new Object[] {entity.getName()});
    					worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, entity.posX, entity.posY, entity.posZ, false));
    	    		}
    	    		
    	    		default: break;
    	    	}
    	    }
        }
		else
        {
			Entity entity = getEntity(server, sender, args[0]);
            World worldIn = entity.world;
            notifyCommandListener(sender, this, "commands.strike.success", new Object[] {entity.getName()});
			worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, entity.posX, entity.posY, entity.posZ, false));
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