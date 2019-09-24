package dinocraft.util.server;

import com.google.gson.JsonObject;

public class ListForbiddenWordsEntry extends DinocraftEntry<String>
{   
    public ListForbiddenWordsEntry(JsonObject object)
    {
        super(constructString(object), object);
    }
    
    public ListForbiddenWordsEntry(String string)
    {
        super(string);
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
        if (object.has("word"))
        {
            String string = object.get("word").getAsString();
            return new String(object.get("word").getAsString());
        }

        return null;
    }
}