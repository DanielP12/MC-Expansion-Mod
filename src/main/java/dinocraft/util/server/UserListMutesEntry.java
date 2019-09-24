package dinocraft.util.server;

import java.util.Date;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListMutesEntry extends UserListEntryMute<GameProfile>
{
    public UserListMutesEntry(GameProfile profile)
    {
        super(profile, (Date) null, (String) null, (Date) null, (String) null);
    }

    public UserListMutesEntry(GameProfile profile, Date startDate, String banner, Date endDate, String banReason)
    {
        super(profile, endDate, banner, endDate, banReason);
    }
    
    public UserListMutesEntry(JsonObject object)
    {
        super(constructProfile(object), object);
    }

    @Override
	protected void onSerialization(JsonObject data)
    {
        if (this.getValue() != null)
        {
            data.addProperty("uuid", this.getValue().getId() == null ? "" : this.getValue().getId().toString());
            data.addProperty("name", this.getValue().getName());
            super.onSerialization(data);
        }
    }

    private static GameProfile constructProfile(JsonObject object)
    {
        if (object.has("uuid") && object.has("name"))
        {
            String string = object.get("uuid").getAsString();
            UUID uuid;

            try
            {
                uuid = UUID.fromString(string);
            }
            catch (Throwable exception)
            {
                return null;
            }

            return new GameProfile(uuid, object.get("name").getAsString());
        }

        return null;
    }
}