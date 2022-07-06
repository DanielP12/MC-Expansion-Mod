package dinocraft.entity;

import java.util.List;

import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
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

public class EntityMagatiumSmallShard extends EntityThrowable
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityMagatiumSmallShard.class, DataSerializers.FLOAT);
	private Entity thrower2;

	public EntityMagatiumSmallShard(World world)
	{
		super(world);
	}

	public EntityMagatiumSmallShard(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}
	
	public EntityMagatiumSmallShard(EntityLivingBase thrower, EntityLivingBase thrower2, float gravity)
	{
		super(thrower.world, thrower);
		this.thrower2 = thrower2;
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
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_METAL_FALL, SoundCategory.NEUTRAL, 0.5F, this.rand.nextFloat(), false);

			for (int i = 0; i < 10; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY + 0.25D, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.MAGATIUM_SHARD));
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.typeOfHit.equals(result.typeOfHit.BLOCK))
		{
			IBlockState block = this.world.getBlockState(result.getBlockPos());
			Material material = block.getMaterial();
			
			if (material == Material.VINE)
			{
				return;
			}
			
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}

		if (!this.world.isRemote && result != null)
		{
			if (result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.thrower && result.entityHit != this.thrower2)
			{
				result.entityHit.attackEntityFrom(this.thrower != null ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.GENERIC, result.entityHit.isNonBoss() ? 8.0F : 4.0F);
				
				if (result.entityHit instanceof EntityLiving)
				{
					((EntityLiving) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
				}
				
				this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
				this.world.setEntityState(this, (byte) 3);
				this.setDead();
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		if (this.ticksExisted > 200)
		{
			this.setDead();
		}
		
		if (this.isInWater())
		{
			this.motionY -= 0.005D;
			this.motionX *= 0.95D;
			this.motionZ *= 0.95D;
		}

		if (this.world.isRemote)
		{
			this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, true, this.posX + 0.1 * (Math.random() - 0.5), this.posY + 0.25 * (Math.random() - 0.5), this.posZ + 0.1 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5), 0.05 * (Math.random() - 0.5));

			if (this.ticksExisted % 3 == 0)
			{
				this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX + 0.1 * (Math.random() - 0.5), this.posY + 0.25 * (Math.random() - 0.5), this.posZ + 0.1 * (Math.random() - 0.5), 0, 0, 0);
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
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0F), entity1 -> entity1 != this.thrower && entity1 != this.thrower2);
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
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}
}