//package dinocraft.command.WIP;
//
//import java.util.Collections;
//import java.util.List;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.util.DinocraftConfig;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.math.BlockPos;
//
//public class CommandNick extends CommandBase
//{
//	@Override
//	public String getName()
//	{
//		return "nick";
//	}
//
//	@Override
//	public String getUsage(ICommandSender sender)
//	{
//		return "commands.nick.usage";
//	}
//
//	@Override
//	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
//	{
//		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(DinocraftConfig.PERMISSION_LEVEL_FREEZE) : true; //TODO: Change
//	}
//
//	@Override
//	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//	{
//		if (args.length == 0)
//		{
//			throw new CommandException("commands.nick.failed");
//		}
//
//		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
//		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
//
//		if (args[0].equals("reset"))
//		{
//			dinoEntity.unNick();
//			player.refreshDisplayName();
//			notifyCommandListener(sender, this, "commands.nick.success", new Object[] {player.getName(), player.getName()});
//			return;
//		}
//
//		for (String name : server.getPlayerProfileCache().getUsernames())
//		{
//			if (args[0].equalsIgnoreCase(name))
//			{
//				throw new CommandException("commands.nick.failed", new Object[] {args[0]});
//			}
//		}
//
//		dinoEntity.nick(args[0]);
//		player.refreshDisplayName();
//		notifyCommandListener(sender, this, "commands.nick.success", new Object[] {player.getName(), args[0]});
//	}
//
//	@Override
//	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
//	{
//		return Collections.<String>emptyList();
//	}
//}