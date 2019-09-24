package dinocraft.command;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

/**
 * Breaks the block that the sender is looking at.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandBreak extends CommandBase
{
	@Override
	public String getName() 
	{
		return "break";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/break";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
 	    EntityPlayer player = getCommandSenderAsPlayer(sender);
 	    RayTraceResult trace = DinocraftEntity.getEntity(player).getTrace(1000.0D);
 	    
 	    if (trace == null || trace.typeOfHit == RayTraceResult.Type.MISS)
 	    {
 	    	throw new CommandException("commands.break.failed", new Object[0]);
 	    }
 	    else
 	    {
 	    	BlockPos pos = trace.getBlockPos();
 	    	player.world.setBlockToAir(pos);
 	    	//player.world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.5F, 1.0F); 	 
 	    	notifyCommandListener(sender, this, "commands.break.success", new Object[] {player.world.getBlockState(pos).getBlock().getLocalizedName()});
 	    }
	}
}