package dinocraft.util.server;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class ListEntryForbiddenWord<T> extends DinocraftEntry<T>
{
	protected final String forbiddenBy;
	protected final String replacement;
	
	public ListEntryForbiddenWord(T value, String forbidder, String replacement)
	{
		super(value);
		this.replacement = replacement == null ? this.getStarReplacement() : replacement;
		this.forbiddenBy = forbidder == null ? "(Unknown)" : forbidder;
	}
	
	protected ListEntryForbiddenWord(T value, JsonObject json)
	{
		super(value, json);
		this.replacement = json.has("replacement") ? json.get("replacement").getAsString() : this.getStarReplacement();
		this.forbiddenBy = json.has("source") ? json.get("source").getAsString() : "(Unknown)";
	}

	/**
	 * Returns the replacement of the word with all letters replaced with stars
	 */
	public String getStarReplacement()
	{
		String stars = "*";

		for (int i = 1; i < this.getValue().toString().length(); i++)
		{
			stars += "*";
		}

		return stars;
	}

	/**
	 * Returns the final replacement of the word
	 */
	public String getReplacement()
	{
		return this.replacement;
	}

	/**
	 * Returns the name of the instance that forbade the word. If the forbidder is not specified, returns "(Unknown)". If the forbidder is the server, returns "Server"
	 */
	public String getForbidderByName()
	{
		return this.forbiddenBy;
	}

	/**
	 * Returns the player who forbade this word. If the player is offline, returns null. If the forbidder is the server, returns null. If the forbidder is not specified, returns null
	 */
	@Nullable
	public EntityPlayerMP getForbidder()
	{
		return this.forbiddenBy.equals("Server") ? null : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(this.forbiddenBy);
	}
	
	@Override
	protected void onSerialization(JsonObject data)
	{
		data.addProperty("source", this.forbiddenBy);
		data.addProperty("replacement", this.replacement);
	}
}