package dinocraft.command;

import java.util.List;

import com.google.common.collect.Lists;

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
	public List<String> getAliases()
	{
		return Lists.newArrayList("j");
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
 	    EntityPlayer playerIn = getCommandSenderAsPlayer(sender);
 	    RayTraceResult trace = DinocraftPlayer.getPlayer(playerIn).getTrace(1000.0D);
 	    
 	    if (trace == null || trace.typeOfHit == RayTraceResult.Type.MISS) throw new CommandException("commands.jump.failure", new Object[0]);
 	    else
 	    {
 	    	BlockPos pos = trace.getBlockPos();
			playerIn.setPositionAndUpdate((double) pos.getX(), (double) pos.getY() + 1.0D, (double) pos.getZ());
			playerIn.world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.5F, 1.0F);
 	    	notifyCommandListener(sender, this, "commands.jump.success", new Object[0]);
 	    }
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}
