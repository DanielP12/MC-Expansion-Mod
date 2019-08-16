package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
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
	public ItemCloudChestplate(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
	{
        super (materialIn, renderIndexIn, equipmentSlotIn);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setMaxDamage(600);        
    }

	/** Cloud Jump */
	@Override
	public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stack) 
	{
		if (worldIn.isRemote && DinocraftPlayer.getPlayer(playerIn).isJumping() && playerIn.motionY < 0.38D)
		{
			if (playerIn.motionY <= 0.0D) DinocraftServer.spawnCloudParticles();		
			playerIn.motionY *= playerIn.motionY > 0.0D ? 1.2D : 0.75D;
			playerIn.fallDistance = 0.0F;
			DinocraftServer.cancelPlayerFallDamage();
		}
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{        
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak; 
        
        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Cloud Jump I");
            tooltip.add(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) +  " / " + stack.getMaxDamage());
        }
        else tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
	}
}
