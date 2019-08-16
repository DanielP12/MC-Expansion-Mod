package dinocraft.handlers;

import dinocraft.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftSoundEvents 
{	
	public static final SoundEvent GRAB = new SoundEvent(new ResourceLocation(Reference.MODID, "grab")).setRegistryName("grab");
	public static final SoundEvent CHARM_USED = new SoundEvent(new ResourceLocation(Reference.MODID, "charm_used")).setRegistryName("charm_used");
	public static final SoundEvent HARP = new SoundEvent(new ResourceLocation(Reference.MODID, "harp")).setRegistryName("harp");
	public static final SoundEvent CRUNCH = new SoundEvent(new ResourceLocation(Reference.MODID, "crunch")).setRegistryName("crunch");
	public static final SoundEvent SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "shot")).setRegistryName("shot");
	public static final SoundEvent SHOT_GUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "shot_gun_shot")).setRegistryName("shot_gun_shot");
	public static final SoundEvent RAY_GUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "ray_gun_shot")).setRegistryName("ray_gun_shot");
	public static final SoundEvent GUN_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "gun_shot")).setRegistryName("gun_shot");
	public static final SoundEvent HIT = new SoundEvent(new ResourceLocation(Reference.MODID, "hit")).setRegistryName("hit");
	public static final SoundEvent CRACK = new SoundEvent(new ResourceLocation(Reference.MODID, "crack")).setRegistryName("crack");
	public static final SoundEvent BOING = new SoundEvent(new ResourceLocation(Reference.MODID, "boing")).setRegistryName("boing");
	public static final SoundEvent SEED_SHOT = new SoundEvent(new ResourceLocation(Reference.MODID, "seed_shot")).setRegistryName("seed_shot");
	
	@EventBusSubscriber
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<SoundEvent> event)
		{
			event.getRegistry().register(GRAB);
			event.getRegistry().register(CHARM_USED);
			event.getRegistry().register(HARP);
			event.getRegistry().register(CRUNCH);
			event.getRegistry().register(SHOT);
			event.getRegistry().register(SHOT_GUN_SHOT);
			event.getRegistry().register(RAY_GUN_SHOT);
			event.getRegistry().register(GUN_SHOT);
			event.getRegistry().register(HIT);
			event.getRegistry().register(CRACK);
			event.getRegistry().register(BOING);
			event.getRegistry().register(SEED_SHOT);
			System.out.println("Registered all sounds");
		}
	}
}
