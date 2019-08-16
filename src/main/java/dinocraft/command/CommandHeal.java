package dinocraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import dinocraft.util.DinocraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandHeal extends CommandBase
{
	@Override
	public String getName() 
	{
		return "heal";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.heal.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.heal(entityLiving.getMaxHealth());
			notifyCommandListener(sender, this, "commands.heal.success", new Object[] {entityLiving.getName()});
		
			World worldIn = entityLiving.world;
			worldIn.playSound((EntityPlayer) null, entityLiving.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.PLAYERS, 0.75F, 1.0F);
			Random rand = worldIn.rand;	
        
			for (int i = 0; i < 25; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, worldIn, 
					  entityLiving.posX + (double)(rand.nextFloat() * entityLiving.width * 2.0F) - (double)entityLiving.width, 
					  entityLiving.posY + 0.5D + (double)(rand.nextFloat() * entityLiving.height), 
					  entityLiving.posZ + (double)(rand.nextFloat() * entityLiving.width * 2.0F) - (double)entityLiving.width, 
					  rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, 1
				  );
			}
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


