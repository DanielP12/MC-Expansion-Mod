package dinocraft.item.splicents;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketThunderJump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSplicentsChestplate extends ItemArmor
{
	public ItemSplicentsChestplate(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (world.isRemote && !player.capabilities.allowFlying && !player.isCreative())
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
			DinocraftEntityActions actions = dinoEntity.getActionsModule();
			boolean jumping = dinoEntity.isJumping();
			boolean sneaking = player.isSneaking();

			if (!player.onGround)
			{
				actions.setCanSneakJump(false);
			}
			else if (!sneaking && !jumping)
			{
				actions.setCanSneakJump(true);
			}

			if (sneaking && jumping && actions.canSneakJump())
			{
				actions.setCanSneakJump(true);
				dinoEntity.setFallDamageReductionAmount(5.0F);
				player.onGround = false;
				PotionEffect effect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
				player.motionY = effect != null ? effect.getAmplifier() * 0.095D + 0.575D : 1.0D;
				player.motionX *= 1.05D;
				player.motionZ *= 1.05D;
				player.fallDistance = 0.0F;
				PacketHandler.sendToServer(new CPacketThunderJump());
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
			tooltip.add(TextFormatting.DARK_AQUA + " Thunder Jump (" + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.DARK_AQUA + "+" + TextFormatting.DARK_GRAY + " [SPACE]" + TextFormatting.DARK_AQUA + ")");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}