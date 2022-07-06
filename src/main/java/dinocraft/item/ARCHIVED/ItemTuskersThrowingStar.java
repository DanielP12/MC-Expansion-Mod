package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import java.util.List;
//
//import dinocraft.entity.EntityTuskersThrowingStar;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemTuskersThrowingStar extends Item
//{
//	public ItemTuskersThrowingStar()
//	{
//		this.setMaxStackSize(1);
//		this.setMaxDamage(876);
//	}
//
//	@Override
//	public int getMaxItemUseDuration(ItemStack stack)
//	{
//		return 16;
//	}
//
//	@Override
//	public EnumAction getItemUseAction(ItemStack stack)
//	{
//		return EnumAction.BOW;
//	}
//
//	@Override
//	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
//	{
//		return oldStack != null;
//	}
//
//	@Override
//	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
//	{
//		if (living instanceof EntityPlayer)
//		{
//			stack.damageItem(1, living);
//
//			if (!world.isRemote)
//			{
//				EntityTuskersThrowingStar star = new EntityTuskersThrowingStar(world, living, stack, 0.005F, 1.0F);
//				star.shoot(living, living.rotationPitch, living.rotationYaw, 0.0F, 1.0F, 0.0F);
//				world.spawnEntity(star);
//				world.playSound(null, living.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
//
//				if (!((EntityPlayer) living).isCreative())
//				{
//					stack.shrink(1);
//				}
//			}
//
//			living.swingArm(living.getActiveHand());
//		}
//
//		return super.onItemUseFinish(stack, world, living);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		player.setActiveHand(hand);
//		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
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
//			tooltip.add(TextFormatting.GRAY + "Chance to drop an absorption heart when killing");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}