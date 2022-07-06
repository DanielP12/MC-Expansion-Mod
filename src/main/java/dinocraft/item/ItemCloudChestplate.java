package dinocraft.item;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketChangeCapability;
import dinocraft.network.client.CPacketChangeCapability.Capability;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCloudChestplate extends ItemArmor
{
	public ItemCloudChestplate(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
	{
		super(material, renderIndex, equipmentSlot);
		this.setMaxDamage(682);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (world.isRemote && DinocraftEntity.getEntity(player).isJumping() && player.motionY < 0.25D)
		{
			if (player.motionY <= 0.0D && player.ticksExisted % 3 == 0)
			{
				for (int i = 0; i < 5; i++)
				{
					DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD, true,
							player.posX + (world.rand.nextDouble() - 0.5D) * player.width,
							player.posY + world.rand.nextDouble() - player.getYOffset() - 1.0D,
							player.posZ + (world.rand.nextDouble() - 0.5D) * player.width,
							world.rand.nextGaussian() * 0.070D, world.rand.nextGaussian() * 0.050D, world.rand.nextGaussian() * 0.070D, 1);
				}
			}
			
			player.motionY *= player.motionY > 0.0D ? 1.35D : 0.8D;
			player.fallDistance = 0.0F;
			PacketHandler.sendToServer(new CPacketChangeCapability(Capability.P_FALL_DISTANCE, 0));
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
		
		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "Cloud Jump");
			tooltip.add(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) +  " / " + stack.getMaxDamage());
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}