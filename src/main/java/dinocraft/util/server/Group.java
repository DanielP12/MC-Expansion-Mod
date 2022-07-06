package dinocraft.util.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.server.management.PlayerProfileCache;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Group extends DinocraftEntry<String>
{
	protected String name;
	protected final ArrayList<String> commands = new ArrayList<>();
	protected final ArrayList<String> playerUUIDs = new ArrayList<>();

	public Group(String name)
	{
		super(DinocraftList.generateRandomID(10));
		this.name = name;
	}

	public Group(JsonObject json)
	{
		super(json.has("ID") ? json.get("ID").getAsString() : null, json);
		this.name = json.has("name") ? json.get("name").getAsString() : null;

		if (json.has("command permissions"))
		{
			JsonObject obj = json.get("command permissions").getAsJsonObject();

			for (int i = 0; i < obj.size(); i++)
			{
				this.commands.add(obj.get("command " + (i + 1)).getAsString());
			}
		}

		if (json.has("players"))
		{
			JsonObject obj = json.get("players").getAsJsonObject();
			
			for (Entry<String, JsonElement> element : obj.entrySet())
			{
				this.playerUUIDs.add(element.getValue().getAsString());
			}
		}
	}

	public List<String> getCommands()
	{
		return this.commands;
	}
	
	public List<String> getPlayerUUIDs()
	{
		return this.playerUUIDs;
	}

	/**
	 * Returns the name of this group
	 */
	public String getName()
	{
		return this.name;
	}
	
	public String getID()
	{
		return this.getValue();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean hasCommandOrAliases(String commandName)
	{
		List<String> commands = this.getCommands();
		
		if (commands.contains(commandName))
		{
			return true;
		}

		for (String alias : FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().getCommands().get(commandName).getAliases())
		{
			if (commands.contains(alias))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected void onSerialization(JsonObject data)
	{
		String value = this.getValue();
		
		if (value != null)
		{
			data.addProperty("name", this.name);
			data.addProperty("ID", value);
			super.onSerialization(data);
			JsonObject commands = new JsonObject();
			
			for (int i = 0; i < this.commands.size(); i++)
			{
				String name = this.commands.get(i);
				
				if (name != null)
				{
					commands.addProperty("command " + (i + 1), name);
				}
				else
				{
					this.commands.remove(i);
					i--;
				}
			}
			
			data.add("command permissions", commands);

			JsonObject players = new JsonObject();
			PlayerProfileCache profiles = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache();
			
			/*
			for (int i = 0; i < this.playerUUIDs.size(); i++)
			{
				String uuid = this.playerUUIDs.get(i);
				String name = profiles.getProfileByUUID(UUID.fromString(uuid)).getName();

				if (uuid != null)
				{
					players.addProperty("player " + (i + 1), name + ", " + uuid);
				}
				else
				{
					this.playerUUIDs.remove(i);
					i--;
				}
			}
			 */
			
			for (String uuid : this.playerUUIDs)
			{
				players.addProperty(profiles.getProfileByUUID(UUID.fromString(uuid)).getName(), uuid);
			}
			
			data.add("players", players);
		}
	}
}