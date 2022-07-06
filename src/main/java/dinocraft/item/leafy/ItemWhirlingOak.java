package dinocraft.item.leafy;

import dinocraft.entity.EntityLeafBolt;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.item.ItemSpellBook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemWhirlingOak extends ItemSpellBook
{
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 35;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (!world.isRemote && living instanceof EntityPlayer)
		{
			EntityLeafBolt bolt = new EntityLeafBolt(world, living);
			bolt.shoot(living, living.rotationPitch, living.rotationYaw, 0.0F, 0.75F, 0.0F);
			world.spawnEntity(bolt);
			world.playSound(null, living.getPosition(), DinocraftSoundEvents.HARP, SoundCategory.NEUTRAL, 0.3F, 0.5F);
			world.playSound(null, living.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			world.playSound(null, living.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			stack.damageItem(1, living);
		}

		return super.onItemUseFinish(stack, world, living);
	}
}