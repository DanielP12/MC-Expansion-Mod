//package dinocraft.command.WIP;
//
//import java.util.Collections;
//import java.util.List;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.command.DinocraftCommandUtilities;
//import dinocraft.network.Packet;
//import dinocraft.network.server.SPacketTag;
//import dinocraft.network.server.SPacketTag.Action;
//import dinocraft.util.DinocraftConfig;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.math.BlockPos;
//
//public class CommandUnvanish extends CommandBase
//{
//	@Override
//	public String getName()
//	{
//		return "unvanish";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender)
//	{
//		return "commands.unvanish.usage";
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
//	{
//		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_UNVANISH, sender);
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//	{
//		EntityPlayerMP player = args.length > 0 ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
//		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
//		String name = player.getName();
//
//		if (dinoEntity.isVanished())
//		{
//			player.setInvisible(false);
//			dinoEntity.unVanish();
//			Packet.sendTo(new SPacketTag("vanished", Action.REMOVE), player);
//
//			if (player.getTags().contains("vanished"))
//			{
//				player.removeTag("vanished");
//			}
//
//			notifyCommandListener(sender, this, "commands.unvanish.success", name);
//		}
//		else
//		{
//			throw new CommandException("commands.unvanish.failed", name);
//		}
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
//	{
//		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
//	}
//
//	@Override
//	public boolean isUsernameIndex(String[] args, int index)
//	{
//		return index == 0;
//	}
//}