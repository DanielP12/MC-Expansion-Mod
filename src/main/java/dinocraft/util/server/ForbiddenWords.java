package dinocraft.util.server;

import java.io.File;

import com.google.gson.JsonObject;

public class ForbiddenWords extends DinocraftList<String, ForbiddenWord>
{
	public ForbiddenWords(File file)
	{
		super(file);
	}

	@Override
	protected DinocraftEntry<String> createEntry(JsonObject data)
	{
		return new ForbiddenWord(data);
	}
	
	public boolean isForbidden(String word)
	{
		return this.hasEntry(word);
	}

	@Override
	public String[] getKeys()
	{
		String[] keys = new String[this.getValues().size()];
		int i = 0;

		for (ForbiddenWord entry : this.getValues().values())
		{
			keys[i++] = entry.getValue();
		}

		return keys;
	}
}