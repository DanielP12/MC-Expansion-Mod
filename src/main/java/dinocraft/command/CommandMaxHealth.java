package dinocraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.util.DinocraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandMaxHealth extends CommandBase
{
	@Override
	public String getName() 
	{
		return "maxhealth";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.maxhealth.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0) throw new WrongUsageException("commands.maxhealth.usage", new Object[0]);
		else
        {
            String arg = args[0];
            int maxHealth = parseInt(arg);
            
            if (maxHealth <= 0) throw new CommandException("commands.maxhealth.failure.level", new Object[0]);
            else
            {
                EntityPlayer playerIn = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
            	notifyCommandListener(sender, this, "commands.maxhealth.success", new Object[] {playerIn.getName(), maxHealth});
        		DinocraftPlayer.getPlayer(playerIn).setMaxHealth((float) maxHealth);
        		
            	World worldIn = playerIn.world;
            	worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.75F, 1.0F);            	
                
                Random rand = worldIn.rand;

        		for (int i = 0; i < 16; ++i)
        		{
        			DinocraftServer.spawnParticle(EnumParticleTypes.HEART, worldIn, 
        				  playerIn.posX + (double)(rand.nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, 
        				  playerIn.posY + 0.5D + (double)(rand.nextFloat() * playerIn.height), 
        				  playerIn.posZ + (double)(rand.nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, 
        				  rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, 1
        			   );
        		}
            }
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) 
	{
        return args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}
