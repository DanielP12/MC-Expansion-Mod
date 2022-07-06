//package dinocraft.util.server;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.UUID;
//
//import javax.annotation.Nullable;
//
//import com.google.gson.JsonObject;
//import com.mojang.authlib.GameProfile;
//
//import dinocraft.util.DinocraftConfig;
//
//public class DataEntry extends DinocraftEntry<GameProfile>
//{
//	public String groupID;
//	public int banLevel;
//	public int muteLevel;
//	public boolean toWarn;
//	public Date timeWarned;
//	public String warnReason;
//	public int previousPunishments;
//	public final ArrayList<Date> decayDates = new ArrayList<>();
//	public final ArrayList<String> warningReasons = new ArrayList<>();
//
//	public DataEntry(GameProfile profile, int muteLevel, int banLevel)
//	{
//		super(profile);
//		this.banLevel = banLevel;
//		this.muteLevel = muteLevel;
//	}
//
//	public DataEntry(JsonObject object)
//	{
//		super(constructProfile(object), object);
//		this.groupID = object.has("group ID") ? object.get("group ID").getAsString() : null;
//		this.muteLevel = object.has("mute level") ? object.get("mute level").getAsInt() : 0;
//		this.banLevel = object.has("ban level") ? object.get("ban level").getAsInt() : 0;
//		this.previousPunishments = object.has("previous punishments") ? object.get("previous punishments").getAsInt() : 0;
//		this.toWarn = object.has("warn") ? object.get("warn").getAsBoolean() : false;
//		Date timeWarned;
//
//		try
//		{
//			timeWarned = object.has("time warned") ? DinocraftList.DATE_FORMAT.parse(object.get("time warned").getAsString()) : null;
//		}
//		catch (ParseException exception)
//		{
//			timeWarned = null;
//		}
//
//		this.timeWarned = timeWarned;
//
//		try
//		{
//			/*
//				if (object.has("mute decay dates"))
//				{
//					JsonArray dates = object.getAsJsonArray("mute decay dates");
//
//					for (int i = 0; i < dates.size(); i++)
//					{
//						JsonObject obj = dates.get(i).getAsJsonObject();
//						String date = obj.get("mute decay date " + (i + 1)).getAsString();
//						this.decayDates.add(DATE_FORMAT.parse(date));
//					}
//				}
//			 */
//
//			if (object.has("mute decay dates"))
//			{
//				JsonObject obj = object.get("mute decay dates").getAsJsonObject();
//
//				for (int i = 0; i < obj.size(); i++)
//				{
//					this.decayDates.add(DinocraftList.DATE_FORMAT.parse(obj.get("mute decay date " + (i + 1)).getAsString()));
//				}
//			}
//		}
//		catch (ParseException exception)
//		{
//
//		}
//
//		if (object.has("previous warning reasons"))
//		{
//			JsonObject obj = object.get("previous warning reasons").getAsJsonObject();
//
//			for (int i = 0; i < obj.size(); i++)
//			{
//				this.warningReasons.add(obj.get("warning reason " + (i + 1)).getAsString());
//			}
//
//			String name = object.get("name").getAsString();
//
//			if (obj.size() > 0 && !DinocraftPlayerList.WARNED_PLAYERS.contains(name))
//			{
//				DinocraftPlayerList.WARNED_PLAYERS.add(name);
//			}
//		}
//	}
//
//	@Override
//	protected void onSerialization(JsonObject data)
//	{
//		GameProfile profile = this.getValue();
//
//		if (profile != null)
//		{
//			data.addProperty("UUID", profile.getId() == null ? "" : profile.getId().toString());
//			data.addProperty("name", profile.getName());
//			data.addProperty("group ID", this.groupID);
//			data.addProperty("mute level", this.muteLevel);
//			data.addProperty("ban level", this.banLevel);
//			data.addProperty("warn", this.toWarn);
//			data.addProperty("time warned", this.timeWarned == null ? null : DinocraftList.DATE_FORMAT.format(this.timeWarned));
//			data.addProperty("previous punishments", this.previousPunishments);
//
//			/*
//				JsonArray dates = new JsonArray();
//
//				for (int i = 0; i < this.decayDates.size(); i++)
//				{
//					JsonObject date = new JsonObject();
//
//					if (this.decayDates.get(i) != null)
//					{
//						date.addProperty("mute decay date " + (i + 1), DATE_FORMAT.format(this.decayDates.get(i)));
//						dates.add(date);
//					}
//				}
//
//				data.add("mute decay dates", dates);
//			 */
//			//TODO: REMOVE JSON ARRAY, just json object
//			/***************************************************************** REAL
//				JsonObject dates = new JsonObject();
//
//				for (int i = 0; i < this.decayDates.size(); i++)
//				{
//					Date date = this.decayDates.get(i);
//
//					if (date != null)
//					{
//						dates.addProperty("mute decay date " + (i + 1), DATE_FORMAT.format(date));
//					}
//					else
//					{
//						this.decayDates.remove(i);
//						i--;
//					}
//				}
//
//				JsonArray decayDates = new JsonArray();
//				decayDates.add(dates);
//				data.add("mute decay dates", decayDates);
//
//				JsonArray warningReasons = new JsonArray();
//				JsonObject warnings = new JsonObject();
//
//				for (int i = 0; i < this.warningReasons.size(); i++)
//				{
//					String reason = this.warningReasons.get(i);
//
//					if (reason != null)
//					{
//						warnings.addProperty("warning reason " + (i + 1), reason);
//					}
//					else
//					{
//						this.warningReasons.remove(i);
//						i--;
//					}
//				}
//
//				warningReasons.add(warnings);
//				data.add("previous warning reasons", warningReasons);
//				REAL ***************************************************/
//
//			JsonObject dates = new JsonObject();
//
//			for (int i = 0; i < this.decayDates.size(); i++)
//			{
//				Date date = this.decayDates.get(i);
//
//				if (date != null)
//				{
//					dates.addProperty("mute decay date " + (i + 1), DinocraftList.DATE_FORMAT.format(date));
//				}
//				else
//				{
//					this.decayDates.remove(i);
//					i--;
//				}
//			}
//
//			data.add("mute decay dates", dates);
//			JsonObject warnings = new JsonObject();
//
//			for (int i = 0; i < this.warningReasons.size(); i++)
//			{
//				String reason = this.warningReasons.get(i);
//
//				if (reason != null)
//				{
//					warnings.addProperty("warning reason " + (i + 1), reason);
//				}
//				else
//				{
//					this.warningReasons.remove(i);
//					i--;
//				}
//			}
//
//			data.add("previous warning reasons", warnings);
//		}
//	}
//
//	private static GameProfile constructProfile(JsonObject object)
//	{
//		if (object.has("UUID") && object.has("name"))
//		{
//			String string = object.get("UUID").getAsString();
//			UUID uuid;
//
//			try
//			{
//				uuid = UUID.fromString(string);
//			}
//			catch (Throwable exception)
//			{
//				return null;
//			}
//
//			return new GameProfile(uuid, object.get("name").getAsString());
//		}
//
//		return null;
//	}
//
//	public String getGroupID()
//	{
//		return this.groupID;
//	}
//
//	public Group getGroup()
//	{
//		if (this.groupID == null)
//		{
//			return null;
//		}
//
//		return DinocraftPlayerList.GROUPS.getEntry(this.groupID);
//	}
//
//	public void setGroupID(@Nullable String ID)
//	{
//		this.groupID = ID;
//	}
//
//	public boolean shouldWarn()
//	{
//		return this.toWarn;
//	}
//
//	/**
//	 * Checks the amount needed to decay depending on the number of times 30 days have passed since the mute expired. Decays that amount. Negative numbers are strictly PROHIBITED for mute entries
//	 */
//	public void decayAndUpdate()
//	{
//		if (this.decayDates.size() > 0 && this.muteLevel != 0 && !this.toWarn)
//		{
//			Date decayDate = this.decayDates.get(0);
//
//			if (decayDate != null && this.muteLevel > 0)
//			{
//				Date today = new Date();
//
//				if (today.after(decayDate))
//				{
//					int amount = (int) (Math.abs((today.getTime() - decayDate.getTime()) / 86400000) / DinocraftConfig.MUTE_DECAY_TIME) + 1;
//					this.muteLevel -= amount;
//					this.decayDates.set(0, new Date(decayDate.getYear(), decayDate.getMonth(), decayDate.getDate() + amount * DinocraftConfig.MUTE_DECAY_TIME, decayDate.getHours(), decayDate.getMinutes(), decayDate.getSeconds()));
//				}
//			}
//
//			if (this.muteLevel <= 0)
//			{
//				this.muteLevel = 0;
//				this.decayDates.clear();
//			}
//
//			//			DinocraftPlayerList.writeDataListChanges();
//		}
//	}
//
//	public void revertLatestMute()
//	{
//		if (this.decayDates.size() > 0)
//		{
//			this.decayDates.remove(0);
//		}
//
//		//		DinocraftPlayerList.writeDataListChanges();
//	}
//
//	public void addNewDecayDate(Date date)
//	{
//		this.decayDates.add(0, new Date(date.getYear(), date.getMonth(), date.getDate() + DinocraftConfig.MUTE_DECAY_TIME, date.getHours(), date.getMinutes(), date.getSeconds()));
//		//		DinocraftPlayerList.writeDataListChanges();
//	}
//
//	public void addWarningReason(String reason)
//	{
//		this.warningReasons.add(0, reason);
//	}
//
//	public void revertLatestWarning()
//	{
//		if (this.warningReasons.size() > 0)
//		{
//			this.warningReasons.remove(0);
//		}
//	}
//}