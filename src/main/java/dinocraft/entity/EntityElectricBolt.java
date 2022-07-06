package dinocraft.entity;

import java.util.List;

import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityElectricBolt extends EntityThrowable
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityElectricBolt.class, DataSerializers.FLOAT);
	
	public EntityElectricBolt(World world)
	{
		super(world);
	}
	
	public EntityElectricBolt(World world, EntityLivingBase shooter)
	{
		super(world, shooter);
	}
	
	public EntityElectricBolt(EntityLivingBase shooter, float gravity)
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
			this.world.playSound(this.posX, this.posY, this.posZ, DinocraftSoundEvents.CRACK, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() * 0.75F + 0.5F, false);
			
			for (int i = 0; i < 4; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.5, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.01, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.ELECTRIC_BOLT));
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
				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
				
				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine)
				{
					return;
				}
				
				break;
			}
			case ENTITY:
			{
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase)
				{
					result.entityHit.attackEntityFrom(this.thrower != null ? this.thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.causeMobDamage(this.thrower) : DamageSource.GENERIC, result.entityHit.isNonBoss() ? this.rand.nextFloat() + 5.0F : this.rand.nextFloat() + 2.5F);
					result.entityHit.hurtResistantTime = 0;
					result.entityHit.setFire(5);
					this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat() + 1.0F);
					float f0 = result.entityHit.height / 2.0F;
					DinocraftServer.spawnElectricParticles(result.entityHit.world, 3, 5, 75, result.entityHit.posX, result.entityHit.posY + f0, result.entityHit.posZ, result.entityHit.width, f0, result.entityHit.width);
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
		}
		
		this.setDead();
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
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
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
		this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
		
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
		float f1 = 0.99F;
		float f2 = this.getGravityVelocity();
		
		if (this.isInWater())
		{
			for (int j = 0; j < 4; ++j)
			{
				float f3 = 0.25F;
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			}
			
			f1 = 0.8F;
		}
		
		if (!this.hasNoGravity())
		{
			this.motionY -= f2;
		}
		
		if (this.world.isRemote)
		{
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, this.lastTickPosX + 0.1 * (Math.random() - 0.5), this.lastTickPosY + 0.25 * (Math.random() - 0.5) + this.height, this.lastTickPosZ + 0.1 * (Math.random() - 0.5), 20, this.world.rand.nextBoolean() ? 10 : 75, 0, 0);
			this.world.spawnParticle(this.world.rand.nextBoolean() ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.FIREWORKS_SPARK, true, this.lastTickPosX + 0.1 * (Math.random() - 0.5), this.lastTickPosY + 0.5 * (Math.random() - 0.5) + this.height, this.lastTickPosZ + 0.1 * (Math.random() - 0.5), 0, 0, 0, 0);
		}
		
		this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}
}