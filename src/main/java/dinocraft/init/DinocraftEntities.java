package dinocraft.init;

import dinocraft.Dinocraft;
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
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DinocraftEntities
{
	public static void init()
	{
		int id = 0;
		registerEntity(EntitySpikeBall.class, "spike_ball", id++);
		registerEntity(EntityMagatiumBolt.class, "magatium_bolt", id++);
		registerEntity(EntityMagatiumSmallShard.class, "magatium_small_shard", id++);
		registerEntity(EntityFallingCrystal.class, "falling_crystal", id++);
		registerEntity(EntityElectricBolt.class, "electric_bolt", id++);
		registerEntity(EntityLeaferang.class, "leaferang", id++);
		registerEntity(EntityLeafBolt.class, "leaf_bolt", id++);
		registerEntity(EntityMesmerizingBolt.class, "mesmerizing_bolt", id++);
		registerEntity(EntityDarkSoul.class, "dark_soul", id++);
		registerEntity(EntityDreadedEye.class, "dreaded_eye", id++);
		registerEntity(EntityDremoniteShuriken.class, "dremonite_shuriken", id++);
		registerEntity(EntityJestersBolt.class, "jesters_bolt", id++);
		registerEntity(EntityPebble.class, "pebble", id++);
		registerEntity(EntitySplicentsThrowingKnife.class, "splicents_throwing_knife", id++);
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String name, int id)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Dinocraft.MODID, name), entityClass, name, id, Dinocraft.INSTANCE, 1024, 1024, true);
	}
}