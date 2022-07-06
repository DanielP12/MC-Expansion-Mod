//package dinocraft.command;
//
//import java.util.Collections;
//import java.util.List;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.network.Packet;
//import dinocraft.network.server.SPacketChangeCapabilityState;
//import dinocraft.network.server.SPacketChangeCapabilityState.Capability;
//import dinocraft.network.server.SPacketSetAttackReach;
//import dinocraft.util.DinocraftConfig;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.command.WrongUsageException;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.math.BlockPos;
//
///**
// * Sets the the specified player's reach distance to the specified amount of blocks.<p>
// * <b>Name:</b><br>
// * <span style="margin-left: 40px; display: inline-block"><tt>reach</tt></span><br>
// * <b>Usage:</b><br>
// * <span style="margin-left: 40px; display: inline-block"><tt>/reach &lt;player&gt; &lt;distance&gt;</tt></span><p>
// * <b>Copyright © 2019 - 2020 Danfinite</b>
// */
////TODO: block reach too. allow to specify with flags as well
//public class CommandReach extends CommandBase
//{
//	@Override
//	public String getName()
//	{
//		return "reach";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender)
//	{
//		return "commands.reach.usage";
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
//	{
//		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_REACH, sender);
//		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//	{
//		if (args.length <= 1)
//		{
//			throw new WrongUsageException("commands.reach.usage");
//		}
//
//		EntityPlayerMP player = getPlayer(server, sender, args[0]);
//
//		if (args[1].equals("reset"))
//		{
//			DinocraftEntity.getEntity(player).setHasModifiedReach(false);
//			Packet.sendTo(new SPacketChangeCapabilityState(Capability.REACH, 0), player);
//			notifyCommandListener(sender, this, "commands.reach.success.reset", player.getName());
//		}
//		else
//		{
//			double distance = parseDouble(args[1]);
//			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
//			dinoEntity.setAttackReach(distance);
//			//dinoEntity.setBlockReach(distance);
//			Packet.sendTo(new SPacketSetAttackReach(distance), player);
//			notifyCommandListener(sender, this, "commands.reach.success", player.getName(), distance);
//		}
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
//	{
//		return args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"reset"}) : args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
//	}
//
//	@Override
//	public boolean isUsernameIndex(String[] args, int index)
//	{
//		return index == 1;
//	}
//}