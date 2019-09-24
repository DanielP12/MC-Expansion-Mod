package dinocraft.entity;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftEntities;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRayBullet extends EntityThrowable 
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityRayBullet.class, DataSerializers.FLOAT);
	
	public ResourceLocation getTexture()
	{
		return DinocraftEntities.RAY_BULLET_TEXTURE;
	}
	
	public EntityRayBullet(World world) 
	{
	    super(world);
	}
	
	public EntityRayBullet(World world, EntityLivingBase shooter)
	{
	    super(world, shooter);
	}

	public EntityRayBullet(EntityLivingBase shooter, float gravity)
	{
		super(shooter.world, shooter);
		this.dataManager.set(GRAVITY, gravity);
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(GRAVITY, 0F);
	}
	
	private void kill() 
	{
		if (this.thrower != null && this.thrower instanceof EntityLiving)
		{
			DinocraftEntity.getEntity(this.thrower).setShootingTick(0);
		}
		
		this.setDead();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) 
	{
		if (id == 3)
		{
			World world = this.world;
			world.playSound(this.posX, this.posY, this.posZ, DinocraftSoundEvents.CRACK, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 1.0F, false);
			
			for (int i = 0; i < 16; ++i)
			{
				world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.RAY_BULLET));
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
				else
				{
					if (!this.world.isRemote)
					{
						this.kill();
					}
					
					this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.RAY_BULLET));
				}
				
				break;
			}
			
			case ENTITY: 
			{
				if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.getThrower())
				{
					EntityLivingBase thrower = this.getThrower();
					result.entityHit.attackEntityFrom(thrower != null ? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower) : DamageSource.causeMobDamage(thrower) : DamageSource.GENERIC, rand.nextFloat() + 4.5F);
					this.world.playSound(null, result.entityHit.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 1.0F);
            		this.kill();
				}
				else
				{
					return;
				}
				
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.RAY_BULLET));

				break;
			}
			
			default: break;
		}
		
		if (!this.world.isRemote)
		{
			this.world.setEntityState(this, (byte) 3);
			this.kill();
		}
	}

	private int existedTicks;

	@Override
	public void onUpdate() 
	{
		if (this.motionX == 0.0D && this.motionY == 0.0D && this.motionZ == 0.0D)
		{
			++this.existedTicks;
		}
		
		if (this.existedTicks > 40)
		{
			this.setDead();
			this.existedTicks = 0;
		}
		
		if (this.ticksExisted > 400)
		{
			this.setDead();
		}
		
		if (this.world.isRemote)
		{
			//this.world.spawnParticle(EnumParticleTypes.CLOUD, true, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 1);
		}
		
		super.onUpdate();
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}
}