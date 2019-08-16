package dinocraft.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.network.MessageBoing;
import dinocraft.network.MessageCapabilitiesClient;
import dinocraft.network.MessageCapabilitiesClient.Capability;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandKaboom extends CommandBase
{
	@Override
	public String getName() 
	{
		return "kaboom";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.kaboom.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0)
        {
			List<EntityPlayer> list = Lists.newArrayList(server.getPlayerList().getPlayers());
			
			for (EntityPlayer playerIn : list)
    		{
    			NetworkHandler.sendTo(new MessageBoing(5.0D), (EntityPlayerMP) playerIn);
    			World worldIn = playerIn.world;
				worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, true));
    		
    			DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);

				if (player.hasFallDamage())
				{
					player.setFallDamage(false);
					NetworkHandler.sendTo(new MessageCapabilitiesClient(Capability.FALL_DAMAGE, false), (EntityPlayerMP) playerIn);
				}
    		}
			
            notifyCommandListener(sender, this, "commands.kaboom.success.allPlayers", new Object[0]);
        }
        else
        {
        	EntityPlayer playerIn = getPlayer(server, sender, args[0]);
        	World worldIn = playerIn.world;
			worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, true));
        	
        	EntityPlayerMP playerMP = getPlayer(server, sender, args[0]);
        	NetworkHandler.sendTo(new MessageBoing(5.0D), playerMP);

			DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
			
			if (player.hasFallDamage())
			{
				player.setFallDamage(false);
	            NetworkHandler.sendTo(new MessageCapabilitiesClient(Capability.FALL_DAMAGE, false), playerMP);
			}
			
        	notifyCommandListener(sender, this, "commands.kaboom.success.player", new Object[] {playerIn.getName()});
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