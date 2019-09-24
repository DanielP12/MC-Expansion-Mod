package dinocraft.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * Adds the specified amount to all instances of the specified stack in the specified player's inventory.
 * If the specified amount is negative, subtracts the specified amount from the specified stack in the specified player's inventory.
 * <br><br>
 * <b> Notes: </b> The specified amount added with the stack's current size must equal less than 128.
 * If the specified amount added with the stack's current size equals a negative number, the stack will be destroyed.
 * <br><br>
 * <b> Copyright © Danfinite 2019 </b>
 */
public class CommandAdd extends CommandBase
{
	@Override
	public String getName() 
	{
		return "add";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.add.usage";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(4) : true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if (args.length == 0) 
		{
			throw new WrongUsageException("commands.add.usage", new Object[0]);
		}
		else if (args.length > 0)
        {
            int amount = parseInt(args[0]);
            EntityPlayer player = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

            if (args.length > 2)
            {
            	boolean flag = false;
            	
            	for (ItemStack stack : player.inventoryContainer.getInventory())
            	{
            		if (args[2].equals(stack.getDisplayName().replace(" ", "_")))
            		{            			
            			if (stack.getCount() + amount >= 128)
            			{
            				throw new CommandException("commands.generic.num.tooBig", new Object[] {amount, 127 - stack.getCount()});
            			}
            			
                        stack.grow(amount);
            			flag = true;
            		}
            	}
            	
            	String item = args[2].replace("_", " ");
            	
            	if (!flag)
            	{
            		throw new CommandException("commands.add.failed.noitem", new Object[] {item});
            	}
            	
                notifyCommandListener(sender, this, amount < 0 ? "commands.add.success.decrease" : "commands.add.success.increase", new Object[] {player.getName(), amount < 0 ? amount * -1 : amount, item});
            }
            else if (!player.getHeldItemMainhand().isEmpty())
            {
            	ItemStack stack = player.getHeldItemMainhand();
            	
    			if (stack.getCount() + amount >= 128)
    			{
    				throw new CommandException("commands.generic.num.tooBig", new Object[] {amount, 127 - stack.getCount()});
    			}
    			
                notifyCommandListener(sender, this, amount < 0 ? "commands.add.success.decrease" : "commands.add.success.increase", new Object[] {player.getName(), amount < 0 ? amount * -1 : amount, stack.getDisplayName()});
    			stack.grow(amount);
            }
            else
            {
            	throw new CommandException("commands.add.failed.empty", new Object[0]);
            }            
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) 
	{		
		if (args.length == 2) 
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		else if (args.length == 3) 
		{
			List<String> stacks = new ArrayList<String>();
			EntityPlayer player = DinocraftEntity.getEntityPlayerByName(args[1]);
			
			if (player != null)
			{
				for (ItemStack stack : player.inventoryContainer.getInventory())
				{
					String name = stack.getDisplayName().replace(" ", "_");
					
					if (!stack.isEmpty() && !stacks.contains(name))
					{
						stacks.add(name);
					}
				}
			}

			return getListOfStringsMatchingLastWord(args, stacks);
		}
		
		return Collections.<String>emptyList();
	}
}