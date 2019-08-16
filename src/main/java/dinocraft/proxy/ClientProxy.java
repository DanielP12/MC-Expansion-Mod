package dinocraft.proxy;

import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntitySeed;
import dinocraft.entity.EntityVineBall;
import dinocraft.init.DinocraftArmour;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftEntities;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
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
		DinocraftEntities.init();
		//registerEntityRenders();
	}
	
	/*
	private static void registerEntityRenders() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityVineBall.class, RenderVineBall::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonBall.class, RenderPoisonBall::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRayBullet.class, RenderRayBullet::new);
	} */
	
	
	@Override
	public void registerRenders() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityVineBall.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.VINE_BALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityRayBullet.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.RAY_BULLET, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLeaferang.class, renderManager -> new RenderSnowball(renderManager, DinocraftTools.LEAFERANG, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySeed.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.SEED, Minecraft.getMinecraft().getRenderItem()));
		DinocraftItems.registerRenders();
		DinocraftBlocks.registerRenders();
		DinocraftTools.registerRenders();
		DinocraftArmour.registerRenders();
	}
	
	@Override
    public EntityPlayer getPlayer()
	{
        return FMLClientHandler.instance().getClient().player;
	}
}