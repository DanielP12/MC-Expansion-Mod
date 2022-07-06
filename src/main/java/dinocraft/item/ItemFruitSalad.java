package dinocraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFruitSalad extends ItemFood
{
	public ItemFruitSalad()
	{
		super(8, 13.0F, false);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		super.onItemUseFinish(stack, world, entityLiving);
		return new ItemStack(Items.BOWL);
	}
}
