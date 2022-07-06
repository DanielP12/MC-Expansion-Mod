package dinocraft.util.server;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import dinocraft.util.DinocraftConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class MuteEntry extends Entry
{
	/** The date this mute entry was created */
	private Date dateCreated;
	/** The date this mute entry becomes active */
	private Date dateIssued;
	/** The date this mute entry expires */
	private Date endDate;
	/** The time this mute entry is initially for */
	private String time = "";
	private final int timeComponents[] = new int[5];
	/** The reason this mute entry was created */
	private final String reason;
	/** The category of this mute entry */
	private final String category;
	/** The creator of this mute entry */
	private final String muter;
	/** The unique ID of this mute entry (an eight-digit hexadecimal number) */
	private final String ID;
	/** The standing level (weight) this mute entry holds */
	private int standingLevel;

	public MuteEntry(@Nonnull GameProfile profile, @Nullable String muter, int years, int days, int hours, int minutes, int seconds, @Nullable String reason, boolean permanent)
	{
		EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(profile.getId());
		this.dateCreated = new Date();
		this.dateIssued = player == null ? null : new Date();
		this.endDate = player == null || permanent ? null : this.addTime(years, days, hours, minutes, seconds);
		this.time = permanent ? "Permanent" : this.writeTime(years, days, hours, minutes, seconds);
		this.timeComponents[0] = years;
		this.timeComponents[1] = days;
		this.timeComponents[2] = hours;
		this.timeComponents[3] = minutes;
		this.timeComponents[4] = seconds;
		this.muter = muter == null ? "(Unknown)" : muter;
		this.reason = "".equals(reason) ? null : reason;
		this.category = null;
		this.ID = DinocraftList.generateRandomID(8);
	}

	public MuteEntry(@Nonnull GameProfile profile, @Nullable String muter, int standingLevel, @Nullable String category, @Nullable String reason)
	{
		EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(profile.getId());
		this.dateCreated = new Date();
		this.dateIssued = player == null ? null : new Date();
		this.muter = muter == null ? "(Unknown)" : muter;
		
		if (standingLevel >= DinocraftConfig.MUTE_TIMES.length)
		{
			this.parse(DinocraftConfig.MUTE_TIMES[DinocraftConfig.MUTE_TIMES.length - 1]);
		}
		else if (standingLevel <= 0)
		{
			this.parse(DinocraftConfig.MUTE_TIMES[0]);
		}
		else
		{
			this.parse(DinocraftConfig.MUTE_TIMES[standingLevel - 1]);
		}
		
		if (!this.isPermanent())
		{
			this.time = this.writeTime(this.timeComponents[0], this.timeComponents[1], this.timeComponents[2], this.timeComponents[3], this.timeComponents[4]);
		}
		
		this.reason = "".equals(reason) ? null : reason;
		this.category = category;
		this.endDate = this.isPermanent() || player == null ? null : this.addTime(this.timeComponents[0], this.timeComponents[1], this.timeComponents[2], this.timeComponents[3], this.timeComponents[4]);
		this.standingLevel = standingLevel;
		this.ID = DinocraftList.generateRandomID(8);
	}
	
	private MuteEntry(JsonObject object) throws ParseException
	{
		this.dateCreated = object.has("created") ? DinocraftList.DATE_FORMAT.parse(object.get("created").getAsString()) : null;
		this.dateIssued = object.has("issued") ? DinocraftList.DATE_FORMAT.parse(object.get("issued").getAsString()) : null;
		this.endDate = object.has("expires") ? DinocraftList.DATE_FORMAT.parse(object.get("expires").getAsString()) : null;

		if (object.has("time"))
		{
			String time = object.get("time").getAsString();

			if (!time.equals("Permanent"))
			{
				this.parseTime(time.replaceAll(" ", "").replaceAll(",", "").replace("years", "y").replace("days", "d").replace("hours", "h").replace("minutes", "m").replace("seconds", "s"));
			}
			
			this.time = time;
		}

		this.muter = object.has("source") ? object.get("source").getAsString() : "(Unknown)";
		this.reason = object.has("reason") ? object.get("reason").getAsString() : null;
		this.category = object.has("category") ? object.get("category").getAsString() : null;
		this.standingLevel = object.has("punishment level") ? object.get("punishment level").getAsInt() : 0;
		this.ID = object.has("mute ID") ? object.get("mute ID").getAsString() : null;
	}

	protected static MuteEntry onDeserialization(JsonObject object) throws ParseException
	{
		return new MuteEntry(object);
	}
	
	protected void onSerialization(JsonObject object)
	{
		object.addProperty("created", this.dateCreated == null ? null : DinocraftList.DATE_FORMAT.format(this.dateCreated));
		object.addProperty("issued", this.dateIssued == null ? null : DinocraftList.DATE_FORMAT.format(this.dateIssued));
		object.addProperty("expires", this.endDate == null ? null : DinocraftList.DATE_FORMAT.format(this.endDate));
		object.addProperty("time", this.time);
		object.addProperty("source", this.muter);
		object.addProperty("reason", this.reason);
		object.addProperty("category", this.category);
		object.addProperty("status", this.dateIssued == null ? "Scheduled" : "Issued");
		
		if (this.standingLevel != 0 && !this.time.equals("Permanent"))
		{
			object.addProperty("punishment level", this.standingLevel);
		}
		
		object.addProperty("mute ID", this.ID);
	}

	private void parseTime(String str)
	{
		String time = str.toLowerCase(), value = "";
		int length = time.length();
		char curr;

		for (int i = 0; i < length; i++)
		{
			curr = time.charAt(i);
			
			if (Character.isDigit(curr))
			{
				value += Character.toString(curr);
			}
			else
			{
				this.timeComponents[curr == 'y' ? 0 : curr == 'd' ? 1 : curr == 'h' ? 2 : curr == 'm' ? 3 : 4] = Integer.valueOf(value);
				value = "";
			}
		}
	}
	
	private void parse(String str)
	{
		String time = str.toLowerCase();
		
		if (time.equals("permanent"))
		{
			this.time = "Permanent";
		}
		else
		{
			this.parseTime(time);
		}
	}
	
	/**
	 * Returns the exact date when this mute will expire
	 */
	public Date getEndDate()
	{
		return this.endDate;
	}
	
	@Override
	public Date getDateCreated()
	{
		return this.dateCreated;
	}
	
	@Override
	public Date getDateIssued()
	{
		return this.dateIssued;
	}
	
	@Override
	public @Nullable String getCategory()
	{
		return this.category;
	}
	
	@Override
	public @Nullable String getReason()
	{
		return this.reason;
	}
	
	/**
	 * Returns the name of the instance that muted this profile. If the muter is not specified, returns "(Unknown)". If the muter is the server, returns "Server"
	 */
	public String getMuterByName()
	{
		return this.muter;
	}
	
	/**
	 * At index 0: The number of years this mute was initially for<br>
	 * At index 1: The number of days this mute was initially for<br>
	 * At index 2: The number of hours this mute was initially for<br>
	 * At index 3: The number of minutes this mute was initially for<br>
	 * At index 4: The number of seconds this mute was initially for<br>
	 */
	public int[] getTimeComponents()
	{
		return this.timeComponents.clone();
	}

	/**
	 * Returns the level of this mute. If the mute is a custom one, returns 0
	 */
	public int getPunishmentLevel()
	{
		return this.standingLevel;
	}
	
	/**
	 * Returns the player who muted this profile. If the player is offline, the muter is the server, or this entry has no muter, returns <code>null</code>
	 */
	@Nullable
	public EntityPlayerMP getMuter()
	{
		return this.muter.equals("Server") ? null : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(this.muter);
	}
	
	/**
	 * Returns the unique ID of this mute (an eight-digit hexadecimal number)
	 */
	public String getID()
	{
		return this.ID;
	}
	
	/**
	 * Returns the remaining time of this mute in the format: [years] years, [days] days, [hours] hours, [minutes] minutes, [seconds] seconds.
	 * @param simple If true, returns the remaining time in the format: [years]y, [days]d, [hours]h, [minutes]m, [seconds]s.
	 */
	public String getRemainingTime(boolean simple)
	{
		if (!this.isPermanent() && this.isIssued() && !this.hasExpired())
		{
			long seconds = Math.abs((new Date().getTime() - this.endDate.getTime()) / 1000);
			int minutes = (int) (seconds / 60);
			seconds %= 60;
			int hours = minutes / 60;
			minutes %= 60;
			int days = hours / 24;
			hours %= 24;
			int years = days / 365;
			days %= 365;
			String time = this.writeTime(years, days, hours, minutes, (int) seconds);
			return simple ? time.replace(" years", "y").replace(" days", "d").replace(" hours", "h").replace(" minutes", "m").replace(" seconds", "s") : time;
		}
		
		return this.getInitialTime(simple);
	}
	
	/**
	 * Returns the time that this mute was originally issued for in the format:
	 * [years] years, [days] days, [hours] hours, [minutes] minutes, [seconds] seconds.
	 * @param simple if true, returns the original time in the format: [years]y, [days]d, [hours]h, [minutes]m, [seconds]s.
	 */
	public String getInitialTime(boolean simple)
	{
		return simple ? this.time.replace(" years", "y").replace(" days", "d").replace(" hours", "h").replace(" minutes", "m").replace(" seconds", "s") : this.time;
	}
	
	/**
	 * Changes the status of this mute entry to issued
	 */
	protected void issue()
	{
		this.dateIssued = new Date();
		
		if (!this.isPermanent())
		{
			this.endDate = this.addTime(this.timeComponents[0], this.timeComponents[1], this.timeComponents[2], this.timeComponents[3], this.timeComponents[4]);
		}
	}
	
	/**
	 * Returns <code>true</code> if this mute was issued and <code>false</code> otherwise
	 */
	public boolean isIssued()
	{
		return this.dateIssued != null;
	}

	/**
	 * Returns <code>true</code> if this mute is permanent and <code>false</code> otherwise
	 */
	public boolean isPermanent()
	{
		return this.getInitialTime(false).equals("Permanent");
	}

	/**
	 * Returns a date with the specified amount of years, days, hours,
	 * minutes, and seconds added to the starting date of this mute
	 */
	protected Date addTime(int years, int days, int hours, int minutes, int seconds)
	{
		return new Date(this.dateIssued.getYear() + years, this.dateIssued.getMonth(), this.dateIssued.getDate() + days, this.dateIssued.getHours() + hours, this.dateIssued.getMinutes() + minutes, this.dateIssued.getSeconds() + seconds);
	}
	
	/**
	 * Returns a <code>String</code> representation of the specified
	 * time components, which includes only non-zero time components
	 */
	protected String writeTime(int years, int days, int hours, int minutes, int seconds)
	{
		int[] times = new int[] {years, days, hours, minutes, seconds};
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < 5; i++)
		{
			if (times[i] != 0)
			{
				String suffix = i == 0 ? " years" : i == 1 ? " days" : i == 2 ? " hours" : i == 3 ? " minutes" : " seconds";
				
				if (result.length() != 0)
				{
					result.append(", ");
				}

				result.append(times[i]).append(suffix);
			}
		}
		
		return result.length() == 0 ? "0 seconds" : result.toString();
	}
	
	/**
	 * Returns <code>true</code> if the date at which this mute entry ends
	 * is before the current date and <code>false</code> otherwise
	 */
	public boolean hasExpired()
	{
		return this.endDate != null && this.endDate.before(new Date());
	}
}