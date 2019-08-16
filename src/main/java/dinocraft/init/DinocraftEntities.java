package dinocraft.init;

import dinocraft.Dinocraft;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntitySeed;
import dinocraft.entity.EntityVineBall;
import dinocraft.entity.RenderEntities;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DinocraftEntities
{
	@SideOnly(Side.CLIENT)
	public static void init()
	{
		int id = 0;

		EntityRegistry.registerModEntity(RenderEntities.VINE_BALL_TEXTURE, EntityVineBall.class, "vine_ball", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(RenderEntities.RAY_BULLET_TEXTURE, EntityRayBullet.class, "ray_bullet", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(RenderEntities.LEAFERANG_TEXTURE, EntityLeaferang.class, "leaferang", id++, Dinocraft.instance, 1000, 1000, true);
		EntityRegistry.registerModEntity(RenderEntities.SEED_TEXTURE, EntitySeed.class, "seed", id++, Dinocraft.instance, 1000, 1000, true);
	} 
	/*
	private static int id;

	public static void preInit() 
	{
		registerEntity(EntityVineBall.class);
		registerEntity(EntityPoisonBall.class);
		registerEntity(EntityRayBullet.class);
	} */
/*
	private static void registerEntity(Class clazz) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":textures/items/vine_ball.png"), clazz, "vine_ball", ++id, Dinocraft.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":textures/items/poison_ball.png"), clazz, "poison_ball", ++id, Dinocraft.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":textures/items/ray_bullet.png"), clazz, "ray_bullet", ++id, Dinocraft.instance, 64, 3, true);

		/*
		String unlocalizedName = clazz.getSimpleName().replace("Entity", ""); 
		unlocalizedName = unlocalizedName.substring(0, 1).toLowerCase() + unlocalizedName.substring(1);
		ResourceLocation registryName = new ResourceLocation(Reference.MODID, unlocalizedName);
		EntityRegistry.registerModEntity(registryName, clazz, unlocalizedName, ++id, Dinocraft.instance, 64, 3, true);
	*/
	//}
}