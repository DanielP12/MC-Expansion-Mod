package dinocraft.item;

import java.util.List;
import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerActions;
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
    public ItemLeafyBoots(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
    {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setMaxDamage(2000);
    }
    
	Random rand = new Random();
    
    /** Double Jump */
    @Override
    public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stack)
    {
    	DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);

		if (worldIn.isRemote && !player.isFlying() && !playerIn.isCreative() && !playerIn.isSpectator())
		{
	    	DinocraftPlayerActions actions = player.getActions();
	    	
	    	if (playerIn.onGround || !playerIn.isAirBorne || playerIn.isInWater() || playerIn.isInLava() || playerIn.isOnLadder())
			{
	    		actions.setHasDoubleJumped(true);
	    		actions.setCanDoubleJumpAgain(false);
			}
	    		
			if (playerIn.isAirBorne && !playerIn.onGround)
			{					
				if (!player.isJumping() && !actions.canDoubleJumpAgain())
				{	
					actions.setHasDoubleJumped(false);
					actions.setCanDoubleJumpAgain(true);
				}
	            	
				if (player.isJumping() && !actions.hasDoubleJumped())
				{
					actions.setHasDoubleJumped(true);
					
					player.setFallDamageReductionAmount(5.0F);
						
					double velY = 0.4875D + (0.4925D - 0.4875D) * rand.nextDouble();
					double velXZ = 1.099D + (1.099D - 1.097D) * rand.nextDouble();							
					playerIn.motionX *= /* 1.0975D */ velXZ;    
					playerIn.motionZ *= /* 1.0975D */ velXZ;
												
					PotionEffect effect = playerIn.getActivePotionEffect(MobEffects.JUMP_BOOST);
					playerIn.motionY = effect != null ? (effect.getAmplifier() * 0.095D) + 0.575D : /* 0.475D */ velY;
						
					NetworkHandler.sendToServer(new MessageDoubleJump());
				}
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
    {
    	tooltip.add(TextFormatting.DARK_GREEN + Utils.getLang().localize("leafy_boots.tooltip"));
        
    	KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak; 
        
        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Double Jump I");
            tooltip.add(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) +  " / " + stack.getMaxDamage());
        } 
        else tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
    }
}