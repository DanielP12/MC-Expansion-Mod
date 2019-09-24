package dinocraft.entity;

import javax.annotation.Nonnull;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftEntities;
import dinocraft.init.DinocraftItems;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
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

	public ResourceLocation getTexture()
	{
		return DinocraftEntities.LEAFERANG_TEXTURE;
	}
	
	public EntityLeaferang(World world) 
	{
		super(world);
	}

	public EntityLeaferang(World world, EntityLivingBase entity, ItemStack stack)
	{
		super(world, entity);
		this.stack = stack.copy();
	}

	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(BOUNCES, 0);
		this.dataManager.register(FLARE, false);
		this.dataManager.register(RETURN_TO, -1);
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
		double mx = this.motionX;
		double my = this.motionY;
		double mz = this.motionZ;

		super.onUpdate();

		if (!this.bounced)
		{
			this.motionX = mx;
			this.motionY = my;
			this.motionZ = mz;
		}

		this.bounced = false;

		if (this.isReturning())
		{
			Entity thrower = this.getThrower();
			
			if (thrower != null) 
			{
				VectorHelper motion = VectorHelper.fromEntityCenter(thrower).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				this.motionX = motion.x;
				this.motionY = motion.y;
				this.motionZ = motion.z;
			}
		}

		if (this.world.isRemote)
		{
			if (this.isFire() || this.isBurning()) 
			{
				double r = 0.1;
				double m = 0.1;
			
				for (int i = 0; i < 3; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.FLAME, true, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.LEAF));
					this.world.spawnParticle(EnumParticleTypes.FLAME, true, this.posX + r * (Math.random() - 0.5), this.posY + r * (Math.random() - 0.5), this.posZ + r * (Math.random() - 0.5), m * (Math.random() - 0.5), m * (Math.random() - 0.5), m * (Math.random() - 0.5));
				}
			}
			else
			{
				for (int i = 0; i < 4; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, true, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.LEAF));
				}
			}
			
			++this.tick;
		}
		
		if (this.tick % 3 == 0)
		{
			this.tick = 0;
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.25F, 0.25F, false);
		}
		
		if (!this.world.isRemote && (this.getTimesBounced() >= MAX_BOUNCES || this.ticksExisted > 60))
		{
			EntityLivingBase thrower = this.getThrower();
			
			if (thrower == null)
			{
				this.dropAndKill();
			}
			else 
			{
				this.setEntityToReturnTo(thrower.getEntityId());
				
				if (this.getDistanceSq(thrower) < 2)
				{
					this.dropAndKill();
				}
			}
		}
	}

	private void dropAndKill() 
	{
		if (this.thrower instanceof EntityPlayer)
		{
			if (!((EntityPlayer) this.thrower).isCreative())
			{
				this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItemStack()));
			}
		}
		else if (this.thrower != null)
		{
			if (!this.thrower.getHeldItemMainhand().isEmpty())
			{
				ItemStack stack = this.thrower.getHeldItemMainhand();
				
				if (stack.getItem() != DinocraftItems.LEAFERANG)
				{
					this.thrower.dropItem(stack.getItem(), stack.getCount());
				}
			}
			
			DinocraftEntity.getEntity(this.thrower).setShootingTick(10);
			this.thrower.setHeldItem(EnumHand.MAIN_HAND, this.getItemStack());
		}
		
		this.setDead();
	}

	private ItemStack getItemStack() 
	{
		return !this.stack.isEmpty() ? this.stack.copy() : new ItemStack(DinocraftItems.LEAFERANG, 1, isFire() ? 1 : 0);
	}

	@Override
	protected void onImpact(@Nonnull RayTraceResult result) 
	{
		if (this.isReturning())
		{
			return;
		}

		switch (result.typeOfHit) 
		{
			case BLOCK: 
			{
				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
				
				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine)
				{
					return;
				}

				int bounces = this.getTimesBounced();
				
				if (bounces < MAX_BOUNCES)
				{
					for (int i = 0; i < 15; ++i)
					{
						this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Block.getIdFromBlock(block));
					}
					
					this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_SMALL_FALL, SoundCategory.NEUTRAL, 1.0F, 0.25F, false);

					VectorHelper currentMovementVector = new VectorHelper(this.motionX, this.motionY, this.motionZ);
					EnumFacing direction = result.sideHit;
					VectorHelper normalVector = new VectorHelper(direction.getFrontOffsetX(), direction.getFrontOffsetY(), direction.getFrontOffsetZ()).normalize();
					VectorHelper movementVector = normalVector.multiply(-2 * currentMovementVector.dotProduct(normalVector)).add(currentMovementVector);

					this.motionX = movementVector.x;
					this.motionY = movementVector.y;
					this.motionZ = movementVector.z;
					this.bounced = true;

					if (!this.world.isRemote)
					{
						this.setTimesBounced(this.getTimesBounced() + 1);
					}
				}

				break;
			}
			
			case ENTITY: 
			{
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.getThrower())
				{
					EntityLivingBase thrower = this.getThrower();
					result.entityHit.attackEntityFrom(thrower != null ? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower) : DamageSource.causeMobDamage(thrower) : DamageSource.GENERIC, (this.isFire() || this.isBurning()) ? 7 : 6);
				
					if (this.isFire() || this.isBurning())
					{
						result.entityHit.setFire(10);
					}
					else if (this.world.rand.nextInt(3) == 0)
					{
						((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
					}
										
					if (thrower == null)
					{
						this.dropAndKill();
					}
					else 
					{
						this.setTimesBounced(16);
						this.setEntityToReturnTo(thrower.getEntityId());

						if (this.getDistanceSq(thrower) < 2) this.dropAndKill();
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
		
		if (!this.stack.isEmpty())
		{
			compound.setTag("fly_stack", this.stack.writeToNBT(new NBTTagCompound()));
		}
		
		compound.setBoolean("flare", this.isFire());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) 
	{
		super.readEntityFromNBT(compound);
		
		if (compound.hasKey("fly_stack"))
		{
			this.stack = new ItemStack(compound.getCompoundTag("fly_stack"));
		}
		
		setFire(compound.getBoolean("flare"));
	}
}