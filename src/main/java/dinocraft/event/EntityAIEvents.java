/*
package dinocraft.event;

import java.util.List;
import java.util.Random;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EntityAIEvents
{	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		EntityLivingBase dead = event.getEntityLiving();
		Entity killer = event.getSource().getTrueSource();

		if (dead instanceof EntityEnderman)
		{
			EntityLiving enderman = (EntityLiving) event.getEntityLiving();
			
			if (killer instanceof EntityPlayer || killer instanceof EntityWolf)
			{	
				EntityPlayer player = killer instanceof EntityWolf && ((EntityWolf) killer).isTamed() ? (EntityPlayer) ((EntityWolf) killer).getOwner() : (EntityPlayer) killer;
				
				if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Item.getItemFromBlock(Blocks.PUMPKIN) && player.inventory.hasItemStack(new ItemStack(Items.MELON)) && enderman.world.rand.nextInt(150) < 1)
				{
					enderman.world.spawnEntity(new EntityItem(enderman.world, enderman.posX, enderman.posY, enderman.posZ, new ItemStack(DinocraftItems.MERCHANTS_LUCKY_BOOTS, 1)));
				}
			}
		}
		
		if (dead instanceof EntityMob && killer instanceof EntityLivingBase)
		{			
			EntityLivingBase entityliving = (EntityLivingBase) event.getSource().getTrueSource();

			if (entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null && entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == DinocraftItems.MERCHANTS_LUCKY_BOOTS)
			{
				DinocraftEntity.getEntity(entityliving).setRegenerating(5, 1.0F, 1.0F);
			}
		}
		
		if (dead.world.isRemote && DinocraftConfig.ENTITY_BLOOD_EFFECTS)
		{
			for (int i = 0; i < 50; ++i)
			{
				dead.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, dead.posX, dead.posY + (dead.height - 0.25D), dead.posZ, 
						Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
			}
		}
		
		if (!dead.world.isRemote)
		{
			for (Entity entity : dead.world.loadedEntityList)
			{
				if (entity instanceof EntityItem)
				{
					EntityItem entityitem = (EntityItem) entity;
					
					if (entityitem.getItem().getItem() == DinocraftItems.HEART && entityitem.getTags().contains(dead.getUniqueID().toString()))
					{
						for (int i = 0; i < 20; i++)
						{
							DinocraftServer.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, entityitem.world, entityitem.posX + (entityitem.world.rand.nextFloat() * entityitem.width * 2.0F) - entityitem.width, 
									entityitem.posY + (entityitem.world.rand.nextFloat() * entityitem.height), entityitem.posZ + (entityitem.world.rand.nextFloat() * entityitem.width * 2.0F) - entityitem.width,
									entityitem.world.rand.nextGaussian() * 0.02D, entityitem.world.rand.nextGaussian() * 0.02D, entityitem.world.rand.nextGaussian() * 0.02D, 0);
						}
						
						entityitem.setDead();
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		EntityLiving entityliving = (EntityLiving) event.getEntityLiving();
		
		if (entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == DinocraftItems.LEAFY_BOOTS)
		{
			if (entityliving.ticksExisted % 80 == 0 && !entityliving.isInLava() && !entityliving.isInWater() && !entityliving.isOnLadder() && !entityliving.isRiding())
			{
				entityliving.motionY = 0.485D;
				entityliving.motionX *= 1.025D;    
				entityliving.motionZ *= 1.025D;
			}
			
			if (!entityliving.world.isRemote && (entityliving instanceof EntityZombie || entityliving instanceof EntitySkeleton))
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
				DinocraftEntityActions actions = dinoEntity.getActionsModule();
				
				if (entityliving.onGround || entityliving.isInWater() || entityliving.isInLava() || entityliving.isOnLadder() || entityliving.isRiding())
				{
				    actions.setHasDoubleJumped(true);
				    actions.setCanDoubleJumpAgain(false);
				}

				if (!entityliving.onGround)
				{
					if (!actions.canDoubleJumpAgain())
					{
						actions.setHasDoubleJumped(false);
						actions.setCanDoubleJumpAgain(true);
					}
					
					Random rand = entityliving.world.rand;

					if (!actions.hasDoubleJumped() && entityliving.ticksExisted % (rand.nextInt(10) + 1) == 0 && entityliving.motionY < 0.01D)
					{
						actions.setHasDoubleJumped(true);
						
						PotionEffect effect = entityliving.getActivePotionEffect(MobEffects.JUMP_BOOST);
						entityliving.motionY = effect != null ? (effect.getAmplifier() * 0.095D) + 0.575D : 0.4875D + (0.01D * entityliving.world.rand.nextDouble());
						entityliving.motionX *= 1.05D;    
						entityliving.motionZ *= 1.05D;
						entityliving.fallDistance = 0.0F;
						
						for (int i = 0; i < 25; ++i)
						{
							dinoEntity.spawnParticle(EnumParticleTypes.CLOUD, true, entityliving.posX + (rand.nextFloat() * entityliving.width * 2.5F) - entityliving.width, 
									entityliving.posY + 0.5D + (rand.nextFloat() * entityliving.height) - 1.25D, entityliving.posZ + (rand.nextFloat() * entityliving.width * 2.5F) - entityliving.width, 
									rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, 1);
						}
						
						float j = rand.nextFloat();
						entityliving.world.playSound(null, entityliving.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.NEUTRAL, 1.0F, j - (j / 1.5F));
						dinoEntity.setFallDamageReductionAmount(7.5F);
					}
				}
			}
		}
		
		if (entityliving instanceof EntityMob && !entityliving.world.isRemote)
		{
			Item item = entityliving.getHeldItemMainhand().getItem();
			
			if (item != null)
			{
				if (item == DinocraftItems.LEAFERANG)
				{
					DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
					dinoEntity.setShootingTick(dinoEntity.getShootingTick() + 1);
				
					if (dinoEntity.getShootingTick() >= 20)
					{				
						ItemStack stack = entityliving.getHeldItemMainhand();
						ItemStack copy = stack.copy();
						copy.setCount(1);
						entityliving.setActiveHand(EnumHand.MAIN_HAND);
						EntityLeaferang leaferang = new EntityLeaferang(entityliving.world, entityliving, copy);
						leaferang.shoot(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0F, entityliving.world.rand.nextFloat() + 0.75F, 0.75F);
						entityliving.world.spawnEntity(leaferang);
						entityliving.world.playSound(null, entityliving.posX, entityliving.posY, entityliving.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (entityliving.world.rand.nextFloat() * 0.4F + 0.8F));
						stack.shrink(1);
					}
				}

				if (item == DinocraftItems.RAY_GUN)
				{
					DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
					dinoEntity.setShootingTick(dinoEntity.getShootingTick() + 1);
					
					if (dinoEntity.getShootingTick() >= 15)
					{
						ItemStack stack = entityliving.getHeldItemMainhand();
						stack.damageItem(1, entityliving);
						dinoEntity.setShootingTick(0);
						entityliving.setActiveHand(EnumHand.MAIN_HAND);
						EntityRayBullet ball = new EntityRayBullet(entityliving, 0.001F);
						ball.shoot(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0F, 5.0F, 0.75F);
						Vec3d vector = entityliving.getLookVec();
			            ball.motionX = vector.x * 3.0D;
			            ball.motionY = vector.y * 3.0D;
			            ball.motionZ = vector.z * 3.0D;
			            entityliving.world.spawnEntity(ball);
						entityliving.world.playSound(null, entityliving.getPosition(), DinocraftSoundEvents.RAY_GUN_SHOT, SoundCategory.NEUTRAL, 3.0F, entityliving.world.rand.nextFloat() + 0.5F);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
	public static void onLivingHurt(LivingHurtEvent event)
	{		
		EntityLivingBase entity = event.getEntityLiving();
		
		if (entity instanceof EntityCreature && entity.getHeldItemMainhand().getItem() == DinocraftItems.TUSKERERS_CHARM && entity.getHealth() < 4.0F)
		{
			EntityCreature creature = (EntityCreature) entity;
			List<EntityLivingBase> list = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(10.0D, 3.0D, 10.0D), null);

	        if (!list.isEmpty())
	        {
	            EntityLivingBase closestLivingEntity = list.get(0);
	            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(creature, 16, 7, new Vec3d(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));

	            if (vec3d != null && closestLivingEntity.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) < closestLivingEntity.getDistanceSq(entity))
	            {

	            }
	            else
	            {
	            	PathNavigate navigation = creature.getNavigator();
	            	Path path = navigation.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
	                navigation.setPath(path, 1.5D);
	                DinocraftEntity.getEntity(entity).setRegenerating(5, 0.5F, 1.0F);
	            }
	        }
		}
		
		if (entity instanceof EntityPlayer && DinocraftConfig.PLAYER_BLOOD_EFFECTS && !entity.isActiveItemStackBlocking() && !entity.world.isRemote)
		{				
			for (int i = 0; i < 50; ++i)
            {
				DinocraftEntity.getEntity(entity).spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, entity.posX + (entity.world.rand.nextFloat() * entity.width * 2.0F) - entity.width, 
						entity.posY + 0.5D + (entity.world.rand.nextFloat() * entity.height), entity.posZ + (entity.world.rand.nextFloat() * entity.width * 2.0F) - entity.width, 
						entity.world.rand.nextGaussian() * 0.0015D, entity.world.rand.nextGaussian() * 0.0015D, entity.world.rand.nextGaussian() * 0.0015D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
            }
			
			entity.world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.NEUTRAL, 1.0F, entity.world.rand.nextFloat() + 0.75F);
		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving) event.getEntityLiving();
			//entityliving.setCanPickUpLoot(true);
			AxisAlignedBB axisalignedbb = new AxisAlignedBB(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.posX + 1.0D, entityliving.posY + 1.0D, entityliving.posZ + 1.0D).expand(1.0D, 3.0D, 1.0D);
			List<Entity> list = entityliving.world.getEntitiesWithinAABBExcludingEntity(entityliving, axisalignedbb);
			
			if (!list.isEmpty())
			{
				for (Entity entity : list)
				{
					if (entity instanceof EntityItem)
					{
						EntityItem entityitem = (EntityItem) entity;
						Item item = entityitem.getItem().getItem();
						
						if (item != null && item == DinocraftItems.HEART && entityitem.getOwner() != null)
						{
							if (!entityliving.world.isRemote && entityitem.getOwner().equals(entityliving.getUniqueID().toString()))
							{
								entityliving.heal(2.0F);
								entityliving.world.playSound(null, entityliving.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.NEUTRAL, 0.5F, ((entityliving.getRNG().nextFloat() - entityliving.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
								entityitem.setDead();
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		DamageSource source = event.getSource();
		EntityLivingBase target = event.getEntityLiving();

		if (source.getImmediateSource() instanceof EntityRayBullet)
		{
			target.hurtResistantTime = 0;
		}
		
		EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();

		if (attacker != null && attacker instanceof EntityLiving)
		{
			Item item = attacker.getHeldItemMainhand().getItem();

			if (item != null)
			{
				if (item == DinocraftItems.TUSKERERS_SWORD && target.world.rand.nextInt(2) < 1)
				{	
					EntityItem heart = new EntityItem(target.world, target.posX, target.posY, target.posZ, new ItemStack(DinocraftItems.HEART, 1));
					heart.setInfinitePickupDelay();
						
					String id = attacker.getUniqueID().toString();
					heart.addTag(id);
					heart.setOwner(id);
					target.world.spawnEntity(heart);
				}
				
				if (item == DinocraftItems.SHEEPLITE_SWORD)
				{
					Vec3d vector = attacker.getLookVec().normalize();
				    target.motionX = vector.x;
				    target.motionY = vector.y + target.world.rand.nextDouble();
				    target.motionZ = vector.z;
				}
				
				if (item == DinocraftItems.CHLOROPHYTE_SWORD)
				{
					target.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 2, false, true));
				}
			}
		}
	}
	
	@SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) 
	{
        Entity entity = event.getEntity();
        Random rand = entity.world.rand;
        BlockPos pos = entity.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        
        if ((entity instanceof EntityPig || entity instanceof EntityPigZombie) && rand.nextInt(3) < 1)
        {
            event.getDrops().add(new EntityItem(entity.world, x, y, z, new ItemStack(DinocraftItems.TUSK, rand.nextInt(2) + 1)));
        }
        else if (entity instanceof EntityCreeper && rand.nextInt(1000) < 1) 
        {
            event.getDrops().add(new EntityItem(entity.world, x, y, z, new ItemStack(DinocraftItems.CLOUD_CHESTPLATE)));
        }
        else if (entity instanceof EntitySkeleton && rand.nextInt(1500) < 1) 
        {
            event.getDrops().add(new EntityItem(entity.world, x, y, z, new ItemStack(DinocraftItems.KATANA)));
        }
        else if (entity instanceof EntityZombie && rand.nextInt(500) < 1)
        {
            event.getDrops().add(new EntityItem(entity.world, x, y, z, new ItemStack(DinocraftItems.SOUL_SCRATCHER)));
        }
    }
	
	@SubscribeEvent
    public static void onSpecialSpawn(SpecialSpawn event) 
	{
		EntityLivingBase entityliving = event.getEntityLiving();
		World world = event.getWorld();
		EnumDifficulty difficulty = world.getDifficulty();
		
		if (entityliving instanceof EntityZombie)
		{
			int i = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			int j = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			
			if (i < 16 && entityliving.getHeldItemMainhand().isEmpty())
			{
				entityliving.setHeldItem(EnumHand.MAIN_HAND, i < 1 ? new ItemStack(DinocraftItems.RAY_GUN) : i < 2 ? new ItemStack(DinocraftItems.TUSKERERS_SWORD) : i < 4 ? new ItemStack(DinocraftItems.KATANA) : i < 7 ? new ItemStack(DinocraftItems.CHLOROPHYTE_SWORD) : i < 10 ? new ItemStack(DinocraftItems.SHEEPLITE_SWORD) : i < 13 ? new ItemStack(DinocraftItems.SOUL_SCRATCHER) : i < 16 ? new ItemStack(DinocraftItems.LEAFERANG) : null);
			}
			
			if (j < 4)
			{
				entityliving.setItemStackToSlot(EntityEquipmentSlot.FEET, j < 1 ? new ItemStack(DinocraftItems.LEAFY_BOOTS) : j < 2 ? new ItemStack(DinocraftItems.MERCHANTS_LUCKY_BOOTS) : j < 4 ? new ItemStack(DinocraftItems.BLEVENT_BOOTS) : null);
			}
		}
		
		if (entityliving instanceof EntityZombie || entityliving instanceof EntitySkeleton)
		{
			int i = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			
			if (i < 3)
			{				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.RANIUM_BOOTS));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.RANIUM_LEGGINGS));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.RANIUM_CHESTPLATE));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.RANIUM_HELMET));
				}
			}
			else if (i < 6)
			{
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.CHLOROPHYTE_BOOTS));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.CHLOROPHYTE_LEGGINGS));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.CHLOROPHYTE_CHESTPLATE));
				}
				
				if (world.rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.CHLOROPHYTE_HELMET));
				}
			}
		}
	}
}
*/