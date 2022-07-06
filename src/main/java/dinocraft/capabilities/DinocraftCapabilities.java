package dinocraft.capabilities;

import dinocraft.Dinocraft;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityProvider;
import dinocraft.capabilities.entity.IDinocraftEntity;
import dinocraft.capabilities.itemstack.DinocraftItemStack;
import dinocraft.capabilities.itemstack.DinocraftItemStackProvider;
import dinocraft.capabilities.itemstack.IDinocraftItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftCapabilities
{
	@CapabilityInject(IDinocraftEntity.class)
	public static final Capability<IDinocraftEntity> DINOCRAFT_ENTITY = null;
	@CapabilityInject(IDinocraftItemStack.class)
	public static final Capability<IDinocraftItemStack> DINOCRAFT_ITEM_STACK = null;

	public static void registerCapabilities()
	{
		MinecraftForge.EVENT_BUS.register(DinocraftCapabilities.class);
		CapabilityManager.INSTANCE.register(IDinocraftEntity.class, new DinocraftEntity.Storage(), DinocraftEntity::new);
		CapabilityManager.INSTANCE.register(IDinocraftItemStack.class, new DinocraftItemStack.Storage(), DinocraftItemStack::new);
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		Entity entity = event.getObject();
		
		if (entity instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Dinocraft.MODID, "DinocraftEntity"), new DinocraftEntityProvider(new DinocraftEntity((EntityLivingBase) entity)));
		}
	}
	
	@SubscribeEvent
	public static void onAttachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event)
	{
		event.addCapability(new ResourceLocation(Dinocraft.MODID, "DinocraftItemStack"),
				new DinocraftItemStackProvider(new DinocraftItemStack(event.getObject())));
	}
}