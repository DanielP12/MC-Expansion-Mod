package dinocraft.util.server;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MuteHistoryEntry extends HistoryEntry
{
	/** The date this mute entry was created */
	private final Date dateCreated;
	/** The date this mute entry activated */
	private final Date dateIssued;
	/** The date this mute entry expired */
	private final Date endDate;
	/** The time this mute entry was initially for */
	private String time = "";
	/** The years, days, hours, minutes, and seconds this mute entry was initially for */
	private int[] timeComponents = new int[5];
	/** The reason this mute entry was created */
	private final String reason;
	/** The category of this mute entry */
	private final String category;
	/** The creator of this mute entry */
	private final String muter;
	/** The unique ID of this mute entry (an eight-digit hexadecimal number) */
	private final String ID;
	/** The standing level (weight) this mute entry held */
	private final int standingLevel;

	public MuteHistoryEntry(MuteEntry entry)
	{
		this.dateCreated = entry.getDateCreated();
		this.dateIssued = entry.getDateIssued();
		this.endDate = entry.getEndDate();
		this.time = entry.getInitialTime(false);
		int[] times = entry.getTimeComponents();
		
		for (int i = 0; i < 5; i++)
		{
			this.timeComponents[i] = times[i];
		}

		String reason = entry.getReason();
		this.reason = reason == null || reason.equals("") ? null : reason;
		String category = entry.getCategory();
		this.category = category == null || category.equals("") ? null : category;
		this.muter = entry.getMuterByName();
		this.ID = entry.getID();
		this.standingLevel = entry.getPunishmentLevel();
	}

	private MuteHistoryEntry(JsonObject object)
	{
		Date dateCreated;
		
		try
		{
			dateCreated = object.has("created") ? DinocraftList.DATE_FORMAT.parse(object.get("created").getAsString()) : null;
		}
		catch (ParseException exception)
		{
			dateCreated = null;
		}

		this.dateCreated = dateCreated;
		Date dateIssued;
		
		try
		{
			dateIssued = object.has("issued") ? DinocraftList.DATE_FORMAT.parse(object.get("issued").getAsString()) : null;
		}
		catch (ParseException exception)
		{
			dateIssued = null;
		}

		this.dateIssued = dateIssued;
		Date endDate;

		try
		{
			endDate = object.has("expired") ? DinocraftList.DATE_FORMAT.parse(object.get("expired").getAsString()) : null;
		}
		catch (ParseException exception)
		{
			endDate = null;
		}

		this.endDate = endDate;
		this.muter = object.has("source") ? object.get("source").getAsString() : "(Unknown)";
		this.reason = object.has("reason") ? object.get("reason").getAsString() : null;
		this.standingLevel = object.has("punishment level") ? object.get("punishment level").getAsInt() : 0;
		
		if (object.has("time"))
		{
			String time = object.get("time").getAsString();
			this.parseTime(time.replaceAll(" ", "").replaceAll(",", "").replace("years", "y").replace("days", "d").replace("hours", "h").replace("minutes", "m").replace("seconds", "s"));
			this.time = time;
		}
		
		this.category = object.has("category") ? object.get("category").getAsString() : null;
		this.ID = object.has("mute ID") ? object.get("mute ID").getAsString() : null;
	}
	
	public static MuteHistoryEntry onDeserialization(JsonObject object)
	{
		return new MuteHistoryEntry(object);
	}

	@Override
	protected void onSerialization(JsonObject object)
	{
		object.addProperty("type", "MUTE");
		object.addProperty("created", this.dateCreated == null ? null : DinocraftList.DATE_FORMAT.format(this.dateCreated));
		object.addProperty("issued", this.dateIssued == null ? null : DinocraftList.DATE_FORMAT.format(this.dateIssued));
		object.addProperty("expired", this.endDate == null ? null : DinocraftList.DATE_FORMAT.format(this.endDate));
		object.addProperty("time", this.time);
		object.addProperty("reason", this.reason);
		object.addProperty("category", this.category);
		object.addProperty("source", this.muter);
		object.addProperty("mute ID", this.ID);

		if (this.standingLevel != 0)
		{
			object.addProperty("punishment level", this.standingLevel);
		}
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

	/**
	 * Returns the exact date when this mute expired
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
	public String getCategory()
	{
		return this.category;
	}
	
	@Override
	public String getReason()
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
	 * Returns the time that this mute was originally issued for in the format: [years] years, [days] days, [hours] hours, [minutes] minutes, [seconds] seconds.
	 * @param simple If true, returns the original time in the format: [years]y, [days]d, [hours]h, [minutes]m, [seconds]s.
	 */
	public String getInitialTime(boolean simple)
	{
		return simple ? this.time.replace(" years", "y").replace(" days", "d").replace(" hours", "h").replace(" minutes", "m").replace(" seconds", "s") : this.time;
	}
}