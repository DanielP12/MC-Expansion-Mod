package dinocraft.item;

import javax.annotation.Nonnull;

import dinocraft.Reference;
import dinocraft.entity.EntityLeaferang;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemLeaferang extends Item
{
	public ItemLeaferang(String name)
	{
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(2000);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		stack.damageItem(1, player);

		if (!world.isRemote) 
		{
			ItemStack copy = stack.copy();
			copy.setCount(1);
			EntityLeaferang leaferang = new EntityLeaferang(world, player, copy);
			leaferang.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, world.rand.nextFloat() + 1.0F, 1.0F);
			world.spawnEntity(leaferang);
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			stack.shrink(1);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
