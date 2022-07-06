package dinocraft.item.jesters;

import java.util.List;

import dinocraft.init.DinocraftSoundEvents;
import dinocraft.item.ItemSpellBook;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBookOfTricks extends ItemSpellBook
{
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 50;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		if (entity instanceof EntityPlayer && !world.isRemote)
		{
			List<Entity> entities = world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().grow(7.5F),
					entity1 -> entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && entity1.posY <= entity.posY + 1 && entity1.posY >= entity.posY - 1);

			DinocraftServer.spawnJesterParticles(entity.world, 250, 100, entity.posX, entity.posY + 0.25F, entity.posZ, 7.5F, 0.25F, 7.5F);
			
			for (Entity entity1 : entities)
			{
				float f0 = entity1.height / 2.0F;
				DinocraftServer.spawnJesterParticles(entity1.world, 25, 10, entity1.posX, entity1.posY + f0, entity1.posZ, entity1.width, f0, entity1.width);
				double d = entity1.posX - entity.posX;
				double d1;
				
				for (d1 = entity1.posZ - entity.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
				{
					d = (Math.random() - Math.random()) * 0.01D;
				}
				
				float f = MathHelper.sqrt(d * d + d1 * d1);
				entity1.motionX /= 2.0D;
				entity1.motionY /= 2.0D;
				entity1.motionZ /= 2.0D;
				entity1.motionX += d / f * 2.0D;
				entity1.motionY += 1.0D;
				entity1.motionZ += d1 / f * 2.0D;
			}

			world.playSound(null, entity.getPosition(), DinocraftSoundEvents.HARP, SoundCategory.NEUTRAL, 0.3F, 0.5F);
			world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
			((EntityPlayer) entity).getCooldownTracker().setCooldown(this, 150);
			stack.damageItem(1, entity);
		}

		return super.onItemUseFinish(stack, world, entity);
	}
}