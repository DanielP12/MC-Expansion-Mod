package dinocraft.item.tuskers;

import dinocraft.entity.EntityMesmerizingBolt;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.item.ItemSpellBook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTuskersHopesAndWishes extends ItemSpellBook
{
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (!world.isRemote && living instanceof EntityPlayer)
		{
			EntityMesmerizingBolt bolt = new EntityMesmerizingBolt(world, living);
			bolt.shoot(living, living.rotationPitch, living.rotationYaw, 0.0F, 0.75F, 0.0F);
			world.spawnEntity(bolt);
			BlockPos pos = living.getPosition();
			world.playSound(null, pos, DinocraftSoundEvents.SPELL, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() * 0.5F + 0.25F);
			world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			((EntityPlayer) living).getCooldownTracker().setCooldown(this, 200);
			stack.damageItem(1, living);
		}

		return super.onItemUseFinish(stack, world, living);
	}
}
