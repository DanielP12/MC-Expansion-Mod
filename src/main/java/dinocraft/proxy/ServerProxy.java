package dinocraft.proxy;

import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntityVineBall;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
import dinocraft.worldgen.OreGen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy
{	
	public void preInit(FMLPreInitializationEvent event) 
	{
		initRenderers();
	}
	
	private void initRenderers() 
	{
		
	}
	
	public void init()
	{
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
	}
	
	public void registerRenders() 
	{
		
	}
	
	public EntityPlayer getPlayer()
	{
        return null;
    }
}
