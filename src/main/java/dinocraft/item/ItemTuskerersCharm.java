package dinocraft.item;

import java.util.List;
import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.util.DinocraftServer;
import dinocraft.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersCharm extends Item
{
	public ItemTuskerersCharm(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (stack != null && worldIn.isRemote)
		{
			if (playerIn.getMaxHealth() < 24.0F)
			{
				if (playerIn.getHealth() < 4.0F)
				{
					player.sendChatMessage(TextFormatting.GRAY + "You are cursed by the devils and blessed by the fairies...");
					playerIn.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 0.5F);
					playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
					playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 2.0F);
					playerIn.playSound(DinocraftSoundEvents.CHARM_USED, 10.0F, 1.0F);
				}
				else {
                    player.sendChatMessage(TextFormatting.RED + "You must be weak!");
                }
            }
            else {
                player.sendChatMessage(TextFormatting.RED + "You have achieved max health!");
            }
		}
			
		if (stack != null && !worldIn.isRemote && playerIn.getHealth() < 4.0F && playerIn.getMaxHealth() < 24.0F)
		{	
			stack.shrink(1);
			playerIn.addExhaustion(50.0F);
			playerIn.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000, 4, false, false));
			playerIn.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 1, false, false));
			playerIn.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600, 0, false, false));
		    playerIn.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 580, 0, false, false));
		    playerIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 1, false, false));
		    player.addMaxHealth(1.0F);
	    	/*
			if (!playerIn.hasAchievement(DinocraftAchievements.THE_MYTH))
			{
				playerIn.addStat(DinocraftAchievements.THE_MYTH);
			}
					*/
			Random rand = worldIn.rand;

			for (int i = 0; i < 20; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, worldIn,
						playerIn.posX + (rand.nextDouble() * 2D) - 1D, 
						playerIn.posY + (rand.nextDouble() * 0.25D), 
						playerIn.posZ + (rand.nextDouble() * 2D) - 1D, 
						0.0D, rand.nextDouble() * 0.03D, 0.0D, 1
					);
			}
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
		tooltip.add(TextFormatting.DARK_RED + Utils.getLang().localize("tuskerers_charm.tooltip"));

        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Increases Max Health when the player is...");
        	tooltip.add(TextFormatting.GRAY + "  -Very weak");
        	tooltip.add(TextFormatting.GRAY + "  -Healthy");
        	tooltip.add(TextFormatting.GRAY + "Be..... ware..");
        }
        else tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
	}
 }