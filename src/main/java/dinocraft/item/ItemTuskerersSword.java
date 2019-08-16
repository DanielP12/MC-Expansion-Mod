package dinocraft.item;

import java.util.List;
import java.util.Random;

import org.jline.utils.Log;

import dinocraft.Reference;
import dinocraft.init.DinocraftItems;
import dinocraft.util.DinocraftServer;
import dinocraft.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersSword extends ItemSword
{
	public ItemTuskerersSword(ToolMaterial material, String unlocalizedName)
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/** Tuskerer's Healing */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (!target.world.isRemote && target.world.rand.nextInt(1000) < 900)
		{
		    EntityItem heart = new EntityItem(target.world, target.posX, target.posY, target.posZ, new ItemStack(DinocraftItems.HEART, 1));
			target.world.spawnEntity(heart);
		}
		
		return super.hitEntity(stack, target, attacker);
	}
	
	public static void trySpawnItems(ItemStack stack, Entity target, EntityLivingBase attacker)
	{
		
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(TextFormatting.GRAY + Utils.getLang().localize("tuskerers_sword.tooltip"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	/* Event fired when an item is crafted */
	/*
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event)
	{
		if (event.crafting.getItem() == DinocraftTools.TUSKERERS_SWORD)
		{
			EntityPlayer playerIn = event.player;
			
			if (!playerIn.hasAchievement(DinocraftAchievements.OUT_OF_PURE_TUSK))
			{
				playerIn.addStat(DinocraftAchievements.OUT_OF_PURE_TUSK);
			}
			
		}
	}
	*/
}

