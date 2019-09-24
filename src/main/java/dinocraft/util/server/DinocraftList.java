package dinocraft.util.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import net.minecraft.util.JsonUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DinocraftList<K, V extends DinocraftEntry<K>>
{
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final Gson gson;
    private final File saveFile;
    public final Map<String, V> values = Maps.<String, V>newHashMap();
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
        GsonBuilder builder = (new GsonBuilder()).setPrettyPrinting();
        builder.registerTypeHierarchyAdapter(DinocraftEntry.class, new Serializer());
        this.gson = builder.create();
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
     * Adds an entry to the list
     */
    public void addEntry(V entry)
    {
        this.values.put(this.getObjectKey(entry.getValue()), entry);

        try
        {
            this.writeChanges();
        }
        catch (IOException exception)
        {
            LOGGER.warn("Could not save the list after adding a user.", exception);
        }
    }

    public void removeEntry(K entry)
    {
        this.values.remove(this.getObjectKey(entry));

        try
        {
            this.writeChanges();
        }
        catch (IOException exception)
        {
            LOGGER.warn("Could not save the list after removing a user.", exception);
        }
    }

    public V getEntry(K obj)
    {
        this.removeExpired();
        return (this.values.get(this.getObjectKey(obj)));
    }
    
    /**
     * Removes expired mutes from the list.
     */
    private void removeExpired()
    {
        List<K> list = Lists.<K>newArrayList();

        for (V v : this.values.values())
        {
            if (v.hasMuteExpired())
            {
                list.add(v.getValue());
            }
        }

        for (K k : list)
        {
            this.values.remove(k);
        }
    }
    
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

    protected boolean hasEntry(K entry)
    {
        return this.values.containsKey(this.getObjectKey(entry));
    }

    protected DinocraftEntry<K> createEntry(JsonObject entryData)
    {
        return new DinocraftEntry<K>(null, entryData);
    }

    public Map<String, V> getValues()
    {
        return this.values;
    }
    
    public void writeChanges() throws IOException
    {
        Collection<V> collection = this.values.values();
        BufferedWriter writer = null;

        try
        {
        	writer = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);
        	writer.write(this.gson.toJson(collection));
        }
        finally
        {
            IOUtils.closeQuietly(writer);
        }
    }

    @SideOnly(Side.SERVER)
    public boolean isEmpty()
    {
        return this.values.size() < 1;
    }

    @SideOnly(Side.SERVER)
    public void readSavedFile() throws IOException, FileNotFoundException
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
                    if (entry.getValue() != null)
                    {
                        this.values.put(this.getObjectKey(entry.getValue()), (V) entry);
                    }
                }
            }
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
            JsonObject object = new JsonObject();
            entry.onSerialization(object);
            return object;
        }
        
        @Override
        public DinocraftEntry<K> deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            if (element.isJsonObject())
            {
                JsonObject object = element.getAsJsonObject();
                return DinocraftList.this.createEntry(object);
            }

            return null;
        }
    }
}