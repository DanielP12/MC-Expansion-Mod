package dinocraft.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import dinocraft.Dinocraft;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulScratcher extends ItemTool
{
	private static final Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet();
	
	public ItemSoulScratcher()
	{
		super(EnumHelper.addToolMaterial(Dinocraft.MODID + ":soul_scratcher", 0, 65, 0, 0, 0), EFFECTIVE_BLOCKS);
		this.setMaxDamage(65);
	}
	
	//	@Override
	//	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	//	{
	//		if (attacker.isSneaking() && target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
	//		{
	//			if (!target.world.isRemote)
	//			{
	//				for (int i = 0; i < 10; ++i)
	//				{
	//					DinocraftServer.spawnParticle(EnumParticleTypes.SMOKE_LARGE, false, target.world, target.posX, target.posY, target.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, 1);
	//				}
	//			}
	//
	//			stack.damageItem(1, attacker);
	//		}
	//
	//		return true;
	//	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(TextFormatting.DARK_GREEN + Dinocraft.lang().localize("soul_scratcher.tooltip"));
		super.addInformation(stack, world, tooltip, flag);
	}
}