package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketCapabilities;
import dinocraft.network.PacketCapabilities.Capability;
import dinocraft.util.DinocraftServer;
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

public class ItemCloudChestplate extends ItemArmor
{
	public ItemCloudChestplate(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
	{
        super(material, renderIndex, equipmentSlot);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setMaxDamage(600);        
    }
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) 
	{
		if (world.isRemote && DinocraftEntity.getEntity(player).isJumping() && player.motionY < 0.33D)
		{
			if (player.motionY <= 0.0D && player.ticksExisted % 3 == 0)
			{
				for (int i = 0; i < 2; ++i)
				{
					DinocraftServer.spawnCloudParticles();
				}				
			}
			
			player.motionY *= player.motionY > 0.0D ? 1.25D : 0.75D;
			player.fallDistance = 0.0F;
			NetworkHandler.sendToServer(new PacketCapabilities(Capability.FALL_DISTANCE));
		}
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{        
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak; 
        
        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Cloud Jump");
            tooltip.add(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) +  " / " + stack.getMaxDamage());
        }
        else tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
	}
}