package dinocraft.util.server;

import java.io.File;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListMutes extends DinocraftList<GameProfile, UserListMutesEntry>
{
    public UserListMutes(File file)
    {
        super(file);
    }

    @Override
	protected DinocraftEntry<GameProfile> createEntry(JsonObject entryData)
    {
        return new UserListMutesEntry(entryData);
    }
    
    public boolean isMuted(GameProfile profile)
    {
        return this.hasEntry(profile);
    }

    @Override
	public String[] getKeys()
    {
        String[] astring = new String[this.getValues().size()];
        int i = 0;

        for (UserListMutesEntry entry : this.getValues().values())
        {
            astring[i++] = entry.getValue().getName();
        }

        return astring;
    }

    /**
     * Gets the key value for the given object
     */
    @Override
	protected String getObjectKey(GameProfile obj)
    {
        return obj.getId().toString();
    }

    /**
     * Gets the GameProfile of based on the specified username.
     */
    public GameProfile getGameProfileFromName(String username)
    {
        for (UserListMutesEntry entry : this.getValues().values())
        {
            if (username.equalsIgnoreCase(entry.getValue().getName()))
            {
                return entry.getValue();
            }
        }

        return null;
    }
}