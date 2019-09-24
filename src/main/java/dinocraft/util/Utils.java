package dinocraft.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dinocraft.Reference;

public class Utils
{
	private static Logger logger;
	private static Lang lang;

	public static Logger getLogger()
	{
		return logger == null ? LogManager.getFormatterLogger(Reference.MODID) : logger;
	}

	public static Lang getLang() 
	{
		return lang == null ? new Lang(Reference.MODID) : lang;
	}
}