package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import java.util.List;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.init.DinocraftItems;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.init.MobEffects;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemArmor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemRaniumArmor extends ItemArmor
//{
//	public ItemRaniumArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
//	{
//		super(material, renderIndex, equipmentSlot);
//		MinecraftForge.EVENT_BUS.register(this);
//	}
//
//	@SubscribeEvent
//	public void onLivingUpdate(LivingUpdateEvent event)
//	{
//		EntityLivingBase entity = event.getEntityLiving();
//
//		if (entity.getHealth() < 6.0F && DinocraftEntity.isWearing(entity, DinocraftItems.RANIUM_HELMET, DinocraftItems.RANIUM_CHESTPLATE, DinocraftItems.RANIUM_LEGGINGS, DinocraftItems.RANIUM_BOOTS))
//		{
//			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 15, 2, true, true));
//		}
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
//	{
//		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
//
//		if (GameSettings.isKeyDown(shift))
//		{
//			tooltip.add(TextFormatting.GRAY + "When wearing full set:");
//			tooltip.add(TextFormatting.BLUE + " Resistance III " + TextFormatting.GRAY + "when below 3 hearts of health");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}