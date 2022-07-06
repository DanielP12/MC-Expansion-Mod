package dinocraft.item.leafy;

import dinocraft.entity.EntityLeaferang;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentArrowFire;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentMending;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemLeaferang extends Item
{
	public ItemLeaferang()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(1986);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!world.isRemote)
		{
			float j = world.rand.nextFloat() * 0.25F + 0.5F;
			EntityLeaferang leaferang = new EntityLeaferang(world, player, stack, hand, j);
			leaferang.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, j, 1.0F);
			world.spawnEntity(leaferang);
		}

		if (!player.capabilities.isCreativeMode)
		{
			stack.shrink(1);
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		//int id = enchantment.getEnchantmentID(enchantment);
		return enchantment instanceof EnchantmentArrowDamage || enchantment instanceof EnchantmentArrowKnockback
				|| enchantment instanceof EnchantmentArrowFire || enchantment instanceof EnchantmentDurability
				|| enchantment instanceof EnchantmentMending;
		//		return id == Enchantment.getEnchantmentID(Enchantments.POWER) || id == Enchantment.getEnchantmentID(Enchantments.PUNCH)
		//				|| id == Enchantment.getEnchantmentID(Enchantments.FLAME) || id == Enchantment.getEnchantmentID(Enchantments.UNBREAKING)
		//				|| id == Enchantment.getEnchantmentID(Enchantments.MENDING);
	}

	@Override
	public int getItemEnchantability()
	{
		return 4;
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}
