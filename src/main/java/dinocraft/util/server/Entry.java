package dinocraft.util.server;

import java.util.Date;

public abstract class Entry
{
	/**
	 * Returns the date this entry was created
	 */
	public abstract Date getDateCreated();
	
	/**
	 * Returns the date this entry was issued
	 */
	public abstract Date getDateIssued();
	
	/**
	 * Returns the category of this entry
	 */
	public abstract String getCategory();
	
	/**
	 * Returns the reason of this entry
	 */
	public abstract String getReason();
}
