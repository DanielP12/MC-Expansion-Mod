package dinocraft.item.splicents;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.item.ItemSpellBook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChainedThundrivel extends ItemSpellBook
{
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (!world.isRemote)
		{
			DinocraftEntity.getEntity(living).getActionsModule().setElectrified(10);
			
			if (living instanceof EntityPlayer)
			{
				((EntityPlayer) living).getCooldownTracker().setCooldown(this, 500);
			}

			stack.damageItem(1, living);
		}

		return super.onItemUseFinish(stack, world, living);
	}
}