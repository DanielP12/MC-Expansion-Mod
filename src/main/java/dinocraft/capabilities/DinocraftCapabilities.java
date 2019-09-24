package dinocraft.capabilities;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityProvider;
import dinocraft.capabilities.entity.IDinocraftEntity;
import dinocraft.capabilities.player.IDinocraftPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftCapabilities
{
	@CapabilityInject(IDinocraftPlayer.class)
	public static final Capability<IDinocraftPlayer> DINOCRAFT_PLAYER = null;
	@CapabilityInject(IDinocraftEntity.class)
	public static final Capability<IDinocraftEntity> DINOCRAFT_ENTITY = null;

	public static void registerCapabilities()
	{
		MinecraftForge.EVENT_BUS.register(DinocraftCapabilities.class);
		//CapabilityManager.INSTANCE.register(IDinocraftPlayer.class, new DinocraftPlayer.Storage(), DinocraftPlayer.class);
		CapabilityManager.INSTANCE.register(IDinocraftEntity.class, new DinocraftEntity.Storage(), DinocraftEntity.class);
	}
	
	@SubscribeEvent
	public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() == null)
		{
			return;
		}
		
		if (event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Reference.MODID, "DinocraftEntity"), new DinocraftEntityProvider(new DinocraftEntity((EntityLivingBase) event.getObject())));
		}
	}
}