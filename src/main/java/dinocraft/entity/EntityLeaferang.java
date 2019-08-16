package dinocraft.entity;

import javax.annotation.Nonnull;

import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
import dinocraft.util.VectorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLeaferang extends EntityThrowable
{
	private static final DataParameter<Integer> BOUNCES = EntityDataManager.createKey(EntityLeaferang.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> FLARE = EntityDataManager.createKey(EntityLeaferang.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> RETURN_TO = EntityDataManager.createKey(EntityLeaferang.class, DataSerializers.VARINT);
	private static final int MAX_BOUNCES = 16;
	private boolean bounced = false;
	private ItemStack stack = ItemStack.EMPTY;

	public EntityLeaferang(World world) 
	{
		super(world);
	}

	public EntityLeaferang(World world, EntityLivingBase e, ItemStack stack)
	{
		super(world, e);
		this.stack = stack.copy();
	}

	@Override
	protected void entityInit() 
	{
		super.entityInit();
		dataManager.register(BOUNCES, 0);
		dataManager.register(FLARE, false);
		dataManager.register(RETURN_TO, -1);
	}

	@Override
	public boolean isImmuneToExplosions()
	{
		return true;
	}

	int tick = 0;

	@Override
	public void onUpdate() 
	{
		// Standard motion
		double mx = motionX;
		double my = motionY;
		double mz = motionZ;

		super.onUpdate();

		if (!bounced)
		{
			// Reset the drag applied by super
			motionX = mx;
			motionY = my;
			motionZ = mz;
		}

		bounced = false;

		// Returning motion
		if (isReturning())
		{
			Entity thrower = getThrower();
			
			if (thrower != null) 
			{
				VectorHelper motion = VectorHelper.fromEntityCenter(thrower).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				motionX = motion.x;
				motionY = motion.y;
				motionZ = motion.z;
			}
		}

		// Client FX
		if (world.isRemote)
		{
			if (isFire() || isBurning()) 
			{
				double r = 0.1;
				double m = 0.1;
			
				for (int i = 0; i < 3; i++)
				{
					world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.LEAF));
					world.spawnParticle(EnumParticleTypes.FLAME, posX + r * (Math.random() - 0.5), posY + r * (Math.random() - 0.5), posZ + r * (Math.random() - 0.5), m * (Math.random() - 0.5), m * (Math.random() - 0.5), m * (Math.random() - 0.5));
				}
			}
			else
			for (int i = 0; i < 4; ++i)
			{
				world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.LEAF));
			}
			
			++tick;
		}
		
		if (tick % 3 == 0)
		{
			tick = 0;
			world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.25F, 0.25F, false);
		}
		
		// Server state control
		if (!world.isRemote && (getTimesBounced() >= MAX_BOUNCES || ticksExisted > 60))
		{
			EntityLivingBase thrower = getThrower();
			
			if (thrower == null) dropAndKill();
			else 
			{
				setEntityToReturnTo(thrower.getEntityId());
				
				if (getDistanceSq(thrower) < 2) dropAndKill();
			}
		}
	}

	private void dropAndKill() 
	{
		ItemStack stack = getItemStack();
		EntityItem item = new EntityItem(world, posX, posY, posZ, stack);
		world.spawnEntity(item);
		setDead();
	}

	private ItemStack getItemStack() 
	{
		return !stack.isEmpty() ? stack.copy() : new ItemStack(DinocraftTools.LEAFERANG, 1, isFire() ? 1 : 0);
	}

	@Override
	protected void onImpact(@Nonnull RayTraceResult pos) 
	{
		if (isReturning()) return;

		switch (pos.typeOfHit) 
		{
			case BLOCK: 
			{
				Block block = world.getBlockState(pos.getBlockPos()).getBlock();
				
				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine) return;

				int bounces = getTimesBounced();
				
				if (bounces < MAX_BOUNCES)
				{
					for (int i = 0; i < 15; ++i)
					{
						world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Block.getIdFromBlock(block));
					}
					
					world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_SMALL_FALL, SoundCategory.NEUTRAL, 1.0F, 0.25F, false);

					VectorHelper currentMovementVec = new VectorHelper(motionX, motionY, motionZ);
					EnumFacing dir = pos.sideHit;
					VectorHelper normalVector = new VectorHelper(dir.getFrontOffsetX(), dir.getFrontOffsetY(), dir.getFrontOffsetZ()).normalize();
					VectorHelper movementVec = normalVector.multiply(-2 * currentMovementVec.dotProduct(normalVector)).add(currentMovementVec);

					motionX = movementVec.x;
					motionY = movementVec.y;
					motionZ = movementVec.z;
					bounced = true;

					if (!world.isRemote) setTimesBounced(getTimesBounced() + 1);
				}

				break;
			}
			
			case ENTITY: 
			{
				if (!world.isRemote && pos.entityHit != null && pos.entityHit instanceof EntityLivingBase && pos.entityHit != getThrower())
				{
					EntityLivingBase thrower = getThrower();
					pos.entityHit.attackEntityFrom(thrower != null ? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower) : DamageSource.causeMobDamage(thrower) : DamageSource.GENERIC, (isFire() || isBurning()) ? 7 : 6);
				
					if (isFire() || isBurning()) pos.entityHit.setFire(10);
					else if (world.rand.nextInt(3) == 0) ((EntityLivingBase) pos.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
										
					if (thrower == null) dropAndKill();
					else 
					{
						setTimesBounced(16);
						setEntityToReturnTo(thrower.getEntityId());

						if (getDistanceSq(thrower) < 2) dropAndKill();
					}
				}

				break;
			}
			
			default: break;
		}
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0.0F;
	}

	private int getTimesBounced() 
	{
		return dataManager.get(BOUNCES);
	}

	private void setTimesBounced(int times) 
	{
		dataManager.set(BOUNCES, times);
	}

	public boolean isFire() 
	{
		return dataManager.get(FLARE);
	}

	public void setFire(boolean fire) 
	{
		dataManager.set(FLARE, fire);
	}

	private boolean isReturning()
	{
		return getEntityToReturnTo() > -1;
	}

	private int getEntityToReturnTo()
	{
		return dataManager.get(RETURN_TO);
	}

	private void setEntityToReturnTo(int entityID) 
	{
		dataManager.set(RETURN_TO, entityID);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		
		if(!stack.isEmpty()) compound.setTag("fly_stack", stack.writeToNBT(new NBTTagCompound()));
		
		compound.setBoolean("flare", isFire());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) 
	{
		super.readEntityFromNBT(compound);
		
		if(compound.hasKey("fly_stack")) stack = new ItemStack(compound.getCompoundTag("fly_stack"));
		
		setFire(compound.getBoolean("flare"));
	}

}