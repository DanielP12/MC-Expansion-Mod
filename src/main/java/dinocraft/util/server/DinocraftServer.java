package dinocraft.util.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jline.utils.Log;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketSpawnParticle;
import dinocraft.network.server.SPacketElectricParticles;
import dinocraft.network.server.SPacketJesterParticles;
import dinocraft.network.server.SPacketSpawnParticle;
import dinocraft.network.server.SPacketSpawnParticles;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftServer
{
	public static final File FILE_GROUPS = new File("groups.json");
	public static final File FILE_FORBIDDENWORDS = new File("forbidden-words.json");
	public static final ForbiddenWords FORBIDDEN_WORDS = new ForbiddenWords(FILE_FORBIDDENWORDS);
	public static final Groups GROUPS = new Groups(FILE_GROUPS);
	/** Whether or not the chat is enabled */
	private static boolean chatEnabled = true;
	/** A <code>Map</code> representing all of the online players that are muted */
	public static final Map<UUID, MuteEntry> PLAYER_MUTES = new HashMap<>();
	/** A <code>Map</code> representing the data for every online player */
	protected static final Map<UUID, EntityPlayerOP> PLAYER_DATA = new HashMap<>();

	public DinocraftServer()
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		if (server != null && server.isDedicatedServer())
		{
			if (server.getFile("groups.json").exists() && !GROUPS.isEmpty())
			{
				GROUPS.load();
				GROUPS.setLanServer(false);
			}
			
			if (server.getFile("forbidden-words.json").exists() && !FORBIDDEN_WORDS.isEmpty())
			{
				FORBIDDEN_WORDS.load();
				FORBIDDEN_WORDS.setLanServer(false);
			}
		}
	}
	
	public static boolean isChatEnabled()
	{
		return chatEnabled;
	}
	
	public static void enableChat()
	{
		chatEnabled = true;
	}
	
	public static void disableChat()
	{
		chatEnabled = false;
	}

	/**
	 * Kicks this player from the game with specified message
	 */
	public static void kick(EntityPlayer player, String msg)
	{
		((EntityPlayerMP) player).connection.disconnect(new TextComponentString(msg));
	}

	/**
	 * Returns a copy of the data for every online player
	 */
	public static Map<UUID, EntityPlayerOP> getPlayerData()
	{
		//		Map<UUID, EntityPlayerOP> playerData = new HashMap<>();
		//
		//		for (UUID UUID : PLAYER_DATA.keySet())
		//		{
		//			playerData.put(UUID, PLAYER_DATA.get(UUID));
		//		}
		//
		//		return playerData;
		return Collections.unmodifiableMap(PLAYER_DATA);
	}
	
	public static Map<UUID, MuteEntry> getPlayerMutes()
	{
		Map<UUID, MuteEntry> playerMutes = new HashMap<>();
		Set<Entry<UUID, MuteEntry>> UUIDs = PLAYER_MUTES.entrySet();
		Iterator<Entry<UUID, MuteEntry>> iterator = UUIDs.iterator();
		
		while (iterator.hasNext())
		{
			Entry<UUID, MuteEntry> entry = iterator.next();
			MuteEntry muteEntry = entry.getValue();

			if (!muteEntry.hasExpired())
			{
				playerMutes.put(entry.getKey(), muteEntry);
			}
		}

		return playerMutes;
	}
	
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		EntityPlayerMP player = event.getPlayer();
		GameProfile profile = player.getGameProfile();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		String username = event.getUsername();
		
		if (!chatEnabled && !dinoEntity.hasOpLevel(2))
		{
			player.sendMessage(new TextComponentTranslation("commands.togglechat.tryTalk").setStyle(new Style().setColor(TextFormatting.RED)));
			event.setCanceled(true);
		}
		
		String[] words = FORBIDDEN_WORDS.getKeys();
		
		if (words != null)
		{
			ITextComponent component = event.getComponent();
			List<String> list = new ArrayList<>();
			
			for (String word : words)
			{
				if (StringUtils.containsIgnoreCase(component.getUnformattedComponentText(), word))
				{
					list.add(word);
				}
			}
			
			list.sort((first, second) -> Integer.compare(second.length(), first.length()));
			
			for (String word : list)
			{
				String formattedText = component.getFormattedText();
				int endOfUsername = formattedText.indexOf(username) + username.length();
				
				if (word.equalsIgnoreCase(username))
				{
					component = new TextComponentString(formattedText.substring(0, endOfUsername) + formattedText.substring(endOfUsername).replaceAll("(?i)" + word, FORBIDDEN_WORDS.getEntry(word).getReplacement()));
				}
				else
				{
					component = new TextComponentString(formattedText.replaceAll("(?i)" + word, FORBIDDEN_WORDS.getEntry(word).getReplacement()));
				}
			}
			
			//			if (dinoEntity.isNicked())
			//			{
			//				component = new TextComponentString(StringUtils.replaceIgnoreCase(component.getFormattedText(), username, dinoEntity.getNickname(), 1));
			//			}
			
			event.setComponent(component);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onCommand(CommandEvent event) throws CommandException
	{
		ICommand command = event.getCommand();
		ICommandSender sender = event.getSender();

		if (sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) sender;
			//
			//			if (sender.getServer().isDedicatedServer())
			//			{
			//				DataEntry data = DinocraftPlayerList.USER_DATA.getEntry(player.getGameProfile());
			//				String commandName = command.getName();
			//
			//				if (data != null)
			//				{
			//					Group group = data.getGroup();
			//
			//					if (group != null)
			//					{
			//						List<String> commands = group.getCommands();
			//
			//						if (!commands.contains(commandName))
			//						{
			//							boolean contains = false;
			//
			//							for (String alias : sender.getServer().getCommandManager().getCommands().get(commandName).getAliases())
			//							{
			//								if (commands.contains(alias))
			//								{
			//									contains = true;
			//									break;
			//								}
			//							}
			//
			//							if (!contains)
			//							{
			//								player.sendMessage(new TextComponentTranslation("commands.generic.permission").setStyle(new Style().setColor(TextFormatting.RED)));
			//								event.setCanceled(true);
			//								return;
			//							}
			//						}
			//					}
			//				}
			//			}
			
			if (command instanceof CommandBroadcast || command instanceof CommandEmote || command instanceof CommandMessage)
			{
				GameProfile profile = player.getGameProfile();
				
				if (!chatEnabled && !DinocraftEntity.getEntity(player).hasOpLevel(2))
				{
					player.sendMessage(new TextComponentTranslation("commands.togglechat.tryTalk").setStyle(new Style().setColor(TextFormatting.RED)));
					event.setCanceled(true);
				}
			}
		}
	}
	
	//	@SubscribeEvent
	//	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	//	{
	//		EntityPlayerMP player = (EntityPlayerMP) event.player;
	//		GameProfile profile = player.getGameProfile();
	//		UUID UUID = player.getUniqueID();
	
	//		try
	//		{
	//			PLAYER_DATA.put(UUID, EntityPlayerOP.getOfflinePlayer(UUID));
	//		}
	//		catch (IOException exception)
	//		{
	//			FileInputStream inputStream = null;
	//			FileOutputStream outputStream = null;
	//
	//			try
	//			{
	//				File backup = new File("./player_data/errored_data/" + UUID + ".json");
	//
	//				if (backup.exists())
	//				{
	//					backup.delete();
	//				}
	//
	//				backup.createNewFile();
	//				inputStream = new FileInputStream(new File("./player_data/" + UUID + ".json"));
	//				outputStream = new FileOutputStream(backup);
	//				byte[] buffer = new byte[1024];
	//				int length;
	//
	//				while ((length = inputStream.read(buffer)) > 0)
	//				{
	//					outputStream.write(buffer, 0, length);
	//				}
	//
	//				inputStream.close();
	//				outputStream.close();
	//				new File("./player_data/" + UUID + ".json").delete();
	//				PLAYER_DATA.put(UUID, EntityPlayerOP.createNewPlayer(UUID));
	//			}
	//			catch(IOException expection)
	//			{
	//
	//			}
	//
	//			Dinocraft.log().fatal("The server encountered an error while loading the data for player '"
	//					+ profile.getName() + "' with UUID '" + UUID + "'. The old file has been saved to file path '"
	//					+ new File("./player_data/errored_data/" + UUID + ".json").getAbsolutePath() + "'");
	//		}
	//	}
	
	/**
	 * Spawns the particle at the specified position - Server-side.
	 */
	public static void spawnParticle(EnumParticleTypes particleType, boolean longRange, World world, float xCoord, float yCoord, float zCoord, float xSpeed, float ySpeed, float zSpeed, int parameters)
	{
		PacketHandler.sendToAllAround(new SPacketSpawnParticle(particleType, longRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters), world, longRange ? 262144.0D : 1024.0D);
	}
	
	/**
	 * Spawns the particle around the entity - Server-side.
	 */
	public static void spawnParticle(EnumParticleTypes particleType, boolean longRange, Entity entity, float xRadius, float yRadius, float zRadius, float xSpeed, float ySpeed, float zSpeed, int parameters)
	{
		PacketHandler.sendToAllAround(new SPacketSpawnParticle(particleType, longRange,
				(float) entity.posX - xRadius + entity.world.rand.nextFloat() * 2.0F * xRadius,
				(float) entity.posY + entity.height * 0.5F - yRadius + entity.world.rand.nextFloat() * 2.0F * yRadius,
				(float) entity.posZ - zRadius + entity.world.rand.nextFloat() * 2.0F * zRadius,
				xSpeed, ySpeed, zSpeed, parameters), entity.world, longRange ? 262144.0D : 1024.0D);
	}
	
	/**
	 * Spawns the particle at the specified position - Server-side.
	 */
	public static void spawnParticles(EnumParticleTypes particleType, World world, int particleCount, double xCoord, double yCoord, double zCoord, float xOffset, float yOffset, float zOffset, double xSpeed, double ySpeed, double zSpeed, int... parameters)
	{
		PacketHandler.sendToAllAround(new SPacketSpawnParticles(particleType, particleCount, (float) xCoord, (float) yCoord,
				(float) zCoord, xOffset, yOffset, zOffset, (float) xSpeed, (float) ySpeed, (float) zSpeed, parameters), world, 1024.0D);
	}
	
	public static void spawnJesterParticles(World world, int count1, int count2, double xCoord, double yCoord, double zCoord, float xOffset, float yOffset, float zOffset)
	{
		PacketHandler.sendToAllAround(new SPacketJesterParticles(count1, count2, (float) xCoord, (float) yCoord, (float) zCoord, xOffset, yOffset, zOffset), world, 1024.0D);
	}
	
	public static void spawnElectricParticles(World world, int count1, int count2, int degreeOfElectricity, double xCoord, double yCoord, double zCoord, float xOffset, float yOffset, float zOffset)
	{
		PacketHandler.sendToAllAround(new SPacketElectricParticles(count1, count2, (byte) degreeOfElectricity, (float) xCoord, (float) yCoord, (float) zCoord, xOffset, yOffset, zOffset), world, 1024.0D);
	}

	/**
	 * Spawns the particle at the specified position
	 *
	 * @param particleType the particle type
	 * @param longRange whether or not this particle is visible to players that are far away
	 * @param world the world
	 * @param xCoord the x-coordinate
	 * @param yCoord the y-coordinate
	 * @param zCoord the z-coordinate
	 * @param xOffset the x-coordinate offset/spread
	 * @param yOffset the y-coordinate offset/spread
	 * @param zOffset the z-coordinate offset/spread
	 * @param particleSpeed the particle speed
	 */
	public static void spawnParticle(EnumParticleTypes particleType, boolean longRange, World world, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, double particleSpeed, int parameters)
	{
		((WorldServer) world).spawnParticle(particleType, longRange, xCoord, yCoord, zCoord, 1, xOffset, yOffset, zOffset, particleSpeed, parameters);
	}

	/**
	 * Spawns the particle at the specified position - Client-side.
	 */
	public static void spawnParticle(EnumParticleTypes particleType, boolean longRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int parameters)
	{
		PacketHandler.sendToServer(new CPacketSpawnParticle(particleType, longRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters));
	}

	public static void getSide(World world)
	{
		if (world.isRemote)
		{
			Log.info("CLIENT");
		}
		else
		{
			Log.info("SERVER");
		}
	}
}