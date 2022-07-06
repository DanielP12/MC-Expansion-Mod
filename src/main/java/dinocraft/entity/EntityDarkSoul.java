package dinocraft.entity;

import java.util.List;

import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
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

public class EntityDarkSoul extends EntityThrowable
{
	private int numEntitiesHit;
	private Entity[] entitiesHit = new Entity[4];
	private float damage = 5.5F;

	public EntityDarkSoul(World world)
	{
		super(world);
	}

	public EntityDarkSoul(World world, EntityLivingBase shooter, float damage)
	{
		super(world, shooter);
		this.damage = damage;
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0.03F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat(), false);

			for (int i = 0; i < 20; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - 2.5 * this.width,
						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - 2.5 * this.width,
						this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, 0);
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - 2.5 * this.width,
						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - 2.5 * this.width,
						this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian(), 0);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		switch (result.typeOfHit)
		{
			case BLOCK:
				if (!this.world.isRemote)
				{
					if (result.sideHit != EnumFacing.UP)
					{
						this.world.setEntityState(this, (byte) 3);
						this.setDead();
					}
					else
					{
						this.motionY = this.getLookVec().y;
						this.markVelocityChanged();
					}
				}

				return;
			case ENTITY:
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.thrower && !this.wasPreviousTarget(result.entityHit))
				{
					result.entityHit.attackEntityFrom(this.thrower != null ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.GENERIC, this.damage);
					result.entityHit.hurtResistantTime = 0;
					this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
					this.entitiesHit[this.numEntitiesHit++] = result.entityHit;
					((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 160, 0));
					
					if (this.numEntitiesHit >= 4)
					{
						this.world.setEntityState(this, (byte) 3);
						this.setDead();
					}
				}
				
				return;
			default:
				break;
		}
	}
	

	/**
	 * Returns whether the specified entity was a previous target
	 */
	public boolean wasPreviousTarget(Entity target)
	{
		return target == this.entitiesHit[0] || target == this.entitiesHit[1] || target == this.entitiesHit[2] || target == this.entitiesHit[3];
	}
	
	@Override
	public void onUpdate()
	{
		if (this.world.isRemote)
		{
			for (int i = 0; i < 2; i++)
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, this.posX + this.rand.nextFloat() * 2.0D * this.width - this.width,
						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * 2.0D * this.width - this.width,
						this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D);
			}
		}
		else if (this.ticksExisted > 100)
		{
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
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
		this.motionY -= this.getGravityVelocity();
		this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("entities_hit", this.numEntitiesHit);
		compound.setFloat("damage", this.damage);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.numEntitiesHit = compound.getInteger("entities_hit");
		this.damage = compound.getFloat("damage");
	}
}