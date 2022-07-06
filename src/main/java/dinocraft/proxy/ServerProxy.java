package dinocraft.proxy;

import dinocraft.worldgen.BushGen;
import dinocraft.worldgen.OreGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{

	}

	public void init()
	{
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		GameRegistry.registerWorldGenerator(new BushGen(), 0);
		//		GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
	}

	public void registerRenders()
	{

	}

	public EntityPlayer getPlayer()
	{
		return null;
	}
}