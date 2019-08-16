package dinocraft.command;

import dinocraft.capabilities.player.DinocraftPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
 	    EntityPlayer playerIn = getCommandSenderAsPlayer(sender);
 	    RayTraceResult trace = DinocraftPlayer.getPlayer(playerIn).getTrace(1000.0D);
 	    
 	    if (trace == null || trace.typeOfHit == RayTraceResult.Type.MISS) throw new CommandException("commands.break.failure", new Object[0]);
 	    else
 	    {
 	    	BlockPos pos = trace.getBlockPos();
 	    	World worldIn = playerIn.world;
 	    	notifyCommandListener(sender, this, "commands.break.success", new Object[] {worldIn.getBlockState(pos).getBlock().getLocalizedName()});
 	    	worldIn.setBlockToAir(pos);
 	    	worldIn.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.5F, 1.0F); 	    	        
 	    }
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}
