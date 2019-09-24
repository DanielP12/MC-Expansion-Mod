package dinocraft.event;

import java.util.List;
import java.util.Random;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntityRayBullet;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.network.NetworkHandler;
import dinocraft.network.reach.MessageExtendedReachAttack;
import dinocraft.util.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class DinocraftFunctionEvents
{
	/** The server's existed ticks */
	private static int serverTick = 0;
	public static boolean flag = false;
	public static boolean toggle = false;
	public static int time = 0;
	public static boolean flag2 = true;
	
	/* Server Thread */
	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event)
	{		
		if (event.phase != Phase.END)
		{
			return;
		}
		
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		++serverTick;
		
		if (serverTick == 12000 /* 10 minutes */)
		{
			serverTick = 0;

			server.sendMessage(new TextComponentTranslation("commands.save.start", new Object[0]));

			if (server.getPlayerList() != null)
	        {
				PlayerList list = server.getPlayerList();
				list.sendMessage(new TextComponentString(TextFormatting.GRAY + (TextFormatting.ITALIC + "[Server: Saved the world]")));
				list.saveAllPlayerData();
	        }

	        try
	        {
	            for (int i = 0; i < server.worlds.length; ++i)
	            {
	                if (server.worlds[i] != null)
	                {
	                    WorldServer worldserver = server.worlds[i];
	                    boolean flag = worldserver.disableLevelSaving;
	                    worldserver.disableLevelSaving = false;
	                    worldserver.saveAllChunks(true, (IProgressUpdate) null);
	                    worldserver.disableLevelSaving = flag;
	                }
	            }
	        }
	        catch (MinecraftException exception)
	        {
				PlayerList list = server.getPlayerList();
	        	list.sendMessage(new TextComponentString(TextFormatting.GRAY + (TextFormatting.ITALIC + "[Server: Could not save the world]")));

				server.sendMessage(new TextComponentTranslation("commands.save.failed", new Object[] {exception.getMessage()}));
	        	return;
	        }
	        
			server.sendMessage(new TextComponentTranslation("commands.save.success", new Object[0]));
		}
		
		if (flag && toggle)
		{
			--time;
			
			PlayerList list = server.getPlayerList();
			
			if (list != null)
			{
				if (flag2)
				{
					list.sendMessage(new TextComponentString("Server shutting down in " + (time + 1) / 20 + " seconds"));
					flag2 = false;
				}
				
				if (time == 1200 || time == 1000 || time == 800 || time == 600 || time == 400 || time == 200 || time == 100 || time == 80 || time == 60 || time == 40 || time == 20)
				{
					list.sendMessage(new TextComponentString("Server shutting down in " + time / 20 + " seconds"));
				}
			}
			
			if (time == 0)
			{
				if (list != null)
				{
					list.sendMessage(new TextComponentString(TextFormatting.RED + "SHUTTING DOWN"));
				}
				
				flag = false;
				flag2 = true;
				server.initiateShutdown();
			}
		}
	}
	
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
				
				if (DinocraftEntity.getEntity(player).isWearing(Item.getItemFromBlock(Blocks.PUMPKIN), null, DinocraftItems.FEATHERY_UNDERWEAR, null) && player.inventory.hasItemStack(new ItemStack(Items.MELON)) && enderman.world.rand.nextInt(150) < 1)
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
		
		if (dead.world.isRemote)
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
				if (!dead.world.loadedEntityList.isEmpty() && entity instanceof EntityItem)
				{
					EntityItem entityitem = (EntityItem) entity;
				
					if (entityitem.getItem().getItem() == DinocraftItems.HEART)
					{
						for (String tag : entityitem.getTags())
						{
							if (tag.equals(dead.getUniqueID().toString()))
							{
								Random rand = entityitem.world.rand;

								for (int i = 0; i < 20; i++)
								{
									DinocraftServer.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, entityitem.world, entityitem.posX + (rand.nextFloat() * entityitem.width * 2.0F) - entityitem.width, 
											entityitem.posY + (rand.nextFloat() * entityitem.height), entityitem.posZ + (rand.nextFloat() * entityitem.width * 2.0F) - entityitem.width,
											rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, 0);
								}
							
								entityitem.setDead();
							}
						}
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
			if (entityliving.ticksExisted % 100 == 0)
			{
				entityliving.motionY = 0.485D;
			}
			
			if (!entityliving.world.isRemote && (entityliving instanceof EntityZombie || entityliving instanceof EntitySkeleton))
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entityliving);
				DinocraftEntityActions actions = dinoEntity.getActionsModule();
				
				if (entityliving.onGround || entityliving.isInWater() || entityliving.isInLava() || entityliving.isOnLadder())
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

					if (!actions.hasDoubleJumped() && entityliving.ticksExisted % (rand.nextInt(5) + 15) == 0 && entityliving.motionY < 0.01D)
					{
						actions.setHasDoubleJumped(true);
						
						PotionEffect effect = entityliving.getActivePotionEffect(MobEffects.JUMP_BOOST);
						entityliving.motionY = effect != null ? (effect.getAmplifier() * 0.095D) + 0.575D : 0.4875D + (0.01D * entityliving.world.rand.nextDouble());
						entityliving.motionX *= 1.015D;    
						entityliving.motionZ *= 1.015D;
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
		
		if (event.getEntityLiving() instanceof EntityMob)
		{
			EntityMob mob = (EntityMob) event.getEntityLiving();
			
			if (mob.getHeldItemMainhand().getItem() != null && mob.getHeldItemMainhand().getItem() == DinocraftItems.LEAFERANG && !mob.world.isRemote)
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(mob);
				dinoEntity.setShootingTick(dinoEntity.getShootingTick() + 1);
				
				if (dinoEntity.getShootingTick() >= 20)
				{				
					ItemStack stack = mob.getHeldItemMainhand();
					ItemStack copy = stack.copy();
					copy.setCount(1);
					EntityLeaferang leaferang = new EntityLeaferang(mob.world, mob, copy);
					leaferang.shoot(mob, mob.rotationPitch, mob.rotationYaw, 0.0F, mob.world.rand.nextFloat() + 0.75F, 0.75F);
					mob.world.spawnEntity(leaferang);
					mob.world.playSound(null, mob.posX, mob.posY, mob.posZ, SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (mob.world.rand.nextFloat() * 0.4F + 0.8F));
					stack.shrink(1);
				}
			}

			if (mob.getHeldItemMainhand().getItem() != null && mob.getHeldItemMainhand().getItem() == DinocraftItems.RAY_GUN && !mob.world.isRemote)
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(mob);
				dinoEntity.setShootingTick(dinoEntity.getShootingTick() + 1);
				
				if (dinoEntity.getShootingTick() >= 15)
				{
					ItemStack stack = mob.getHeldItemMainhand();
					stack.damageItem(1, mob);
					dinoEntity.setShootingTick(0);
					mob.setActiveHand(EnumHand.MAIN_HAND);
					EntityRayBullet ball = new EntityRayBullet(mob, 0.001F);
					ball.shoot(mob, mob.rotationPitch, mob.rotationYaw, 0.0F, 5.0F, 0.75F);
					Vec3d vector = mob.getLookVec();
		            ball.motionX = vector.x * 3.0D;
		            ball.motionY = vector.y * 3.0D;
		            ball.motionZ = vector.z * 3.0D;
					mob.world.spawnEntity(ball);
					
					mob.world.playSound(null, mob.getPosition(), DinocraftSoundEvents.RAY_GUN_SHOT, SoundCategory.NEUTRAL, 3.0F, mob.world.rand.nextFloat() + 0.5F);
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
	public static void onLivingHurt(LivingHurtEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			
			if (!player.isActiveItemStackBlocking() && !player.world.isRemote)
			{
				Random rand = player.world.rand;
				
				for (int i = 0; i < 50; ++i)
                {
					DinocraftEntity.getEntity(player).spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX + (rand.nextFloat() * player.width * 2.0F) - player.width, 
                			player.posY + 0.5D + (rand.nextFloat() * player.height), player.posZ + (rand.nextFloat() * player.width * 2.0F) - player.width, 
                			rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, rand.nextGaussian() * 0.0015D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
                }
				
				player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 0.75F);
			}
		}
	}	
	
	@SubscribeEvent
	public static void onBreak(BreakEvent event)
	{
		Block block = event.getState().getBlock();
		
		if (block == Blocks.LEAVES || block == Blocks.LEAVES2)
		{
			World world = event.getWorld();
			
			if (world.rand.nextInt(10000) < 1)
            {
				BlockPos pos = event.getPos();
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DinocraftItems.LEAF, 1)));
            }
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPre(Pre event)
	{
		if (DinocraftEntity.getEntity(event.getEntityPlayer()).isVanished())
		{
			event.setCanceled(true);
		}
	}
	
	/*
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityZombie)
		{
			EntityZombie zombie = (EntityZombie) event.getEntityLiving();
			List list = zombie.world.getEntitiesWithinAABBExcludingEntity(zombie, new AxisAlignedBB(zombie.posX, zombie.posY, zombie.posZ, zombie.posX + 1.0D, zombie.posY + 1.0D, zombie.posZ + 1.0D).expand(2.0D, 4.0D, 2.0D));
			Iterator iterator = list.iterator();	
			
			if (!list.isEmpty())
			{
				do
				{					
					if (!iterator.hasNext())
					{
						break;
					}
					
					Entity entity = (Entity) iterator.next();
					
					if (!(entity instanceof EntityItem))
					{
						continue;
					}
					
					EntityItem entityitem = (EntityItem) entity;
					Item item = entityitem.getItem().getItem();
					
					if (!zombie.world.isRemote && item != null && item == DinocraftItems.HEART && entityitem.getOwner().equals(zombie.getUniqueID().toString()))
					{	
						zombie.heal(2.0F);
						zombie.world.playSound(null, zombie.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.NEUTRAL, 0.5F, ((zombie.getRNG().nextFloat() - zombie.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
						entityitem.setDead();
					}
				}
					
				while (true);
			}
		}
	}
	*/
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityLiving)
		{	
			EntityLiving entityliving = (EntityLiving) event.getEntityLiving();
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
		if (event.getSource().getTrueSource() instanceof EntityLiving)
		{
			EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
			
			if (attacker.getHeldItemMainhand().getItem() == DinocraftItems.TUSKERERS_SWORD)
			{
				EntityLivingBase target = event.getEntityLiving();
				
				if (target.world.rand.nextInt(2) < 1)
				{
					EntityItem heart = new EntityItem(target.world, target.posX, target.posY, target.posZ, new ItemStack(DinocraftItems.HEART, 1));
					heart.setInfinitePickupDelay();
					heart.addTag(attacker.getUniqueID().toString());
					heart.setOwner(attacker.getUniqueID().toString());
					target.world.spawnEntity(heart);
				}
			}
			
			if (attacker.getHeldItemMainhand().getItem() == DinocraftItems.SHEEPLITE_SWORD)
			{
				EntityLivingBase target = event.getEntityLiving();

				if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
				{
					Vec3d vector = attacker.getLookVec().normalize();
			        target.motionX = vector.x;
			        target.motionY = vector.y + target.world.rand.nextDouble();
			        target.motionZ = vector.z;	
				}
			}
			
			if (attacker.getHeldItemMainhand().getItem() == DinocraftItems.CHLOROPHYTE_SWORD)
			{
				EntityLivingBase target = event.getEntityLiving();

				if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
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
    public static void onLivingSpawn(LivingSpawnEvent event) 
	{
		EntityLivingBase entityliving = event.getEntityLiving();
		Random rand = entityliving.world.rand;
		EnumDifficulty difficulty = entityliving.world.getDifficulty();
		
		if (entityliving instanceof EntityZombie)
		{
			int i = rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			int j = rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			
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
			int i = rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
			
			if (i < 3)
			{				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.RANIUM_BOOTS));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.RANIUM_LEGGINGS));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.RANIUM_CHESTPLATE));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.RANIUM_HELMET));
				}
			}
			else if (i < 6)
			{
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.CHLOROPHYTE_BOOTS));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.CHLOROPHYTE_LEGGINGS));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.CHLOROPHYTE_CHESTPLATE));
				}
				
				if (rand.nextInt(2) > 0)
				{
					entityliving.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.CHLOROPHYTE_HELMET));
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onMouse(MouseEvent event)
	{ 
	    if (event.getButton() == 0 && event.isButtonstate())
	    {	    	
	        EntityPlayer player = Minecraft.getMinecraft().player;
	        
	        if (player != null)
	        {
	        	/*
	            ItemStack stack = player.getHeldItemMainhand();
	            IExtendedReach extendedreach;
	            
	            if (stack != null)
	            {
	            	extendedreach = stack.getItem() instanceof IExtendedReach ? (IExtendedReach) stack.getItem() : null;
	   
	                if (extendedreach != null)
	                {
	                    RayTraceResult result = this.getMouseOverExtended(extendedreach.getReach()); 

	                    if (result != null && result.entityHit != null && result.entityHit != player)
	                    {
	                        NetworkHandler.sendToServer(new MessageExtendedReachAttack(result.entityHit.getEntityId()));
	                    }
	                }
	            }
	            */
	            DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	            
	            if (player.getHeldItemMainhand() != null && dinoEntity.hasExtraReach())
	            {	   
	                RayTraceResult result = getMouseOverExtended(dinoEntity.getAttackReach()); 

	                if (result != null && result.entityHit != null && result.entityHit != player)
	                {
	                    NetworkHandler.sendToServer(new MessageExtendedReachAttack(result.entityHit.getEntityId()));
	                }
	            }
	        }
	    }
	}
	        
	public static RayTraceResult getMouseOverExtended(double distance)
	{
	    Minecraft minecraft = FMLClientHandler.instance().getClient();
	    Entity renderviewentity = minecraft.getRenderViewEntity();
	    AxisAlignedBB box = new AxisAlignedBB(renderviewentity.posX - 0.5D, renderviewentity.posY - 0.0D, renderviewentity.posZ - 0.5D,
	    		renderviewentity.posX + 0.5D, renderviewentity.posY + 1.5D, renderviewentity.posZ + 0.5D);
	    RayTraceResult result = null;
	    
	    if (minecraft.world != null)
	    {
	        double var2 = distance;
	        result = renderviewentity.rayTrace(var2, 0.0F);
	        double calcdist = var2;
	        Vec3d pos = renderviewentity.getPositionEyes(0.0F);
	        var2 = calcdist;
	        
	        if (result != null)
	        {
	            calcdist = result.hitVec.distanceTo(pos);
	        }
	         
	        Vec3d lookvec = renderviewentity.getLook(0.0F);
	        Vec3d var8 = pos.addVector(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
	        Entity pointedentity = null;
	        float var9 = 1.0F;
	        List<Entity> list = minecraft.world.getEntitiesWithinAABBExcludingEntity(renderviewentity, box.grow(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2).expand(var9, var9, var9));
	        double d = calcdist;
	            
	        for (Entity entity : list)
	        {
	            if (entity.canBeCollidedWith())
	            {
	                float bordersize = entity.getCollisionBorderSize();
	                AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2.0D, entity.posY, entity.posZ - entity.width / 2.0D, 
	                		entity.posX + entity.width / 2.0D, entity.posY + entity.height, entity.posZ + entity.width / 2.0D);
	                aabb.expand(bordersize, bordersize, bordersize);
	                RayTraceResult result2 = aabb.calculateIntercept(pos, var8);
	                    
	                if (aabb.contains(pos))
	                {
	                    if (0.0D < d || d == 0.0D)
	                    {
	                        pointedentity = entity;
	                        d = 0.0D;
	                    }
	                } 
	                else if (result2 != null)
	                {
	                    double d1 = pos.distanceTo(result2.hitVec);
	                    
	                    if (d1 < d || d == 0.0D)
	                    {
	                        pointedentity = entity;
	                        d = d1;
	                    }
	                }
	            }
	        }
	           
	        if (pointedentity != null && (d < calcdist || result == null))
	        {
	        	result = new RayTraceResult(pointedentity);
	        }
	    }
	    
	    return result;
	}
	
	@SubscribeEvent
	public static void onServerChat(ServerChatEvent event)
	{
		EntityPlayerMP player = event.getPlayer();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (player.getServer().isDedicatedServer())
		{
			if (dinoEntity.hasOpLevel(2))
			{
				String prefix = dinoEntity.hasOpLevel(4) ? TextFormatting.DARK_RED + "[OWNER] " : dinoEntity.hasOpLevel(3) ? TextFormatting.RED + "[ADMIN] " : dinoEntity.hasOpLevel(2) ? TextFormatting.DARK_GREEN + "[MODERATOR] " : "";
				event.setComponent(new TextComponentString(prefix + player.getName() + TextFormatting.RESET + ": " + event.getMessage()));
			}
			else
			{
				event.setComponent(new TextComponentString(TextFormatting.GRAY + player.getName() + ": " + event.getMessage()));
			}
		}
	}
}