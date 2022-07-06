package dinocraft.entity;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
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

public class EntityJestersBolt extends EntityThrowable
{
	public EntityJestersBolt(World world)
	{
		super(world);
	}

	public EntityJestersBolt(World world, EntityLivingBase shooter)
	{
		super(world, shooter);
	}
	
	public EntityJestersBolt(EntityLivingBase shooter, float gravity)
	{
		super(shooter.world, shooter);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.NEUTRAL, 1.0F, this.rand.nextFloat(), false);

			for (int i = 0; i < 50; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width,
						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width,
						this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, 0);
				this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width,
						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width,
						this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, this.rand.nextGaussian() * 0.33D, 0);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		switch (result.typeOfHit)
		{
			case BLOCK:
				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();

				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine)
				{
					return;
				}
				
				if (!this.world.isRemote)
				{
					this.world.setEntityState(this, (byte) 3);
					this.setDead();
				}

				return;
			case ENTITY:
				if (!this.world.isRemote && result.entityHit != null && result.entityHit != this.thrower && result.entityHit instanceof EntityLivingBase)
				{
					if (result.entityHit.isNonBoss())
					{
						DinocraftEntity.getEntity((EntityLivingBase) result.entityHit).getActionsModule().setJesterized(this.thrower, this.rand.nextInt(11) + 20);
					}
					
					this.world.setEntityState(this, (byte) 3);
					this.setDead();
				}
				
				return;
			default:
				break;
		}
	}
	
	@Override
	public void onUpdate()
	{
		if (this.world.isRemote)
		{
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
					this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
					175, 30, 1);
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
					this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
					20, 100, 1);
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
					this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
					20, 100, 0);
			this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
					this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
					this.rand.nextGaussian() * 0.0025D, this.rand.nextGaussian() * 0.0025D, this.rand.nextGaussian() * 0.0025D, 0);
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
		this.setPosition(this.posX, this.posY, this.posZ);
	}
}