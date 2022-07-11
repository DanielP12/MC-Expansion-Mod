package dinocraft.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.command.server.CommandShutDown;
import dinocraft.entity.EntityLeaferang;
import dinocraft.entity.EntitySplicentsThrowingKnife;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.item.DinocraftWeapon;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketItemPickupEffect;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class DinocraftFunctionEvents
{
	public static boolean flag = false;
	public static boolean toggle = false;
	public static int time = 0;
	public static boolean flag2 = true;

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event)
	{
		if (event.phase != Phase.END)
		{
			return;
		}
		
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		if (server.isDedicatedServer() && DinocraftConfig.WORLD_SAVING && server.getTickCounter() % (DinocraftConfig.WORLD_SAVING_INTERVAL * 20) == 0)
		{
			server.commandManager.executeCommand(server, "save-all");
		}
		
		if (flag && toggle)
		{
			time--;
			
			PlayerList list = server.getPlayerList();
			
			if (list != null)
			{
				if (flag2)
				{
					list.sendMessage(new TextComponentString("Server shutting down in " + (time + 1) / 20 + " seconds"));
					flag2 = false;
				}
				
				if (time <= 12000 && time != 0 && (time % 200 == 0 || time == 100 || time == 80 || time == 60 || time == 40 || time == 20)) //TODO: allow to handle in config?
				{
					int seconds = time / 20;
					int minutes = seconds / 60;
					seconds %= 60;
					int hours = minutes / 60;
					minutes %= 60;
					int days = hours / 24;
					hours %= 24;
					int[] times = new int[] {days, hours, minutes, seconds};
					int pos = 0;
					
					for (int time : times)
					{
						if (time > 0)
						{
							pos++;
						}
					}
					
					String[] display = new String[pos];
					int posDisplay = 0;
					
					for (int i = 0; i < times.length; i++)
					{
						if (times[i] > 0)
						{
							display[posDisplay++] = times[i] + (i == 0 ? " days" : i == 1 ? " hours" : i == 2 ? " minutes" : " seconds");
						}
					}
					
					String time = "";
					
					for (int i = 0; i < display.length; i++)
					{
						time += i == display.length - 1 ? display[i] : display[i] + ", ";
					}

					list.sendMessage(new TextComponentString("Server shutting down in " + time));
				}
			}
			
			if (time == 0)
			{
				if (list != null)
				{
					list.sendMessage(new TextComponentString(TextFormatting.RED + "SERVER SHUTTING DOWN"));
					CommandBase.notifyCommandListener(server, new CommandShutDown(), "commands.shutdown.success.done");
				}
				
				flag = false;
				flag2 = true;
				server.initiateShutdown();
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		EntityLivingBase dead = event.getEntityLiving();
		Entity immediateSource = event.getSource().getImmediateSource();
		Entity killer = event.getSource().getTrueSource();
		
		//		if (dead.world.isRemote && DinocraftConfig.ENTITY_BLOOD_EFFECTS)
		//		{
		//			for (int i = 0; i < 50; i++)
		//			{
		//				dead.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, dead.posX, dead.posY + (dead.height - 0.25D), dead.posZ,
		//						Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
		//			}
		//		}
		
		if (!dead.world.isRemote)
		{
			List<EntityItem> entities = dead.world.getEntities(EntityItem.class, entityitem ->
			{
				Item item = entityitem.getItem().getItem();
				return (item == DinocraftItems.HEART || item == DinocraftItems.ABSORPTION_HEART) && entityitem.getTags().contains(dead.getUniqueID().toString());
			});
			
			for (EntityItem entity : entities)
			{
				float f0 = entity.height / 2.0F;
				DinocraftServer.spawnParticles(EnumParticleTypes.EXPLOSION_NORMAL, entity.world, 10, entity.posX, entity.posY + f0, entity.posZ, entity.width, f0, entity.width,
						entity.world.rand.nextGaussian() * 0.02D, entity.world.rand.nextGaussian() * 0.02D, entity.world.rand.nextGaussian() * 0.02D);
				entity.setDead();
			}
		}
		
		if (!dead.world.isRemote && immediateSource instanceof EntityArrow && immediateSource.getTags().contains("HeartArrow") && killer.world.rand.nextInt(2) == 0)
		{
			EntityItem heart = new EntityItem(killer.world, dead.posX, dead.posY, dead.posZ, new ItemStack(DinocraftItems.ABSORPTION_HEART, 1));

			if (!(killer instanceof EntityPlayer))
			{
				heart.setInfinitePickupDelay();
			}

			String id = killer.getUniqueID().toString();
			heart.setOwner(id);
			heart.addTag(id);
			killer.world.spawnEntity(heart);
		}
	}

	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		EntityLiving entity = (EntityLiving) event.getEntityLiving();
		ItemStack boots = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		if (boots.getItem() == DinocraftItems.LEAFY_BOOTS)
		{
			if (entity.ticksExisted % 80 == 0 && entity.onGround && !entity.isInLava() && !entity.isInWater() && !entity.isOnLadder() && !entity.isRiding())
			{
				entity.motionY = 0.488D;
				entity.motionX *= 1.025D;
				entity.motionZ *= 1.025D;
			}
			
			if (!entity.world.isRemote && (entity instanceof EntityZombie || entity instanceof EntitySkeleton))
			{
				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
				DinocraftEntityActions actions = dinoEntity.getActionsModule();
				
				if (entity.motionY > 0.45D && (entity.onGround || entity.isInWater() || entity.isInLava() || entity.isOnLadder() || entity.isRiding()))
				{
					actions.setHasJumpedInAir(true);
					actions.setCanJumpInAir(false);
				}

				if (!entity.onGround)
				{
					if (!actions.canJumpInAir())
					{
						actions.setHasJumpedInAir(false);
						actions.setCanJumpInAir(true);
					}
					else if (!actions.hasJumpedInAir() && entity.motionY < -0.33D)
					{
						actions.setHasJumpedInAir(true);
						dinoEntity.setFallDamageReductionAmount(5.0F);
						PotionEffect effect = entity.getActivePotionEffect(MobEffects.JUMP_BOOST);
						double d0 = 0.5D;
						
						if (effect != null)
						{
							d0 += 0.075D + effect.getAmplifier() * 0.1D;
						}
						
						entity.motionY = d0;
						entity.motionX *= 1.05D;
						entity.motionZ *= 1.05D;
						entity.fallDistance = 0.0F;
						float f0 = entity.width * 1.33F;
						DinocraftServer.spawnParticles(EnumParticleTypes.CLOUD, entity.world, 25, entity.posX, entity.posY - 0.2F * entity.height, entity.posZ, f0, 0.1F * entity.height, f0,
								entity.world.rand.nextGaussian() * 0.033D, entity.world.rand.nextGaussian() * 0.033D, entity.world.rand.nextGaussian() * 0.033D);
						entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.HOSTILE, 1.0F, entity.world.rand.nextFloat() + 0.25F);
						boots.damageItem(1, entity);
					}
				}
			}
		}
		
		if (entity instanceof EntityMob && !entity.world.isRemote)
		{
			ItemStack mainhandItem = entity.getHeldItemMainhand(), offhandItem = entity.getHeldItemOffhand();
			ItemStack leaferang = mainhandItem.getItem() == DinocraftItems.LEAFERANG ? mainhandItem : offhandItem.getItem() == DinocraftItems.LEAFERANG ? offhandItem : null;
			
			if (leaferang != null && entity.ticksExisted % 20 == 0)
			{
				float vel = entity.world.rand.nextFloat() * 0.25F + 0.5F;
				EntityLeaferang entityleaferang = new EntityLeaferang(entity.world, entity, leaferang, mainhandItem == leaferang ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, vel);
				entityleaferang.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, vel, 1.0F);
				entity.world.spawnEntity(entityleaferang);
				entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));
				leaferang.shrink(1);
			}

			ItemStack knife = mainhandItem.getItem() == DinocraftItems.SPLICENTS_THROWING_KNIFE ? mainhandItem : offhandItem.getItem() == DinocraftItems.SPLICENTS_THROWING_KNIFE ? offhandItem : null;
			
			if (knife != null && entity.ticksExisted % 80 == 0)
			{
				EntitySplicentsThrowingKnife splicentsthrowingknife = new EntitySplicentsThrowingKnife(entity, 0.001F);
				splicentsthrowingknife.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, entity.world.rand.nextFloat() * 0.5F + 1.0F, 1.0F);
				entity.world.spawnEntity(splicentsthrowingknife);
				entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));
				knife.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public static void onRender(RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
		{
			Minecraft minecraft = Minecraft.getMinecraft();
			GameSettings gamesettings = minecraft.gameSettings;
			//			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(minecraft.player);
			//
			//			if (dinoEntity.hasModifiedReach())
			//			{
			//				event.setCanceled(true);
			//
			//				if (gamesettings.thirdPersonView == 0)
			//				{
			//					if (minecraft.playerController.isSpectator() && minecraft.pointedEntity == null)
			//					{
			//						RayTraceResult raytraceresult = minecraft.objectMouseOver;
			//
			//						if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
			//						{
			//							return;
			//						}
			//
			//						BlockPos blockpos = raytraceresult.getBlockPos();
			//						IBlockState state = minecraft.world.getBlockState(blockpos);
			//
			//						if (!state.getBlock().hasTileEntity(state) || !(minecraft.world.getTileEntity(blockpos) instanceof IInventory))
			//						{
			//							return;
			//						}
			//					}
			//
			//					ScaledResolution resolution = new ScaledResolution(minecraft);
			//					int width = resolution.getScaledWidth();
			//					int height = resolution.getScaledHeight();
			//					minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
			//					GlStateManager.enableBlend();
			//
			//					if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !minecraft.player.hasReducedDebug() && !gamesettings.reducedDebugInfo)
			//					{
			//						GlStateManager.pushMatrix();
			//						GlStateManager.translate(width / 2, height / 2, 0);
			//						Entity entity = minecraft.getRenderViewEntity();
			//						GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * event.getPartialTicks(), -1.0F, 0.0F, 0.0F);
			//						GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * event.getPartialTicks(), 0.0F, 1.0F, 0.0F);
			//						GlStateManager.scale(-1.0F, -1.0F, -1.0F);
			//						OpenGlHelper.renderDirections(10);
			//						GlStateManager.popMatrix();
			//					}
			//					else
			//					{
			//						double range = dinoEntity.getAttackReach();
			//						GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			//						GlStateManager.enableAlpha();
			//						//Draws the crosshair
			//						minecraft.ingameGUI.drawTexturedModalRect(width / 2 - 7, height / 2 - 7, 0, 0, 16, 16);
			//
			//						if (minecraft.gameSettings.attackIndicator == 1)
			//						{
			//							float f = minecraft.player.getCooledAttackStrength(0.0F);
			//							boolean flag = false;
			//							RayTraceResult result = getMouseOver(range);
			//
			//							if (result != null && result.entityHit != null && result.entityHit instanceof EntityLivingBase && f >= 1.0F)
			//							{
			//								flag = ((EntityLivingBase) result.entityHit).isEntityAlive();
			//							}
			//
			//							int i = height / 2 - 7 + 16;
			//							int j = width / 2 - 8;
			//
			//							if (flag)
			//							{
			//								minecraft.ingameGUI.drawTexturedModalRect(j, i, 68, 94, 16, 16);
			//							}
			//							else if (f < 1.0F)
			//							{
			//								minecraft.ingameGUI.drawTexturedModalRect(j, i, 36, 94, 16, 4);
			//								minecraft.ingameGUI.drawTexturedModalRect(j, i, 52, 94, (int) (f * 17.0F), 4);
			//							}
			//						}
			//					}
			//				}
			//			}
			/*else */if (minecraft.player.getHeldItemMainhand().getItem() instanceof DinocraftWeapon)
			{
				event.setCanceled(true);
				
				if (gamesettings.thirdPersonView == 0)
				{
					if (minecraft.playerController.isSpectator() && minecraft.pointedEntity == null)
					{
						RayTraceResult raytraceresult = minecraft.objectMouseOver;

						if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
						{
							return;
						}

						BlockPos blockpos = raytraceresult.getBlockPos();
						IBlockState state = minecraft.world.getBlockState(blockpos);

						if (!state.getBlock().hasTileEntity(state) || !(minecraft.world.getTileEntity(blockpos) instanceof IInventory))
						{
							return;
						}
					}
					
					ScaledResolution resolution = new ScaledResolution(minecraft);
					int width = resolution.getScaledWidth();
					int height = resolution.getScaledHeight();
					minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
					GlStateManager.enableBlend();

					if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !minecraft.player.hasReducedDebug() && !gamesettings.reducedDebugInfo)
					{
						GlStateManager.pushMatrix();
						GlStateManager.translate(width / 2, height / 2, 0);
						Entity entity = minecraft.getRenderViewEntity();
						GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * event.getPartialTicks(), -1.0F, 0.0F, 0.0F);
						GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * event.getPartialTicks(), 0.0F, 1.0F, 0.0F);
						GlStateManager.scale(-1.0F, -1.0F, -1.0F);
						OpenGlHelper.renderDirections(10);
						GlStateManager.popMatrix();
					}
					else
					{
						double range = ((DinocraftWeapon) minecraft.player.getHeldItemMainhand().getItem()).getRange();
						GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						GlStateManager.enableAlpha();
						//Draws the crosshair
						minecraft.ingameGUI.drawTexturedModalRect(width / 2 - 7, height / 2 - 7, 0, 0, 16, 16);

						if (minecraft.gameSettings.attackIndicator == 1)
						{
							float f = minecraft.player.getCooledAttackStrength(0.0F);
							boolean flag = false;
							RayTraceResult result = DinocraftEntity.getEntityTrace(range);
							
							if (result != null && result.entityHit != null && result.entityHit instanceof EntityLivingBase && f >= 1.0F)
							{
								flag = minecraft.player.getCooldownPeriod() > 5.0F;
								flag = flag & ((EntityLivingBase) result.entityHit).isEntityAlive();
							}

							int i = height / 2 - 7 + 16;
							int j = width / 2 - 8;

							if (flag)
							{
								minecraft.ingameGUI.drawTexturedModalRect(j, i, 68, 94, 16, 16);
							}
							else if (f < 1.0F)
							{
								minecraft.ingameGUI.drawTexturedModalRect(j, i, 36, 94, 16, 4);
								minecraft.ingameGUI.drawTexturedModalRect(j, i, 52, 94, (int) (f * 17.0F), 4);
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
	public static void onLivingHurt(LivingHurtEvent event)
	{
		//		EntityLivingBase living = event.getEntityLiving();
		//
		//		if (living instanceof EntityPlayer && DinocraftConfig.PLAYER_BLOOD_EFFECTS && !living.isActiveItemStackBlocking() && !living.world.isRemote)
		//		{
		//			for (int i = 0; i < 50; i++)
		//			{
		//				DinocraftServer.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, living.world, living.posX + living.world.rand.nextFloat() * living.width * 2.0F - living.width,
		//						living.posY + 0.5D + living.world.rand.nextFloat() * living.height, living.posZ + living.world.rand.nextFloat() * living.width * 2.0F - living.width,
		//						living.world.rand.nextGaussian() * 0.0015D, living.world.rand.nextGaussian() * 0.0015D, living.world.rand.nextGaussian() * 0.0015D, Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
		//			}
		//
		//			living.world.playSound(null, living.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.NEUTRAL, 1.0F, living.world.rand.nextFloat() + 0.75F);
		//		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(living);
		if (living instanceof EntityLiving)
		{
			((EntityLiving) living).setCanPickUpLoot(true);
		}//TODO: remove
		
		if (!living.world.isRemote)
		{
			if (living.getHealth() < 6.0F && living.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == DinocraftItems.MAGATIUM_BOOTS)
			{
				living.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 15, 2, true, true));
			}

			if (!(living instanceof EntityPlayer))
			{
				AxisAlignedBB axisalignedbb = living.getEntityBoundingBox().grow(living.width, 0.5D, living.width);
				List<Entity> list = living.world.getEntitiesInAABBexcluding(living, axisalignedbb, entity ->
				{
					if (entity instanceof EntityItem)
					{
						EntityItem entityitem = (EntityItem) entity;
						Item item = entityitem.getItem().getItem();
						return (item == DinocraftItems.HEART || item == DinocraftItems.ABSORPTION_HEART) && living.getUniqueID().toString().equals(entityitem.getOwner());
					}
					
					return false;
				});
				
				for (Entity entity : list)
				{
					PacketHandler.sendToAllAround(new SPacketItemPickupEffect((EntityItem) entity, living), new TargetPoint(living.world.provider.getDimension(), living.posX, living.posY, living.posZ, 64.0D));
					living.world.playSound(null, living.getPosition(), DinocraftSoundEvents.GRAB, SoundCategory.AMBIENT, 0.5F, ((living.world.rand.nextFloat() - living.world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					entity.setDead();
					
					if (((EntityItem) entity).getItem().getItem() == DinocraftItems.HEART)
					{
						living.heal(2.0F);
					}
					else
					{
						living.setAbsorptionAmount(living.getAbsorptionAmount() + 1.0F);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		Entity attackerEntity = event.getSource().getTrueSource();
		
		if (attackerEntity instanceof EntityLivingBase && !(attackerEntity instanceof EntityPlayer))
		{
			EntityLivingBase attacker = (EntityLivingBase) attackerEntity;
			ItemStack stack = attacker.getHeldItemMainhand();
			Item item = stack.getItem();
			
			if (item == DinocraftItems.TUSKERS_SWORD || item == DinocraftItems.LEAFY_DAGGER || item == DinocraftItems.MAGATIUM_SCYTHE
					|| item == DinocraftItems.SPLICENTS_BLADE || item == DinocraftItems.JESTERS_SWORD)
			{
				item.hitEntity(stack, event.getEntityLiving(), attacker);
			}
			else if (item == DinocraftItems.DREMONITE_SWORD)
			{
				item.onEntitySwing(attacker, stack);
				event.setCanceled(true);
			}
		}

		Entity entity = event.getSource().getImmediateSource();

		if (entity != null && !entity.world.isRemote)
		{
			EntityLivingBase living = event.getEntityLiving();

			if (DinocraftEntity.getEntity(living).getActionsModule().isElectrified())
			{
				double d = entity.posX - living.posX;
				double d1;
				
				for (d1 = entity.posZ - living.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
				{
					d = (Math.random() - Math.random()) * 0.01D;
				}
				
				float f = MathHelper.sqrt(d * d + d1 * d1);
				entity.motionX /= 2.0D;
				entity.motionY /= 2.0D;
				entity.motionZ /= 2.0D;
				entity.motionX += d / f;
				entity.motionY += 0.5D;
				entity.motionZ += d1 / f;
				entity.world.playSound(null, entity.getPosition(), entity.world.rand.nextBoolean() ? DinocraftSoundEvents.ZAP : DinocraftSoundEvents.ZAP2, SoundCategory.NEUTRAL, 0.5F, entity.world.rand.nextFloat() + 0.5F);
				entity.attackEntityFrom(DamageSource.causeMobDamage(living), 1.0F);
				float f0 = entity.height / 2.0F;
				DinocraftServer.spawnElectricParticles(entity.world, 20, 15, 10, entity.posX, entity.posY + f0, entity.posZ, entity.width, f0, entity.width);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		Random rand = living.world.rand;
		BlockPos pos = living.getPosition();
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		List<EntityItem> drops = event.getDrops();
		
		if ((living instanceof EntityPig || living instanceof EntityPigZombie) && rand.nextInt(3) < 1)
		{
			drops.add(new EntityItem(living.world, x, y, z, new ItemStack(DinocraftItems.TUSK, rand.nextInt(2) + 1)));
		}
		else if (living instanceof EntityCreeper && rand.nextInt(1000) < 1)
		{
			drops.add(new EntityItem(living.world, x, y, z, new ItemStack(DinocraftItems.CLOUD_CHESTPLATE)));
		}
		else if (living instanceof EntitySkeleton && rand.nextInt(1500) < 1)
		{
			//			drops.add(new EntityItem(living.world, x, y, z, new ItemStack(DinocraftItems.KATANA)));
		}
		else if (living instanceof EntityZombie && rand.nextInt(500) < 1)
		{
			drops.add(new EntityItem(living.world, x, y, z, new ItemStack(DinocraftItems.SOUL_SCRATCHER)));
		}
		
		List<EntityItem> toRemove = new ArrayList<>();
		
		for (EntityItem item : drops)
		{
			if (item.getItem().getItem() == DinocraftItems.TUSKERS_JUG)
			{
				toRemove.add(item);
			}
		}
		
		if (!toRemove.isEmpty())
		{
			living.world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1.0F, 0.5F);
			drops.removeAll(toRemove);
		}
	}
	
	protected static void setEquipmentBasedOnDifficulty(EntityLiving entity, DifficultyInstance difficulty)
	{
		float k = difficulty.getClampedAdditionalDifficulty();
		
		if (entity.world.rand.nextFloat() < 0.075F * k)
		{
			int i = entity.world.rand.nextInt(4);
			float f = entity.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;
			boolean flag = true;

			for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
			{
				if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
				{
					if (!flag && entity.world.rand.nextFloat() < f)
					{
						break;
					}

					flag = false;

					if (entity.getItemStackFromSlot(slot).isEmpty())
					{
						Item item = getArmorByIndex(slot, i);

						if (item != null)
						{
							entity.setItemStackToSlot(slot, new ItemStack(item));
						}
					}
				}
			}
		}

		if (entity.world.rand.nextFloat() < 0.05F * k)
		{
			EntityEquipmentSlot slot = EntityEquipmentSlot.values()[entity.world.rand.nextInt(4) + 2];
			
			if (entity.getItemStackFromSlot(slot).isEmpty())
			{
				Item item = getRandomSpecialArmorForSlot(slot);

				if (item != null)
				{
					entity.setItemStackToSlot(slot, new ItemStack(item));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onSpawn(SpecialSpawn event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		World world = event.getWorld();
		EnumDifficulty difficulty = world.getDifficulty();

		if (entity instanceof EntityZombie || entity instanceof EntitySkeleton)
		{
			setEquipmentBasedOnDifficulty((EntityLiving) entity, world.getDifficultyForLocation(entity.getPosition()));
		}

		if (entity instanceof EntityZombie)
		{
			setHeldWeaponBasedOnDifficulty((EntityLiving) entity, difficulty);
		}
	}

	protected static void setHeldWeaponBasedOnDifficulty(EntityLiving entity, EnumDifficulty difficulty)
	{
		if (entity.world.rand.nextFloat() < (difficulty == EnumDifficulty.HARD ? 0.01F /* 1% */: 0.002F /* 0.2% */))
		{
			int i = entity.world.rand.nextInt(6);

			if (entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty())
			{
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(
						i == 0 ? DinocraftItems.TUSKERS_SWORD :
							i == 1 ? DinocraftItems.LEAFY_DAGGER :
								i == 2 ? DinocraftItems.MAGATIUM_SCYTHE :
									i == 3 ? DinocraftItems.SPLICENTS_BLADE :
										i == 4 ? DinocraftItems.JESTERS_SWORD :
											DinocraftItems.DREMONITE_SWORD));
			}
		}
	}
	
	@Nullable
	public static Item getRandomSpecialArmorForSlot(EntityEquipmentSlot slot)
	{
		Random rand = new Random();
		
		switch (slot)
		{
			case HEAD:
				
				return DinocraftItems.JESTERS_HAT;
				
			case CHEST:
				
				return DinocraftItems.SPLICENTS_CHESTPLATE;
				
			case LEGS:
				
				return DinocraftItems.TUSKERS_OLD_RAGS;
				
			case FEET:
				
				int i = rand.nextInt(3);
				
				if (i == 0)
				{
					return DinocraftItems.LEAFY_BOOTS;
				}
				else if (i == 1)
				{
					return DinocraftItems.MAGATIUM_BOOTS;
				}
				else
				{
					return DinocraftItems.DREMONITE_BOOTS;
				}
				
			default:
				return null;
		}
	}

	@Nullable
	public static Item getArmorByIndex(EntityEquipmentSlot slot, int index)
	{
		switch (slot)
		{
			case HEAD:
				
				if (index == 0)
				{
					return DinocraftItems.OLITROPY_HELMET;
				}
				else if (index == 1)
				{
					return DinocraftItems.WADRONITE_HELMET;
				}
				else if (index == 2)
				{
					return DinocraftItems.DRACOLITE_HELMET;
				}
				else if (index == 3)
				{
					return DinocraftItems.ARRANIUM_HELMET;
				}
				
			case CHEST:
				
				if (index == 0)
				{
					return DinocraftItems.OLITROPY_CHESTPLATE;
				}
				else if (index == 1)
				{
					return DinocraftItems.WADRONITE_CHESTPLATE;
				}
				else if (index == 2)
				{
					return DinocraftItems.DRACOLITE_CHESTPLATE;
				}
				else if (index == 3)
				{
					return DinocraftItems.ARRANIUM_CHESTPLATE;
				}
				
			case LEGS:
				
				if (index == 0)
				{
					return DinocraftItems.OLITROPY_LEGGINGS;
				}
				else if (index == 1)
				{
					return DinocraftItems.WADRONITE_LEGGINGS;
				}
				else if (index == 2)
				{
					return DinocraftItems.DRACOLITE_LEGGINGS;
				}
				else if (index == 3)
				{
					return DinocraftItems.ARRANIUM_LEGGINGS;
				}
				
			case FEET:
				
				if (index == 0)
				{
					return DinocraftItems.OLITROPY_BOOTS;
				}
				else if (index == 1)
				{
					return DinocraftItems.WADRONITE_BOOTS;
				}
				else if (index == 2)
				{
					return DinocraftItems.DRACOLITE_BOOTS;
				}
				else if (index == 3)
				{
					return DinocraftItems.ARRANIUM_BOOTS;
				}
				
			default:
				return null;
		}
	}

	
	//	@SubscribeEvent
	//	public static void onSpecialSpawn(SpecialSpawn event)
	//	{
	//		EntityLivingBase living = event.getEntityLiving();
	//		World world = event.getWorld();
	//		EnumDifficulty difficulty = world.getDifficulty();
	//
	//		if (living instanceof EntityZombie)
	//		{
	//			int i = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
	//			int j = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
	//			int l = world.rand.nextInt(difficulty == difficulty.EASY ? 10000 : difficulty == difficulty.NORMAL ? 5000 : 2500);
	//
	//			if (l < 1)
	//			{
	//				living.setHeldItem(living.getHeldItemMainhand().isEmpty() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, new ItemStack(DinocraftItems.TUSKERS_JUG));
	//			}
	//
	//			if (i < 16 && living.getHeldItemMainhand().isEmpty())
	//			{
	//				int k = world.rand.nextInt(16);
	//
	//				if (k < 1)
	//				{
	//					living.setHeldItem(EnumHand.OFF_HAND, new ItemStack(DinocraftItems.TUSKERS_JUG));
	//				}
	//
	//				//TODO: implement				living.setHeldItem(EnumHand.MAIN_HAND, i < 1 ? new ItemStack(DinocraftItems.FLARE_GUN) : i < 2 ? new ItemStack(DinocraftItems.TUSKERS_SWORD) : i < 4 ? new ItemStack(DinocraftItems.KATANA) : i < 7 ? new ItemStack(DinocraftItems.CHLOROPHYTE_SWORD) : i < 10 ? new ItemStack(DinocraftItems.DREADED_SWORD) : i < 13 ? new ItemStack(DinocraftItems.SOUL_SCRATCHER) : i < 16 ? new ItemStack(DinocraftItems.LEAFERANG) : null);
	//			}
	//
	//			if (j < 4)
	//			{
	//				//TODO: implement 				living.setItemStackToSlot(EntityEquipmentSlot.FEET, j < 1 ? new ItemStack(DinocraftItems.LEAFY_BOOTS) : j < 2 ? new ItemStack(DinocraftItems.MERCHANTS_LUCKY_BOOTS) : j < 4 ? new ItemStack(DinocraftItems.DREADED_BOOTS) : null);
	//			}
	//		}
	//
	//		if (living instanceof EntityZombie || living instanceof EntitySkeleton)
	//		{
	//			int i = world.rand.nextInt(difficulty == difficulty.EASY ? 20000 : difficulty == difficulty.NORMAL ? 15000 : 10000);
	//
	//			if (i < 3)
	//			{
	//				if (world.rand.nextInt(2) > 0)
	//				{
	//					living.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.RANIUM_BOOTS));
	//				}
	//
	//				if (world.rand.nextInt(2) > 0)
	//				{
	//					living.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.RANIUM_LEGGINGS));
	//				}
	//
	//				if (world.rand.nextInt(2) > 0)
	//				{
	//					living.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.RANIUM_CHESTPLATE));
	//				}
	//
	//				if (world.rand.nextInt(2) > 0)
	//				{
	//					living.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.RANIUM_HELMET));
	//				}
	//			}
	//			//			else if (i < 6)
	//			//			{
	//			//				if (world.rand.nextInt(2) > 0)
	//			//				{
	//			//					living.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(DinocraftItems.CHLOROPHYTE_BOOTS));
	//			//				}
	//			//
	//			//				if (world.rand.nextInt(2) > 0)
	//			//				{
	//			//					living.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(DinocraftItems.CHLOROPHYTE_LEGGINGS));
	//			//				}
	//			//
	//			//				if (world.rand.nextInt(2) > 0)
	//			//				{
	//			//					living.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(DinocraftItems.CHLOROPHYTE_CHESTPLATE));
	//			//				}
	//			//
	//			//				if (world.rand.nextInt(2) > 0)
	//			//				{
	//			//					living.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(DinocraftItems.CHLOROPHYTE_HELMET));
	//			//				}
	//			//			}
	//		}
	//	}
	
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



				//				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
				//
				//				if (player.getHeldItemMainhand() != null && dinoEntity.hasModifiedReach())
				//				{
				//					RayTraceResult result = DinocraftEntity.getEntityTrace(dinoEntity.getAttackReach());
				//
				//					if (result != null && result.entityHit != null && result.entityHit instanceof EntityLivingBase)
				//					{
				//						Packet.sendToServer(new CPacketReachAttack(result.entityHit));
				//					}
				//				}

				//				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
				//
				//				if (dinoEntity.hasModifiedReach())
				//				{
				//					RayTraceResult result = DinocraftEntity.getEntityTrace(dinoEntity.getAttackReach());
				//
				//					if (result != null && result.entityHit != null)
				//					{
				//						Packet.sendToServer(new CPacketReachAttack(result.entityHit));
				//					}
				//				}
			}
		}
	}
}