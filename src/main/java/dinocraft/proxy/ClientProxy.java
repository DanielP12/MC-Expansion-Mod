package dinocraft.proxy;

import dinocraft.entity.EntityDarkSoul;
import dinocraft.entity.EntityDreadedEye;
import dinocraft.entity.EntityDremoniteShuriken;
import dinocraft.entity.EntityElectricBolt;
import dinocraft.entity.EntityFallingCrystal;
import dinocraft.entity.EntityJestersBolt;
import dinocraft.entity.EntityLeafBolt;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityMagatiumBolt;
import dinocraft.entity.EntityMagatiumSmallShard;
import dinocraft.entity.EntityMesmerizingBolt;
import dinocraft.entity.EntityPebble;
import dinocraft.entity.EntitySpikeBall;
import dinocraft.entity.EntitySplicentsThrowingKnife;
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
		RenderingRegistry.registerEntityRenderingHandler(EntitySpikeBall.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.SPIKE_BALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagatiumBolt.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.MAGATIUM_BOLT, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagatiumSmallShard.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.MAGATIUM_SMALL_SHARD, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityFallingCrystal.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.FALLING_CRYSTAL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityElectricBolt.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.ELECTRIC_BOLT, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLeaferang.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.LEAFERANG, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLeafBolt.class, renderManager -> new RenderSnowball(renderManager, null, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMesmerizingBolt.class, renderManager -> new RenderSnowball(renderManager, null, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkSoul.class, renderManager -> new RenderSnowball(renderManager, null, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadedEye.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.DREADED_EYE, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDremoniteShuriken.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.DREMONITE_SHURIKEN, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityJestersBolt.class, renderManager -> new RenderSnowball(renderManager, null, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.PEBBLE, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySplicentsThrowingKnife.class, renderManager -> new RenderSnowball(renderManager, DinocraftItems.SPLICENTS_THROWING_KNIFE, Minecraft.getMinecraft().getRenderItem()));
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return FMLClientHandler.instance().getClient().player;
	}
}