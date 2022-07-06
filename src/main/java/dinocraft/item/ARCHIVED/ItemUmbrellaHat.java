package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import java.util.List;
//
//import dinocraft.Dinocraft;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemArmor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemUmbrellaHat extends ItemArmor
//{
//	public ItemUmbrellaHat(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
//	{
//		super(material, renderIndex, equipmentSlot);
//		this.setUnlocalizedName(name);
//		this.setMaxDamage(400);
//		this.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
//	}
//
//	/*
//	@Override
//	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
//	{
//		if (player.motionY < 0.0F)
//		{
//			player.motionY *= 0.625F;
//		}
//
//		DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(player);
//
//		if (dinoPlayer.hasFallDamage())
//		{
//			dinoPlayer.setFallDamage(false);
//		}
//	}
//	 */
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
//	{
//		tooltip.add(TextFormatting.WHITE + Dinocraft.lang().localize("umbrella_hat.tooltip"));
//
//		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
//
//		if (GameSettings.isKeyDown(shift))
//		{
//			tooltip.add(TextFormatting.GRAY + "Slowfall");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}