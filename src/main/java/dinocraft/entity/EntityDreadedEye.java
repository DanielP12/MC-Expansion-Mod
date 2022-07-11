package dinocraft.entity;

import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.VectorHelper;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDreadedEye extends EntityThrowable
{
	private static final DataParameter<Boolean> RETURNING = EntityDataManager.createKey(EntityDreadedEye.class, DataSerializers.BOOLEAN);
	private EntityLivingBase target;
	private int startTargetTick;

	public EntityDreadedEye(World world)
	{
		super(world);
	}
	
	public EntityDreadedEye(World world, EntityLivingBase shooter, EntityLivingBase target)
	{
		super(world, shooter);
		this.target = target;
		this.startTargetTick = this.rand.nextInt(11) + 10;
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(RETURNING, false);
	}
	
	@Override
	public boolean hasNoGravity()
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			this.world.playSound(this.posX, this.posY, this.posZ, DinocraftSoundEvents.SPLAT, SoundCategory.NEUTRAL, 0.75F, this.rand.nextFloat() * 0.5F + 0.5F, false);
			
			for (int i = 0; i < 30; ++i)
			{
				this.world.spawnParticle(this.rand.nextBoolean() ? EnumParticleTypes.SMOKE_LARGE : EnumParticleTypes.SMOKE_NORMAL, true,
						this.posX + Math.random() * this.width * 5.0F - 2.5F * this.width,
						this.posY + Math.random() * this.height,
						this.posZ + Math.random() * this.width * 5.0F - 2.5F * this.width,
						this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.1D);
				this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, this.posX, this.posY, this.posZ,
						Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
			}
		}
	}
	
	//	private void spawnParticlesAround(Entity entity)
	//	{
	//		for (int i = 0; i < 10; ++i)
	//		{
	//			DinocraftServer.spawnParticle(EnumParticleTypes.SPELL_MOB, true, entity, entity.width,
	//					entity.height / 2.0F, entity.width, 150, this.rand.nextInt(51) + 150, 100, 0);
	//			//			DinocraftServer.spawnParticle(this.rand.nextBoolean() ? EnumParticleTypes.SMOKE_LARGE : EnumParticleTypes.SMOKE_NORMAL,
	//			//					true, entity, entity.width, entity.height / 2.0F, entity.width, this.rand.nextGaussian() * 0.0025D,
	//			//					this.rand.nextGaussian() * 0.0025D, this.rand.nextGaussian() * 0.0025D, 0);
	//			DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, true, entity, entity.width, entity.height / 2.0F, entity.width, 0, 0, 0, 0);
	//		}
	//
	//		for (int i = 0; i < 20; ++i)
	//		{
	//			DinocraftServer.spawnParticle(EnumParticleTypes.SPELL_MOB, true, entity, entity.width,
	//					entity.height / 2.0F, entity.width, this.rand.nextInt(21) + 130, 200, 200, 0);
	//		}
	//	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (this.isReturning())
		{
			return;
		}
		
		if (result.entityHit != null)
		{
			if (!this.world.isRemote && result.entityHit instanceof EntityLivingBase)
			{
				this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat());
				this.world.playSound(null, result.entityHit.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat());
				((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0));
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.0F);
				float f0 = result.entityHit.height / 2.0F;
				DinocraftServer.spawnDreadedParticles(result.entityHit.world, 15, 10, result.entityHit.posX, result.entityHit.posY + f0,
						result.entityHit.posZ, result.entityHit.width, f0, result.entityHit.width);
				this.returnToThrower();
			}
		}
	}


	@Override
	public void onUpdate()
	{
		if (this.world.isRemote)
		{
			if (this.ticksExisted % 2 == 0)
			{
				this.world.spawnParticle(this.rand.nextBoolean() ? EnumParticleTypes.SMOKE_LARGE : EnumParticleTypes.SMOKE_NORMAL,
						true, this.posX + Math.random() * 1.0D * this.width - 0.5 * this.width,
						this.posY + this.height * 0.25F + Math.random() * this.height * 0.5F,
						this.posZ + Math.random() * 1.0D * this.width - 0.5 * this.width, 0, 0, 0);
			}

			if (this.rand.nextBoolean())
			{
				this.world.spawnParticle(EnumParticleTypes.FALLING_DUST, true,
						this.posX + Math.random() * 1.0D * this.width - 0.5 * this.width,
						this.posY + this.height * 0.25F + Math.random() * this.height * 0.5F,
						this.posZ + Math.random() * 1.0D * this.width - 0.5 * this.width,
						0, 0, 0, 0);
			}
			
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					this.posX + Math.random() * 1.0D * this.width - 0.5 * this.width,
					this.posY + this.height * 0.25F + Math.random() * this.height * 0.5F,
					this.posZ + Math.random() * 1.0D * this.width - 0.5 * this.width,
					150, this.rand.nextInt(51) + 150, 100);
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					this.posX + Math.random() * 1.0D * this.width - 0.5 * this.width,
					this.posY + this.height * 0.25F + Math.random() * this.height * 0.5F,
					this.posZ + Math.random() * 1.0D * this.width - 0.5 * this.width,
					this.rand.nextInt(21) + 130, 200, 200);
			int j = 200 + this.rand.nextInt(26);
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true,
					this.posX + Math.random() * 1.0D * this.width - 0.5 * this.width,
					this.posY + this.height * 0.25F + Math.random() * this.height * 0.5F,
					this.posZ + Math.random() * 1.0D * this.width - 0.5 * this.width,
					j, j, j);
		}
		else
		{
			if (this.thrower == null || !this.thrower.isEntityAlive())
			{
				this.world.setEntityState(this, (byte) 3);
				this.setDead();
				return;
			}

			if (this.isReturning())
			{
				VectorHelper vec = VectorHelper.fromEntityCenter(this.thrower).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				this.motionX = vec.x * 0.35F;
				this.motionY = vec.y * 0.35F;
				this.motionZ = vec.z * 0.35F;
				this.markVelocityChanged();
				
				if (this.getDistance(this.thrower) < 1.5D)
				{
					float f0 = this.thrower.height / 2.0F;
					DinocraftServer.spawnDreadedParticles(this.thrower.world, 12, 7, this.thrower.posX, this.thrower.posY + f0,
							this.thrower.posZ, this.thrower.width, f0, this.thrower.width);
					this.world.playSound(null, this.thrower.getPosition(), DinocraftSoundEvents.GHOST_WHISPER, SoundCategory.NEUTRAL, 0.25F, this.rand.nextFloat() * 0.5F + 0.5F);

					if (this.rand.nextInt(4) == 0)
					{
						this.thrower.heal(1.0F);
					}

					this.setDead();
					return;
				}
			}
			else if (this.ticksExisted > this.startTargetTick)
			{
				if (this.target == null || !this.target.isEntityAlive())
				{
					this.world.setEntityState(this, (byte) 3);
					this.setDead();
					return;
				}
				
				VectorHelper vec = VectorHelper.fromEntityCenter(this.target).subtract(VectorHelper.fromEntityCenter(this)).normalize();
				this.motionX = vec.x * 0.5F;
				this.motionY = vec.y * 0.5F;
				this.motionZ = vec.z * 0.5F;
				this.markVelocityChanged();
				
				if (this.getDistance(this.target) < 1.5D)
				{
					RayTraceResult result = new RayTraceResult(this.target);

					if (!ForgeEventFactory.onProjectileImpact(this, result))
					{
						this.onImpact(result);
					}
				}
			}
		}
		
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		this.onEntityUpdate();
		
		if (this.throwableShake > 0)
		{
			--this.throwableShake;
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
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	/**
	 * Returns whether or not this Dreaded Eye is returning to its thrower
	 */
	public boolean isReturning()
	{
		return this.dataManager.get(RETURNING);
	}

	/**
	 * Returns this Dreaded Eye to its thrower
	 */
	protected void returnToThrower()
	{
		this.dataManager.set(RETURNING, true);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}
}