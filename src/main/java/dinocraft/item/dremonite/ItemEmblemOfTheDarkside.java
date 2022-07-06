package dinocraft.item.dremonite;
//package dinocraft.item.dreaded;
//
//import java.util.List;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLiving;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.init.MobEffects;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemEmblemOfTheDarkside extends Item
//{
//	public ItemEmblemOfTheDarkside()
//	{
//		this.setMaxStackSize(1);
//		this.setMaxDamage(742);
//		MinecraftForge.EVENT_BUS.register(this);
//	}
//
//	@Override
//	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
//	{
//		if (!world.isRemote && entity instanceof EntityLivingBase)
//		{
//			EntityLivingBase living = (EntityLivingBase) entity;
//
//			if (isSelected || living.getHeldItemOffhand().getItem() == this)
//			{
//				if (!living.isPotionActive(MobEffects.WITHER))
//				{
//					living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0, false, true));
//				}
//
//				if (!living.isPotionActive(MobEffects.NAUSEA) || living.isPotionActive(MobEffects.NAUSEA) && living.getActivePotionEffect(MobEffects.NAUSEA).getDuration() <= 30)
//				{
//					living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160, 0, false, true));
//				}
//
//				if (!living.isPotionActive(MobEffects.BLINDNESS))
//				{
//					living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0, false, true));
//				}
//			}
//		}
//
//		super.onUpdate(stack, world, entity, itemSlot, isSelected);
//	}
//
//	@SubscribeEvent
//	public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
//	{
//		EntityLivingBase target = event.getTarget();
//		EntityLivingBase entity = event.getEntityLiving();
//
//		if (target != null && entity instanceof EntityLiving && (target.getHeldItemMainhand().getItem() == this || target.getHeldItemOffhand().getItem() == this))
//		{
//			((EntityLiving) entity).setAttackTarget(null);
//		}
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
//	{
//		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
//
//		if (GameSettings.isKeyDown(shift))
//		{
//			tooltip.add(TextFormatting.GRAY + "When held:");
//			tooltip.add(TextFormatting.GRAY + " The player becomes invisible to hostile mobs");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}
