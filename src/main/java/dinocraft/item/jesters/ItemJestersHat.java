package dinocraft.item.jesters;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketJestersDash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJestersHat extends ItemArmor
{
	public ItemJestersHat(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (!player.getCooldownTracker().hasCooldown(this) && world.isRemote && !player.capabilities.allowFlying)
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
			DinocraftEntityActions actions = dinoEntity.getActionsModule();
			
			if (player.onGround || player.isInWater() || player.isInLava() || player.isOnLadder() || player.isElytraFlying() || player.isRiding())
			{
				actions.setHasJumpedInAir(true);
				actions.setCanJumpInAir(false);
			}
			else
			{
				boolean jumping = dinoEntity.isJumping();
				
				if (!jumping && !actions.canJumpInAir())
				{
					actions.setHasJumpedInAir(false);
					actions.setCanJumpInAir(true);
				}
				else if (jumping && !actions.hasJumpedInAir())
				{
					actions.setHasJumpedInAir(true);
					dinoEntity.setFallDamageReductionAmount(2.0F);
					Vec3d vec = player.getLookVec().normalize();
					player.motionY += 0.33D;
					player.motionX += vec.x * 0.5D;
					player.motionZ += vec.z * 0.5D;
					player.fallDistance = 0.0F;
					PacketHandler.sendToServer(new CPacketJestersDash());
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
			tooltip.add(TextFormatting.DARK_AQUA + " Jester's Dash (" + TextFormatting.DARK_GRAY + "[SPACE] " + TextFormatting.DARK_AQUA + "+" + TextFormatting.DARK_GRAY + " [SPACE]" + TextFormatting.DARK_AQUA + ")");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}