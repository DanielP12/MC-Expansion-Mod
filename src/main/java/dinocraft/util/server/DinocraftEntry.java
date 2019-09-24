package dinocraft.util.server;

import com.google.gson.JsonObject;

public class DinocraftEntry<T>
{
    private final T value;

    public DinocraftEntry(T value)
    {
        this.value = value;
    }

    protected DinocraftEntry(T value, JsonObject object)
    {
        this.value = value;
    }

    T getValue()
    {
        return this.value;
    }

    boolean hasMuteExpired()
    {
        return false;
    }
    
    protected void onSerialization(JsonObject object)
    {
    	
    }
}