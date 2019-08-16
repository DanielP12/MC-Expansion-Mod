package dinocraft.entity;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVineBall extends EntityThrowable 
{
	private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityVineBall.class, DataSerializers.FLOAT);
	private static final Map<EnumFacing, PropertyBool> propMap = ImmutableMap.of(EnumFacing.NORTH, BlockVine.NORTH, EnumFacing.SOUTH, BlockVine.SOUTH, EnumFacing.WEST, BlockVine.WEST, EnumFacing.EAST, BlockVine.EAST);
	
	public ResourceLocation getTexture()
	{
		return RenderEntities.VINE_BALL_TEXTURE;
	}
	
	public EntityVineBall(World worldIn) 
	{
	    super(worldIn);
	}
	
	public EntityVineBall(World worldIn, EntityLivingBase living) 
	{
	    super(worldIn, living);
	}

	public EntityVineBall(EntityLivingBase living, float gravity)
	{
		super(living.world, living);
		dataManager.set(GRAVITY, gravity);
	}
		
	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(GRAVITY, 0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id)
	{	
		if (id == 3) 
		{
			World worldIn = this.world;
			worldIn.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
			worldIn.playSound(this.posX, this.posY, this.posZ, DinocraftSoundEvents.GRAB, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
			
			for (int i = 0; i < 16; ++i) 
			{
				worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.VINE_BALL));
			}
		}
	}
	
	@Override
	protected void onImpact(RayTraceResult trace) 
	{
		World worldIn = this.world;
		
		if (trace.entityHit != null && trace.entityHit instanceof EntityLivingBase)
        {
			EntityLivingBase livingIn = (EntityLivingBase) trace.entityHit;
             
            if (!worldIn.isRemote)
            {
            	livingIn.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1F);
            	livingIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0, false, false));
     		}
             
            worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, livingIn.posX, livingIn.posY, livingIn.posZ, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, Item.getIdFromItem(DinocraftItems.VINE_BALL));
        }
			
		if (!worldIn.isRemote && trace != null) 
		{
			EnumFacing dir = trace.sideHit;
				
			if (dir != null && dir.getAxis() != EnumFacing.Axis.Y) 
			{
				BlockPos pos = trace.getBlockPos().offset(dir);
					
				while (pos.getY() > 0) 
				{
					IBlockState state = worldIn.getBlockState(pos);
					Block block = state.getBlock();
						
					if (block.isAir(state, worldIn, pos)) 
					{
						IBlockState stateSet = Blocks.VINE.getDefaultState().withProperty(propMap.get(dir.getOpposite()), true);
						worldIn.setBlockState(pos, stateSet, 1 | 2);
						worldIn.playEvent(2001, pos, Block.getStateId(stateSet));
						pos = pos.down();
					}
					else break;
				}
			}
			
			worldIn.setEntityState(this, (byte)3);
			this.setDead();
		}
	}

	private int existedTicks;
	
	@Override
	public void onUpdate() 
	{
		if (this.motionX == 0 && this.motionY == 0 && this.motionZ == 0) ++this.existedTicks;
		
		if (this.existedTicks > 40)
		{
			this.setDead();
			this.existedTicks = 0;
		}
		
		super.onUpdate();
	}
	
	@Override
	protected float getGravityVelocity() 
	{
		return dataManager.get(GRAVITY);
	}
}
