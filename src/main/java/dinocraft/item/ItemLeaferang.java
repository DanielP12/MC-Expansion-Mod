package dinocraft.item;

import java.util.Random;

import javax.annotation.Nonnull;

import dinocraft.Reference;
import dinocraft.entity.EntityLeaferang;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemLeaferang extends Item
{
	public ItemLeaferang(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(2000);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) 
	{
		if (isInCreativeTab(tab)) for (int i = 0; i < 2; i++) list.add(new ItemStack(this, 1, i));
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		stack.damageItem(1, player);

		if (!world.isRemote) 
		{
			Random random = world.rand;
			ItemStack copy = stack.copy();
			copy.setCount(1);
			EntityLeaferang c = new EntityLeaferang(world, player, copy);
			float f = random.nextFloat() + 1.0F;
			c.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f, 1.0F);
			world.spawnEntity(c);
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			stack.shrink(1);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
