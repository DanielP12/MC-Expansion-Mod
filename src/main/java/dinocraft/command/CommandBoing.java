package dinocraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.network.MessageBoing;
import dinocraft.network.MessageCapabilitiesClient;
import dinocraft.network.MessageCapabilitiesClient.Capability;
import dinocraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class CommandBoing extends CommandBase
{
	@Override
	public String getName() 
	{
		return "boing";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.boing.usage";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("bounce");
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
        DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
        EntityPlayerMP playerMP = (EntityPlayerMP) playerIn;

        if (player.hasFallDamage())
        {
			player.setFallDamage(false);
	        NetworkHandler.sendTo(new MessageCapabilitiesClient(Capability.FALL_DAMAGE, false), playerMP);
		}
                        
        Random rand = new Random();
        float j = rand.nextFloat() + 0.45F;
        NetworkHandler.sendTo(new MessageBoing(rand.nextInt(3) + 1D), playerMP);
            
        playerIn.world.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.BOING, SoundCategory.PLAYERS, 3.0F, j);
        notifyCommandListener(sender, this, "commands.boing.success", new Object[] {playerIn.getName()});
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