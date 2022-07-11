package dinocraft.entity;

import java.util.List;

import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySplicentsThrowingKnife extends EntityThrowable
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntitySplicentsThrowingKnife.class, DataSerializers.FLOAT);

	public EntitySplicentsThrowingKnife(World world)
	{
		super(world);
	}

	public EntitySplicentsThrowingKnife(World world, EntityLivingBase shooter)
	{
		super(world, shooter);
	}
	
	public EntitySplicentsThrowingKnife(EntityLivingBase shooter, float gravity)
	{
		super(shooter.world, shooter);
		this.dataManager.set(GRAVITY, gravity);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(GRAVITY, 0.0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 4; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.5, this.posZ + this.height * 0.5D,
						Math.random() * 0.2D - 0.1D, Math.random() * 0.01D, Math.random() * 0.2D - 0.1D, 0);
			}
		}
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		switch (result.typeOfHit)
		{
			case BLOCK:
			{
				BlockPos pos = result.getBlockPos();
				
				if (!this.world.isRemote)
				{
					EntityLightningBolt lightning = new EntityLightningBolt(this.world, pos.getX(), pos.getY(), pos.getZ(), true);
					this.world.addWeatherEffect(lightning);
					this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
				}

				break;
			}
			case ENTITY:
			{
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase)
				{
					this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.ZAP2, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
					result.entityHit.attackEntityFrom(this.thrower != null ? this.thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.causeMobDamage(this.thrower) : DamageSource.GENERIC, 8.0F);
					float f0 = result.entityHit.height / 2.0F;
					DinocraftServer.spawnElectricParticles(result.entityHit.world, 18, 12, 20, result.entityHit.posX, result.entityHit.posY + f0, result.entityHit.posZ, result.entityHit.width, f0, result.entityHit.width);
					double range = 12.0D;
					List<Entity> entities = this.world.getEntitiesInAABBexcluding(this.thrower, this.getEntityBoundingBox().grow(range),
							entity -> entity instanceof EntityLivingBase && entity.canBeCollidedWith() && entity != result.entityHit);
					EntityLivingBase target1 = null;

					for (Entity entity : entities)
					{
						if (!(result.entityHit instanceof EntityPigZombie) && entity instanceof EntityPigZombie && !((EntityPigZombie) entity).isAngry())
						{
							continue;
						}
						
						EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
						
						if (this.thrower.getRidingEntity() != entity && (entitylivingbase.getRevengeTarget() == this.thrower || entity.isCreatureType(EnumCreatureType.MONSTER, false)))
						{
							double len = new Vec3d(entity.posX - this.posX, entity.getEntityBoundingBox().minY + entity.height / 2.0F - this.posY - this.getEyeHeight(), entity.posZ - this.posZ).lengthVector();

							if (len < range)
							{
								target1 = entitylivingbase;
								range = len;
							}
						}
					}

					EntityLivingBase target2 = null;
					
					if (target1 != null)
					{
						range = 6.0D;
						entities = this.world.getEntitiesInAABBexcluding(target1, target1.getEntityBoundingBox().grow(range),
								entity -> entity instanceof EntityLivingBase && entity.canBeCollidedWith() && entity != this.thrower && entity != result.entityHit);

						for (Entity entity : entities)
						{
							if (!(result.entityHit instanceof EntityPigZombie) && entity instanceof EntityPigZombie && !((EntityPigZombie) entity).isAngry())
							{
								continue;
							}

							EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
							
							if (this.thrower.getRidingEntity() != entity && (entitylivingbase.getRevengeTarget() == this.thrower || entity.isCreatureType(EnumCreatureType.MONSTER, false)))
							{
								double len = new Vec3d(entity.posX - target1.posX, entity.getEntityBoundingBox().minY + entity.height / 2.0F - target1.posY - target1.getEyeHeight(), entity.posZ - target1.posZ).lengthVector();

								if (len < range)
								{
									target2 = entitylivingbase;
									range = len;
								}
							}
						}

						DinocraftServer.spawnElectricParticlesConnection(this.world, (EntityLivingBase) result.entityHit, target1, 24);
						this.world.playSound(null, target1.getPosition(), DinocraftSoundEvents.ZAP, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
						target1.attackEntityFrom(this.thrower != null ? this.thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.causeMobDamage(this.thrower) : DamageSource.GENERIC, 5.0F);
						float f1 = target1.height / 2.0F;
						DinocraftServer.spawnElectricParticles(target1.world, 15, 10, 15, target1.posX, target1.posY + f1, target1.posZ, target1.width, f1, target1.width);
					}
					
					if (target2 != null)
					{
						DinocraftServer.spawnElectricParticlesConnection(this.world, target1, target2, 18);
						this.world.playSound(null, target2.getPosition(), DinocraftSoundEvents.ZAP, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
						target2.attackEntityFrom(this.thrower != null ? this.thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.causeMobDamage(this.thrower) : DamageSource.GENERIC, 3.0F);
						float f1 = target2.height / 2.0F;
						DinocraftServer.spawnElectricParticles(target2.world, 13, 8, 10, target2.posX, target2.posY + f1, target2.posZ, target2.width, f1, target2.width);
					}
				}
				else
				{
					return;
				}

				break;
			}
			default: break;
		}

		if (!this.world.isRemote)
		{
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}
	
	@Override
	public void onUpdate()
	{
		if (this.ticksExisted > 300)
		{
			this.setDead();
		}

		if (this.isInWater())
		{
			this.motionY -= 0.005D;
			this.motionX *= 0.95D;
			this.motionZ *= 0.95D;
		}

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
			if (raytraceresult.typeOfHit == Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
			{
				this.setPortal(raytraceresult.getBlockPos());
			}
			else
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
		}
		
		if (!this.hasNoGravity())
		{
			this.motionY -= this.getGravityVelocity();
		}

		if (this.world.isRemote)
		{
			this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, true, this.posX + 0.1 * (Math.random() - 0.5), this.posY + 0.25 * (Math.random() - 0.5) + this.height, this.posZ + 0.1 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5));
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, this.posX + 0.1 * (Math.random() - 0.5), this.posY + 0.25 * (Math.random() - 0.5) + this.height, this.posZ + 0.1 * (Math.random() - 0.5), 20, 60 * (this.world.rand.nextInt(3) + 1) - 50, 0);
			
			if (this.ticksExisted % 2 == 0)
			{
				this.world.spawnParticle(this.world.rand.nextBoolean() ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.SMOKE_LARGE, true, this.lastTickPosX + 0.1 * (Math.random() - 0.5), this.lastTickPosY + 0.5 * (Math.random() - 0.5) + this.height, this.lastTickPosZ + 0.1 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5));
			}

		}
		
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}
}