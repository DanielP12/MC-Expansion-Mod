package dinocraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.util.DinocraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandFeed extends CommandBase
{
	@Override
	public String getName() 
	{
		return "feed";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.feed.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{	
	    EntityPlayer playerIn = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
        notifyCommandListener(sender, this, "commands.feed.success", new Object[] {playerIn.getName()});      
        DinocraftPlayer.getPlayer(playerIn).feed(20, 10.0F);
        	
		World worldIn = playerIn.world;
       	worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.CRUNCH, SoundCategory.PLAYERS, 0.75F, 1.0F);
       	Random rand = worldIn.rand;
        	
       	for (int i = 0; i < 25; ++i)
    	{
    		DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, worldIn, 
    			  playerIn.posX + (double)(rand.nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, 
   				  playerIn.posY + 0.5D + (double)(rand.nextFloat() * playerIn.height), 
   				  playerIn.posZ + (double)(rand.nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, 
  				  rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, 1
   			   );
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}