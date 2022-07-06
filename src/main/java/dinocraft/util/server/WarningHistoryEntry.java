package dinocraft.util.server;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WarningHistoryEntry extends HistoryEntry
{
	/** The date this warning entry was officially issued */
	private Date dateIssued;
	/** The date this warning entry was created by the warner */
	private Date dateCreated;
	/** The reason this warning entry was created */
	private final String reason;
	/** The category of this warning entry */
	private String category;
	/** The creator of this warning entry */
	private final String warner;
	/** The standing level (weight) this warning entry holds */
	private int standingLevel;

	public WarningHistoryEntry(WarningEntry entry)
	{
		this.dateCreated = entry.getDateCreated();
		this.dateIssued = entry.getDateIssued();
		String reason = entry.getReason();
		this.reason = reason == null || reason.equals("") ? null : reason;
		String category = entry.getCategory();
		this.category = category == null || category.equals("") ? null : category;
		this.warner = entry.getWarnerByName();
		this.standingLevel = entry.getPunishmentLevel();
	}
	
	public WarningHistoryEntry(@Nullable String warner, @Nullable String reason)
	{
		this.warner = warner == null ? "(Unknown)" : warner;
		this.reason = reason.equals("") || reason == null ? null : reason;
		this.dateCreated = new Date();
	}
	
	public WarningHistoryEntry(@Nullable String warner, int standingLevel, String category, @Nullable String reason)
	{
		this.dateCreated = new Date();
		this.warner = warner == null ? "(Unknown)" : warner;
		this.reason = reason == null || reason.equals("") ? null : reason;
		this.standingLevel = standingLevel;
		this.category = category;
	}
	
	private WarningHistoryEntry(JsonObject object)
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
		this.warner = object.has("source") ? object.get("source").getAsString() : "(Unknown)";
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
		this.reason = object.has("reason") ? object.get("reason").getAsString() : null;
		this.standingLevel = object.has("punishment level") ? object.get("punishment level").getAsInt() : 0;
		this.category = object.has("category") ? object.get("category").getAsString() : null;
	}

	protected static WarningHistoryEntry onDeserialization(JsonObject object)
	{
		return new WarningHistoryEntry(object);
	}
	
	@Override
	protected void onSerialization(JsonObject object)
	{
		object.addProperty("type", "WARNING");
		object.addProperty("created", this.dateCreated == null ? null : DinocraftList.DATE_FORMAT.format(this.dateCreated));
		object.addProperty("issued", this.dateIssued == null ? null : DinocraftList.DATE_FORMAT.format(this.dateIssued));
		object.addProperty("reason", this.reason);
		object.addProperty("category", this.category);
		object.addProperty("source", this.warner);
		
		if (this.standingLevel != 0)
		{
			object.addProperty("punishment level", this.standingLevel);
		}
	}
	
	@Override
	public Date getDateIssued()
	{
		return this.dateIssued;
	}
	
	@Override
	public Date getDateCreated()
	{
		return this.dateCreated;
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
	 * Returns the name of the instance that warned this profile. If the warner is not specified, returns "(Unknown)". If the warner is the server, returns "Server"
	 */
	public String getWarnerByName()
	{
		return this.warner;
	}

	/**
	 * Returns the level of this warning. If this warning is a custom one, returns 0
	 */
	public int getPunishmentLevel()
	{
		return this.standingLevel;
	}
	
	/**
	 * Returns the player who warned this profile. If the player is offline, returns null. If the warner is the server, returns null. If the warner is not specified, returns null
	 */
	@Nullable
	public EntityPlayerMP getWarner()
	{
		return this.warner.equals("Server") ? null : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(this.warner);
	}
}
