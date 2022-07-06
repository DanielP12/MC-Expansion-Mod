package dinocraft.util.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;

import dinocraft.util.DinocraftConfig;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

@EventBusSubscriber
public final class EntityPlayerOP
{
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(EntityPlayerOP.class, new Serializer()).setPrettyPrinting().create();
	/** The game profile associated with this <code>EntityPlayerOP</code> object */
	private final GameProfile profile;
	/** The file in which this profile's data is saved */
	private File saveFile;
	/** The mute standing level this profile is currently on */
	private int muteStandingLevel;
	/** The total number of punishments this profile has received */
	private int numPunishmentsReceived;
	/** The total number of punishments this profile has given */
	private int numPunishmentsGiven;
	/** The date at which the latest punishment this profile has received expires */
	private Date muteDecayDate;
	/** The date at which this profile first logged in to the server */
	private @Nullable Date firstLoginDate;
	/** The date at which this profile last logged in to the server */
	private @Nullable Date lastLoginDate;
	/** All of the queued warnings this profile is bound to receive */
	private final List<WarningEntry> queuedWarnings = new ArrayList<>();
	/**
	 * All of the warnings and mutes this profile has received excluding the current mute and queued warnings, if any
	 */
	private final List<HistoryEntry> punishmentHistory = new ArrayList<>();
	/** The mute this profile is currently facing */
	private @Nullable MuteEntry currentMute;

	private EntityPlayerOP(UUID uuid)
	{
		this.profile = getServer().getPlayerProfileCache().getProfileByUUID(uuid);
		this.saveFile = new File("./player_data/" + uuid + ".json");
	}

	private EntityPlayerOP(JsonObject object)
	{
		this.profile = constructProfile(object);
		this.saveFile = new File("./player_data/" + object.get("UUID").getAsString() + ".json");
		this.muteStandingLevel = object.has("mute standing level") ? object.get("mute standing level").getAsInt() : 0;
		this.numPunishmentsReceived = object.has("punishments received") ? object.get("punishments received").getAsInt() : 0;
		this.numPunishmentsGiven = object.has("punishments given") ? object.get("punishments given").getAsInt() : 0;
		
		try
		{
			this.firstLoginDate = object.has("first login") ? DinocraftList.DATE_FORMAT.parse(object.get("first login").getAsString()) : null;
		}
		catch (ParseException exception)
		{

		}

		try
		{
			this.lastLoginDate = object.has("last login") ? DinocraftList.DATE_FORMAT.parse(object.get("last login").getAsString()) : null;
		}
		catch (ParseException exception)
		{

		}

		try
		{
			this.muteDecayDate = object.has("mute decay date") ? DinocraftList.DATE_FORMAT.parse(object.get("mute decay date").getAsString()) : null;
		}
		catch (ParseException exception)
		{

		}

		if (object.has("chat punishment history"))
		{
			JsonArray punishments = object.getAsJsonArray("chat punishment history");

			for (JsonElement punishment : punishments)
			{
				JsonObject obj = punishment.getAsJsonObject();

				if (obj.has("type"))
				{
					String type = obj.get("type").getAsString();

					if ("MUTE".equals(type))
					{
						this.punishmentHistory.add(MuteHistoryEntry.onDeserialization(obj));
					}
					else if ("WARNING".equals(type))
					{
						this.punishmentHistory.add(WarningHistoryEntry.onDeserialization(obj));
					}
				}
			}
		}

		if (object.has("queued warnings"))
		{
			JsonArray warnings = object.getAsJsonArray("queued warnings");

			for (JsonElement warning : warnings)
			{
				try
				{
					this.queuedWarnings.add(WarningEntry.onDeserialization(warning.getAsJsonObject()));
				}
				catch (ParseException exception)
				{
					getServer().logSevere("Could not queue warning for player '" + this.getUsername() + "' with UUID '" + this.getUUID() + "' due an erorred entry date from file '" + this.saveFile + "'.");
				}
			}
		}

		try
		{
			this.currentMute = object.has("current mute") ? MuteEntry.onDeserialization(object.get("current mute").getAsJsonObject()) : null;
		}
		catch (ParseException exception)
		{
			getServer().logSevere("Could not obtain current mute for player '" + this.getUsername() + "' with UUID '" + this.getUUID() + "' due an erorred entry date from file '" + this.saveFile + "'.");
		}
	}

	/**
	 * Stores the assets of this <code>EntityPlayerOP</code> object into the specified JSON data
	 */
	void serializeTo(JsonObject object)
	{
		if (this.profile != null)
		{
			UUID uuid = this.profile.getId();
			object.addProperty("UUID", uuid == null ? "" : uuid.toString());
			object.addProperty("name", this.profile.getName());
			object.addProperty("last login", this.lastLoginDate != null ? DinocraftList.DATE_FORMAT.format(this.lastLoginDate) : null);
			object.addProperty("first login", this.firstLoginDate != null ? DinocraftList.DATE_FORMAT.format(this.firstLoginDate) : null);
			object.addProperty("mute level", this.muteStandingLevel);
			object.addProperty("punishments received", this.numPunishmentsReceived);
			object.addProperty("punishments given", this.numPunishmentsGiven);

			if (!this.queuedWarnings.isEmpty())
			{
				JsonArray warnings = new JsonArray();

				for (WarningEntry warning : this.queuedWarnings)
				{
					JsonObject obj = new JsonObject();
					warning.onSerialization(obj);
					warnings.add(obj);
				}

				object.add("queued warnings", warnings);
			}

			if (this.isMuted())
			{
				JsonObject obj = new JsonObject();
				this.currentMute.onSerialization(obj);
				object.add("current mute", obj);
			}

			if (!this.punishmentHistory.isEmpty())
			{
				JsonArray punishments = new JsonArray();

				for (HistoryEntry punishment : this.punishmentHistory)
				{
					JsonObject obj = new JsonObject();
					punishment.onSerialization(obj);
					punishments.add(obj);
				}

				object.add("chat punishment history", punishments);
			}

			object.addProperty("mute decay date", this.muteDecayDate != null ? DinocraftList.DATE_FORMAT.format(this.muteDecayDate) : null);
		}
	}

	@Nullable
	private static GameProfile constructProfile(JsonObject object)
	{
		if (object.has("UUID") && object.has("name"))
		{
			UUID uuid;

			try
			{
				uuid = UUID.fromString(object.get("UUID").getAsString());
			}
			catch (Exception exception)
			{
				return null;
			}

			return new GameProfile(uuid, object.get("name").getAsString());
		}

		return null;
	}

	/**
	 * Returns the running Minecraft Server
	 */
	public static MinecraftServer getServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		UUID uuid = event.player.getUniqueID();
		EntityPlayerOP offlinePlayer = null;

		try
		{
			offlinePlayer = EntityPlayerOP.getOfflinePlayer(uuid);
			DinocraftServer.PLAYER_DATA.put(uuid, offlinePlayer);
		}
		catch (IOException exception)
		{
			FileInputStream inputStream = null;
			FileOutputStream outputStream = null;

			try
			{
				File backup = new File("./player_data/errored_data/" + uuid + ".json");

				if (backup.exists())
				{
					backup.delete();
				}

				backup.createNewFile();
				inputStream = new FileInputStream(new File("./player_data/" + uuid + ".json"));
				outputStream = new FileOutputStream(backup);
				byte[] buffer = new byte[1024];
				int length;

				while ((length = inputStream.read(buffer)) > 0)
				{
					outputStream.write(buffer, 0, length);
				}

				inputStream.close();
				outputStream.close();
				new File("./player_data/" + uuid + ".json").delete();
				DinocraftServer.PLAYER_DATA.put(uuid, EntityPlayerOP.createNewPlayer(uuid));
			}
			catch (IOException expection)
			{

			}

			event.player.getServer().logSevere("There was an error reading file '" + uuid + ".json' while attempting to load player '" + event.player.getName() + "' with UUID '" + uuid + "'.");
		}

		Date today = new Date();

		if (offlinePlayer.firstLoginDate == null)
		{
			offlinePlayer.firstLoginDate = today;
		}

		offlinePlayer.lastLoginDate = today;

		if (!offlinePlayer.queuedWarnings.isEmpty())
		{
			offlinePlayer.muteDecayDate = today;
		}

		for (int i = 0; i < offlinePlayer.queuedWarnings.size(); i++)
		{
			WarningEntry warning = offlinePlayer.queuedWarnings.get(i);
			String reason = warning.getReason();

			if ("".equals(reason))
			{
				event.player.sendMessage(new TextComponentTranslation("commands.mute.warnPlayer.noReason2"));
			}
			else
			{
				event.player.sendMessage(new TextComponentTranslation("commands.mute.warnPlayer2", reason));
			}

			warning.issue();
			offlinePlayer.punishmentHistory.add(0, new WarningHistoryEntry(warning));
			offlinePlayer.queuedWarnings.remove(i--);
		}

		if (offlinePlayer.isMuted())
		{
			// if (!DinocraftServer.MUTED_PLAYERS.containsKey(UUID))
			// {
			// DinocraftServer.MUTED_PLAYERS.put(UUID, offlinePlayer);
			// } //TODO

			DinocraftServer.PLAYER_MUTES.put(uuid, offlinePlayer.currentMute);

			if (!offlinePlayer.currentMute.isIssued())
			{
				offlinePlayer.currentMute.issue();

				if (!offlinePlayer.currentMute.isPermanent())
				{
					offlinePlayer.muteDecayDate = offlinePlayer.currentMute.getEndDate();
				}
				else
				{
					offlinePlayer.muteDecayDate = null;
				}
			}
		}

		offlinePlayer.save();
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerLoggedOutEvent event)
	{
		EntityPlayerOP offlinePlayer = DinocraftServer.PLAYER_DATA.remove(event.player.getUniqueID());

		// if (offlinePlayer.isMuted())
		// {
		// DinocraftServer.MUTED_PLAYERS.remove(event.player.getUniqueID());
		// } //TODO

		if (offlinePlayer.isMuted())
		{
			DinocraftServer.PLAYER_MUTES.remove(event.player.getUniqueID());
		}

		offlinePlayer.save();
	}

	@SubscribeEvent
	public static void onServerChat(ServerChatEvent event)
	{
		if (checkMuted(event.getPlayer()))
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onCommand(CommandEvent event)
	{
		ICommand command = event.getCommand();
		ICommandSender sender = event.getSender();

		if (sender instanceof EntityPlayerMP && (command instanceof CommandBroadcast || command instanceof CommandEmote || command instanceof CommandMessage))
		{
			if (checkMuted((EntityPlayerMP) sender))
			{
				event.setCanceled(true);
			}
		}
	}

	private static boolean checkMuted(EntityPlayerMP player)
	{
		// UUID UUID = player.getUniqueID();
		//
		// if (DinocraftServer.MUTED_PLAYERS.containsKey(UUID))
		// {
		// EntityPlayerOP offlinePlayer = DinocraftServer.MUTED_PLAYERS.get(UUID);
		//
		// if (offlinePlayer.isMuted())
		// {
		// String reason = offlinePlayer.currentMute.getReason();
		//
		// if (!offlinePlayer.currentMute.isPermanent())
		// {
		// String time = offlinePlayer.currentMute.getRemainingTime(false);
		//
		// if (reason != null)
		// {
		// player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk2", TextFormatting.RED + reason,
		// TextFormatting.RED + offlinePlayer.currentMute.getRemainingTime(true), offlinePlayer.currentMute.getID()));
		// }
		// else
		// {
		// player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.noReason2", TextFormatting.RED +
		// offlinePlayer.currentMute.getRemainingTime(true), offlinePlayer.currentMute.getID()));
		// }
		// }
		// else
		// {
		// if (reason != null)
		// {
		// player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.permanent2", TextFormatting.RED +
		// reason,
		// offlinePlayer.currentMute.getID()));
		// }
		// else
		// {
		// player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.permanent.noReason2",
		// offlinePlayer.currentMute.getID()));
		// }
		// }
		//
		// return true;
		// }
		// }
		//
		// return false; //TODO

		UUID uuid = player.getUniqueID();

		if (DinocraftServer.PLAYER_MUTES.containsKey(uuid))
		{
			MuteEntry mute = DinocraftServer.PLAYER_MUTES.get(uuid);

			if (!mute.hasExpired())
			{
				String reason = mute.getReason();

				if (!mute.isPermanent())
				{
					String time = mute.getRemainingTime(false);

					if (reason != null)
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk2", TextFormatting.RED + reason, TextFormatting.RED + mute.getRemainingTime(true), mute.getID()));
					}
					else
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.noReason2", TextFormatting.RED + mute.getRemainingTime(true), mute.getID()));
					}
				}
				else
				{
					if (reason != null)
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.permanent2", TextFormatting.RED + reason, mute.getID()));
					}
					else
					{
						player.sendMessage(new TextComponentTranslation("commands.mute.tryTalk.permanent.noReason2", mute.getID()));
					}
				}

				return true;
			}
			else
			{
				DinocraftServer.PLAYER_MUTES.remove(uuid);
			}
		}

		return false;
	}

	/**
	 * Returns the date at which this profile first logged in to the server
	 */
	@Nullable
	public Date getFirstLoginDate()
	{
		return this.firstLoginDate == null ? null : (Date) this.firstLoginDate.clone();
	}

	/**
	 * Returns the date at which this profile last logged in to the server
	 */
	@Nullable
	public Date getLastLoginDate()
	{
		return this.lastLoginDate == null ? null : (Date) this.lastLoginDate.clone();
	}

	/**
	 * Returns the mute this profile is currently facing
	 */
	@Nullable
	public MuteEntry getCurrentMute()
	{
		return this.currentMute;
	}

	/**
	 * Returns an array of all the queued warnings this profile is bound to receive, if any
	 */
	public WarningEntry[] getQueuedWarnings()
	{
		return this.queuedWarnings.toArray(new WarningEntry[0]);
	}

	/**
	 * Returns an array of all the warnings and mutes this profile has received excluding the current mute and queued
	 * warnings, if any
	 */
	public HistoryEntry[] getPunishmentHistory()
	{
		return this.punishmentHistory.toArray(new HistoryEntry[0]);
	}

	/**
	 * Returns an array of all the mutes this profile has received excluding the current mute, if any
	 */
	public MuteHistoryEntry[] getPreviousMutes()
	{
		ArrayList<HistoryEntry> punishmentHistory = new ArrayList<>(this.punishmentHistory);
		punishmentHistory.removeIf(punishment -> !(punishment instanceof MuteHistoryEntry));
		return punishmentHistory.toArray(new MuteHistoryEntry[0]);
	}

	/**
	 * Returns an array of all the warnings this profile has received excluding queued warnings, if any
	 */
	public WarningHistoryEntry[] getPreviousWarnings()
	{
		ArrayList<HistoryEntry> punishmentHistory = new ArrayList<>(this.punishmentHistory);
		punishmentHistory.removeIf(punishment -> !(punishment instanceof WarningHistoryEntry));
		return punishmentHistory.toArray(new WarningHistoryEntry[0]);
	}

	/**
	 * Returns the unique ID of this profile
	 */
	public UUID getUUID()
	{
		return this.profile.getId();
	}

	/**
	 * Returns the username of this profile
	 */
	public String getUsername()
	{
		return this.profile.getName();
	}

	private void createBackup()
	{
		this.save();
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;

		try
		{
			File backup = new File("./player_data/backups/" + this.getUUID() + ".json");

			if (backup.exists())
			{
				backup.delete();
			}

			backup.createNewFile();
			inputStream = new FileInputStream(this.saveFile);
			outputStream = new FileOutputStream(backup);
			byte[] buffer = new byte[1024];
			int length;

			while ((length = inputStream.read(buffer)) > 0)
			{
				outputStream.write(buffer, 0, length);
			}
		}
		catch (IOException expection)
		{

		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * Clears all the current and previous punishments this profile has received and creates a backup file for this
	 * profile
	 */
	public void clearAllPunishments()
	{
		this.createBackup();
		this.currentMute = null;
		this.queuedWarnings.clear();
		this.punishmentHistory.clear();
		this.numPunishmentsReceived = 0;
		this.muteStandingLevel = 0;
		this.muteDecayDate = null;
	}
	
	private void onPunishmentGiven()
	{
		this.numPunishmentsGiven++;
	}

	private void onWarningIssuedOrQueued(WarningEntry warning)
	{
		if (!this.isOnline())
		{
			this.queuedWarnings.add(warning);
		}
		else
		{
			warning.issue();
			this.punishmentHistory.add(0, new WarningHistoryEntry(warning));
		}

		String warner = warning.getWarnerByName();

		if (!warner.equals("(Unknown)") && !warner.equals("Server"))
		{
			getEntityPlayerOP(getServer().getPlayerProfileCache().getGameProfileForUsername(warner).getId()).onPunishmentGiven();
		}

		this.numPunishmentsReceived++;
	}

	/**
	 * Warns this profile
	 *
	 * @param warner the instance that warned this profile
	 * @param reason the reason this profile was warned
	 */
	public void warn(@Nullable String warner, @Nullable String reason)
	{
		this.onWarningIssuedOrQueued(new WarningEntry(this.profile, warner, reason));
	}

	/**
	 * Warns this profile
	 *
	 * @param warner        the instance that warned this profile
	 * @param standingLevel the punishment level (weight) this warning holds
	 * @param category      the category this warning is under
	 * @param reason        the reason this profile was warned
	 */
	public void warn(@Nullable String warner, int standingLevel, String category, @Nullable String reason)
	{
		WarningEntry warning = new WarningEntry(this.profile, warner, standingLevel, category, reason);
		this.onWarningIssuedOrQueued(warning);
		this.muteStandingLevel = standingLevel;
		this.muteDecayDate = warning.getDateIssued();
	}

	/**
	 * Returns the latest warning this profile has received
	 */
	@Nullable
	public Entry getLatestWarning()
	{
		if (this.hasQueuedWarnings())
		{
			return this.queuedWarnings.get(0);
		}

		for (HistoryEntry entry : this.punishmentHistory)
		{
			if (entry instanceof WarningHistoryEntry)
			{
				return entry;
			}
		}

		return null;
	}

	/**
	 * Returns <code>true</code> if this profile is currently muted and <code>false</code> otherwise
	 */
	public boolean isMuted()
	{
		if (this.currentMute == null)
		{
			return false;
		}

		if (this.currentMute.hasExpired())
		{
			this.punishmentHistory.add(0, new MuteHistoryEntry(this.currentMute));
			this.currentMute = null;
			this.save();
			// DinocraftServer.MUTED_PLAYERS.remove(this.profile.getId()); //TODO
			DinocraftServer.PLAYER_MUTES.remove(this.profile.getId());
			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if this profile is online and <code>false</code> otherwise
	 */
	public boolean isOnline()
	{
		return getServer().getPlayerList().getPlayerByUUID(this.profile.getId()) != null;
	}

	private void onMuteIssued(MuteEntry mute)
	{
		this.currentMute = mute;
		this.muteDecayDate = this.currentMute.getEndDate();
		this.numPunishmentsReceived++;
		String muter = mute.getMuterByName();

		if (!muter.equals("(Unknown)") && !muter.equals("Server"))
		{
			getEntityPlayerOP(getServer().getPlayerProfileCache().getGameProfileForUsername(muter).getId()).onPunishmentGiven();
		}

		if (this.isOnline())
		{
			// DinocraftServer.MUTED_PLAYERS.put(this.profile.getId(), this); //TODO
			DinocraftServer.PLAYER_MUTES.put(this.profile.getId(), this.currentMute);
		}
	}

	/**
	 * Temporarily mutes this profile
	 *
	 * @param muter   the instance that muted this profile
	 * @param years   the amount of years this mute is for
	 * @param days    the amount of days this mute is for
	 * @param hours   the amount of hours this mute is for
	 * @param minutes the amount of minutes this mute is for
	 * @param seconds the amount of seconds this mute is for
	 * @param reason  the reason this profile was muted
	 *
	 * @return <code>true</code> if this profile was successfully muted and <code>false</code> if this profile was
	 *         already muted prior to this method invokation
	 */
	public boolean mute(@Nullable String muter, int years, int days, int hours, int minutes, int seconds, @Nullable String reason)
	{
		if (!this.isMuted())
		{
			this.onMuteIssued(new MuteEntry(this.profile, muter, years, days, hours, minutes, seconds, reason, false));
			return true;
		}

		return false;
	}

	/**
	 * Temporarily mutes this profile
	 *
	 * @param muter         the instance that muted this profile
	 * @param standingLevel the punishment level (weight) this mute holds
	 * @param category      the category this mute is under
	 * @param reason        the reason this profile was muted
	 *
	 * @return <code>true</code> if this profile was successfully muted and <code>false</code> if this profile was
	 *         already muted prior to this method invokation
	 */
	public boolean mute(@Nullable String muter, int standingLevel, @Nullable String category, @Nullable String reason)
	{
		if (!this.isMuted())
		{
			this.onMuteIssued(new MuteEntry(this.profile, muter, standingLevel, category, reason));
			this.muteStandingLevel = standingLevel;
			return true;
		}

		return false;
	}

	/**
	 * Permanently mutes this profile
	 *
	 * @param muter  the instance that muted this profile
	 * @param reason the reason this profile was muted
	 *
	 * @return <code>true</code> if this profile was successfully muted and <code>false</code> if this profile was
	 *         already muted prior to this method invokation
	 */
	public boolean mutePermanently(@Nullable String muter, @Nullable String reason)
	{
		if (!this.isMuted())
		{
			this.onMuteIssued(new MuteEntry(this.profile, muter, 0, 0, 0, 0, 0, reason, true));
			return true;
		}

		return false;
	}

	/**
	 * Returns the mute standing level this profile is currently on
	 */
	public int getMuteStandingLevel()
	{
		this.updateStandingLevel(DinocraftConfig.MUTE_DECAY_TIME);
		return this.muteStandingLevel;
	}

	/**
	 * Returns the total number of punishments this profile has received
	 */
	public int getNumPunishmentsReceived()
	{
		return this.numPunishmentsReceived;
	}

	/**
	 * Returns the total number of punishments this profile has given
	 */
	public int getNumPunishmentsGiven()
	{
		return this.numPunishmentsGiven;
	}

	/**
	 * Checks the amount needed to decay depending on the number of times <code>n</code> days have passed since the
	 * expiration of the last chat punishment this profile has received and decays that amount.
	 */
	private void updateStandingLevel(int n)
	{
		if (this.muteDecayDate != null && this.muteStandingLevel > 0 && !this.hasQueuedWarnings() && !this.isMuted())
		{
			Date date = new Date();
			date.setDate(date.getDate() - n);

			if (date.after(this.muteDecayDate))
			{
				int amount = (int) (Math.abs((date.getTime() - this.muteDecayDate.getTime()) / 86400000) / n) + 1;
				this.muteStandingLevel -= amount;
				this.muteDecayDate.setDate(this.muteDecayDate.getDate() + amount * n);
			}
		}

		if (this.muteStandingLevel <= 0 || this.numPunishmentsReceived == 0)
		{
			this.muteStandingLevel = 0;
			this.muteDecayDate = null;
		}
	}

	/**
	 * Returns <code>true</code> if this profile has queued warnings to receive upon logging in and <code>false</code>
	 * otherwise
	 */
	public boolean hasQueuedWarnings()
	{
		return !this.queuedWarnings.isEmpty();
	}

	/**
	 * Reverts the latest chat punishment this profile has received as long as that punishment has not already expired. This method also reverts the punishment level for this profile based on the weight of the reverted punishment
	 *
	 * @return The entry for the reverted punishment, if any.
	 */
	@Nullable
	public Entry unmute()
	{
		if (this.numPunishmentsReceived == 0)
		{
			return null;
		}

		Entry entry = null;

		if (this.isMuted())
		{
			this.numPunishmentsReceived--;
			entry = this.currentMute;
			String muter = this.currentMute.getMuterByName();

			if (!muter.equals("(Unknown)") && !muter.equals("Server"))
			{
				getEntityPlayerOP(getServer().getPlayerProfileCache().getGameProfileForUsername(muter).getId()).numPunishmentsGiven--;
			}

			this.currentMute = null;
		}
		else if (this.hasQueuedWarnings())
		{
			this.numPunishmentsReceived--;
			String warner = this.queuedWarnings.get(0).getWarnerByName();

			if (!warner.equals("(Unknown)") && !warner.equals("Server"))
			{
				getEntityPlayerOP(getServer().getPlayerProfileCache().getGameProfileForUsername(warner).getId()).numPunishmentsGiven--;
			}

			entry = this.queuedWarnings.remove(0);
		}
		else if (!this.punishmentHistory.isEmpty())
		{
			HistoryEntry entry1 = this.punishmentHistory.get(0);

			if (entry1 instanceof WarningHistoryEntry)
			{
				this.numPunishmentsReceived--;
				String warner = ((WarningHistoryEntry) entry1).getWarnerByName();

				if (!warner.equals("(Unknown)") && !warner.equals("Server"))
				{
					getEntityPlayerOP(getServer().getPlayerProfileCache().getGameProfileForUsername(warner).getId()).numPunishmentsGiven--;
				}

				entry = this.punishmentHistory.remove(0);
			}
			else
			{
				return null;
			}
		}

		String category = entry.getCategory();

		if (category != null)
		{
			for (int i = 0; i < DinocraftConfig.MUTE_CATEGORIES.length; i++)
			{
				if (category.equals(DinocraftConfig.MUTE_CATEGORIES[i]))
				{
					this.muteStandingLevel -= Integer.parseInt(DinocraftConfig.MUTE_STANDINGS[i]);
				}
			}
		}

		if (this.hasQueuedWarnings())
		{
			this.muteDecayDate = this.queuedWarnings.get(0).getDateIssued();
		}
		else if (!this.punishmentHistory.isEmpty())
		{
			HistoryEntry historyEntry = this.punishmentHistory.get(0);

			if (historyEntry instanceof WarningHistoryEntry)
			{
				this.muteDecayDate = historyEntry.getDateIssued();
			}
			else if (historyEntry instanceof MuteHistoryEntry)
			{
				this.muteDecayDate = ((MuteHistoryEntry) historyEntry).getEndDate();
			}
		}
		else
		{
			this.muteDecayDate = null;
		}

		if (this.isOnline())
		{
			if (entry instanceof MuteEntry || entry instanceof MuteHistoryEntry)
			{
				// DinocraftServer.MUTED_PLAYERS.remove(this.profile.getId()); //TODO
				DinocraftServer.PLAYER_MUTES.remove(this.profile.getId());
			}
		}

		return entry;
	}

	/**
	 * Saves, to this profile's save file, the changes made to this list since the last invokation of this method
	 */
	public void save()
	{
		try
		{
			BufferedWriter writer = null;

			try
			{
				writer = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);
				// writer.write(new GsonBuilder().registerTypeAdapter(EntityPlayerOP.class, new
				// Serializer()).setPrettyPrinting().create().toJson(this)); //TODO
				writer.write(GSON.toJson(this));
			}
			finally
			{
				IOUtils.closeQuietly(writer);
			}
		}
		catch (IOException exception)
		{
			getServer().logSevere("The server encountered an unknown error while saving the file '" + this.saveFile.getName() + "'");
		}
	}

	private void update()
	{
		// if (this.currentMute != null && this.currentMute.hasExpired())
		// {
		// this.punishmentHistory.add(0, new MuteHistoryEntry(this.currentMute));
		// this.currentMute = null;
		// //DinocraftServer.MUTED_PLAYERS.remove(this.profile.getId()); //TODO
		// DinocraftServer.PLAYER_MUTES.remove(this.profile.getId());
		// } //TODO

		this.updateStandingLevel(DinocraftConfig.MUTE_DECAY_TIME);
	}

	/**
	 * Constructs an <code>EntityPlayerOP</code> object from the specified UUID. More specifically, this method will check if
	 * the file for the profile specified by the UUID exists. If so, then it will return the deserialized
	 * <code>EntityPlayerOP</code> object from this file. Otherwise, it will create the data file and return a blank instance
	 * of an <code>EntityPlayerOP</code> object.
	 *
	 * @param UUID The UUID of the desired player.
	 *
	 * @throws IOException If an I/O error occurs.
	 *
	 * @return An <code>EntityPlayerOP</code> object derived from the specified UUID.
	 */
	@Nonnull
	private static EntityPlayerOP getOfflinePlayer(UUID uuid) throws IOException
	{
		File file = new File("./player_data/" + uuid + ".json");

		if (getServer().getPlayerProfileCache().getProfileByUUID(uuid) == null)
		{
			throw new NullPointerException("Could not find a profile for UUID " + "'" + uuid + "'");
		}

		if (file.exists())
		{
			EntityPlayerOP offlinePlayer = null;
			BufferedReader reader = null;

			try
			{
				reader = Files.newReader(file, StandardCharsets.UTF_8);
				offlinePlayer = JsonUtils.fromJson(GSON, reader, EntityPlayerOP.class);
				offlinePlayer.update();
				return offlinePlayer;
			}
			finally
			{
				IOUtils.closeQuietly(reader);
			}
		}

		return createNewPlayer(uuid);
	}

	/**
	 * Returns a new <code>EntityPlayerOP</code> object for the specified UUID
	 */
	@Nonnull
	private static EntityPlayerOP createNewPlayer(UUID uuid) throws IOException
	{
		new File("./player_data/" + uuid + ".json").createNewFile();
		EntityPlayerOP offlinePlayer = new EntityPlayerOP(uuid);
		offlinePlayer.save();
		return offlinePlayer;
	}

	/**
	 * Returns the <code>EntityPlayerOP</code> object for the specified UUID from the list of online players' data.
	 */
	@Nonnull
	private static EntityPlayerOP getOnlinePlayer(UUID uuid)
	{
		EntityPlayerOP offlinePlayer = DinocraftServer.PLAYER_DATA.get(uuid);
		offlinePlayer.update();
		return offlinePlayer;
	}

	/**
	 * Returns an <code>EntityPlayerOP</code> object derived from the specified UUID, and <code>null</code> if an
	 * {@link IOException} is thrown.
	 */
	@Nullable
	public static EntityPlayerOP getEntityPlayerOP(UUID uuid)
	{
		if (DinocraftServer.PLAYER_DATA.containsKey(uuid))
		{
			return getOnlinePlayer(uuid);
		}

		EntityPlayerOP offlinePlayer = null;

		try
		{
			offlinePlayer = getOfflinePlayer(uuid);
		}
		catch (IOException exception)
		{

		}

		return offlinePlayer;
	}

	/**
	 * Returns <code>true</code> if a data file for the profile with the specified UUID exists and <code>false</code>
	 * otherwise
	 */
	public static boolean hasSavedData(UUID uuid)
	{
		return new File("./player_data/" + uuid + ".json").exists();
	}

	/**
	 * Returns an {@link EntityPlayerOP} object derived from the backup file for the specified UUID, and
	 * <code>null</code> if a backup for the UUID does not exist
	 */
	@Nullable
	public static EntityPlayerOP loadBackupData(UUID uuid)
	{
		File file = new File("./player_data/backups/" + uuid + ".json");

		if (file.exists())
		{
			EntityPlayerOP offlinePlayer = null;
			BufferedReader reader = null;

			try
			{
				reader = Files.newReader(file, StandardCharsets.UTF_8);
				offlinePlayer = JsonUtils.fromJson(GSON, reader, EntityPlayerOP.class);
				offlinePlayer.saveFile = file;
				offlinePlayer.update();
				return offlinePlayer;
			}
			catch (FileNotFoundException exception)
			{

			}
			finally
			{
				IOUtils.closeQuietly(reader);
			}
		}

		return null;
	}

	/**
	 * Returns <code>true</code> if a backup data file for the profile with the specified UUID exists and
	 * <code>false</code> otherwise
	 */
	public static boolean hasBackupData(UUID uuid)
	{
		return new File("./player_data/backups/" + uuid + ".json").exists();
	}

	/**
	 * Deletes the backup data for the specified UUID
	 *
	 * @return <code>true</code> if and only if the file is successfully deleted and <code>false</code> if a file or a
	 *         backup file for the specified UUID does not exist
	 */
	public static boolean deleteBackupData(UUID uuid)
	{
		EntityPlayerOP backup = loadBackupData(uuid);

		if (backup == null || !hasSavedData(uuid))
		{
			return false;
		}

		return backup.saveFile.delete();
	}

	/**
	 * Restores the backup data for the specified UUID to the actual data for the UUID
	 *
	 * @return <code>true</code> if and only if the file is successfully restored and <code>false</code> if a file or a
	 *         backup file for the specified UUID does not exist
	 */
	public static boolean restoreBackupData(UUID uuid)
	{
		EntityPlayerOP backup = loadBackupData(uuid);

		if (backup == null || !hasSavedData(uuid))
		{
			return false;
		}

		EntityPlayerOP offlinePlayer = getEntityPlayerOP(uuid);

		try
		{
			BufferedWriter writer = null;

			try
			{
				writer = Files.newWriter(offlinePlayer.saveFile, StandardCharsets.UTF_8);
				writer.write(GSON.toJson(backup));
				// writer.write(new GsonBuilder().registerTypeAdapter(EntityPlayerOP.class, new
				// Serializer()).setPrettyPrinting().create().toJson(backup)); //TODO
			}
			finally
			{
				IOUtils.closeQuietly(writer);
			}
		}
		catch (IOException exception)
		{
			getServer().logSevere("The server encountered an unknown error while saving the file '" + offlinePlayer.saveFile.getName() + "'");
		}

		backup.saveFile.delete();

		if (offlinePlayer.isOnline())
		{
			DinocraftServer.PLAYER_DATA.replace(uuid, offlinePlayer, backup);
		}

		backup.saveFile = offlinePlayer.saveFile;
		return true;
	}

	static class Serializer implements JsonDeserializer<EntityPlayerOP>, JsonSerializer<EntityPlayerOP>
	{
		private Serializer()
		{

		}

		@Override
		public JsonElement serialize(EntityPlayerOP offlinePlayer, Type type, JsonSerializationContext context)
		{
			JsonObject obj = new JsonObject();
			offlinePlayer.serializeTo(obj);
			return obj;
		}

		@Override
		public EntityPlayerOP deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException
		{
			return element.isJsonObject() ? new EntityPlayerOP(element.getAsJsonObject()) : null;
		}
	}
}