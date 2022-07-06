package dinocraft.util.server;

import com.google.gson.JsonObject;

public class ForbiddenWord extends ListEntryForbiddenWord<String>
{
	public ForbiddenWord(JsonObject object)
	{
		super(constructString(object), object);
	}
	
	public ForbiddenWord(String word, String forbidder, String replacement)
	{
		super(word, forbidder, replacement);
	}

	@Override
	protected void onSerialization(JsonObject data)
	{
		if (this.getValue() != null)
		{
			data.addProperty("word", this.getValue());
			super.onSerialization(data);
		}
	}

	private static String constructString(JsonObject object)
	{
		return object.has("word") ? object.get("word").getAsString() : null;
	}
}