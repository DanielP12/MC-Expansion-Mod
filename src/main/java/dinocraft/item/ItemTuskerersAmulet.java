package dinocraft.item;

import java.util.List;
import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftSoundEvents;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersAmulet extends Item
{
	public ItemTuskerersAmulet(String name)
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		ItemStack stack = player.getHeldItem(hand);
		
		if (stack != null && world.isRemote)
		{
			if (player.getMaxHealth() < 24.0F)
			{
				if (player.getHealth() < 4.0F)
				{
					dinoEntity.sendChatMessage(TextFormatting.GRAY + "You are cursed by the devils and blessed by the fairies...");
					player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 0.5F);
					player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
					player.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 2.0F);
					player.playSound(DinocraftSoundEvents.CHARM, 10.0F, 1.0F);
				}
				else
				{
					dinoEntity.sendChatMessage(TextFormatting.RED + "You must be weak!");
                }
            }
            else
            {
            	dinoEntity.sendChatMessage(TextFormatting.RED + "You have achieved max health!");
            }
		}
			
		if (stack != null && !world.isRemote && player.getHealth() < 4.0F && player.getMaxHealth() < 24.0F)
		{	
			stack.shrink(1);
			player.addExhaustion(50.0F);
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000, 4, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 1, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600, 0, false, false));
		    player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 580, 0, false, false));
		    player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 1, false, false));
		    dinoEntity.addMaxHealth(2.0F);
	    	/*
			if (!playerIn.hasAchievement(DinocraftAchievements.THE_MYTH))
			{
				playerIn.addStat(DinocraftAchievements.THE_MYTH);
			}
			*/
			Random rand = world.rand;

			for (int i = 0; i < 20; ++i)
			{
				dinoEntity.spawnParticle(EnumParticleTypes.END_ROD, false, player.posX + (rand.nextDouble() * 2D) - 1D, player.posY + (rand.nextDouble() * 0.25D), 
						player.posZ + (rand.nextDouble() * 2D) - 1D, 0.0D, rand.nextDouble() * 0.03D, 0.0D, 1);
			}
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
		tooltip.add(TextFormatting.DARK_RED + Utils.getLang().localize("tuskerers_amulet.tooltip"));

        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Increases Max Health when the player is...");
        	tooltip.add(TextFormatting.GRAY + "  -Very weak");
        	tooltip.add(TextFormatting.GRAY + "  -Healthy");
        	tooltip.add(TextFormatting.GRAY + "Be..... ware..");
        }
        else
        {
        	tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
        }
	}
 }