package dinocraft.init;

import dinocraft.Reference;
import dinocraft.util.Utils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftSoundEvents 
{	
	public static final SoundEvent GRAB;
	public static final SoundEvent CHARM;
	public static final SoundEvent HARP;
	public static final SoundEvent CRUNCH;
	public static final SoundEvent SHOT;
	public static final SoundEvent GUN_SHOT;
	public static final SoundEvent SHOTGUN_SHOT;
	public static final SoundEvent RAY_GUN_SHOT;
	public static final SoundEvent SEED_SHOT;
	public static final SoundEvent HIT;
	public static final SoundEvent CRACK;
	public static final SoundEvent BOING;
	
	static
	{
		GRAB = new SoundEvent(new ResourceLocation(Reference.MODID, "grab")).setRegistryName("grab");
		CHARM = new SoundEvent(new ResourceLocation(Reference.MODID, "charm")).setRegistryName("charm");
		HARP = new SoundEvent(new ResourceLocation(Reference.MODID, "harp")).setRegistryName("harp");
		CRUNCH = new SoundEvent(new ResourceLocation(Reference.MODID, "crunch")).setRegistryName("crunch");
		SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "shot")).setRegistryName("shot");
		GUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "gun_shot")).setRegistryName("gun_shot");
		SHOTGUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "shotgun_shot")).setRegistryName("shotgun_shot");
		RAY_GUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "ray_gun_shot")).setRegistryName("ray_gun_shot");
		SEED_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "seed_shot")).setRegistryName("seed_shot");
		HIT = new SoundEvent(new ResourceLocation(Reference.MODID, "hit")).setRegistryName("hit");
		CRACK = new SoundEvent(new ResourceLocation(Reference.MODID, "crack")).setRegistryName("crack");
		BOING = new SoundEvent(new ResourceLocation(Reference.MODID, "boing")).setRegistryName("boing");
	}
	
	@EventBusSubscriber
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void register(Register<SoundEvent> event)
		{
            event.getRegistry().registerAll(GRAB, CHARM, HARP, CRUNCH, SHOT, GUN_SHOT, SHOTGUN_SHOT, RAY_GUN_SHOT, SEED_SHOT, HIT, CRACK, BOING);
	        Utils.getLogger().info("Registered all sounds");
		}
	}
}