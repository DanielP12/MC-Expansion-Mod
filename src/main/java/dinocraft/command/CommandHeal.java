package dinocraft.command;

import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Heals the specified living entity. Gets rid of poison, wither, or fire effects if present on the player.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
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
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{		
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(3) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = args.length > 0 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase) entity;
			entityliving.heal(entityliving.getMaxHealth());
			
			if (entityliving.isBurning())
			{
				entityliving.extinguish();
			}
			
			if (entityliving.isPotionActive(MobEffects.POISON))
			{
				entityliving.removePotionEffect(MobEffects.POISON);
			}
			
			if (entityliving.isPotionActive(MobEffects.WITHER))
			{
				entityliving.removePotionEffect(MobEffects.WITHER);
			}
			
			/*
			Random rand = entityliving.world.rand;	
			
			for (int i = 0; i < 25; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, false, entityliving.world, entityliving.posX + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width,
						entityliving.posY + 0.5D + (rand.nextFloat() * entityliving.height), entityliving.posZ + (rand.nextFloat() * entityliving.width * 2.0F) - entityliving.width,
						rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, 1);
			}
			
			entityliving.world.playSound(null, entityliving.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.NEUTRAL, 0.75F, 1.0F);
			*/
			notifyCommandListener(sender, this, "commands.heal.success", new Object[] {entityliving.getName()});
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