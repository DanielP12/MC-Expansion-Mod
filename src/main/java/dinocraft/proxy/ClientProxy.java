package dinocraft.proxy;

import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntitySeed;
import dinocraft.entity.EntityVineBall;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy 
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}

	@Override
	public void registerRenders() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityVineBall.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.VINE_BALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityRayBullet.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.RAY_BULLET, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLeaferang.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.LEAFERANG, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySeed.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.SEED, Minecraft.getMinecraft().getRenderItem()));
		DinocraftItems.registerRenders();
		DinocraftBlocks.registerRenders();
	}
	
	@Override
    public EntityPlayer getPlayer()
	{
        return FMLClientHandler.instance().getClient().player;
	}
}