package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.network.MessageDoubleJump;
import dinocraft.network.NetworkHandler;
import dinocraft.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLeafyBoots extends ItemArmor
{	
    public ItemLeafyBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
    {
        super(material, renderIndex, equipmentSlot);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setMaxDamage(1000);
    }
        
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
    	DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);

		if (world.isRemote && !dinoEntity.isFlying() && !player.isCreative())
		{
			DinocraftEntityActions actions = dinoEntity.getActionsModule();
	    	
	    	if (player.onGround || !player.isAirBorne || player.isInWater() || player.isInLava() || player.isOnLadder())
			{
	    		actions.setHasDoubleJumped(true);
	    		actions.setCanDoubleJumpAgain(false);
			}
	    		
			if (player.isAirBorne && !player.onGround)
			{
				if (!dinoEntity.isJumping() && !actions.canDoubleJumpAgain())
				{	
					actions.setHasDoubleJumped(false);
					actions.setCanDoubleJumpAgain(true);
				}
	            	
				if (dinoEntity.isJumping() && !actions.hasDoubleJumped())
				{
					actions.setHasDoubleJumped(true);
					dinoEntity.setFallDamageReductionAmount(5.0F);
					
					PotionEffect effect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
					player.motionY = effect != null ? (effect.getAmplifier() * 0.095D) + 0.575D : 0.4875D + (0.01D * world.rand.nextDouble());
					player.motionX *= 1.015D;    
					player.motionZ *= 1.015D;
					player.fallDistance = 0.0F;
					
					NetworkHandler.sendToServer(new MessageDoubleJump());
				}
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) 
    {
    	tooltip.add(TextFormatting.DARK_GREEN + Utils.getLang().localize("leafy_boots.tooltip"));
        
    	KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak; 
        
        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Double Jump");
            tooltip.add(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) +  " / " + stack.getMaxDamage());
        } 
        else
        {
        	tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
        }
    }
}