package dinocraft.util.server;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WarningEntry extends Entry
{
	/** The date this warning entry was created */
	private Date dateCreated;
	/** The date this warning entry was issued */
	private Date dateIssued;
	/** The creator of this warning entry */
	private final String warner;
	/** The reason this warning entry was created */
	private final String reason;
	/** The category of this warning entry */
	private String category;
	/** The standing level (weight) this warning entry holds */
	private int standingLevel;
	
	public WarningEntry(@Nonnull GameProfile profile, @Nullable String warner, @Nullable String reason)
	{
		this.dateCreated = new Date();
		
		if (FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(profile.getId()) != null)
		{
			this.dateIssued = new Date();
		}
		
		this.warner = warner == null ? "(Unknown)" : warner;
		this.reason = "".equals(reason) ? null : reason;
	}

	public WarningEntry(@Nonnull GameProfile profile, @Nullable String warner, int standingLevel, String category, @Nullable String reason)
	{
		this.dateCreated = new Date();
		
		if (FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(profile.getId()) != null)
		{
			this.dateIssued = new Date();
		}
		
		this.warner = warner == null ? "(Unknown)" : warner;
		this.reason = "".equals(reason) ? null : reason;
		this.category = category;
		this.standingLevel = standingLevel;
	}

	private WarningEntry(JsonObject object) throws ParseException
	{
		this.dateCreated = object.has("created") ? DinocraftList.DATE_FORMAT.parse(object.get("created").getAsString()) : null;
		this.dateIssued = object.has("issued") ? DinocraftList.DATE_FORMAT.parse(object.get("issued").getAsString()) : null;
		this.warner = object.has("source") ? object.get("source").getAsString() : "(Unknown)";
		this.reason = object.has("reason") ? object.get("reason").getAsString() : null;
		this.category = object.has("category") ? object.get("category").getAsString() : null;
		this.standingLevel = object.has("punishment level") ? object.get("punishment level").getAsInt() : 0;
	}
	
	protected static WarningEntry onDeserialization(JsonObject object) throws ParseException
	{
		return new WarningEntry(object);
	}

	protected void onSerialization(JsonObject object)
	{
		object.addProperty("created", this.dateCreated == null ? null : DinocraftList.DATE_FORMAT.format(this.dateCreated));
		object.addProperty("issued", this.dateIssued == null ? null : DinocraftList.DATE_FORMAT.format(this.dateIssued));
		object.addProperty("source", this.warner);
		object.addProperty("reason", this.reason);
		object.addProperty("category", this.category);
		object.addProperty("status", this.dateIssued == null ? "Scheduled" : "Issued");

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
	
	/**
	 * Returns <code>true</code> if this warning was issued and <code>false</code> otherwise
	 */
	public boolean isIssued()
	{
		return this.dateIssued != null;
	}

	/**
	 * Changes the status of this mute entry to issued
	 */
	protected void issue()
	{
		this.dateIssued = new Date();
	}
}