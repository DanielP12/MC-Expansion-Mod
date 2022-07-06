package dinocraft.util.server;

import com.google.gson.JsonObject;

public abstract class HistoryEntry extends Entry
{
	/**
	 * Invoked when the data in this entry is serialized into a file
	 */
	protected abstract void onSerialization(JsonObject object);
}
