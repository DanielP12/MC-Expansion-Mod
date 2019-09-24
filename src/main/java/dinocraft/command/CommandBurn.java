package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Burns the specified living entity for the specified amount of time.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandBurn extends CommandBase
{
	@Override
	public String getName() 
	{
		return "burn";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.burn.usage";
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
			throw new WrongUsageException("commands.burn.usage", new Object[0]);
		}
		else
        {
			Entity entity = args.length > 1 ? getEntity(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

			if (entity instanceof EntityLivingBase)
			{
				int time = parseInt(args[0]);
				entity.setFire(time);
                
				/*
				Random rand = entity.world.rand;
            
				for (int i = 0; i < 50; ++i)
				{
					DinocraftServer.spawnParticle(EnumParticleTypes.BLOCK_CRACK, false, entity.world, entity.posX + (rand.nextFloat() * entity.width * 2.0F) - entity.width, 
							entity.posY + 0.5D + (rand.nextFloat() * entity.height), entity.posZ + (rand.nextFloat() * entity.width * 2.0F) - entity.width, 
							rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, Block.getIdFromBlock(Blocks.LAVA));
				}
				
				entity.world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.NEUTRAL, 0.75F, 0.75F);
				*/
				notifyCommandListener(sender, this, "commands.burn.success", new Object[] {entity.getName(), time});
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