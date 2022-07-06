package dinocraft.item.leafy;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketDoubleJump;
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

public class ItemLeafyBoots extends ItemArmor
{
	public ItemLeafyBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
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

			if (player.onGround || player.isInWater() || player.isInLava() || player.isOnLadder() || player.isElytraFlying() || player.isRiding())
			{
				actions.setHasJumpedInAir(true);
				actions.setCanJumpInAir(false);
			}

			if (!player.onGround)
			{
				boolean jumping = dinoEntity.isJumping();

				if (!jumping && !actions.canJumpInAir())
				{
					actions.setHasJumpedInAir(false);
					actions.setCanJumpInAir(true);
				}

				if (jumping && !actions.hasJumpedInAir())
				{
					actions.setHasJumpedInAir(true);
					dinoEntity.setFallDamageReductionAmount(5.0F);
					PotionEffect effect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
					player.motionY = effect != null ? effect.getAmplifier() * 0.095D + 0.575D : 0.488D;
					player.motionX *= 1.015D;
					player.motionZ *= 1.015D;
					player.fallDistance = 0.0F;
					PacketHandler.sendToServer(new CPacketDoubleJump());
				}
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
			tooltip.add(TextFormatting.DARK_AQUA + " Double Jump (" + TextFormatting.DARK_GRAY + "[SPACE] " + TextFormatting.DARK_AQUA + "+" + TextFormatting.DARK_GRAY + " [SPACE]" + TextFormatting.DARK_AQUA + ")");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}