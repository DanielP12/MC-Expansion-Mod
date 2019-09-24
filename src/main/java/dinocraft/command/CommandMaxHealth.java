package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Sets the max health of the specified living entity to the specified amount.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
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
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.maxhealth.usage", new Object[0]);
		}
		else
        {
            float maxHealth = Float.parseFloat(args[0]);
            
            if (maxHealth <= 0.0F)
            {
            	throw new CommandException("commands.maxhealth.failed.level", new Object[0]);
            }
            else
            {
                EntityPlayer player = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
                DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
                dinoEntity.setMaxHealth(maxHealth);
        		
                /*
                Random rand = player.world.rand;

        		for (int i = 0; i < 16; ++i)
        		{
        			dinoEntity.spawnParticle(EnumParticleTypes.HEART, false, player.posX + (rand.nextFloat() * player.width * 2.0F) - player.width, 
        					player.posY + 0.5D + (rand.nextFloat() * player.height), player.posZ + (rand.nextFloat() * player.width * 2.0F) - player.width, 
        					rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, 1);
        		}
        		
            	player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.75F, 1.0F);
            	*/
            	notifyCommandListener(sender, this, "commands.maxhealth.success", new Object[] {player.getName(), maxHealth});
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