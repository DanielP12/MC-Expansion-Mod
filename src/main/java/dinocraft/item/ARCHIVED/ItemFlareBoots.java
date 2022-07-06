package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import java.util.List;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.MobEffects;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemArmor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemFlareBoots extends ItemArmor
//{
//	public ItemFlareBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
//	{
//		super(material, renderIndex, equipmentSlot);
//	}
//
//	@Override
//	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
//	{
//		if (!world.isRemote)
//		{
//			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 0, true, true));
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
//			tooltip.add(TextFormatting.GRAY + "When worn:");
//			tooltip.add(TextFormatting.BLUE + " Fire Resistance");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}