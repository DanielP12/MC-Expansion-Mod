package dinocraft.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dinocraft.Reference;

public class Utils
{	
	private static Logger logger;
	
	public static Logger getLogger()
	{
		if (logger == null) logger = LogManager.getFormatterLogger(Reference.MODID);
		return logger;
	}

	private static Lang lang;

	public static Lang getLang() 
	{
		if (lang == null) lang = new Lang(Reference.MODID);
		return lang;
	}
}