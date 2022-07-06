package dinocraft.entity;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nonnull;

import dinocraft.init.DinocraftItems;
import dinocraft.util.VectorHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLeaferang extends EntityThrowable
{
	private static final DataParameter<Boolean> RETURNING = EntityDataManager.createKey(EntityLeaferang.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> FIRE = EntityDataManager.createKey(EntityLeaferang.class, DataSerializers.BOOLEAN);
	/** The <code>ItemStack</code> corresponding to this Leaferang entity */
	private ItemStack stack = new ItemStack(DinocraftItems.LEAFERANG);
	/** The targets this Leaferang entity hits before returning to its thrower */
	private final Entity[] targets = new Entity[3];
	private int numEntitiesHit;
	/** The amount of ticks that passed since this entity hit a target */
	private int ticksSinceLastHit;
	/** The amount of knockback this Leaferang entity inflicts on a target */
	private int knockbackStrength;
	private float velocity;
	private EnumHand returnHand;
	private WeakReference<EntityLivingBase> owner;
	
	public EntityLeaferang(World world)
	{
		super(world);
	}
	
	public EntityLeaferang(World world, EntityLivingBase entity, ItemStack stack, EnumHand returnHand, float velocity)
	{
		super(world, entity);
		ItemStack itemstack = stack.copy();
		itemstack.setCount(1);
		this.stack = itemstack;

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
		{
			this.dataManager.set(FIRE, true);
		}
		
		int knockbackStrength = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
		
		if (knockbackStrength > 0)
		{
			this.knockbackStrength = knockbackStrength;
		}
		
		this.velocity = velocity;
		this.returnHand = returnHand;
		this.owner = new WeakReference<>(entity);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(FIRE, false);
		this.dataManager.register(RETURNING, false);
	}
	
	@Override
	public boolean isImmuneToExplosions()
	{
		return true;
	}
	
	@Override
	public boolean hasNoGravity()
	{
		return true;
	}
	
	@Override
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		this.onEntityUpdate();

		if (this.throwableShake > 0)
		{
			--this.throwableShake;
		}

		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null)
		{
			vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), entity1 -> entity1 != this.thrower);
		double d0 = 0.0D;

		for (Entity entity1 : list)
		{
			if (entity1.isEntityAlive() && entity1.canBeCollidedWith())
			{
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
				RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

				if (raytraceresult1 != null)
				{
					double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

					if (d1 < d0 || d0 == 0.0D)
					{
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (entity != null)
		{
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null)
		{
			if (raytraceresult.typeOfHit == Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
			{
				this.setPortal(raytraceresult.getBlockPos());
			}
			else if (!ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onImpact(raytraceresult);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

		for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{

		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

		if (this.isInWater())
		{
			for (int j = 0; j < 4; ++j)
			{
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			}

			this.motionX *= 0.925F;
			this.motionY = (this.motionY - 0.0075F) * 0.925F;
			this.motionZ *= 0.925F;
		}
		
		if (!this.world.isRemote)
		{
			if (this.owner == null || !this.owner.get().isEntityAlive())
			{
				this.returnToHand();
				return;
			}

			if (this.ticksExisted >= 300 || this.ticksSinceLastHit >= 80)
			{
				this.returnToThrower();
			}
			
			if (this.targets[0] == null)
			{
				if (this.ticksExisted == 60)
				{
					this.returnToThrower();
				}
			}
			else
			{
				this.ticksSinceLastHit++;
			}
			
			if (this.isReturning())
			{
				EntityLivingBase owner = this.owner.get();
				VectorHelper motion = VectorHelper.fromEntityCenter(owner).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				float velocity = this.getVelocity();
				this.motionX = motion.x * velocity;
				this.motionY = motion.y * velocity;
				this.motionZ = motion.z * velocity;
				this.markVelocityChanged();

				if (this.getDistance(owner) < 1.5D)
				{
					this.returnToHand();
					return;
				}
			}
		}
		else
		{
			if (this.isOnFire() || this.isBurning())
			{
				this.world.spawnParticle(EnumParticleTypes.FLAME, true, this.lastTickPosX + 0.1 * (Math.random() - 0.5), this.lastTickPosY + 0.1 * (Math.random() - 0.5), this.lastTickPosZ + 0.1 * (Math.random() - 0.5), 0.1 * (Math.random() - 0.5), 0.1 * (Math.random() - 0.5), 0.1 * (Math.random() - 0.5));
			}
			else
			{
				for (int i = 0; i < 3; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, true, this.lastTickPosX, this.lastTickPosY + this.height / 2.0F, this.lastTickPosZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25 - 0.1, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.LEAF));
				}
			}
		}

		if (this.ticksExisted % 4 == 0)
		{
			if (this.isOnFire() || this.isBurning())
			{
				this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.15F, this.rand.nextFloat() * 0.75F, true);
			}
			else
			{
				this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.20F, 0.25F, true);
				this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.15F, this.rand.nextFloat() * 0.75F, true);
			}
		}

		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 20; i++)
			{
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.25, Math.random() * 0.01, Math.random() * 0.25, Item.getIdFromItem(DinocraftItems.LEAFERANG));
			}
		}
	}
	
	/**
	 * Returns the initial <code>ItemStack</code> corresponding to this Leaferang to its thrower
	 * and destroys this Leaferang next tick
	 */
	public void returnToHand()
	{
		if (this.owner != null)
		{
			EntityLivingBase entity = this.owner.get();
			
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;

				if (!player.capabilities.isCreativeMode)
				{
					boolean alive = player.isEntityAlive();

					if (alive && player.getHeldItem(this.returnHand).isEmpty())
					{
						player.setHeldItem(this.returnHand, this.stack);
					}
					else if (!alive || !player.inventory.addItemStackToInventory(this.stack))
					{
						EntityItem leaferang = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.stack);
						leaferang.setOwner(player.getName());
						this.world.spawnEntity(leaferang);
					}
				}
			}
			else
			{
				ItemStack mainhandStack = entity.getHeldItemMainhand();
				ItemStack offhandStack = entity.getHeldItemOffhand();

				if (!mainhandStack.isEmpty())
				{
					if (!offhandStack.isEmpty())
					{
						entity.dropItem(offhandStack.getItem(), offhandStack.getCount());
					}
					
					entity.setHeldItem(EnumHand.OFF_HAND, this.stack);
				}
				else
				{
					entity.setHeldItem(EnumHand.MAIN_HAND, this.stack);
				}
			}
		}
		else
		{
			this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, this.stack));
		}

		this.setDead();
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
				this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_PLAYER_SMALL_FALL, SoundCategory.NEUTRAL, 1.0F, 0.25F);
				
				if (!this.world.isRemote)
				{
					this.world.setEntityState(this, (byte) 3);
					this.returnToThrower();
				}
				
				return;
			case ENTITY:
				if (!this.world.isRemote && this.owner != null && result.entityHit != null && result.entityHit instanceof EntityLivingBase && !this.wasPreviousTarget(result.entityHit))
				{
					EntityLivingBase owner = this.owner.get();

					if (result.entityHit != owner)
					{
						this.ticksSinceLastHit = 0;
						this.targets[0] = result.entityHit;
						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, this.stack);
						float baseDamage = 7.5F;

						if (j > 0)
						{
							baseDamage += j - 0.5D;
						}

						result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), (float) (baseDamage * Math.pow(0.8F, this.numEntitiesHit)));

						if (this.isOnFire() || this.isBurning())
						{
							result.entityHit.setFire(5);
						}

						if (this.knockbackStrength > 0)
						{
							float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
							
							if (f1 > 0.0F)
							{
								result.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f1, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f1);
							}
						}

						((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
						this.world.playSound(null, result.entityHit.getPosition(), SoundEvents.ENTITY_HOSTILE_BIG_FALL, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
						this.numEntitiesHit++;
						this.stack.damageItem(1, owner);
						
						if (this.stack.isEmpty())
						{
							this.world.setEntityState(this, (byte) 3);
							this.setDead();
							return;
						}

						if (this.numEntitiesHit > 2)
						{
							this.returnToThrower();
						}
						else
						{
							List<Entity> entities = this.world.getEntitiesInAABBexcluding(owner, this.getEntityBoundingBox().grow(20.0F),
									entity -> entity instanceof EntityLivingBase && entity.canBeCollidedWith() && entity != result.entityHit);
							EntityLivingBase nextTarget = null;
							double foundLen = 20.0D;

							for (Entity entity : entities)
							{
								EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
								
								if (!(result.entityHit instanceof EntityPigZombie) && entitylivingbase instanceof EntityPigZombie && !((EntityPigZombie) entitylivingbase).isAngry())
								{
									continue;
								}
								
								if (this.isValidNextTarget(entitylivingbase) && owner.getRidingEntity() != entitylivingbase && entitylivingbase.canEntityBeSeen(this) && (entitylivingbase.getRevengeTarget() == owner || entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)))
								{
									Vec3d vec = new Vec3d(entity.posX - this.posX, entity.getEntityBoundingBox().minY + entity.height / 2.0F - this.posY - this.getEyeHeight(), entity.posZ - this.posZ);
									double len = vec.lengthVector();

									if (len < foundLen)
									{
										nextTarget = entitylivingbase;
										foundLen = len;
									}
								}
							}
							
							this.setNewTarget(nextTarget);
							
							if (nextTarget != null)
							{
								VectorHelper motion = VectorHelper.fromEntityCenter(nextTarget).subtract(VectorHelper.fromEntityCenter(this)).normalize();
								float velocity = this.getVelocity();
								this.motionX = motion.x * velocity;
								this.motionY = motion.y * velocity;
								this.motionZ = motion.z * velocity;
								this.markVelocityChanged();
							}
							else
							{
								this.returnToThrower();
							}
						}
					}
				}
			default:
		}
	}
	
	/**
	 * Sets the entity this Leaferang should target next
	 */
	private void setNewTarget(EntityLivingBase target)
	{
		this.targets[2] = this.targets[1];
		this.targets[1] = this.targets[0];
		this.targets[0] = target;
	}
	
	/**
	 * Returns whether the specified entity is not any of the previous targets and current target this Leaferang had
	 */
	public boolean isValidNextTarget(EntityLivingBase target)
	{
		return this.targets[0] != target && this.targets[1] != target && this.targets[2] != target;
	}
	
	/**
	 * Returns whether the specified entity was a previous target
	 */
	public boolean wasPreviousTarget(Entity target)
	{
		return target == this.targets[1] || target == this.targets[2];
	}
	
	public boolean isOnFire()
	{
		return this.dataManager.get(FIRE);
	}
	
	public float getVelocity()
	{
		return this.velocity;
	}

	/**
	 * Returns whether or not this Leaferang is returning to its owner
	 */
	public boolean isReturning()
	{
		return this.dataManager.get(RETURNING);
	}
	
	/**
	 * Returns this Leaferang to its thrower. If the thrower is non-existent, destroys this Leaferang next tick
	 */
	protected void returnToThrower()
	{
		this.dataManager.set(RETURNING, true);
	}

	public int getKnockbackStrength()
	{
		return this.knockbackStrength;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		if (!this.stack.isEmpty())
		{
			compound.setTag("stack", this.stack.writeToNBT(new NBTTagCompound()));
		}

		compound.setBoolean("fire", this.dataManager.get(FIRE));
		compound.setBoolean("isOffhand", this.returnHand == EnumHand.OFF_HAND);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("stack"))
		{
			this.stack = new ItemStack(compound.getCompoundTag("stack"));
		}

		this.dataManager.set(FIRE, compound.getBoolean("fire"));
		this.returnHand = compound.getBoolean("isOffhand") ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}
}