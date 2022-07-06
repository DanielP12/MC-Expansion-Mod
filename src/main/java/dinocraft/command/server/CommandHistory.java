package dinocraft.command.server;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dinocraft.Dinocraft;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.command.DinocraftCommandUtilities;
import dinocraft.gui.GUIHandler;
import dinocraft.gui.PunishmentHistory;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketTag;
import dinocraft.network.server.SPacketTag.Action;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftList;
import dinocraft.util.server.EntityPlayerOP;
import dinocraft.util.server.HistoryEntry;
import dinocraft.util.server.MuteHistoryEntry;
import dinocraft.util.server.WarningHistoryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class CommandHistory extends CommandBase
{
	@Override
	public String getName()
	{
		return "history";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_HISTORY_FULL, sender.getServer()) ? "commands.history.fullUsage" : "commands.history.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return DinocraftCommandUtilities.checkPermissions(DinocraftConfig.PERMISSION_LEVEL_MUTE, sender);
		//return DinocraftCommandUtilities.checkGroupPermissions(sender, this.getName());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		
		if (stack != null)
		{
			NBTTagCompound comp = stack.getTagCompound();
			
			if (comp != null)
			{
				List<String> tooltip = event.getToolTip();
				Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT = 9;
				
				if (comp.getTag("mute") != null)
				{
					NBTTagList list = (NBTTagList) comp.getTag("mute");
					tooltip.clear();
					tooltip.add(TextFormatting.RED + "" + TextFormatting.UNDERLINE + "MUTE");
					int size = list.tagCount();

					for (int i = 0; i < size; i++)
					{
						List<String> condensed = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(((NBTTagString) list.get(i)).getString(), 300);
						
						for (int j = 0; j < condensed.size(); j++)
						{
							tooltip.add((j > 0 ? "     " : "   ") + TextFormatting.WHITE + condensed.get(j));
						}
					}
				}
				else if (comp.getTag("warning") != null)
				{
					NBTTagList list = (NBTTagList) comp.getTag("warning");
					tooltip.clear();
					tooltip.add(TextFormatting.GOLD + "" + TextFormatting.UNDERLINE + "WARNING");
					int size = list.tagCount();
					
					for (int i = 0; i < size; i++)
					{
						List<String> condensed = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(((NBTTagString) list.get(i)).getString(), 300);
						
						for (int j = 0; j < condensed.size(); j++)
						{
							tooltip.add((j > 0 ? "     " : "   ") + TextFormatting.WHITE + condensed.get(j));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException("commands.history.usage");
		}
		
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		GameProfile profile = DinocraftCommandUtilities.getProfileFromUsernameOrUUID(server, args[0]);
		EntityPlayerOP offlinePlayer = null;
		UUID UUID = profile.getId();
		boolean backup = false;

		if (args.length >= 3 && args[1].equals("backup"))
		{
			if (args[2].equals("view"))
			{
				offlinePlayer = EntityPlayerOP.loadBackupData(UUID);

				if (offlinePlayer == null)
				{
					throw new CommandException("commands.history.backup.failed.noBackup", profile.getName());
				}

				backup = true;
			}
			else if (args[2].equals("restore"))
			{
				if (!EntityPlayerOP.hasBackupData(UUID))
				{
					throw new CommandException("commands.history.backup.failed.noBackup", profile.getName());
				}

				EntityPlayerOP.restoreBackupData(UUID);
				notifyCommandListener(sender, this, "commands.history.backup.success.restore", profile.getName());
				return;
			}
			else if (args[2].equals("delete"))
			{
				if (!EntityPlayerOP.hasBackupData(UUID))
				{
					throw new CommandException("commands.history.backup.failed.noBackup", profile.getName());
				}

				EntityPlayerOP.deleteBackupData(UUID);
				notifyCommandListener(sender, this, "commands.history.backup.success.delete", profile.getName());
				return;
			}
		}
		else if (EntityPlayerOP.hasSavedData(UUID))
		{
			offlinePlayer = EntityPlayerOP.getEntityPlayerOP(UUID);
		}

		if (offlinePlayer == null || offlinePlayer.getNumPunishmentsReceived() == 0)
		{
			throw new CommandException("commands.history.failed.noHistory", profile.getName());
		}

		if (args.length >= 2 && args[1].equals("clear"))
		{
			offlinePlayer.clearAllPunishments();
			notifyCommandListener(sender, this, "commands.history.success.cleared", profile.getName());
			offlinePlayer.save();
			return;
		}

		player.sendMessage(new TextComponentTranslation(backup ? "commands.history.backup.displayingBackup" : "commands.history.displayingPunishments", profile.getName()));
		PacketHandler.sendTo(new SPacketTag("viewing " + (backup ? "backup " : "") + "punishment history of " + profile.getName(), Action.ADD), player);
		player.openGui(Dinocraft.INSTANCE, GUIHandler.GUI_LOCKED_CONTAINER_ID, player.world, 0, 0, 0);

		if (offlinePlayer != null)
		{
			HistoryEntry[] punishments = offlinePlayer.getPunishmentHistory();
			int index = 0;
			
			for (int i = 0; i < punishments.length; i++)
			{
				HistoryEntry punishment = punishments[i];
				NBTTagCompound compound = new NBTTagCompound();
				NBTTagList list = new NBTTagList();
				
				if (punishment instanceof MuteHistoryEntry)
				{
					MuteHistoryEntry entry = (MuteHistoryEntry) punishment;
					list.appendTag(new NBTTagString("Date Issued: " + TextFormatting.GRAY + DinocraftList.DATE_FORMAT.format(entry.getDateIssued())));
					list.appendTag(new NBTTagString("Date Expired: " + TextFormatting.GRAY + DinocraftList.DATE_FORMAT.format(entry.getEndDate())));
					list.appendTag(new NBTTagString("Time: " + TextFormatting.GRAY + entry.getInitialTime(false)));
					String reason = entry.getReason();

					if (reason != null)
					{
						list.appendTag(new NBTTagString("Reason: " + TextFormatting.GRAY + reason));
					}

					String category = entry.getCategory();

					if (category != null)
					{
						list.appendTag(new NBTTagString("Category: " + TextFormatting.GRAY + category));
					}

					String muter = entry.getMuterByName();

					if (muter != null)
					{
						list.appendTag(new NBTTagString("Muter: " + TextFormatting.GRAY + muter));
					}

					list.appendTag(new NBTTagString("ID: " + TextFormatting.GRAY + entry.getID()));
					int standingLevel = entry.getPunishmentLevel();

					if (standingLevel != 0)
					{
						list.appendTag(new NBTTagString("Standling Level: " + TextFormatting.GRAY + standingLevel));
					}
					
					compound.setTag("mute", list);
					ItemStack icon = new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 14);
					icon.setTagCompound(compound);
					player.openContainer.putStackInSlot(i, icon);
				}

				if (punishment instanceof WarningHistoryEntry)
				{
					WarningHistoryEntry entry = (WarningHistoryEntry) punishment;
					list.appendTag(new NBTTagString("Date Created: " + TextFormatting.GRAY + DinocraftList.DATE_FORMAT.format(entry.getDateCreated())));
					list.appendTag(new NBTTagString("Date Issued: " + TextFormatting.GRAY + DinocraftList.DATE_FORMAT.format(entry.getDateIssued())));
					String reason = entry.getReason();

					if (reason != null)
					{
						list.appendTag(new NBTTagString("Reason: " + TextFormatting.GRAY + reason));
					}

					String category = entry.getCategory();

					if (category != null)
					{
						list.appendTag(new NBTTagString("Category: " + TextFormatting.GRAY + category));
					}

					String warner = entry.getWarnerByName();

					if (warner != null)
					{
						list.appendTag(new NBTTagString("Warner: " + TextFormatting.GRAY + warner));
					}

					int standingLevel = entry.getPunishmentLevel();

					if (standingLevel != 0)
					{
						list.appendTag(new NBTTagString("Standling Level: " + TextFormatting.GRAY + standingLevel));
					}
					
					compound.setTag("warning", list);
					ItemStack icon = new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 1);
					icon.setTagCompound(compound);
					player.openContainer.putStackInSlot(i, icon);
				}
			}
			
			((PunishmentHistory) player.openContainer).initialize();
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		boolean isLevel = DinocraftEntity.isLevel(sender, DinocraftConfig.PERMISSION_LEVEL_HISTORY_FULL, server);
		
		if (args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}

		if (isLevel)
		{
			if (args.length == 2)
			{
				return getListOfStringsMatchingLastWord(args, "clear", "backup");
			}
			else if (args.length == 3 && args[1].equals("backup"))
			{
				return getListOfStringsMatchingLastWord(args, "view", "restore");
			}
		}

		return Collections.emptyList();
	}
}