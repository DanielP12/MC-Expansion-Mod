package dinocraft.entity;

import java.util.List;

import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFallingCrystal extends EntityThrowable
{
	private static final DataParameter<Float> SPEED = EntityDataManager.createKey(EntityFallingCrystal.class, DataSerializers.FLOAT);

	public EntityFallingCrystal(World world)
	{
		super(world);
	}

	public EntityFallingCrystal(EntityLivingBase shooter, float speed)
	{
		super(shooter.world, shooter);
		this.dataManager.set(SPEED, speed);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(SPEED, 0.0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 50; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.05, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.MAGATIUM_SHARD));
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

				this.world.playSound(null, this.posX, this.posY, this.posZ, DinocraftSoundEvents.ROCK_SMASH, SoundCategory.AMBIENT, 0.5F, this.world.rand.nextFloat() * 0.5F + 0.75F);
				break;
			}
			case ENTITY:
			{
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase)
				{
					result.entityHit.attackEntityFrom(this.thrower != null ? this.thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, this.thrower) : DamageSource.causeMobDamage(this.thrower) : DamageSource.GENERIC, result.entityHit.isNonBoss() ? this.rand.nextFloat() + 4.0F : this.rand.nextFloat() + 2.5F);
					result.entityHit.hurtResistantTime = 0;
					this.world.playSound(null, this.posX, this.posY, this.posZ, DinocraftSoundEvents.ROCK_SMASH, SoundCategory.AMBIENT, 0.5F, this.world.rand.nextFloat() * 0.5F + 0.75F);
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

		if (this.isInWater())
		{
			for (int j = 0; j < 4; ++j)
			{
				float f3 = 0.25F;
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			}
		}

		this.motionY -= this.getSpeed();

		if (this.world.isRemote)
		{
			this.world.spawnParticle(this.world.rand.nextInt(3) > 1 ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.CRIT_MAGIC, true, this.lastTickPosX + 0.1 * (Math.random() - 0.5), this.lastTickPosY + 0.5 * (Math.random() - 0.5) + this.height, this.lastTickPosZ + 0.1 * (Math.random() - 0.5), 0, 0, 0, 0);
		}

		this.setPosition(this.posX, this.posY, this.posZ);
	}

	protected float getSpeed()
	{
		return this.dataManager.get(SPEED);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setFloat("speed", this.dataManager.get(SPEED));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.dataManager.set(SPEED, compound.getFloat("speed"));
	}
}