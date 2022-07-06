package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import java.util.List;
//
//import dinocraft.Dinocraft;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.init.MobEffects;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemChlorophyteSword extends ItemSword
//{
//	public ItemChlorophyteSword(ToolMaterial material, String name)
//	{
//		super(material);
//		this.setUnlocalizedName(name);
//		this.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
//	}
//
//	@Override
//	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
//	{
//		if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
//		{
//			target.addPotionEffect(new PotionEffect(MobEffects.POISON, 70, 1, false, true));
//		}
//
//		return super.hitEntity(stack, target, attacker);
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
//	{
//		tooltip.add(TextFormatting.DARK_GREEN + Dinocraft.lang().localize("chlorophyte_sword.tooltip"));
//
//		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
//
//		if (GameSettings.isKeyDown(shift))
//		{
//			int maxdamage = stack.getMaxDamage();
//			tooltip.add(TextFormatting.GRAY + "Poisons target");
//			tooltip.add(TextFormatting.GRAY + "Durability: " + (maxdamage - stack.getItemDamage()) +  " / " + maxdamage);
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}