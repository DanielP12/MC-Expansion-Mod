package dinocraft.util.server;

import java.io.File;

import com.google.gson.JsonObject;

public class ListForbiddenWords extends DinocraftList<String, ListForbiddenWordsEntry>
{
    public ListForbiddenWords(File file)
    {
        super(file);
    }

    @Override
	protected DinocraftEntry<String> createEntry(JsonObject entryData)
    {
        return new ListForbiddenWordsEntry(entryData);
    }
    
    public boolean isForbidden(String word)
    {
        return this.hasEntry(word);
    }

    @Override
	public String[] getKeys()
    {
        String[] astring = new String[this.getValues().size()];
        int i = 0;

        for (ListForbiddenWordsEntry entry : this.getValues().values())
        {
            astring[i++] = entry.getValue();
        }

        return astring;
    }
}