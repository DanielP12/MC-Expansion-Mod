package dinocraft.command.WIP;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CommandThrough extends CommandBase
{
	@Override
	public String getName()
	{
		return "through";
	}
	
	@Override
	public List<String> getAliases()
	{
		return Lists.newArrayList("thru");
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "commands.through.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(DinocraftConfig.PERMISSION_LEVEL_FEED) : true;
	}

	public static String getLookDirectionFromLookVec(Vec3d lookvec)
	{
		String direction = "unknown";
		
		if (lookvec == null)
		{
			return direction;
		}
		
		int x = (int) Math.round(lookvec.x);
		int y = (int) Math.round(lookvec.y);
		int z = (int) Math.round(lookvec.z);
		
		if (y == 1)
		{
			direction = "up";
		}
		else if (y == -1)
		{
			direction = "down";
		}
		else if (x == 0 && z == 1)
		{
			direction = "south";
		}
		else if (x == 0 && z == -1)
		{
			direction = "north";
		}
		else if (x == 1 && z == 0)
		{
			direction = "east";
		}
		else if (x == -1 && z == 0)
		{
			direction = "west";
		}
		else if (x == 1 && z == 1)
		{
			direction = "south-east";
		}
		else if (x == -1 && z == -1)
		{
			direction = "north-west";
		}
		else if (x == 1 && z == -1)
		{
			direction = "north-east";
		}
		else if (x == -1 && z == 1)
		{
			direction = "south-west";
		}
		
		return direction;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayer player = (EntityPlayer) sender;
		BlockPos pos = player.getPosition();
		World world = player.getEntityWorld();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		String direction = getLookDirectionFromLookVec(player.getLookVec());
		boolean yLowered = false;
		boolean found = false;
		
		while (!found)
		{
			for (int x1 = 0; x1 < 64; x1++)
			{ // it will look 64 blocks in front of you at most.
				if (direction.equals("north"))
				{
					z--;
				}
				else if (direction.equals("west"))
				{
					x--;
				}
				else if (direction.equals("south"))
				{
					z++;
				}
				else if (direction.equals("east"))
				{
					x++;
				}
				else if (direction.equals("down"))
				{
					//server.getCommandManager().getCommands().get("descend").execute(server, sender, args);
					return;
				}
				else if (direction.equals("up"))
				{
					//server.getCommandManager().getCommands().get("ascend").execute(server, sender, args);
					return;
				}
				else if (direction.equals("unknown"))
				{
					//Reference.sendMessage(player, "The direction your facing could not be determined.");
					throw new CommandException("commands.through.failed.unknown");
				}
				/*
				else
				{
					Reference.sendMessage(player, "Someone messed with the code, the returned direction wasn't north, west, south, east, down, up or unknown.");
					return;
				}
				 */
				Block block = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock(); // Block under your feet.
				Block tpblock = world.getBlockState(new BlockPos(x, y, z)).getBlock(); // Block at your feet.
				Block tpblock2 = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock(); // Block at your head.
				
				/*if ((!Reference.blockBlacklist.contains(block) || player.capabilities.isFlying) && (Reference.blockWhitelist.contains(tpblock) || tpblock == Blocks.AIR && player.capabilities.isFlying) && (Reference.blockWhitelist.contains(tpblock2) || tpblock2 == Blocks.AIR && player.capabilities.isFlying))*/
				//{
				if (tpblock == Blocks.AIR && tpblock2 == Blocks.AIR)
				{
					player.setPositionAndUpdate(x + player.posX - (int) player.posX, y, z + player.posZ - (int) player.posZ);
					//Reference.sendMessage(player, "You have been teleported through the wall.");
					notifyCommandListener(sender, this, "commands.through.success", new Object[] {player.getName()});
					found = true;
					return;
				}
				//}
			}
			
			if (y <= pos.getY() && y != pos.getY() - 8 && !yLowered)
			{
				y -= 1;
				x = pos.getX();
				z = pos.getZ();
			}
			else if (y == pos.getY() - 8 && y != pos.getY() + 8)
			{
				yLowered = true;
				y += 1;
				x = pos.getX();
				z = pos.getZ();
			}
		}

		//Reference.sendMessage(player, "No free spot found ahead of you.");
		throw new CommandException("commands.through.failed.noSpot");
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}