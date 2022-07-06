package dinocraft.item.dremonite;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.entity.EntityDreadedEye;
import dinocraft.item.ItemSpellBook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemDreadedEyes extends ItemSpellBook
{
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		if (!world.isRemote && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (!player.getCooldownTracker().hasCooldown(this))
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
				
				if (dinoEntity != null)
				{
					DinocraftEntityActions actions = dinoEntity.getActionsModule();
					EntityLivingBase[] targets = new EntityLivingBase[3];
					double range = 20.0D;
					List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().grow(range),
							entity1 -> entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith()
							&& player.getRidingEntity() != entity1 && ((EntityLivingBase) entity1).canEntityBeSeen(player)
							&& (((EntityLivingBase) entity1).getRevengeTarget() == player || entity1.isCreatureType(EnumCreatureType.MONSTER, false)));
					int size = Math.min(entities.size(), 3), i;

					for (i = 0; i < size; ++i)
					{
						for (Entity entity1 : entities)
						{
							double len = new Vec3d(entity1.posX - player.posX, entity1.getEntityBoundingBox().minY + entity1.height / 2.0F - player.posY - player.getEyeHeight(), entity1.posZ - player.posZ).lengthVector();
							
							if (len < range)
							{
								targets[i] = (EntityLivingBase) entity1;
								range = len;
							}
						}

						entities.remove(targets[i]);
						range = 20.0D;
					}
					
					i = 0;

					while (i < targets.length && targets[i] != null)
					{
						EntityDreadedEye eye = new EntityDreadedEye(world, player, targets[i++]);
						eye.shoot(player, player.rotationPitch - 25 + 50 * world.rand.nextFloat(), player.rotationYaw - 25 + 50 * world.rand.nextFloat(), 0.0F, itemRand.nextFloat() * 0.2F + 0.1F, 3.0F);
						world.spawnEntity(eye);

						if (i == 1)
						{
							stack.damageItem(1, player);
							player.getCooldownTracker().setCooldown(this, 100);
							player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_HUSK_AMBIENT, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat() * 0.5F);
						}
					}
				}
			}
		}

		return super.onItemUseFinish(stack, world, entity);
	}
}
