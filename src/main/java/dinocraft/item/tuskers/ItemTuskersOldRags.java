package dinocraft.item.tuskers;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskersOldRags extends ItemArmor
{
	public ItemTuskersOldRags(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
		
		if (!entity.world.isRemote && dinoEntity != null)
		{
			DinocraftEntityActions actions = dinoEntity.getActionsModule();
			ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

			if (stack.getItem() == this)
			{
				if (!actions.hasExtraMaxHealth())
				{
					DinocraftEntity.addMaxHealth(entity, 2.0F);
					actions.setHasExtraMaxHealth(true);
				}
			}
			else if (actions.hasExtraMaxHealth())
			{
				DinocraftEntity.addMaxHealth(entity, -2.0F);
				actions.setHasExtraMaxHealth(false);
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
			tooltip.add(TextFormatting.GRAY + "When worn:");
			tooltip.add(TextFormatting.BLUE + " +2 Max Health");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}