package dinocraft.item.tuskers;

import java.util.List;

import dinocraft.init.DinocraftItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskersSword extends ItemSword
{
	public ItemTuskersSword(ToolMaterial material)
	{
		super(material);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (!target.world.isRemote && itemRand.nextInt(3) < 1)
		{
			EntityItem heart = new EntityItem(target.world, target.posX, target.posY, target.posZ, new ItemStack(DinocraftItems.HEART, 1));
			
			if (!(attacker instanceof EntityPlayer))
			{
				heart.setInfinitePickupDelay();
			}
			
			String id = attacker.getUniqueID().toString();
			heart.setOwner(id);
			heart.addTag(id);
			target.world.spawnEntity(heart);
		}
		
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;

		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "Chance to drop a heart when attacking");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}