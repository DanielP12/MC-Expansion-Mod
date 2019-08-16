package dinocraft.handlers;

import dinocraft.event.DinocraftFunctionEvents;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler 
{
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new RecipeHandler());
		MinecraftForge.EVENT_BUS.register(new DinocraftFunctionEvents());
	}
}
