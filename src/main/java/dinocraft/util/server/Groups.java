package dinocraft.util.server;

import java.io.File;

import com.google.gson.JsonObject;

public class Groups extends DinocraftList<String, Group>
{
	public Groups(File saveFile)
	{
		super(saveFile);
	}

	@Override
	protected DinocraftEntry<String> createEntry(JsonObject data)
	{
		return new Group(data);
	}

	public Group getGroupByID(String ID)
	{
		for (Group value : this.getValues().values())
		{
			if (value.getValue().equals(ID))
			{
				return value;
			}
		}

		return null;
	}
	
	public Group getGroupByName(String name)
	{
		for (Group value : this.getValues().values())
		{
			if (value.name.equals(name))
			{
				return value;
			}
		}
		
		return null;
	}
	
	@Override
	public String[] getKeys()
	{
		String[] keys = new String[this.getValues().size()];
		int i = 0;

		for (Group entry : this.getValues().values())
		{
			keys[i++] = entry.getValue();
		}

		return keys;
	}
	
	public String[] getNames()
	{
		String[] names = new String[this.getValues().size()];
		int i = 0;

		for (Group entry : this.getValues().values())
		{
			names[i++] = entry.getName();
		}

		return names;
	}
}