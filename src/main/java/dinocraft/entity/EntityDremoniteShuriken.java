package dinocraft.entity;

import java.util.List;

import javax.annotation.Nonnull;

import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityDremoniteShuriken extends EntityThrowable
{
	private static final DataParameter<Boolean> RETURNING = EntityDataManager.createKey(EntityDremoniteShuriken.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityDremoniteShuriken.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> VELOCITY = EntityDataManager.createKey(EntityDremoniteShuriken.class, DataSerializers.FLOAT);
	/** The <code>ItemStack</code> corresponding to this Dreaded Shuriken entity */
	private ItemStack stack = new ItemStack(DinocraftItems.DREMONITE_SHURIKEN);
	private String throwerName;
	
	public EntityDremoniteShuriken(World world)
	{
		super(world);
	}
	
	public EntityDremoniteShuriken(World world, EntityLivingBase entity, ItemStack stack, float gravity, float velocity)
	{
		super(world, entity);
		ItemStack itemstack = stack.copy();
		itemstack.setCount(1);
		this.stack = itemstack;
		this.dataManager.set(GRAVITY, gravity);
		this.dataManager.set(VELOCITY, velocity);
		this.throwerName = this.thrower.getName();
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(RETURNING, false);
		this.dataManager.register(GRAVITY, 0.0F);
		this.dataManager.register(VELOCITY, 0.0F);
	}
	
	@Override
	public boolean isImmuneToExplosions()
	{
		return true;
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}

	public float getVelocity()
	{
		return this.dataManager.get(VELOCITY);
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
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0F), entity1 -> entity1 != this.thrower);
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
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
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
		float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

		for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
			if (this.isReturning())
			{
				if (this.thrower == null || !this.thrower.isEntityAlive())
				{
					this.drop();
					this.setDead();
					return;
				}
				
				VectorHelper motion = VectorHelper.fromEntityCenter(this.thrower).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				float velocity = this.getVelocity();
				this.motionX = motion.x * 3F / 4F * velocity;
				this.motionY = motion.y * 3F / 4F * velocity;
				this.motionZ = motion.z * 3F / 4F * velocity;
				this.markVelocityChanged();

				if (this.getDistance(this.thrower) < 1.5D)
				{
					this.returnToHand();
					return;
				}
			}
		}
		else
		{
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
					this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
					this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D);
		}

		if (this.ticksExisted % 3 == 0)
		{
			this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		}

		this.motionY -= this.getGravityVelocity();
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	/**
	 * Returns the initial <code>ItemStack</code> corresponding to this Dreaded Shuriken to its thrower
	 * and destroys this Dreaded Shuriken next tick
	 */
	private void returnToHand()
	{
		if (this.thrower != null)
		{
			if (this.thrower instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) this.thrower;

				if (!player.capabilities.isCreativeMode && (!player.isEntityAlive() || !player.inventory.addItemStackToInventory(this.stack)))
				{
					this.drop();
				}
			}
			else
			{
				ItemStack mainhandStack = this.thrower.getHeldItemMainhand();
				ItemStack offhandStack = this.thrower.getHeldItemOffhand();

				if (!mainhandStack.isEmpty())
				{
					if (!offhandStack.isEmpty())
					{
						this.thrower.dropItem(offhandStack.getItem(), offhandStack.getCount());
					}
					
					this.thrower.setHeldItem(EnumHand.OFF_HAND, this.stack);
				}
				else
				{
					this.thrower.setHeldItem(EnumHand.MAIN_HAND, this.stack);
				}
			}
		}
		else
		{
			EntityItem item = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.stack);
			item.setGlowing(true);
			item.setNoDespawn();
			this.world.spawnEntity(item);
		}
		
		this.setDead();
	}
	
	private void drop()
	{
		EntityItem item = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.stack);
		item.setOwner(this.throwerName);
		item.setGlowing(true);
		item.setNoDespawn();
		this.world.spawnEntity(item);
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
				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();

				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine)
				{
					return;
				}
				
				this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				
				if (!this.world.isRemote)
				{
					if (!(this.thrower instanceof EntityPlayer) || this.thrower instanceof EntityPlayer && !((EntityPlayer) this.thrower).isCreative())
					{
						this.drop();
					}
					
					this.setDead();
				}
				
				return;
			case ENTITY:
				if (!this.world.isRemote && result.entityHit instanceof EntityLivingBase)
				{
					result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), 4.0F);
					((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0));
					
					if (this.isBurning())
					{
						result.entityHit.setFire(5);
					}

					result.entityHit.hurtResistantTime = 0;
					this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
					
					if (this.thrower == null || !this.thrower.isEntityAlive())
					{
						this.drop();
						this.setDead();
					}
					else
					{
						this.returnToThrower();
					}
				}
				
				return;
			default:
		}
	}

	/**
	 * Returns whether or not this Dreaded Shuriken is returning to its thrower
	 */
	public boolean isReturning()
	{
		return this.dataManager.get(RETURNING);
	}
	
	/**
	 * Returns this Dreaded Shuriken to its thrower. If the thrower is non-existent, destroys this Dreaded Shuriken next tick
	 */
	protected void returnToThrower()
	{
		this.dataManager.set(RETURNING, true);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		if (!this.stack.isEmpty())
		{
			compound.setTag("stack", this.stack.writeToNBT(new NBTTagCompound()));
		}
		
		compound.setBoolean("returning", this.dataManager.get(RETURNING));
		compound.setFloat("velocity", this.dataManager.get(VELOCITY));
		compound.setFloat("gravity", this.dataManager.get(GRAVITY));
		compound.setString("throwerName", this.thrower.getName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("stack"))
		{
			this.stack = new ItemStack(compound.getCompoundTag("stack"));
		}
		
		this.dataManager.set(RETURNING, compound.getBoolean("returning"));
		this.dataManager.set(VELOCITY, compound.getFloat("velocity"));
		this.dataManager.set(GRAVITY, compound.getFloat("gravity"));
		this.throwerName = compound.getString("throwerName");
	}
}