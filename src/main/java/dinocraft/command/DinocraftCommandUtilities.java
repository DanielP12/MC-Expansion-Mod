package dinocraft.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class DinocraftCommandUtilities
{
	/**
	 * Attempts to return a profile from the value in the String by checking if it is a valid profile UUID or if it is a valid username
	 * @throws CommandException
	 */
	public static GameProfile getProfileFromUsernameOrUUID(MinecraftServer server, String str) throws CommandException
	{
		GameProfile profile, uuidProfile;
		PlayerProfileCache profiles = server.getPlayerProfileCache();

		try
		{
			uuidProfile = profiles.getProfileByUUID(UUID.fromString(str));
		}
		catch (IllegalArgumentException exception)
		{
			uuidProfile = null;
		}

		profile = uuidProfile != null ? uuidProfile : profiles.getGameProfileForUsername(str);
		
		if (profile == null)
		{
			throw new CommandException("commands.generic.failed.invalidUsername", str);
		}

		return profile;
	}

	/**
	 * Attempts to return an entity from the value in the String by checking if it is a valid entity UUID or if it is a valid entity name
	 * @throws EntityNotFoundException
	 * @throws CommandException
	 */
	public static Entity getEntityFromUsernameOrUUID(MinecraftServer server, ICommandSender sender, String str) throws EntityNotFoundException, CommandException
	{
		Entity uuidEntity;

		try
		{
			uuidEntity = server.getEntityFromUuid(UUID.fromString(str));
		}
		catch (IllegalArgumentException exception)
		{
			uuidEntity = null;
		}
		
		return uuidEntity != null ? uuidEntity : CommandBase.getEntity(server, sender, str);
	}
	
	/**
	 * Checks if the specified command sender has the specified permission level
	 */
	public static boolean checkPermissions(int level, ICommandSender sender)
	{
		return sender instanceof EntityPlayerMP ? DinocraftEntity.getEntity((EntityPlayerMP) sender).hasOpLevel(level) : true;
	}

	//	public static boolean checkGroupPermissions(ICommandSender sender, String commandName)
	//	{
	//		if (sender instanceof EntityPlayerMP && sender.getServer().isDedicatedServer())
	//		{
	//			DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(((EntityPlayerMP) sender).getGameProfile());
	//
	//			if (data != null)
	//			{
	//				Group group = data.getGroup();
	//
	//				if (group != null)
	//				{
	//					return group.hasCommandOrAliases(commandName);
	//				}
	//			}
	//
	//			return false;
	//		}
	//
	//		return true;
	//	}

	/**
	 * Returns a grammatically correct conjunction of all of the elements inside the array as a <code>String</code>.<p>
	 * <b>Notes:</b><br>
	 * <span style="margin-left: 40px; display: inline-block">Ignores null values (does not include them in the result).</span><br>
	 * <b>Examples:</b><br>
	 * <span style="margin-left: 40px; display: inline-block"><code>joinNicely(new String[] {"Daniel"});</code> &#8594; "Daniel"<br>
	 * <code>joinNicely(new String[] {"Daniel", "Steve"});</code> &#8594; "Daniel and Steve"<br>
	 * <code>joinNicely(new String[] {"Daniel", null, "Steve"});</code> &#8594; "Daniel and Steve"<br>
	 * <code>joinNicely(new String[] {"Daniel", "Steve", "Mark"});</code> &#8594; "Daniel, Steve, and Mark"</span>
	 */
	public static String joinNicely(Object[] elements)
	{
		StringBuilder result = new StringBuilder();
		ArrayList list = new ArrayList(Arrays.asList(elements));
		list.removeAll(Collections.singleton(null));
		int size = list.size();

		for (int i = 0; i < size; i++)
		{
			Object element = list.get(i);
			
			if (i > 0)
			{
				if (i == size - 1)
				{
					if (size == 2)
					{
						result.append(" and ");
					}
					else
					{
						result.append(", and ");
					}
				}
				else
				{
					result.append(", ");
				}
			}

			result.append(element.toString());
		}

		return result.toString();
	}
	
	/**
	 * Returns a formatted version of the specified text.
	 */
	public static ITextComponent generateSimpleComponentFromString(String str)
	{
		return new TextComponentString(str.replaceAll("&0", TextFormatting.BLACK + "").replaceAll("&1", TextFormatting.DARK_BLUE + "")
				.replaceAll("&2", TextFormatting.DARK_GREEN + "").replaceAll("&3", TextFormatting.DARK_AQUA + "").replaceAll("&4", TextFormatting.DARK_RED + "").replaceAll("&5", TextFormatting.DARK_PURPLE + "").replaceAll("&6", TextFormatting.GOLD + "")
				.replaceAll("&7", TextFormatting.GRAY + "").replaceAll("&8", TextFormatting.DARK_GRAY + "").replaceAll("&9", TextFormatting.BLUE + "").replaceAll("&a", TextFormatting.GREEN + "").replaceAll("&b", TextFormatting.AQUA + "")
				.replaceAll("&c", TextFormatting.RED + "").replaceAll("&d", TextFormatting.LIGHT_PURPLE + "").replaceAll("&e", TextFormatting.YELLOW + "").replaceAll("&f", TextFormatting.WHITE + "").replaceAll("&k", TextFormatting.OBFUSCATED + "")
				.replaceAll("&l", TextFormatting.BOLD + "").replaceAll("&m", TextFormatting.STRIKETHROUGH + "").replaceAll("&n", TextFormatting.UNDERLINE + "").replaceAll("&o", TextFormatting.ITALIC + "").replaceAll("&r", TextFormatting.RESET + "")
				.replaceAll(":n:", "\n").replaceAll(":l:", TextFormatting.STRIKETHROUGH + "-----------------------------------------------------" + TextFormatting.RESET));
	}
}