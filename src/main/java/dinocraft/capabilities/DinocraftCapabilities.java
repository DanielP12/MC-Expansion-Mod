package dinocraft.capabilities;

import org.jline.utils.Log;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerProvider;
import dinocraft.capabilities.player.IDinocraftPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

	public static void registerCapabilities()
	{
		MinecraftForge.EVENT_BUS.register(DinocraftCapabilities.class);
		CapabilityManager.INSTANCE.register(IDinocraftPlayer.class, new DinocraftPlayer.Storage(), DinocraftPlayer.class);
	}
	
	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() == null) return;
		if (event.getObject() instanceof EntityPlayer) event.addCapability(Reference.getResource("DinocraftPlayer"), new DinocraftPlayerProvider(new DinocraftPlayer((EntityPlayer) event.getObject())));
	}
}
