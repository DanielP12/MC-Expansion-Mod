package dinocraft.init;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntitySeed;
import dinocraft.entity.EntityVineBall;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DinocraftEntities
{
	public static final ResourceLocation VINE_BALL_TEXTURE;
	public static final ResourceLocation RAY_BULLET_TEXTURE;
	public static final ResourceLocation SEED_TEXTURE;
	public static final ResourceLocation LEAFERANG_TEXTURE;
	
	static
	{
		VINE_BALL_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/vine_ball.png");
		RAY_BULLET_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/ray_bullet.png");
		SEED_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/seed.png");
		LEAFERANG_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/leaferang.png");
	}
	
	public static void init()
	{
		int id = 0;

		EntityRegistry.registerModEntity(VINE_BALL_TEXTURE, EntityVineBall.class, "vine_ball", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(RAY_BULLET_TEXTURE, EntityRayBullet.class, "ray_bullet", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(LEAFERANG_TEXTURE, EntityLeaferang.class, "leaferang", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(SEED_TEXTURE, EntitySeed.class, "seed", id++, Dinocraft.instance, 1000, 1000, true);
	}
}