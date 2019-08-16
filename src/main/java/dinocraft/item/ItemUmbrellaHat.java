package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUmbrellaHat extends ItemArmor
{
	public ItemUmbrellaHat(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxDamage(400);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	/** Slowfall */
	@Override
	public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stack)
	{
		if (playerIn.motionY < 0.0F) playerIn.motionY *= 0.625F;
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		if (player.hasFallDamage()) player.setFallDamage(false);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
        tooltip.add(TextFormatting.WHITE + Utils.getLang().localize("umbrella_hat.tooltip"));
        
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
        if (GameSettings.isKeyDown(shift)) tooltip.add(TextFormatting.GRAY + "Slowfall I");
        else tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
	}
}