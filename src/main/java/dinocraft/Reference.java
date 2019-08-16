package dinocraft;

import net.minecraft.util.ResourceLocation;

public class Reference 
{	
	public static final String MODID = "dinocraft";
	public static final String VERSION = "1.0";
	public static final String NAME = "Dinocraft";
	public static final String SERVER_PROXY_CLASS = "dinocraft.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "dinocraft.proxy.ClientProxy";

	public static ResourceLocation getResource(String name)
	{
		return new ResourceLocation(Reference.MODID, name);
	}
}
