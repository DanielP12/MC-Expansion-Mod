//package dinocraft.entity;
//
//import java.util.List;
//import java.util.UUID;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockBush;
//import net.minecraft.block.BlockReed;
//import net.minecraft.block.BlockVine;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.projectile.EntityThrowable;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.network.datasync.DataParameter;
//import net.minecraft.network.datasync.DataSerializers;
//import net.minecraft.network.datasync.EntityDataManager;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.math.RayTraceResult.Type;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraftforge.event.ForgeEventFactory;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class EntityShiningStar extends EntityThrowable
//{
//	private static final DataParameter<String> ATTACHED_ENTITY_UUID = EntityDataManager.createKey(EntityShiningStar.class, DataSerializers.STRING);
//	private EntityLivingBase attachedEntity;
//	private int ticksAttached = 0;
//	private int livingTicksAfterAttachement;
//
//	public EntityShiningStar(World world)
//	{
//		super(world);
//	}
//
//	public EntityShiningStar(World world, EntityLivingBase shooter)
//	{
//		super(world, shooter);
//		this.livingTicksAfterAttachement = world.rand.nextInt(40) + 95;
//	}
//
//	@Override
//	public float getBrightness()
//	{
//		return 1.0F;
//	}
//
//	public EntityShiningStar(EntityLivingBase shooter, float gravity)
//	{
//		super(shooter.world, shooter);
//	}
//
//	@Override
//	protected void entityInit()
//	{
//		super.entityInit();
//		this.dataManager.register(ATTACHED_ENTITY_UUID, "");
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void handleStatusUpdate(byte id)
//	{
//		if (id == 3)
//		{
//			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.NEUTRAL, 10.0F, this.rand.nextFloat() * 0.75F, false);
//
//			for (int i = 0; i < 150; ++i)
//			{
//				//				this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width,
//				//						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width,
//				//						this.rand.nextGaussian() * 0.75D, this.rand.nextGaussian() * 0.75D, this.rand.nextGaussian() * 0.75D, 0);
//				this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width,
//						this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width,
//						this.rand.nextGaussian() * 0.75D, this.rand.nextGaussian() * 0.75D, this.rand.nextGaussian() * 0.75D, 5);
//			}
//		}
//	}
//
//	@Override
//	protected void onImpact(RayTraceResult result)
//	{
//		switch (result.typeOfHit)
//		{
//			case BLOCK:
//			{
//				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
//
//				if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine)
//				{
//					return;
//				}
//
//				if (!this.world.isRemote)
//				{
//					this.world.setEntityState(this, (byte) 3);
//					this.setDead();
//				}
//
//				return;
//			}
//			case ENTITY:
//			{
//				if (!this.world.isRemote && result.entityHit != null && this.attachedEntity == null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.thrower)
//				{
//					this.setAttachedEntity((EntityLivingBase) result.entityHit);
//					this.motionX = 0.0D;
//					this.motionY = 0.0D;
//					this.motionZ = 0.0D;
//					this.markVelocityChanged();
//				}
//			}
//			default: break;
//		}
//	}
//
//	@Override
//	public void onUpdate()
//	{
//		if (this.world.isRemote)
//		{
//			if (this.motionX == 0.0D && this.motionZ == 0.0D)
//			{
//				for (int i = 0; i < 3; i++)
//				{
//					this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
//							this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
//							this.rand.nextGaussian() * 0.05D, this.rand.nextFloat() * -1.0D, this.rand.nextGaussian() * 0.05D);
//				}
//			}
//			else
//			{
//				for (int i = 0; i < 2; i++)
//				{
//					this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, this.prevPosX + this.rand.nextFloat() * this.width - this.width,
//							this.prevPosY + this.rand.nextFloat() * this.height, this.prevPosZ + this.rand.nextFloat() * this.width - this.width,
//							this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D, this.rand.nextGaussian() * 0.0005D);
//				}
//			}
//		}
//
//		if (!this.world.isRemote)
//		{
//			if (this.attachedEntity == null && this.ticksAttached > 0)
//			{
//				this.attachedEntity = (EntityLivingBase) this.getServer().getEntityFromUuid(UUID.fromString(this.dataManager.get(ATTACHED_ENTITY_UUID)));
//			}
//
//			if (this.attachedEntity == null && this.ticksExisted > 200 || this.ticksAttached > this.livingTicksAfterAttachement)
//			{
//				this.world.setEntityState(this, (byte) 3);
//				this.setDead();
//			}
//		}
//
//		if (this.attachedEntity != null)
//		{
//			this.ticksAttached++;
//		}
//
//		if (!this.world.isRemote)
//		{
//			if (this.ticksAttached > 0)
//			{
//				this.motionY += this.ticksAttached < this.livingTicksAfterAttachement / 2 ? 0.01D : -0.004D;
//				this.markVelocityChanged();
//
//				if (this.attachedEntity != null && this.attachedEntity.isEntityAlive())
//				{
//					this.attachedEntity.setPositionAndUpdate(this.posX, this.posY, this.posZ);
//					this.attachedEntity.fallDistance = 0;
//				}
//			}
//		}
//
//		this.lastTickPosX = this.posX;
//		this.lastTickPosY = this.posY;
//		this.lastTickPosZ = this.posZ;
//		this.onEntityUpdate();
//
//		if (this.throwableShake > 0)
//		{
//			--this.throwableShake;
//		}
//
//		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
//		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
//		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
//		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//
//		if (raytraceresult != null)
//		{
//			vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
//		}
//
//		Entity entity = null;
//		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0F), entity1 -> entity1 != this.thrower);
//		double d0 = 0.0D;
//
//		for (Entity entity1 : list)
//		{
//			if (entity1.isEntityAlive() && entity1.canBeCollidedWith())
//			{
//				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
//				RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
//
//				if (raytraceresult1 != null)
//				{
//					double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
//
//					if (d1 < d0 || d0 == 0.0D)
//					{
//						entity = entity1;
//						d0 = d1;
//					}
//				}
//			}
//		}
//
//		if (entity != null)
//		{
//			raytraceresult = new RayTraceResult(entity);
//		}
//
//		if (raytraceresult != null)
//		{
//			if (raytraceresult.typeOfHit == Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
//			{
//				this.setPortal(raytraceresult.getBlockPos());
//			}
//			else if (!ForgeEventFactory.onProjectileImpact(this, raytraceresult))
//			{
//				this.onImpact(raytraceresult);
//			}
//		}
//
//		this.posX += this.motionX;
//		this.posY += this.motionY;
//		this.posZ += this.motionZ;
//		float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
//		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
//
//		for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//		{
//
//		}
//
//		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
//		{
//			this.prevRotationPitch += 360.0F;
//		}
//
//		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
//		{
//			this.prevRotationYaw -= 360.0F;
//		}
//
//		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
//		{
//			this.prevRotationYaw += 360.0F;
//		}
//
//		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
//		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
//		this.setPosition(this.posX, this.posY, this.posZ);
//	}
//
//	public EntityLivingBase getAttachedEntity()
//	{
//		return this.attachedEntity;
//	}
//
//	private void setAttachedEntity(EntityLivingBase entity)
//	{
//		this.attachedEntity = entity;
//		this.dataManager.set(ATTACHED_ENTITY_UUID, entity.getUniqueID().toString());
//	}
//
//	@Override
//	public void writeEntityToNBT(NBTTagCompound compound)
//	{
//		super.writeEntityToNBT(compound);
//		compound.setString("attached_entity", this.dataManager.get(ATTACHED_ENTITY_UUID));
//		compound.setInteger("ticks_attached", this.ticksAttached);
//		compound.setInteger("living_ticks_after_attachment", this.livingTicksAfterAttachement);
//	}
//
//	@Override
//	public void readEntityFromNBT(NBTTagCompound compound)
//	{
//		super.readEntityFromNBT(compound);
//		this.dataManager.set(ATTACHED_ENTITY_UUID, compound.getString("attached_entity"));
//		this.ticksAttached = compound.getInteger("ticks_attached");
//		this.livingTicksAfterAttachement = compound.getInteger("living_ticks_after_attachment");
//	}
//}