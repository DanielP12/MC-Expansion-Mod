package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Feeds the specified player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
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
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{	
	    EntityPlayer player = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
	    DinocraftEntity.getEntity(player).feed(20, 10.0F);
	    
	    if (player.isPotionActive(MobEffects.HUNGER))
		{
	    	player.removePotionEffect(MobEffects.HUNGER);
		}
	    
	    /*
        Random rand = player.world.rand;
        	
       	for (int i = 0; i < 25; ++i)
    	{
       		dinoEntity.spawnParticle(EnumParticleTypes.END_ROD, false, player.posX + (rand.nextFloat() * player.width * 2.0F) - player.width, 
       				player.posY + 0.5D + (rand.nextFloat() * player.height), player.posZ + (rand.nextFloat() * player.width * 2.0F) - player.width, 
       				rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, 1);
        }
       	
        player.world.playSound(null, player.getPosition(), DinocraftSoundEvents.CRUNCH, SoundCategory.NEUTRAL, 0.75F, 1.0F);
        */
        notifyCommandListener(sender, this, "commands.feed.success", new Object[] {player.getName()});
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}
}