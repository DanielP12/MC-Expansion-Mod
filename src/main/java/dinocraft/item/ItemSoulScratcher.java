package dinocraft.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import dinocraft.Reference;
import dinocraft.init.DinocraftItems;
import dinocraft.util.DinocraftServer;
import dinocraft.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulScratcher extends ItemTool
{
	private static final Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet(new Block[] {});
	
	public ItemSoulScratcher(String unlocalizedName) 
	{
		super(EnumHelper.addToolMaterial(Reference.MODID + ":soul_scratcher", 0, 65, 0, 0, 0), EFFECTIVE_BLOCKS);
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxDamage(64);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (attacker.isSneaking() && target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
		{
			for (int i = 0; i < 10; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.SMOKE_LARGE, target.world, target.posX, target.posY, target.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, 1); //MODIFY
			}
				
			target.dropItem(DinocraftItems.CHUNKY_FLESH, 1);
			target.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, 1F, 0.5F);
		}
		
		return true;
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.DARK_GREEN + Utils.getLang().localize("soul_scratcher.tooltip"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
