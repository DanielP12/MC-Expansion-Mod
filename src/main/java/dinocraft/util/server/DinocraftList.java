package dinocraft.util.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dinocraft.Dinocraft;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DinocraftList<K, V extends DinocraftEntry<K>>
{
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm:ss a z");
	private final Gson gson;
	/** The file this list's contents are saved in */
	private final File saveFile;
	private final Map<String, V> values = new HashMap<>();
	private boolean lanServer = true;
	private static final ParameterizedType DINOCRAFT_LIST_ENTRY_TYPE = new ParameterizedType()
	{
		@Override
		public Type[] getActualTypeArguments()
		{
			return new Type[] {DinocraftEntry.class};
		}
		
		@Override
		public Type getRawType()
		{
			return List.class;
		}
		
		@Override
		public Type getOwnerType()
		{
			return null;
		}
	};
	
	public DinocraftList(File file)
	{
		this.saveFile = file;
		this.gson = new GsonBuilder().setPrettyPrinting().registerTypeHierarchyAdapter(DinocraftEntry.class, new Serializer()).create();
	}
	
	public boolean isLanServer()
	{
		return this.lanServer;
	}
	
	public void setLanServer(boolean state)
	{
		this.lanServer = state;
	}
	
	/**
	 * Adds the specified entry to this list
	 */
	public void addEntry(V entry)
	{
		this.values.put(this.getObjectKey(entry.getValue()), entry);
	}
	
	/**
	 * Removes the specified entry from this list
	 */
	public void removeEntry(K entry)
	{
		this.values.remove(this.getObjectKey(entry));
	}
	
	public V getEntry(K obj)
	{
		this.removeExpired();
		return this.values.get(this.getObjectKey(obj));
	}

	/**
	 * Generates a random ID (hexadecimal number) with the specified length
	 */
	public static String generateRandomID(int length)
	{
		Random rand = new Random();
		StringBuilder ID = new StringBuilder();
		
		while (ID.length() < length)
		{
			ID.append(Integer.toHexString(rand.nextInt(16)));
		}
		
		return "#" + ID.toString().toUpperCase();
	}
	
	/**
	 * Removes all expired entries from this list
	 */
	public void removeExpired()
	{
		List<K> list = new ArrayList<>();
		
		for (V v : this.values.values())
		{
			if (v.hasExpired())
			{
				list.add(v.getValue());
			}
		}
		
		for (K k : list)
		{
			this.values.remove(this.getObjectKey(k));
		}
	}
	
	/**
	 * Returns the file this list's contents are saved in
	 */
	@SideOnly(Side.SERVER)
	public File getFile()
	{
		return this.saveFile;
	}
	
	public String[] getKeys()
	{
		return this.values.keySet().toArray(new String[this.values.size()]);
	}
	
	/**
	 * Gets the key value for the given object
	 */
	protected String getObjectKey(K obj)
	{
		return obj.toString();
	}
	
	/**
	 * Returns whether or not this list contains the specified entry
	 */
	protected boolean hasEntry(K entry)
	{
		return this.values.containsKey(this.getObjectKey(entry));
	}
	
	protected DinocraftEntry<K> createEntry(JsonObject object)
	{
		return new DinocraftEntry<>(null, object);
	}
	
	public Map<String, V> getValues()
	{
		return this.values;
	}
	
	/**
	 * Saves, to the save file, the changes made to this list since the last invokation of this method
	 */
	public void save()
	{
		try
		{
			BufferedWriter writer = null;

			try
			{
				writer = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);
				writer.write(this.gson.toJson(this.values.values()));
			}
			finally
			{
				IOUtils.closeQuietly(writer);
			}
		}
		catch (IOException exception)
		{
			Dinocraft.LOGGER.error("The server encountered an error while saving the file '" + this.saveFile.getName() + "'", exception);
		}
	}
	
	/**
	 * Returns whether or not this list is empty
	 */
	@SideOnly(Side.SERVER)
	public boolean isEmpty()
	{
		return this.values.size() < 1;
	}
	
	/**
	 * Loads the data contained in the save file to this list
	 */
	@SideOnly(Side.SERVER)
	public void load()
	{
		try
		{
			if (this.saveFile.exists())
			{
				Collection<DinocraftEntry<K>> collection = null;
				BufferedReader reader = null;
				
				try
				{
					reader = Files.newReader(this.saveFile, StandardCharsets.UTF_8);
					collection = JsonUtils.fromJson(this.gson, reader, DINOCRAFT_LIST_ENTRY_TYPE);
				}
				finally
				{
					IOUtils.closeQuietly(reader);
				}
				
				if (collection != null)
				{
					this.values.clear();
					
					for (DinocraftEntry<K> entry : collection)
					{
						K value = entry.getValue();
						
						if (value != null)
						{
							this.values.put(this.getObjectKey(value), (V) entry);
						}
					}
				}
			}
		}
		catch (Exception exception)
		{
			Dinocraft.LOGGER.error("The server encountered an error while loading the file '" + this.saveFile.getName() + "'", exception);
		}
	}
	
	class Serializer implements JsonDeserializer<DinocraftEntry<K>>, JsonSerializer<DinocraftEntry<K>>
	{
		private Serializer()
		{
			
		}
		
		@Override
		public JsonElement serialize(DinocraftEntry<K> entry, Type type, JsonSerializationContext context)
		{
			JsonObject obj = new JsonObject();
			entry.onSerialization(obj);
			return obj;
		}
		
		@Override
		public DinocraftEntry<K> deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException
		{
			return element.isJsonObject() ? DinocraftList.this.createEntry(element.getAsJsonObject()) : null;
		}
	}
}