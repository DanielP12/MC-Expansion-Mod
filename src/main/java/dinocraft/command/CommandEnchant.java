/*package dinocraft.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.player.DinocraftPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandEnchant extends CommandBase
{
	@Override
	public String getName() 
	{
		return "more";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.more.usage";
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0) throw new WrongUsageException("commands.more.usage", new Object[0]);
		else if (args.length >= 1)
        {
            String arg = args[0];
            int level = parseInt(arg);
            
            EntityPlayer playerIn = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

            if (args.length > 2)
            {
            	int flag = 0;
            	
            	for (ItemStack stack : playerIn.inventoryContainer.getInventory())
            	{
            		if (args[2].equals(stack.getDisplayName().replace(" ", "_")))
            		{
            			stack.addEnchantment(Enchantment.getEnchantmentByLocation(args[2]), level);
            			flag = 1;
            			if (stack.getCount() + amount >= 128) throw new CommandException("commands.more.failure.amount", new Object[] {amount, 127 - stack.getCount()});
                        stack.grow(amount);
            		}
            	}
            	
            	if (flag == 0) throw new CommandException("commands.more.failure.noitem", new Object[] {args[2].replace("_", " ")});
            }
            else if (!playerIn.getHeldItemMainhand().isEmpty())
            {
            	ItemStack stack = playerIn.getHeldItemMainhand();
            	
    			if (stack.getCount() + amount >= 128) throw new CommandException("commands.more.failure.amount", new Object[] {amount, 127 - stack.getCount()});
                stack.grow(amount);
            }
            else throw new CommandException("commands.more.failure.empty", new Object[0]);
            
            notifyCommandListener(sender, this, amount < 0 ? "commands.more.success.decrease" : "commands.more.success.increase", new Object[] {playerIn.getName(), amount < 0 ? amount * -1 : amount, args.length > 2 ? args[2].replace("_", " ") : playerIn.getHeldItemMainhand().getDisplayName()});
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{
		List<String> stacks = new ArrayList<String>();
		
		if (args.length == 2) return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		else if (args.length == 3) 
		{
			for (ItemStack stack : DinocraftPlayer.getEntityPlayerByName(args[1]).inventoryContainer.getInventory())
			{
				if (stack.getDisplayName().equals("Air")) continue;
				else stacks.add(stack.getDisplayName().replace(" ", "_"));
			}
			
			return getListOfStringsMatchingLastWord(args, stacks);
		}
		
		return Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
        return index == 0;
	}
}
*/