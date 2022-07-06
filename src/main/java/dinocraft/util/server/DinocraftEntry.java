package dinocraft.util.server;

import com.google.gson.JsonObject;

public class DinocraftEntry<T>
{
	private T value;

	DinocraftEntry(T value)
	{
		this.value = value;
	}

	DinocraftEntry(T value, JsonObject object)
	{
		this.value = value;
	}

	T getValue()
	{
		return this.value;
	}
	
	boolean hasExpired()
	{
		return false;
	}
	
	/**
	 * Invoked when this entry expires and is ready to be disposed
	 */
	void onExpiration()
	{

	}

	/**
	 * Invoked when this entry is serialized into the specified <code>JsonObject</code>
	 */
	protected void onSerialization(JsonObject object)
	{
		
	}
}