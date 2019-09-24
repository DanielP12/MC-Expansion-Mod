package dinocraft.util.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public abstract class UserListEntryMute<T> extends DinocraftEntry<T>
{
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    protected final Date muteStartDate;
    protected final String mutedBy;
    protected final Date muteEndDate;
    protected final String reason;

    public UserListEntryMute(T value, Date startDate, String muter, Date endDate, String reason)
    {
        super(value);
        this.muteStartDate = startDate == null ? new Date() : startDate;
        this.mutedBy = muter == null ? "(Unknown)" : muter;
        this.muteEndDate = endDate;
        this.reason = reason == null ? "Muted by an operator." : reason;
    }

    protected UserListEntryMute(T value, JsonObject json)
    {
        super(value, json);
        Date date;

        try
        {
            date = json.has("created") ? DATE_FORMAT.parse(json.get("created").getAsString()) : new Date();
        }
        catch (ParseException var7)
        {
            date = new Date();
        }

        this.muteStartDate = date;
        this.mutedBy = json.has("source") ? json.get("source").getAsString() : "(Unknown)";
        Date date1;

        try
        {
            date1 = json.has("expires") ? DATE_FORMAT.parse(json.get("expires").getAsString()) : null;
        }
        catch (ParseException var6)
        {
            date1 = null;
        }

        this.muteEndDate = date1;
        this.reason = json.has("reason") ? json.get("reason").getAsString() : "Muted by an operator.";
    }

    public Date getMuteEndDate()
    {
        return this.muteEndDate;
    }

    public String getMuteReason()
    {
        return this.reason;
    }

    @Override
	public boolean hasMuteExpired()
    {
        return this.muteEndDate == null ? false : this.muteEndDate.before(new Date());
    }

    @Override
	protected void onSerialization(JsonObject data)
    {
        data.addProperty("created", DATE_FORMAT.format(this.muteStartDate));
        data.addProperty("source", this.mutedBy);
        data.addProperty("expires", this.muteEndDate == null ? "forever" : DATE_FORMAT.format(this.muteEndDate));
        data.addProperty("reason", this.reason);
    }
}