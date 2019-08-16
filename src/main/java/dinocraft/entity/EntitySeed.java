package dinocraft.entity;

import java.util.Random;

import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
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

public class EntitySeed extends EntityThrowable 
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntitySeed.class, DataSerializers.FLOAT);
	
	public ResourceLocation getTexture()
	{
		return RenderEntities.SEED_TEXTURE;
	}
	
	public EntitySeed(World worldIn) 
	{
	    super(worldIn);
	}
	
	/** The entity shooting this seed */
	public Entity shootingEntity;
	
	public EntitySeed(World worldIn, EntityLivingBase shooter)
	{
	    super(worldIn, shooter);
        this.shootingEntity = shooter;
	}

	public EntitySeed(EntityLivingBase shooter, float gravityIn)
	{
		super(shooter.world, shooter);
		this.dataManager.set(GRAVITY, gravityIn);
        this.shootingEntity = shooter;
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(GRAVITY, 0.5F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) 
	{
		if (id == 3)
		{
			World worldIn = this.world;
			worldIn.playSound(this.posX, this.posY, this.posZ, DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 1.0F, false);
			
			for (int i = 0; i < 16; ++i)
			{
				worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.SEED));
			}
		}
	}
/*
	@Override
	protected void onImpact(RayTraceResult result)
	{
		World worldIn = this.worldObj;
		
		if (result.entityHit != null)
		{	
            if (result.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) result.entityHit;
                entity.playSound(DinocraftSoundEvents.HIT, 1.0F, 1.0F);

   			 	if (result.entityHit != this.shootingEntity)
   			 	{
   			 		entity.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 4F);
   			 	}
   			 	else if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && result.entityHit == this.shootingEntity)
   			 	{
   			 		if (worldIn.isRemote)
   			 		{
   			 			this.setDead();
   						worldIn.setEntityState(this, (byte) 3);
   			 		}
   			 		
					return;
   			 	}
   			 	
                worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, entity.posX, entity.posY, entity.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.RAY_BULLET));
            }
	    }
		
		if (!worldIn.isRemote)
		{
			worldIn.setEntityState(this, (byte) 3);
			this.setDead();
		}
	} */
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		World worldIn = this.world;
		Random rand = worldIn.rand;
		
		if (result.typeOfHit.equals(result.typeOfHit.BLOCK))
		{
			Block block = world.getBlockState(result.getBlockPos()).getBlock();
			if (block instanceof BlockBush || block instanceof BlockReed || block instanceof BlockVine) return;
		}
		
        if (result.entityHit != null && result.entityHit instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) result.entityHit;

            if (!worldIn.isRemote)
            {
   				if (result.entityHit != this.shootingEntity)
   			 	{
            		worldIn.playSound(null, entity.getPosition(), DinocraftSoundEvents.HIT, SoundCategory.NEUTRAL, 0.5F, rand.nextFloat() + 1.0F);            	
   			 		entity.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), rand.nextFloat() + 3.5F);
   			 	}
   			 	else if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && result.entityHit == this.shootingEntity)
   			 	{
   			 		this.setDead();
					return;
   			 	}
            }
            
            worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, entity.posX, entity.posY, entity.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.SEED));
        }
		
		if (!worldIn.isRemote)
		{
			worldIn.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	private int existedTicks;

	@Override
	public void onUpdate() 
	{
		if (this.motionX == 0.0D && this.motionY == 0.0D && this.motionZ == 0.0D) ++this.existedTicks;
		
		if (this.existedTicks > 40)
		{
			this.setDead();
			this.existedTicks = 0;
		}
		
		if (this.ticksExisted > 400) this.setDead();
		
		super.onUpdate();
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return this.dataManager.get(GRAVITY);
	}
}