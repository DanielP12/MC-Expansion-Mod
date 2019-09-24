package dinocraft.command;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

/**
 * Teleports the sender to the block they are looking at.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandJump extends CommandBase
{
	@Override
	public String getName() 
	{
		return "jump";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/jump";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(2) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
 	    EntityPlayer player = getCommandSenderAsPlayer(sender);
 	    RayTraceResult result = DinocraftEntity.getEntity(player).getTrace(1000.0D);
 	    
 	    if (result == null || result.typeOfHit == RayTraceResult.Type.MISS)
 	    {
 	    	throw new CommandException("commands.jump.failed", new Object[0]);
 	    }
 	    else
 	    {
 	    	BlockPos pos = result.getBlockPos();
 	    	player.setPositionAndUpdate(pos.getX(), pos.getY() + 1.0D, pos.getZ());
 	    	player.world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.5F, 1.0F);
 	    	notifyCommandListener(sender, this, "commands.jump.success", new Object[0]);
 	    }
	}
}