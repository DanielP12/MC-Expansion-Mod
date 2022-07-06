package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import dinocraft.item.DinocraftWeapon;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.SoundCategory;
//import net.minecraftforge.common.IRarity;
//
//public class ItemFlameScythe extends DinocraftWeapon
//{
//	public ItemFlameScythe(ToolMaterial material)
//	{
//		super(material, 1.6F, 4.0D);
//	}
//
//	@Override
//	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
//	{
//		if (!target.world.isRemote)
//		{
//			target.setFire(10);
//			attacker.world.playSound(null, attacker.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 1.0F, attacker.world.rand.nextFloat() * 0.5F + 0.5F);
//		}
//
//		return super.hitEntity(stack, target, attacker);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//}