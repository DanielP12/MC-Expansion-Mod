package dinocraft.item.tuskers;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityTicks;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketChangeCapability;
import dinocraft.network.client.CPacketChangeCapability.Capability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskersJug extends Item
{
	public ItemTuskersJug()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
		MinecraftForge.EVENT_BUS.register(this);
	}

	//	@SubscribeEvent
	//	public void onLivingUpdate(LivingUpdateEvent event)
	//	{
	//		EntityLivingBase entity = event.getEntityLiving();
	//
	//		if (entity.world.isRemote && entity instanceof EntityPlayer)
	//		{
	//			EntityPlayer player = (EntityPlayer) entity;
	//			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	//			ItemStack mainhandItem = entity.getHeldItemMainhand(), offhandItem = entity.getHeldItemOffhand();
	//			ItemStack stack = mainhandItem.getItem() == this ? mainhandItem : offhandItem.getItem() == this ? offhandItem : null;
	//
	//			if (stack != null && player.shouldHeal() && player.posX == player.prevPosX && player.posY == player.prevPosY && player.posZ == player.prevPosZ)
	//			{
	//				DinocraftEntityTicks ticks = dinoEntity.getTicksModule();
	//				ticks.incrementTicksStandingStill();
	//
	//				if (ticks.getTicksStandingStill() == 60)
	//				{
	//					PacketHandler.sendToServer(new CPacketStandingStill(mainhandItem == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND));
	//					dinoEntity.getActionsModule().setStandingStill(true);
	//				}
	//			}
	//			else
	//			{
	//				dinoEntity.getTicksModule().resetTicksStandingStill();
	//				DinocraftEntityActions actions = dinoEntity.getActionsModule();
	//
	//				if (actions.isStandingStill())
	//				{
	//					PacketHandler.sendToServer(new CPacketChangeCapability(Capability.DA_STANDING_STILL, false));
	//					actions.resetStandingStill();
	//				}
	//			}
	//		}
	//	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		
		if (entity.world.isRemote && entity instanceof EntityPlayer)
		{
			ItemStack mainhandItem = entity.getHeldItemMainhand(), offhandItem = entity.getHeldItemOffhand();
			ItemStack stack = mainhandItem.getItem() == this ? mainhandItem : offhandItem.getItem() == this ? offhandItem : null;
			
			if (stack != null && ((EntityPlayer) entity).shouldHeal() && entity.posX == entity.prevPosX && entity.posY == entity.prevPosY && entity.posZ == entity.prevPosZ)
			{
				DinocraftEntityTicks ticks = DinocraftEntity.getEntity(entity).getTicksModule();
				ticks.incrementTicksStandingStill();

				if (ticks.getTicksStandingStill() == 60)
				{
					PacketHandler.sendToServer(new CPacketChangeCapability(Capability.DA_STANDING_STILL, true));
				}
			}
			else
			{
				DinocraftEntityTicks ticks = DinocraftEntity.getEntity(entity).getTicksModule();

				if (ticks.getTicksStandingStill() >= 60)
				{
					PacketHandler.sendToServer(new CPacketChangeCapability(Capability.DA_STANDING_STILL, false));
				}
				
				ticks.resetTicksStandingStill();
			}
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
		
		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "When held:");
			tooltip.add(TextFormatting.GRAY + " Regenerates the player after three seconds of standing still");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}
