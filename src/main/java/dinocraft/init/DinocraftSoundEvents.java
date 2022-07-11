package dinocraft.init;

import dinocraft.Dinocraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class DinocraftSoundEvents
{
	public static final SoundEvent GRAB;
	public static final SoundEvent HARP;
	public static final SoundEvent SPELL;
	public static final SoundEvent SEED_SHOT;
	public static final SoundEvent HIT;
	public static final SoundEvent CRACK;
	public static final SoundEvent BOUNCE;
	public static final SoundEvent GHOST_WHISPER;
	public static final SoundEvent CLOCK;
	public static final SoundEvent ROCK_SMASH;
	public static final SoundEvent ZAP;
	public static final SoundEvent ZAP2;
	public static final SoundEvent SPLAT;

	static
	{
		BOUNCE = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "bounce"));
		CLOCK = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "clock"));
		CRACK = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "crack"));
		GHOST_WHISPER = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "ghost_whisper"));
		GRAB = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "grab"));
		HARP = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "harp"));
		HIT = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "hit"));
		ROCK_SMASH = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "rock_smash"));
		SEED_SHOT = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "seed_shot"));
		SPELL = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "spell"));
		ZAP = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "zap"));
		ZAP2 = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "zap2"));
		SPLAT = new SoundEvent(new ResourceLocation(Dinocraft.MODID, "splat"));
	}

	@EventBusSubscriber
	public static class RegistrationHandler
	{
		public static SoundEvent getUnregisteredSoundEvent(SoundEvent soundEvent, String name)
		{
			return soundEvent.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
		}
		
		@SubscribeEvent
		public static void onEvent(Register<SoundEvent> event)
		{
			IForgeRegistry<SoundEvent> registry = event.getRegistry();
			registry.register(getUnregisteredSoundEvent(BOUNCE, "bounce"));
			registry.register(getUnregisteredSoundEvent(CLOCK, "clock"));
			registry.register(getUnregisteredSoundEvent(CRACK, "crack"));
			registry.register(getUnregisteredSoundEvent(GHOST_WHISPER, "ghost_whisper"));
			registry.register(getUnregisteredSoundEvent(GRAB, "grab"));
			registry.register(getUnregisteredSoundEvent(HARP, "harp"));
			registry.register(getUnregisteredSoundEvent(HIT, "hit"));
			registry.register(getUnregisteredSoundEvent(ROCK_SMASH, "rock_smash"));
			registry.register(getUnregisteredSoundEvent(SEED_SHOT, "seed_shot"));
			registry.register(getUnregisteredSoundEvent(SPELL, "spell"));
			registry.register(getUnregisteredSoundEvent(SPLAT, "splat"));
			registry.register(getUnregisteredSoundEvent(ZAP, "zap"));
			registry.register(getUnregisteredSoundEvent(ZAP2, "zap2"));
			Dinocraft.LOGGER.info("Registered all sounds");
		}
	}
}