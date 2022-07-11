package dinocraft.capabilities.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.command.CommandJump;
import dinocraft.command.CommandTPHere;
import dinocraft.command.CommandTPTo;
import dinocraft.entity.EntityFallingCrystal;
import dinocraft.entity.EntityMagatiumBolt;
import dinocraft.init.DinocraftItems;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketAllowFlying;
import dinocraft.network.server.SPacketChangeCapability;
import dinocraft.network.server.SPacketFlySpeed;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftServer;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandTP;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class DinocraftEntity implements IDinocraftEntity
{
	private static MinecraftServer SERVER;
	public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("B04DB08B-ED8A-4B82-B1EF-ADB425174925");
	public static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("FFAB9AA7-2B58-4DCD-BE62-2FEA1A13FBA6");
	private final EntityLivingBase entity;
	protected DinocraftEntityActions actions;
	protected DinocraftEntityTicks ticks;
	/** Whether this entity takes fall damage on their next fall or not */
	private boolean fallDamage = true;
	/** Whether this entity is immune to fall damage */
	private boolean fallDamageImmune = false;
	/** Whether this entity has reduced fall damage */
	private boolean reducedFallDamage = false;
	/** This entity's fall damage reduction amount */
	private float fallDamageReductionAmount = 0.0F;
	/** Whether this entity has permanent reduced fall damage */
	private boolean fallDamageReducer = false;
	/** Whether this entity is invulnerable */
	private boolean invulnerable = false;
	/** Whether this entity is regenerating */
	private boolean regenerating = false;
	/** Whether this entity is muted */
	private boolean muted = false;
	/** Whether this entity is frozen in place */
	private boolean frozen = false;
	/** Whether this entity is allowed to fly */
	private boolean allowFlight = false;
	/** Whether this entity is flying */
	private boolean flying = false;
	/** The position this entity was in right before it last teleported */
	private @Nullable BlockPos lastTeleportPos;
	/** The position this entity was in right before it last died */
	private @Nullable BlockPos lastDeathPos;
	private Random rand = new Random();

	public DinocraftEntity(EntityLivingBase entity)
	{
		this.actions = new DinocraftEntityActions(this);
		this.ticks = new DinocraftEntityTicks(this);
		this.entity = entity;
		SERVER = entity.getServer() != null ? entity.getServer() : FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	public DinocraftEntity()
	{
		this.entity = null;
	}

	/**
	 * Gets this capability data's corresponding entity
	 */
	@Override
	public EntityLivingBase getEntity()
	{
		return this.entity;
	}

	/**
	 * If this entity is a player, gets this capability data's corresponding entity by their name
	 */
	public static EntityPlayer getEntityPlayerByName(String name)
	{
		return SERVER.getPlayerList().getPlayerByUsername(name);
	}

	/**
	 * If this entity is a player, gets the capability data for the entity by their name
	 */
	public static DinocraftEntity getPlayer(String name)
	{
		return DinocraftEntity.getEntity(DinocraftEntity.getEntityPlayerByName(name));
	}

	/**
	 * Gets the capability data for specified entity
	 */
	public static DinocraftEntity getEntity(EntityLivingBase entity)
	{
		return !DinocraftEntity.hasCapability(entity) ? null : (DinocraftEntity) entity.getCapability(DinocraftCapabilities.DINOCRAFT_ENTITY, null);
	}

	/**
	 * Returns if this entity has this capability
	 */
	public static boolean hasCapability(EntityLivingBase entity)
	{
		return entity.hasCapability(DinocraftCapabilities.DINOCRAFT_ENTITY, null);
	}

	/**
	 * Gets this entity's actions module
	 */
	public DinocraftEntityActions getActionsModule()
	{
		return this.actions;
	}
	
	/**
	 * Gets this entity's ticks module
	 */
	public DinocraftEntityTicks getTicksModule()
	{
		return this.ticks;
	}

	/**
	 * Returns if this entity is jumping
	 */
	@SideOnly(Side.CLIENT)
	public boolean isJumping()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
	}

	/**
	 * Sets if this entity takes fall damage on their next fall
	 */
	public void setFallDamage(boolean fallDamage)
	{
		if (!this.isFallDamageImmune())
		{
			this.fallDamage = fallDamage;
		}
	}

	/**
	 * Returns if this entity takes fall damage on their next fall
	 */
	public boolean hasFallDamage()
	{
		return this.isFallDamageImmune() || this.isInvulnerable() ? false : this.fallDamage;
	}

	/**
	 * Sets if this entity is immune to fall damage
	 */
	public void setFallDamageImmune(boolean fallDamageImmune)
	{
		this.fallDamageImmune = fallDamageImmune;
	}

	/**
	 * Returns if this entity is immune to fall damage
	 */
	public boolean isFallDamageImmune()
	{
		return this.isInvulnerable() ? true : this.fallDamageImmune;
	}

	/**
	 * Returns if this entity has reduced fall damage on their next fall
	 */
	public boolean hasReducedFallDamage()
	{
		return this.reducedFallDamage;
	}

	/**
	 * Sets this entity's fall damage reduction amount on their next fall (param: amount of half-hearts)
	 */
	public void setFallDamageReductionAmount(float amount)
	{
		this.fallDamageReductionAmount = amount;
		this.reducedFallDamage = true;
	}

	/**
	 * Returns this entity's fall damage reduction amount (amount of half-hearts)
	 */
	public float getFallDamageReductionAmount()
	{
		return this.fallDamageReductionAmount;
	}

	/**
	 * Sets this entity's permanent fall damage reduction amount (param: amount of half-hearts)
	 */
	public void setFallDamageReducer(float amount)
	{
		this.fallDamageReductionAmount = amount;
		this.fallDamageReducer = true;
	}
	
	/**
	 * Returns if this entity has permanent reduced fall damage
	 */
	public boolean hasFallDamageReducer()
	{
		return this.fallDamageReducer;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingFall(LivingFallEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
		
		if (!dinoEntity.hasFallDamage() || dinoEntity.isFallDamageImmune())
		{
			if (entity.fallDistance < 4.0F || entity.fallDistance >= 4.0F && !entity.world.isRemote)
			{
				event.setCanceled(true);
			}
			
			if (!dinoEntity.hasFallDamage())
			{
				dinoEntity.setFallDamage(true);
			}
			
			return;
		}
		
		if (dinoEntity.hasReducedFallDamage() || dinoEntity.hasFallDamageReducer())
		{
			if (entity.fallDistance < 4.0F || entity.fallDistance >= 4.0F && !entity.world.isRemote)
			{
				event.setCanceled(true);
			}
			
			PotionEffect effect = entity.getActivePotionEffect(MobEffects.JUMP_BOOST);
			float modifier = effect == null ? 0.0F : effect.getAmplifier() + 1.0F;
			float damage = MathHelper.ceil(entity.fallDistance - 3.0F - modifier - dinoEntity.getFallDamageReductionAmount());
			
			if (damage > 0.0F)
			{
				entity.attackEntityFrom(DamageSource.FALL, damage);
			}
			
			dinoEntity.reducedFallDamage = false;
		}
	}
	
	/**
	 * Sets this entity's max health to specified amount
	 */
	public static void setMaxHealth(EntityLivingBase entity, float amount)
	{
		IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Setter", amount - 20.0F, 0);
		multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
		entity.getAttributeMap().applyAttributeModifiers(multimap);
	}
	
	/**
	 * Sets this entity's movement speed to specified amount
	 */
	public static void setMovementSpeed(EntityLivingBase entity, double amount)
	{
		IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
		AttributeModifier modifier = instance.getModifier(MOVEMENT_SPEED_MODIFIER_ID);
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		modifier = new AttributeModifier(MOVEMENT_SPEED_MODIFIER_ID, "Movement Speed Setter", amount, 0);
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), modifier);
		entity.getAttributeMap().applyAttributeModifiers(multimap);
	}
	
	/**
	 * Sets this entity's fly speed to specified amount
	 */
	public static void setFlySpeed(EntityPlayer player, double amount)
	{
		PacketHandler.sendTo(new SPacketFlySpeed(amount), (EntityPlayerMP) player);
	}
	
	/**
	 * Adds specified amount to this entity's max health
	 */
	public static void addMaxHealth(EntityLivingBase entity, float amount)
	{
		IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
		double hearts = modifier != null ? modifier.getAmount() : 0.0D;
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Adder", hearts + amount, 0);
		multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
		entity.getAttributeMap().applyAttributeModifiers(multimap);
	}
	
	/**
	 * If this entity is a player, stops the specified sound for this entity
	 */
	public static void stopSound(EntityPlayer player, SoundEvent sound)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeString("");
		buffer.writeString(sound.getRegistryName().toString());
		((EntityPlayerMP) player).connection.sendPacket(new SPacketCustomPayload("MC|StopSound", buffer));
	}
	
	/**
	 * Gets a RayTraceResult describing whatever block this entity is looking at within specified distance. DOESN'T DETECT ENTITIES.
	 */
	public static RayTraceResult getBlockTrace(EntityLivingBase entity, double distance)
	{
		Vec3d vector = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		return entity.world.rayTraceBlocks(vector, vector.add(entity.getLookVec().scale(distance)));
	}
	
	/**
	 * Gets a RayTraceResult describing whatever block this entity is looking at within specified distance. DOESN'T DETECT ENTITIES.
	 */
	public static RayTraceResult getBlockTraceNearest(EntityLivingBase entity, double distance)
	{
		Vec3d vector = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		return entity.world.rayTraceBlocks(vector, vector.add(entity.getLookVec().scale(distance)), false, true, false);
	}
	
	/**
	 * If this entity is a player, sets whether this entity is allowed to fly when they double jump
	 */
	public void setAllowFlight(boolean allowFlight)
	{
		if (this.entity instanceof EntityPlayer)
		{
			((EntityPlayer) this.entity).capabilities.allowFlying = allowFlight;
			this.allowFlight = allowFlight;
		}
	}
	
	/**
	 * If this entity is a player, returns if this entity is allowed to fly when they double jump
	 */
	public boolean canFly()
	{
		return this.entity instanceof EntityPlayer ? this.allowFlight : false;
		//return this.entity instanceof EntityPlayer ? ((EntityPlayer) this.entity).capabilities.allowFlying : false;
	}
	
	/**
	 * If this entity is a player, sets whether this entity is flying
	 */
	public void setFlight(boolean flight)
	{
		if (this.entity instanceof EntityPlayer)
		{
			((EntityPlayer) this.entity).capabilities.isFlying = flight;
			this.flying = flight;
		}
	}
	
	/**
	 * If this entity is a player, returns whether this entity is flying
	 */
	public boolean isFlying()
	{
		return this.entity instanceof EntityPlayer ? ((EntityPlayer) this.entity).capabilities.isFlying : false;
		//return this.entity instanceof EntityPlayer ? this.isFlying : false;
	}
	
	/**
	 * Returns if this entity is currently invulnerable
	 */
	public boolean isInvulnerable()
	{
		return this.invulnerable;
	}
	
	/**
	 * Sets this entity invulnerable for the specified time (seconds)
	 */
	public void setInvulnerable(int time)
	{
		this.ticks.ticksInvulnerable = time * 20;
		this.invulnerable = true;
	}

	/**
	 * Freezes this entity in place until unfrozen
	 */
	public void freeze()
	{
		if (!this.entity.world.isRemote && this.entity instanceof EntityPlayerMP)
		{
			PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DE_FROZEN, true), (EntityPlayerMP) this.entity);
		}
		
		this.frozen = true;
	}
	
	/**
	 * Unfreezes this entity
	 */
	public void unfreeze()
	{
		if (!this.entity.world.isRemote && this.entity instanceof EntityPlayerMP)
		{
			PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DE_FROZEN, false), (EntityPlayerMP) this.entity);
		}
		
		this.frozen = false;
	}
	
	/**
	 * Returns if this entity is frozen in place
	 */
	public boolean isFrozen()
	{
		return this.frozen;
	}
	
	/**
	 * Returns if this entity is regenerating
	 */
	public boolean isRegenerating()
	{
		return this.entity.getActivePotionEffect(MobEffects.REGENERATION) != null || this.regenerating;
	}
	
	/**
	 * Sets whether this entity is regenerating or not
	 */
	public void setRegenerating(boolean regenerating)
	{
		this.regenerating = regenerating;
	}
	
	/**
	 * Sets this entity regenerating
	 * @param time the amount of time to regenerate for (seconds)
	 * @param loopTime the amount of time in between each regeneration loop (seconds)
	 * @param health the amount of health to regenerate for each loop (half-hearts)
	 */
	public void setRegenerating(int time, float loopTime, float health)
	{
		this.regenerating = true;
		this.ticks.regenerationTicks = time * 20;
		this.ticks.regenerationLoopTicks = loopTime * 20;
		this.ticks.healthToRegenerate = health;
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event)
	{
		if (!event.player.world.isRemote && DinocraftEntity.getEntity(event.player).isFrozen())
		{
			PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DE_FROZEN, true), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if (!event.player.world.isRemote)
		{
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(event.player);
			
			if (dinoEntity.isFrozen())
			{
				PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DE_FROZEN, true), (EntityPlayerMP) event.player);
			}
			
			boolean canFly = !event.player.isCreative() && dinoEntity.canFly();
			PacketHandler.sendTo(new SPacketAllowFlying(canFly, false), (EntityPlayerMP) event.player);
			event.player.capabilities.allowFlying = canFly;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onMouse(MouseEvent event)
	{
		if (DinocraftEntity.getEntity(Minecraft.getMinecraft().player).isFrozen())
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(event.player);
		
		if (dinoEntity.isFrozen())
		{
			PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DE_FROZEN, true), (EntityPlayerMP) event.player);
		}

		if (dinoEntity.actions.isDreadedFlying())
		{
			PacketHandler.sendTo(new SPacketChangeCapability(SPacketChangeCapability.Capability.DA_DREADED_FLYING, true), (EntityPlayerMP) event.player);
		}
		
		boolean canFly = event.player.isCreative() || dinoEntity.canFly();
		boolean isFlying = dinoEntity.isFlying();
		PacketHandler.sendTo(new SPacketAllowFlying(canFly, dinoEntity.flying), (EntityPlayerMP) event.player);
		event.player.capabilities.allowFlying = canFly;
		event.player.capabilities.isFlying = dinoEntity.flying;
	}

	@SubscribeEvent
	public static void onLivingJoinWorld(EntityJoinWorldEvent event)
	{
		//		if (event.getEntity() instanceof EntityCreeper)
		//		{
		//			((EntityCreeper) event.getEntity()).tasks.addTask(1, new EntityAIAvoidEntity<>((EntityCreature) event.getEntity(), EntityPlayer.class, 20.0F, 1.0D, 2.0D));
		//			EntityCreeper creeper = (EntityCreeper) event.getEntity();
		//
		//			for(Object task : creeper.targetTasks.taskEntries.toArray())
		//			{
		//				EntityAIBase ai = ((EntityAITaskEntry) task).action;
		
		//				if (ai instanceof EntityAINearestAttackableTarget)
		//				{
		//					creeper.targetTasks.removeTask(ai);
		//				}
		//			}
		
		//			creeper.targetTasks.addTask(0, new EntityAIPanic(creeper, 2.0D));

		//		}
	}

	private static Method getExperiencePoints()
	{
		Method method = null;

		try
		{
			method = EntityLivingBase.class.getDeclaredMethod("getExperiencePoints", EntityPlayer.class);
			method.setAccessible(true);
		}
		catch (Exception exception)
		{

		}

		return method;
	}
	
	private static Field isRecentlyHit()
	{
		Field field = null;
		
		try
		{
			field = EntityLivingBase.class.getDeclaredField("recentlyHit");
			field.setAccessible(true);
		}
		catch (Exception exception)
		{

		}

		return field;
	}
	
	private static final Method EXP = getExperiencePoints();
	private static final Field HIT = isRecentlyHit();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);

		if (dinoEntity.ticks.ticksStandingStill >= 60)
		{
			if (dinoEntity.ticks.ticksStandingStill % 15 == 0)
			{
				if (!entity.world.isRemote)
				{
					entity.heal(1.0F);
				}
				else
				{
					for (int i = 0; i < 16; ++i)
					{
						entity.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT,
								true, entity.posX + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width,
								entity.posY + entity.height / 3.0F + entity.world.rand.nextFloat() * entity.height / 3.0F,
								entity.posZ + entity.world.rand.nextFloat() * entity.width * 2.0F - entity.width, 0, 0, 0);
					}
				}
			}

			if (!entity.world.isRemote)
			{
				if ((dinoEntity.ticks.ticksStandingStill - 60) % 25 == 0)
				{
					if ((dinoEntity.ticks.ticksStandingStill - 60) % 100 == 0)
					{
						ItemStack mainhandItem = entity.getHeldItemMainhand(), offhandItem = entity.getHeldItemOffhand();
						ItemStack stack = mainhandItem.getItem() == DinocraftItems.TUSKERS_JUG ? mainhandItem : offhandItem.getItem() == DinocraftItems.TUSKERS_JUG ? offhandItem : null;

						if (stack != null)
						{
							stack.damageItem(1, entity);
						}
					}

					entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 0.7F, entity.world.rand.nextFloat() * 0.5F + 0.5F);
				}

				dinoEntity.ticks.incrementTicksStandingStill();
			}
		}
		
		if (!entity.world.isRemote)
		{
			try
			{
				if (entity.world.getGameRules().getBoolean("doMobLoot") && entity.deathTime == 19 && HIT.getInt(entity) <= 0)
				{
					if (dinoEntity.actions.jesterized && dinoEntity.actions.jesterizingEntity instanceof EntityPlayer)
					{
						int i = (Integer) EXP.invoke(entity, (EntityPlayer) dinoEntity.actions.jesterizingEntity);
						i = ForgeEventFactory.getExperienceDrop(entity, (EntityPlayer) dinoEntity.actions.jesterizingEntity, i);

						while (i > 0)
						{
							int j = EntityXPOrb.getXPSplit(i);
							i -= j;
							entity.world.spawnEntity(new EntityXPOrb(entity.world, entity.posX, entity.posY, entity.posZ, j));
						}
					}
				}
			}
			catch (Exception exception)
			{

			}

			if (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == DinocraftItems.DREMONITE_BOOTS && dinoEntity.actions.dreadedFlying)
			{
				if (entity.ticksExisted % 2 == 0)
				{
					float f0 = entity.width * 2.0F;
					Vec3d vec = entity.getLookVec().normalize();
					DinocraftServer.spawnParticles(EnumParticleTypes.SMOKE_LARGE, entity.world, 40, entity.posX + vec.x * 2.0D, entity.posY + vec.y * 2.0D + entity.getEyeHeight() / 1.5D, entity.posZ + vec.z * 2.0D, f0, entity.height * 0.5F, f0, 0, 0, 0);

					if (entity.ticksExisted % 6 == 0)
					{
						entity.fallDistance = 0.0F;
						entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, entity.world.rand.nextFloat() + 0.25F);
					}
				}
			}

			if (dinoEntity.actions.boltCount > 0)
			{
				if (entity.getHeldItemMainhand().getItem() != DinocraftItems.MAGATIUM_STAFF && entity.getHeldItemOffhand().getItem() != DinocraftItems.MAGATIUM_STAFF)
				{
					dinoEntity.actions.boltCount = 0;
				}
				
				if (dinoEntity.actions.shootingCount <= 0)
				{
					dinoEntity.actions.shootingCount = 10;
					EntityMagatiumBolt bolt = new EntityMagatiumBolt(entity.world, entity);
					bolt.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, 1.33F, 0.33F);
					entity.world.spawnEntity(bolt);
					entity.world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, entity.world.rand.nextFloat() * 0.5F + 0.5F);
					dinoEntity.actions.boltCount--;
				}
				
				dinoEntity.actions.shootingCount--;
			}
			
			if (dinoEntity.actions.electrified)
			{
				if (entity.ticksExisted % 2 == 0)
				{
					float f0 = entity.height / 2.0F;
					DinocraftServer.spawnElectricParticles(entity.world, 2, 2, 20, entity.posX, entity.posY + f0, entity.posZ, entity.width, f0, entity.width);
				}
				
				dinoEntity.ticks.electrifiedTicks--;
				
				if (dinoEntity.ticks.electrifiedTicks <= 0)
				{
					dinoEntity.ticks.electrifiedTicks = 0;
					dinoEntity.actions.electrified = false;
				}
			}
			
			if (dinoEntity.actions.jesterized)
			{
				if (entity.ticksExisted % 2 == 0)
				{
					float f0 = entity.height / 3.0F;
					DinocraftServer.spawnJesterParticles(entity.world, 2, 2, entity.posX, entity.posY + f0, entity.posZ, entity.width, f0, entity.width);
				}

				if (entity.onGround)
				{
					int j = entity.world.rand.nextInt(4);

					if (j == 0)
					{
						entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40, 2, true, false));
					}
					else
					{
						double x = entity.world.rand.nextDouble() - 0.5D;
						double y = entity.world.rand.nextDouble() * 0.2D + 0.7D;
						double z = entity.world.rand.nextDouble() - 0.5D;

						if (entity instanceof EntityPlayer)
						{
							((EntityPlayerMP) entity).connection.sendPacket(new SPacketEntityVelocity(entity.getEntityId(), x, y, z));
						}

						entity.addVelocity(x, y, z);
					}
				}
				
				dinoEntity.ticks.jesterizedTicks--;
				
				if (dinoEntity.ticks.jesterizedTicks <= 0)
				{
					dinoEntity.ticks.jesterizedTicks = 0;
					dinoEntity.actions.jesterized = false;
				}
			}
			
			if (dinoEntity.actions.fallingCrystals)
			{
				if (dinoEntity.ticks.fallingCrystalsTicks % 8 == 0)
				{
					EntityFallingCrystal fallingCrystal = new EntityFallingCrystal(dinoEntity.actions.fallingCrystalsShooter == null ? entity : dinoEntity.actions.fallingCrystalsShooter, 0.075F);
					AxisAlignedBB aabb = entity.getEntityBoundingBox();
					double posX = entity.world.rand.nextDouble() * (aabb.maxX - aabb.minX) + aabb.minX;
					double posZ = entity.world.rand.nextDouble() * (aabb.maxZ - aabb.minZ) + aabb.minZ;
					double topOfEntity = entity.posY + entity.height;

					if (entity.world.getBlockState(new BlockPos(posX, topOfEntity, posZ)).getBlock() == Blocks.AIR &&
							entity.world.getBlockState(new BlockPos(posX, topOfEntity + 1.0D, posZ)).getBlock() == Blocks.AIR)
					{
						double posY = topOfEntity + 2.0D;
						double maxPosY = topOfEntity + 5.0D;

						while (posY < maxPosY)
						{
							if (entity.world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() != Blocks.AIR)
							{
								posY--;
								break;
							}

							posY++;
						}

						float a = 7.5F;
						double d = posY - topOfEntity;
						double t = Math.sqrt(2.0D * a * d) / a;
						
						if (entity.hurtResistantTime < 5)
						{
							t *= 20.0D;
						}
						
						fallingCrystal.setPositionAndUpdate(posX + t * entity.motionX, posY, posZ + t * entity.motionZ);
						entity.world.spawnEntity(fallingCrystal);
						entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.AMBIENT, 0.3F, entity.world.rand.nextFloat() * 0.25F + 0.5F);
					}
				}
				
				dinoEntity.ticks.fallingCrystalsTicks--;
				
				if (dinoEntity.ticks.fallingCrystalsTicks <= 0)
				{
					dinoEntity.ticks.fallingCrystalsTicks = 0;
					dinoEntity.actions.fallingCrystals = false;
				}
			}
			
			if (!(entity instanceof EntityPlayer) && dinoEntity.actions.isMesmerized())
			{
				if (entity instanceof EntityLiving)
				{
					EntityLivingBase target = ((EntityLiving) entity).getAttackTarget();
					
					if (target == null || !target.isEntityAlive() || target instanceof EntityPlayer)
					{
						List<Entity> entities = entity.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().grow(20.0D),
								entity1 -> !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase) entity1).canEntityBeSeen(entity) && entity1.canBeCollidedWith() && entity1.isCreatureType(EnumCreatureType.MONSTER, false));
						EntityLivingBase found = null;
						double foundLen = 20.0D;

						for (Entity entity1 : entities)
						{
							EntityLivingBase living = (EntityLivingBase) entity1;
							
							if (!DinocraftEntity.getEntity(living).actions.isMesmerized())
							{
								double length = entity1.getDistance(entity);
								
								if (length < foundLen)
								{
									found = living;
									foundLen = length;
								}
							}
						}
						
						((EntityLiving) entity).setAttackTarget(found);
					}
				}

				if (entity.ticksExisted % 2 == 0)
				{
					float f0 = entity.height / 2.5F;
					DinocraftServer.spawnMesmerizedParticles(entity.world, 3, 1, entity.posX, entity.posY + f0, entity.posZ, entity.width, f0, entity.width);
				}

				dinoEntity.ticks.mesmerizedTicks--;
				
				if (dinoEntity.ticks.mesmerizedTicks <= 0)
				{
					dinoEntity.ticks.mesmerizedTicks = 0;
					dinoEntity.actions.mesmerized = false;
				}
			}

			if (dinoEntity.regenerating)
			{
				if (dinoEntity.ticks.regenerationTicks <= 0)
				{
					dinoEntity.regenerating = false;
					dinoEntity.ticks.regenerationCount = 0;
				}
				else
				{
					dinoEntity.ticks.regenerationTicks--;
					dinoEntity.ticks.regenerationCount++;
					
					if (dinoEntity.ticks.regenerationCount == dinoEntity.ticks.regenerationLoopTicks)
					{
						dinoEntity.ticks.regenerationCount = 0;
						
						if (dinoEntity.ticks.healthToRegenerate > 0.0F)
						{
							entity.heal(dinoEntity.ticks.healthToRegenerate);
						}
						else
						{
							float health = entity.getHealth() + dinoEntity.ticks.healthToRegenerate;
							entity.setHealth(health > 0.0F ? health : 1.0F);
						}
					}
				}
			}
			else
			{
				dinoEntity.ticks.regenerationLoopTicks = 0.0F;
				dinoEntity.ticks.healthToRegenerate = 0.0F;
				dinoEntity.ticks.regenerationTicks = 0;
				dinoEntity.ticks.regenerationCount = 0;
			}
			
			if (dinoEntity.invulnerable)
			{
				if (dinoEntity.ticks.ticksInvulnerable <= 0)
				{
					entity.setEntityInvulnerable(false);
					dinoEntity.invulnerable = false;
				}
				else
				{
					if (!entity.getIsInvulnerable())
					{
						entity.setEntityInvulnerable(true);
					}
					
					dinoEntity.ticks.ticksInvulnerable--;
				}
			}
		}
		else if (dinoEntity.isFrozen())
		{
			KeyBinding.unPressAllKeys();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onClone(Clone event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer newPlayer = event.getEntityPlayer();
			EntityPlayer oldPlayer = event.getOriginal();
			IAttributeInstance oldMaxHealth = event.getOriginal().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
			AttributeModifier modifier = oldMaxHealth.getModifier(MAX_HEALTH_MODIFIER_ID);

			if (modifier != null)
			{
				Multimap<String, AttributeModifier> multimap = HashMultimap.create();
				multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
				newPlayer.getAttributeMap().applyAttributeModifiers(multimap);
			}

			DinocraftEntity newDinoPlayer = DinocraftEntity.getEntity(newPlayer);
			DinocraftEntity oldDinoPlayer = DinocraftEntity.getEntity(oldPlayer);
			IStorage<IDinocraftEntity> storage = DinocraftCapabilities.DINOCRAFT_ENTITY.getStorage();
			NBTBase state = storage.writeNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, oldDinoPlayer, null);
			storage.readNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, newDinoPlayer, null, state);
		}
	}
	
	/**
	 * Returns if this entity has the specified permission level or higher
	 */
	public boolean hasOpLevel(int level)
	{
		return this.getPermissionLevel() >= level ? true : false;
	}
	
	/**
	 * Returns the OP permission level of this entity
	 */
	public int getPermissionLevel()
	{
		if (this.entity instanceof EntityPlayerMP)
		{
			if (SERVER.isDedicatedServer())
			{
				return SERVER.getPlayerList().getOppedPlayers().getPermissionLevel(((EntityPlayerMP) this.entity).getGameProfile());
			}
			else
			{
				ICommandSender sender = this.entity;
				return sender.canUseCommand(4, "") ? 4 : sender.canUseCommand(3, "") ? 3 : sender.canUseCommand(2, "") ? 2 : sender.canUseCommand(1, "") ? 1 : 0;
			}
		}

		return 0;
	}

	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();

		if (!(living instanceof EntityPlayer) && living instanceof EntityLiving)
		{
			if (DinocraftEntity.getEntity(living).actions.isMesmerized())
			{
				EntityLivingBase target = event.getTarget();

				if (target instanceof EntityPlayer || target != null && !target.isEntityAlive())
				{
					((EntityLiving) living).setAttackTarget(null);
				}
			}
		}
	}
	
	public static ItemStack getAmmo(EntityLivingBase entity, Item item)
	{
		ItemStack offhand = entity.getHeldItemOffhand();
		ItemStack mainhand = entity.getHeldItemMainhand();
		
		if (offhand.getItem() == item)
		{
			return offhand;
		}
		else if (mainhand.getItem() == item)
		{
			return mainhand;
		}
		else if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			int size = player.inventory.getSizeInventory();
			
			for (int i = 0; i < size; ++i)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if (stack.getItem() == item)
				{
					return stack;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if this entity has the specified ammunition
	 */
	public static boolean hasAmmo(EntityLivingBase entity, Item item)
	{
		return getAmmo(entity, item) != null;
	}
	
	/**
	 * Consumes the specified amount of ammunition from this entity's inventory
	 */
	public static void consumeAmmo(EntityLivingBase entity, Item item, int amount)
	{
		getAmmo(entity, item).shrink(amount);
	}
	
	/**
	 * If this entity is a player, adds the specified item to the entity's inventory
	 */
	public static void addStack(EntityPlayer player, ItemStack stack)
	{
		boolean flag = player.inventory.addItemStackToInventory(stack);
		
		if (flag)
		{
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			player.inventoryContainer.detectAndSendChanges();
		}
		
		if (flag && stack.isEmpty())
		{
			stack.setCount(1);
			EntityItem entity = player.dropItem(stack, false);
			
			if (entity != null)
			{
				entity.makeFakeItem();
			}
		}
		else
		{
			EntityItem entity = player.dropItem(stack, false);
			
			if (entity != null)
			{
				entity.setNoPickupDelay();
				entity.setOwner(player.getName());
			}
		}
	}
	
	/**
	 * If this entity is a player, removes the specified item from the entity's inventory
	 */
	public static void removeStack(EntityPlayer player, Item item, int amount)
	{
		int size = player.inventory.getSizeInventory();
		
		for (int i = 0; i < size; ++i)
		{
			ItemStack stack = player.inventory.getStackInSlot(i);
			
			if (stack != null && stack.getItem() == item)
			{
				stack.shrink(amount);
				return;
			}
		}
	}
	
	/**
	 * Causes this entity to receive an upward cursor jolt
	 * @param recoil the amount of recoil (upward jolt) this entity will receive
	 */
	public static void recoil(EntityLivingBase entity, float recoil)
	{
		if (DinocraftConfig.WEAPON_RECOIL)
		{
			boolean sneaking = entity.isSneaking();
			entity.rotationPitch -= sneaking ? recoil / 2.0F : recoil;
			float f = entity.world.rand.nextFloat() * (recoil / (sneaking ? 3.0F : 2.0F));
			entity.rotationYaw -= entity.world.rand.nextBoolean() ? f : -f;
		}
	}
	
	/**
	 * Returns the position this entity was in right before it last teleported, and <code>null</code> if it never teleported
	 */
	@Nullable
	public BlockPos getPosBeforeLastTeleport()
	{
		return this.lastTeleportPos;
	}
	
	/**
	 * Returns the position this entity was in right before it last died, and <code>null</code> if it never died
	 */
	@Nullable
	public BlockPos getPosBeforeLastDeath()
	{
		return this.lastDeathPos;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(living);

		if (living instanceof EntityPlayer)
		{
			dinoEntity.lastDeathPos = new BlockPos(living.lastTickPosX, living.lastTickPosY, living.lastTickPosZ);
		}
	}
	
	@SubscribeEvent
	public static void onTeleport(CommandEvent event) throws EntityNotFoundException, CommandException
	{
		ICommand command = event.getCommand();
		ICommandSender sender = event.getSender();
		MinecraftServer server = sender.getServer();
		String[] args = event.getParameters();
		
		if (command instanceof CommandTP && args.length > 1 || command instanceof CommandTPHere && args.length >= 1)
		{
			List<Entity> list = new ArrayList<>();
			Entity entity;
			Entity uuidEntity;
			
			try
			{
				uuidEntity = server.getEntityFromUuid(UUID.fromString(args[0]));
			}
			catch (IllegalArgumentException exception)
			{
				uuidEntity = null;
			}
			
			if (uuidEntity != null)
			{
				entity = uuidEntity;
			}
			else if (server.getPlayerList().getPlayerByUsername(args[0]) != null)
			{
				entity = CommandBase.getEntity(server, sender, args[0]);
				list.add(entity);
			}
			else
			{
				try
				{
					list.addAll(CommandBase.getEntityList(sender.getServer(), sender, args[0]));
				}
				catch (EntityNotFoundException exception)
				{
					return;
				}
			}
			
			for (Entity entity0 : list)
			{
				if (entity0 instanceof EntityLivingBase)
				{
					DinocraftEntity.getEntity((EntityLivingBase) entity0).lastTeleportPos = new BlockPos(entity0.lastTickPosX, entity0.lastTickPosY, entity0.lastTickPosZ);
				}
			}
		}
		else if (command instanceof CommandTP && args.length == 1 || command instanceof CommandTPTo || command instanceof CommandJump)
		{
			EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
			DinocraftEntity.getEntity(player).lastTeleportPos = new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
		}
	}
	
	public static boolean isLevel(ICommandSender sender, int level, MinecraftServer server)
	{
		return sender instanceof MinecraftServer || sender instanceof EntityPlayer && DinocraftEntity.getEntity((EntityPlayer) sender).hasOpLevel(level);
	}
	
	/**
	 * Gets a RayTraceResult describing whatever entity or block this entity is looking at within specified distance.
	 */
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getEntityTrace(double distance)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		Entity entity = minecraft.getRenderViewEntity();
		RayTraceResult result = null;

		if (entity != null && minecraft.world != null)
		{
			result = entity.rayTrace(distance, 1.0F);
			Vec3d pos = entity.getPositionEyes(1.0F);
			double calcdist = distance;

			if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
			{
				calcdist = result.hitVec.distanceTo(pos);
			}

			Vec3d lookvec = entity.getLookVec();
			Vec3d vec3d = pos.addVector(lookvec.x * distance, lookvec.y * distance, lookvec.z * distance);
			Entity pointedEntity = null;
			List<Entity> list = minecraft.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(lookvec.x * distance, lookvec.y * distance, lookvec.z * distance).grow(1.0F, 1.0F, 1.0F),
					Predicates.and(EntitySelectors.NOT_SPECTATING, entity1 -> entity1 != null && entity1.canBeCollidedWith()));
			double d = calcdist;

			for (Entity entity1 : list)
			{
				AxisAlignedBB aabb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
				RayTraceResult result2 = aabb.calculateIntercept(pos, vec3d);

				if (aabb.contains(pos))
				{
					if (d >= 0.0D)
					{
						pointedEntity = entity1;
						d = 0.0D;
					}
				}
				else if (result2 != null)
				{
					double d1 = pos.distanceTo(result2.hitVec);
					
					if (d1 < d || d == 0.0D)
					{
						if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract())
						{
							if (d == 0.0D)
							{
								pointedEntity = entity1;
							}
						}
						else
						{
							pointedEntity = entity1;
							d = d1;
						}
					}
				}
			}

			if (pointedEntity != null && (d < calcdist || result == null))
			{
				minecraft.pointedEntity = pointedEntity;
				result = new RayTraceResult(pointedEntity);
			}
		}
		
		return result;
	}

	public static class Storage implements IStorage<IDinocraftEntity>
	{
		@Override
		public NBTBase writeNBT(Capability<IDinocraftEntity> capability, IDinocraftEntity instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();
			instance.write(compound);
			return compound;
		}
		
		@Override
		public void readNBT(Capability<IDinocraftEntity> capability, IDinocraftEntity instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;
			instance.read(compound);
		}
	}
	
	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setBoolean("FallDamage", this.fallDamage);
		tag.setBoolean("FallDamageImmune", this.fallDamageImmune);
		tag.setBoolean("ReducedFallDamage", this.reducedFallDamage);
		tag.setFloat("FallDamageReductionAmount", this.fallDamageReductionAmount);
		tag.setBoolean("Invulnerable", this.invulnerable);
		tag.setBoolean("Frozen", this.frozen);
		tag.setBoolean("Regenerating", this.regenerating);
		
		if (this.lastDeathPos != null)
		{
			tag.setInteger("LastDeathX", this.lastDeathPos.getX());
			tag.setInteger("LastDeathY", this.lastDeathPos.getY());
			tag.setInteger("LastDeathZ", this.lastDeathPos.getZ());
		}
		
		if (this.lastTeleportPos != null)
		{
			tag.setInteger("LastTeleportX", this.lastTeleportPos.getX());
			tag.setInteger("LastTeleportY", this.lastTeleportPos.getY());
			tag.setInteger("LastTeleportZ", this.lastTeleportPos.getZ());
		}

		this.actions.write(tag);
		this.ticks.write(tag);

		tag.setBoolean("canFly", this.canFly());
		tag.setBoolean("isFlying", this.isFlying());
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.fallDamage = tag.getBoolean("FallDamage");
		this.fallDamageImmune = tag.getBoolean("FallDamageImmune");
		this.reducedFallDamage = tag.getBoolean("ReducedFallDamage");
		this.fallDamageReductionAmount = tag.getFloat("FallDamageReductionAmount");
		this.invulnerable = tag.getBoolean("Invulnerable");
		this.frozen = tag.getBoolean("Frozen");
		this.regenerating = tag.getBoolean("Regenerating");
		
		if (tag.hasKey("LastDeathX") && tag.hasKey("LastDeathY") && tag.hasKey("LastDeathZ"))
		{
			this.lastDeathPos = new BlockPos(tag.getInteger("LastDeathX"), tag.getInteger("LastDeathY"), tag.getInteger("LastDeathZ"));
		}
		
		if (tag.hasKey("LastTeleportX") && tag.hasKey("LastTeleportY") && tag.hasKey("LastTeleportZ"))
		{
			this.lastTeleportPos = new BlockPos(tag.getInteger("LastTeleportX"), tag.getInteger("LastTeleportY"), tag.getInteger("LastTeleportZ"));
		}

		this.actions.read(tag);
		this.ticks.read(tag);

		this.setAllowFlight(tag.getBoolean("canFly"));
		this.setFlight(tag.getBoolean("isFlying"));
	}
}