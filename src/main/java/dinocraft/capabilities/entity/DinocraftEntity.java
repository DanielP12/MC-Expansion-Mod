package dinocraft.capabilities.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketSpawnParticle;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The ultimate EntityLivingBase class extension */
@EventBusSubscriber
public class DinocraftEntity implements IDinocraftEntity
{
	private static MinecraftServer SERVER;
    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("B04DB08B-ED8A-4B82-B1EF-ADB425174925");
    public static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("FFAB9AA7-2B58-4DCD-BE62-2FEA1A13FBA6");
    public static final UUID FLYING_SPEED_MODIFIER_ID = UUID.fromString("FFAB9AA7-2B58-4DCD-BE62-2FEA1A13FBA7");
	private final EntityLivingBase entity;
	private final DinocraftEntityActions actions;
	private final DinocraftEntityTicks ticks;
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
	/** Whether this entity is invulnerable or not */
	private boolean invulnerable = false;
	/** Whether this entity is degenerating */
	private boolean degenerating = false;
    /** Whether this entity is regenerating */
	private boolean regenerating = false;
	/** Whether this entity is muted */
	private boolean isMuted = false;
	/** Whether this entity is frozen */
	private boolean isFrozen = false;
	
	public DinocraftEntity(EntityLivingBase entity)
	{
		this.actions = new DinocraftEntityActions(this);
		this.ticks = new DinocraftEntityTicks(this);
		this.entity = entity;
		SERVER = entity.getServer() != null ? entity.getServer() : FMLCommonHandler.instance().getMinecraftServerInstance();
	}
 
	public MinecraftServer getServer()
	{
		return this.getEntity().getServer();
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
	 * Returns if specified items are equipped on this entity. In this case, null means nothing OR any other item equipped.
	 */
	public boolean isWearing(@Nullable Item helmet, @Nullable Item chestplate, @Nullable Item leggings, @Nullable Item boots)
	{
		EntityLivingBase entity = this.getEntity();
		ItemStack helmet2 = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chestplate2 = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack leggings2 = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack boots2 = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		return (helmet != null ? helmet2 != null && helmet2.getItem() == helmet : (helmet2 == null || helmet2.getItem() != helmet))
				&& (chestplate != null ? chestplate2 != null && chestplate2.getItem() == chestplate : (chestplate2 == null || chestplate2.getItem() != chestplate))
				&& (leggings != null ? leggings2 != null && leggings2.getItem() == leggings : (leggings2 == null || leggings2.getItem() != leggings))
				&& (boots != null ? boots2 != null && boots2.getItem() == boots : (boots2 == null || boots2.getItem() != boots));
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
	 * The direction that this entity is moving towards
	 */
	public enum Direction
	{
		LEFT, RIGHT, FORWARD, BACKWARD
	}
 
	/**
	 * Returns if this entity is moving in specified direction 
	 */
	@SideOnly(Side.CLIENT)
	public boolean isMoving(Direction direction)
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
 
		switch (direction)
        {
        	case LEFT:
        	{
        		return settings.keyBindLeft.isKeyDown();
        	}
 
        	case RIGHT: 
        	{
        		return settings.keyBindRight.isKeyDown();
        	}
        	
        	case FORWARD: 
        	{
        		return settings.keyBindForward.isKeyDown();
        	}
        	
        	case BACKWARD: 
        	{
        		return settings.keyBindBack.isKeyDown();
        	}
        	
        	default: return false;
        }
	}
 
	/**
	 * Returns if this entity is moving to any direction
	 */
	@SideOnly(Side.CLIENT)
	public boolean isMoving()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
        return DinocraftEntity.getEntity(this.getEntity()).isStrafing() || settings.keyBindForward.isKeyDown() || settings.keyBindBack.isKeyDown();
	}
 
	/** 
	 * Returns if this entity is strafing to any side
	 */
	@SideOnly(Side.CLIENT)
	public boolean isStrafing()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		return settings.keyBindLeft.isKeyDown() || settings.keyBindRight.isKeyDown();
	}
 
	/**
	 * Spawns particles around this entity
	 */
	public void spawnParticle(EnumParticleTypes particleType, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int parameters)
	{
		NetworkHandler.sendToAllAround(new PacketSpawnParticle(particleType, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters), this.getEntity().world);
	}
 
	/** 
	 * Sets if this entity takes fall damage on their next fall
	 */
	public void setFallDamage(boolean fallDamage)
	{
		if (!DinocraftEntity.getEntity(this.getEntity()).isFallDamageImmune())
		{
			this.fallDamage = fallDamage;
		}
	}
 
	/** 
	 * Returns if this entity takes fall damage on their next fall
	 */
	public boolean hasFallDamage()
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(this.getEntity());
		return (dinoEntity.isFallDamageImmune() || dinoEntity.isInvulnerable()) ? false : this.fallDamage;
	}
 
	/** 
	 * Sets if this entity is immune to fall damage
	 */
	public void setFallDamageImmune(boolean immune)
	{
		this.fallDamageImmune = immune;
	}
 
	/** 
	 * Returns if this entity is immune to fall damage
	 */
	public boolean isFallDamageImmune()
	{
		return (DinocraftEntity.getEntity(this.getEntity()).isInvulnerable()) ? true : this.fallDamageImmune;
	}
 
	/**
	 * Returns if this entity has reduced fall damage on their next fall
	 */
	public boolean hasReducedFallDamage()
	{
		return this.reducedFallDamage;
	}
 
	/** Sets if this entity has reduced fall damage on their next fall */
	private void setReducedFallDamage(boolean reducedFallDamage)
	{
		this.reducedFallDamage = reducedFallDamage;
	}
 
	/**
	 * Sets this entity's fall damage reduction amount on their next fall (param: amount of half-hearts)
	 */
	public void setFallDamageReductionAmount(float amount)
	{
		this.fallDamageReductionAmount = amount;
		DinocraftEntity.getEntity(this.getEntity()).setReducedFallDamage(true);
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
        DinocraftEntity.getEntity(this.getEntity()).setFallDamageReducer(true);
    }
	
	/** 
	 * Returns if this entity has permanent reduced fall damage 
	 */
	public boolean hasFallDamageReducer() 
	{
        return this.fallDamageReducer;
	}
	   
	/** Sets whether this entity has permanent reduced fall damage */
    private void setFallDamageReducer(boolean reducedFallDamage) 
    {
        this.fallDamageReducer = reducedFallDamage;
    }
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingFall(LivingFallEvent event) 
	{
		EntityLivingBase entity = event.getEntityLiving();
        DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);

        if (!dinoEntity.hasFallDamage() || dinoEntity.isFallDamageImmune()) 
        {
            if (entity.fallDistance < 4.0F || (entity.fallDistance >= 4.0F && !entity.world.isRemote))
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
            if (entity.fallDistance < 4.0F || (entity.fallDistance >= 4.0F && !entity.world.isRemote))
            {
            	event.setCanceled(true);
            }
            
            PotionEffect effect = entity.getActivePotionEffect(MobEffects.JUMP_BOOST);
			float modifier = effect == null ? 0.0F : effect.getAmplifier() + 1.0F;
			float damage = MathHelper.ceil((entity.fallDistance - 3.0F - modifier - dinoEntity.getFallDamageReductionAmount()));
			
            if (damage > 0.0F)
            {
            	entity.attackEntityFrom(DamageSource.FALL, damage);
            }
            
            dinoEntity.setReducedFallDamage(false);
        }
    }
		
	/** 
	 * Sets this entity's max health to specified amount 
	 */
	public void setMaxHealth(float amount)
    {
		EntityLivingBase entity = this.getEntity();
        IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Setter", amount - 20.0F, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        entity.getAttributeMap().applyAttributeModifiers(multimap);
    }
	
	/** 
	 * Sets this entity's movement speed to specified amount 
	 */
	public void setMovementSpeed(double amount)
    {
		EntityLivingBase entity = this.getEntity();
        IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
        AttributeModifier modifier = instance.getModifier(MOVEMENT_SPEED_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MOVEMENT_SPEED_MODIFIER_ID, "Movement Speed Setter", amount, 0);
        multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), modifier);
        entity.getAttributeMap().applyAttributeModifiers(multimap);
    }
	
	/** 
	 * Sets this entity's fly speed to specified amount 
	 */
	public void setFlySpeed(double amount)
    {
		EntityLivingBase entity = this.getEntity();
        IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FLYING_SPEED);
        //AttributeModifier modifier = instance.getModifier(FLYING_SPEED_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        AttributeModifier modifier = new AttributeModifier(FLYING_SPEED_MODIFIER_ID, "Flying Speed Setter", amount, 0);
        multimap.put(SharedMonsterAttributes.FLYING_SPEED.getName(), modifier);
        entity.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/** 
	 * Adds specified amount to this entity's max health 
	 */
	public void addMaxHealth(float amount)
    {
		EntityLivingBase entity = this.getEntity();
        IAttributeInstance instance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
        double hearts = modifier != null ? modifier.getAmount() : 0.0D;

        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Adder", hearts + amount, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        entity.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/**
	 * If this entity is a player, feeds this entity to specified amounts
	 */
	public void feed(int amount, float saturation)
	{
		if (this.getEntity() instanceof EntityPlayer)
		{
			((EntityPlayer) this.getEntity()).getFoodStats().addStats(amount, saturation);
		}
	}
	
	/**
	 * Hurts this entity (param: amount of half-hearts)
	 */
	public void hurt(float amount)
	{
		if (amount <= 0.0F)
		{
			return;
		}
		
		EntityLivingBase entity = this.getEntity();
        float health = entity.getHealth();
        
        if (health > 0.0F)
        {
        	entity.setHealth(health - amount);
        }
	}
 
	/** 
	 * Stops the specified sound for all players
	 */
	public void stopSoundForAll(SoundEvent sound)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString("");
        buffer.writeString(sound.getRegistryName().toString());
        this.SERVER.getPlayerList().sendPacketToAllPlayers(new SPacketCustomPayload("MC|StopSound", buffer));
	}

	/** 
	 * If this entity is a player, stops the specified sound for this entity
	 */
	public void stopSound(SoundEvent sound)
	{
		if (this.getEntity() instanceof EntityPlayerMP)
		{
			PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
			buffer.writeString("");
			buffer.writeString(sound.getRegistryName().toString());
			((EntityPlayerMP) this.getEntity()).connection.sendPacket(new SPacketCustomPayload("MC|StopSound", buffer));
		}
	}
 
	/**
	 * Gets a RayTraceResult describing whatever block this entity is looking at within specified distance. DOESN'T DETECT ENTITIES.
	 */
	public RayTraceResult getTrace(double distance) 
	{
		EntityLivingBase entity = this.getEntity();
		Vec3d vector = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		return entity.world.rayTraceBlocks(vector, vector.add(entity.getLookVec().scale(distance)));
	}
 
	/**
	 * If this entity is a player, sets whether this entity is allowed to fly when they double jump
	 */
	public void setAllowFlight(boolean allowFlight)
	{
		if (this.getEntity() instanceof EntityPlayer)
		{
			((EntityPlayer) this.getEntity()).capabilities.allowFlying = allowFlight;
		}
	}

	/**
	 * If this entity is a player, returns if this entity is allowed to fly when they double jump
	 */
	public boolean canFly()
	{
		return this.getEntity() instanceof EntityPlayer ? ((EntityPlayer) this.getEntity()).capabilities.allowFlying : null;
	}

	/**
	 * If this entity is a player, sets whether this entity is flying
	 */
	public void setFlight(boolean flight)
	{
		if (this.getEntity() instanceof EntityPlayer)
		{
			((EntityPlayer) this.getEntity()).capabilities.isFlying = flight;
		}
	}

	/**
	 * If this entity is a player, returns whether this entity is flying
	 */
	public boolean isFlying()
	{
		return this.getEntity() instanceof EntityPlayer ? ((EntityPlayer) this.getEntity()).capabilities.isFlying : null;
	}

	/**
	 * Gets the world that this entity is in
	 */
	@Override
	public World getWorld()
	{
		return this.getEntity().world;
	}
 
	/** 
	 * Returns if this entity is currently invulnerable 
	 */
    public boolean isInvulnerable()
    {
        return this.invulnerable;
    }
 
    /** Sets whether this entity is invulnerable or not */
    private void setInvulnerable(boolean invulnerable)
    {
        this.invulnerable = invulnerable;
    }
 
    /**
     * Sets this entity invulnerable for the specified time (seconds)
     */
    public void setInvulnerable(int time)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(this.getEntity());
		dinoEntity.getTicksModule().setTicksInvulnerable(time * 20);
		dinoEntity.setInvulnerable(true);
	}
    
	/** 
	 * Returns if this entity is degenerating
	 */
    public boolean isDegenerating()
    {
        return this.degenerating;
    }
    
    /** Sets whether this entity is degenerating or not */
    private void setDegenerating(boolean degenerating)
    {
        this.degenerating = degenerating;
    }

    /**
     * Sets this entity degenerating
     * @param time the amount of time to degenerate for (seconds)
     * @param loopTime the amount of time in between each degeneration loop (seconds)
     * @param health the amount of health to degenerate for each loop (half-hearts)
     */
    public void setDegenerating(int time, float loopTime, float health)
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(this.getEntity());
		dinoEntity.setDegenerating(true);

		DinocraftEntityTicks ticks = dinoEntity.getTicksModule();
		ticks.setDegenerationTicks(time * 20);
		ticks.setDegenerationLoopTicks(loopTime * 20);
		ticks.setDegenerationHearts(health);
	}
    
	/** 
	 * Returns if this entity is regenerating
	 */
    public boolean isRegenerating()
    {
        return this.getEntity().getActivePotionEffect(MobEffects.REGENERATION) != null ? true : this.regenerating;
    }
    
    /** Sets whether this entity is regenerating or not */
    private void setRegenerating(boolean regenerating)
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
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(this.getEntity());
		dinoEntity.setRegenerating(true);

		DinocraftEntityTicks ticks = dinoEntity.getTicksModule();
		ticks.setRegenerationTicks(time * 20);
		ticks.setRegenerationLoopTicks(loopTime * 20);
		ticks.setRegenerationHearts(health);
	}
	
    @SubscribeEvent
    public static void onBreak(BreakEvent event)
    {
    	if (DinocraftEntity.getEntity(event.getPlayer()).isFrozen())
    	{
    		event.setCanceled(true);
    	}
    }
    
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
    	DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
    	DinocraftEntityTicks ticks = dinoEntity.getTicksModule();
    	
    	if (dinoEntity.isFrozen())
    	{
    		entity.setPositionAndRotation(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ, entity.rotationYaw, entity.rotationPitch);
    		entity.setPositionAndUpdate(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
    	}
    	
    	if (!entity.world.isRemote)
    	{
    		if (dinoEntity.isRegenerating())
    		{
    			if (ticks.getRegenerationTicks() <= 0)
    			{
    				dinoEntity.setRegenerating(false);
    				ticks.setRegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setRegenerationTicks(ticks.getRegenerationTicks() - 1);
    	    		ticks.setRegenerationCount(ticks.getRegenerationCount() + 1);
        		
    		    	if (ticks.getRegenerationCount() == ticks.getRegenerationLoopTicks())
    	    		{
    		   			ticks.setRegenerationCount(0);
    		   			
    		   			if (entity.getHealth() != entity.getMaxHealth()) 
    		   			{
    		   				entity.heal(ticks.getRegenerationHearts());
    		   			}
    	    		}
    	    	}
    		}
    		else
    		{
    			ticks.setRegenerationLoopTicks(0);
    			ticks.setRegenerationHearts(0);
    			ticks.setRegenerationTicks(0);
    			ticks.setRegenerationCount(0);
    		}
    		
    		if (dinoEntity.isDegenerating())
    		{
    			if (ticks.getDegenerationTicks() <= 0)
    			{
    				dinoEntity.setDegenerating(false);
    				ticks.setDegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setDegenerationTicks(ticks.getDegenerationTicks() - 1);
    	    		ticks.setDegenerationCount(ticks.getDegenerationCount() + 1);
        		
    		    	if (ticks.getDegenerationCount() == ticks.getDegenerationLoopTicks())
    	    		{
    		   			ticks.setDegenerationCount(0);
    		   			dinoEntity.hurt(ticks.getDegenerationHearts());
    	    		}
    	    	}
    		}
    		
    		if (dinoEntity.isInvulnerable())
    		{
    			if (ticks.getTicksInvulnerable() <= 0)
    			{
    				dinoEntity.setInvulnerable(false);
    				entity.setEntityInvulnerable(false);
    			}
    			else
    			{
    				ticks.setTicksInvulnerable(ticks.getTicksInvulnerable() - 1);
    				entity.setEntityInvulnerable(true);
    			}
    		}
    	}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onClone(Clone event)
    {
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = event.getEntityPlayer();
			IAttributeInstance oldMaxHealth = event.getOriginal().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
	        AttributeModifier modifier = oldMaxHealth.getModifier(MAX_HEALTH_MODIFIER_ID);
	        
	        if (modifier != null)
	        {
	            Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
	            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
	            player.getAttributeMap().applyAttributeModifiers(multimap);
	        }
	        
			DinocraftEntity newPlayer = DinocraftEntity.getEntity(player);
			DinocraftEntity oldPlayer = DinocraftEntity.getEntity(event.getOriginal());
			
			IStorage<IDinocraftEntity> storage = DinocraftCapabilities.DINOCRAFT_ENTITY.getStorage();
    		NBTBase state = storage.writeNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, oldPlayer, null);
    		storage.readNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, newPlayer, null, state);
		}
    }
	    
    /**
     * Sends a raw chat message to this entity
     */
    public void sendChatMessage(String msg)
    {
    	this.getEntity().sendMessage(new TextComponentString(msg));
    }
    
    public enum Type
    {
    	CHAT, ACTIONBAR, TITLE, SUBTITLE
    }
    
    /**
     * If this entity is a player, sends a raw message of any type to this entity
     */
    public void sendMessage(Enum type, String msg)
    {    	
    	if (this.getEntity() instanceof EntityPlayer)
    	{
        	EntityPlayer player = (EntityPlayer) this.getEntity();
        	
        	if (type == Type.ACTIONBAR && !player.world.isRemote)
        	{
        		((EntityPlayerMP) player).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString(msg)));
        	}
        	else if (type == Type.CHAT)
        	{
        		player.sendMessage(new TextComponentString(msg));
        	}
        	else if (type == Type.TITLE && !player.world.isRemote)
        	{
        		((EntityPlayerMP) player).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString(msg)));
        	}
        	else if (type == Type.SUBTITLE && !player.world.isRemote)
        	{
        		((EntityPlayerMP) player).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.SUBTITLE, new TextComponentString(msg)));
        	}
        }
    }
    
    /**
     * If this entity is a player, sends a raw actionbar message to this entity
     */
    //@SideOnly(Side.SERVER)
    public void sendActionbarMessage(String msg)
    {
    	if (this.getEntity() instanceof EntityPlayerMP)
    	{
    		((EntityPlayerMP) this.getEntity()).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString(msg)));
    	}
    }

    /**
     * Forces this entity to say specified message
     */
    public void say(String msg)
    {
    	this.SERVER.getPlayerList().sendMessage(new TextComponentString("<" + this.getEntity().getName() + "> " + msg));
    }
    
    /**
     * If this entity is a player, kicks this entity from the game with specified message
     */
    public void kick(String msg)
    {
    	if (this.getEntity() instanceof EntityPlayerMP)
    	{
            ((EntityPlayerMP) this.getEntity()).connection.disconnect(new TextComponentString(msg));
    	}
    }

    /**
     * Returns if this entity has the specified permission level or higher
     */
    public boolean hasOpLevel(int level)
    {	
    	return this.getOpLevel() >= level ? true : false;
    }

    /**
     * Gets the operator level of this entity
     */
    public int getOpLevel()
    {
    	EntityLivingBase entity = this.getEntity();
    	return entity.canUseCommand(4, "") ? 4 : entity.canUseCommand(3, "") ? 3 : entity.canUseCommand(2, "") ? 2 : entity.canUseCommand(1, "") ? 1 : 0;
    }

    private void sendPlayerPermissionLevel(EntityPlayerMP player, int permLevel)
    {
        if (player != null && player.connection != null)
        {
            player.connection.sendPacket(new SPacketEntityStatus(player, permLevel <= 0 ? 24 : permLevel >= 4 ? 28 : (byte) (24 + permLevel)));
        }
    }
    
    /**
     * If this entity is a player, ops this entity to the specified permission level
     */
    public void op(int permissionLevel)
    {
    	if (this.getEntity() instanceof EntityPlayerMP)
    	{
    		EntityPlayerMP playerMP = (EntityPlayerMP) this.getEntity();
    		UserListOps ops = playerMP.getServer().getPlayerList().getOppedPlayers();
    		GameProfile profile = playerMP.getGameProfile();
    		ops.addEntry(new UserListOpsEntry(profile, permissionLevel, ops.bypassesPlayerLimit(profile)));
    		this.sendPlayerPermissionLevel(playerMP, permissionLevel);
    	}
    }
    
    /**
     * If this entity is a player, ops the specified GameProfile to the specified permission level
     */
    public void op(int permissionLevel, GameProfile profile)
    {
    	if (this.getEntity() instanceof EntityPlayer)
    	{
    		EntityPlayerMP playerMP = (EntityPlayerMP) this.getEntity();
    		UserListOps ops = playerMP.getServer().getPlayerList().getOppedPlayers();
    		ops.addEntry(new UserListOpsEntry(profile, permissionLevel, ops.bypassesPlayerLimit(profile)));
    		this.sendPlayerPermissionLevel(playerMP.getServer().getPlayerList().getPlayerByUUID(profile.getId()), permissionLevel);
    	}
    }

    public void freeze()
    {
    	this.isFrozen = true;
    }
    
    public void unFreeze()
    {
    	this.isFrozen = false;
    }
    
    public boolean isFrozen()
    {
    	return this.isFrozen;
    }
    
    private boolean isVanished;
    
    public void vanish()
    {
    	this.isVanished = true;
    }
    
    public void unVanish()
    {
    	this.isVanished = false;
    }
    
    public boolean isVanished()
    {
    	return this.isVanished;
    }

    public ItemStack getAmmo(Item item) 
	{
    	EntityLivingBase entityliving = this.getEntity();
    	ItemStack offhand = entityliving.getHeldItem(EnumHand.OFF_HAND);
    	ItemStack mainhand = entityliving.getHeldItem(EnumHand.MAIN_HAND);
    	
		if (offhand != null && offhand.getItem() == item)
		{
			return offhand;
		}
		else if (mainhand != null && mainhand.getItem() == item)
		{
			return mainhand;
		}
		else if (entityliving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityliving;
			
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if (stack != null && stack.getItem() == item)
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
	public boolean hasAmmo(Item item)
	{
		return this.getAmmo(item) != null;
	}

	/**
	 * Consumes the specified amount of ammunition from this entity's inventory
	 */
	public void consumeAmmo(Item item, int amount)
	{
		this.getAmmo(item).shrink(amount);
	}

    /**
     * If this entity is a player, adds the specified item to the entity's inventory
     */
    public void addStack(Item item, int amount)
    {
    	if (this.getEntity() instanceof EntityPlayer)
    	{
    		ItemStack stack = new ItemStack(item, amount);
        	EntityPlayer player = (EntityPlayer) this.getEntity();
    		boolean flag = player.inventory.addItemStackToInventory(stack);
    		
            if (flag)
            {
            	player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            	player.inventoryContainer.detectAndSendChanges();
            }

            if (flag && stack.isEmpty())
            {
            	stack.setCount(1);
                EntityItem entityitem = player.dropItem(stack, false);
                
                if (entityitem != null)
                {
                	entityitem.makeFakeItem();
                }
            }
            else
            {
                EntityItem entityitem = player.dropItem(stack, false);

                if (entityitem != null)
                {
                    entityitem.setNoPickupDelay();
                    entityitem.setOwner(player.getName());
                }
            }
    	}
    }

    /**
     * If this entity is a player, removes the specified item from the entity's inventory
     */
    //removes every presence of the stack? not very efficient. take from consumeAmmo() method.
    public void removeStack(Item item, int amount)
    {
    	if (this.getEntity() instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer) this.getEntity();
    		InventoryPlayer inventory = player.inventory;

    		for (ItemStack stack : player.inventoryContainer.getInventory())
    		{
    			if (stack.getItem() == item)
    			{
    				stack.shrink(amount);
    			}
    		}
    	}	
    }
    
    private int shootingTick;
    
    public int getShootingTick()
    {
    	return this.shootingTick;
    }
    
    public void setShootingTick(int tick)
    {
    	this.shootingTick = tick;
    }

    private double attackReach = 0.0F;
    private boolean hasExtraReach = false;
    
    public void setAttackReach(double reach)
    {
    	this.setHasExtraReach(reach > 1.0D ? true : false);
    	this.attackReach = reach;
    }
    
    public boolean hasExtraReach()
    {
    	return this.hasExtraReach;
    }
    
    public void setHasExtraReach(boolean reach)
    {
    	this.hasExtraReach = reach;
    }
    
    public double getAttackReach()
    {
    	return this.attackReach;
    }
    
    public double getBlockReach()
    {
    	return this.getEntity() instanceof EntityPlayer ? ((EntityPlayerMP) this.getEntity()).interactionManager.getBlockReachDistance() : null;
    }
    
    public void setBlockReach(double reach)
    {
    	if (this.getEntity() instanceof EntityPlayer)
    	{
    		((EntityPlayerMP) this.getEntity()).interactionManager.setBlockReachDistance(reach);
    	}
    }
    
    /**
     * Causes this entity to move abruptly backward and receive an upward jolt
     * @param knockback the amount this entity will be knocked back
     * @param recoil the amount of recoil (upward jolt) this entity will receive
     * @param sneakAccuracy whether sneaking reduces the knockback and recoil by one-half
     */
    public void recoil(float knockback, float recoil, boolean sneakAccuracy)
    {
    	EntityLivingBase entityliving = this.getEntity();
    	float f = entityliving.isSneaking() ? -knockback / 2.0F : -knockback;
		double d = -MathHelper.sin((float) (entityliving.rotationYaw / 180F * Math.PI)) * f;
		double d1 = MathHelper.cos((float) (entityliving.rotationYaw / 180F * Math.PI)) * f;
		entityliving.rotationPitch -= entityliving.isSneaking() ? recoil / 2.0F : recoil;
		entityliving.addVelocity(d, 0.0D, d1);
    }
    
    /**
	 * Returns if this entity is holding the specified item in their mainhand or offhand
	 */
	public boolean isHolding(Item item)
	{
		EntityLivingBase entityliving = this.getEntity();
		ItemStack mainhand = entityliving.getHeldItemMainhand();
		ItemStack offhand = entityliving.getHeldItemOffhand();
		return mainhand != null && mainhand.getItem() == item || offhand != null && offhand.getItem() == item;
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
		tag.setBoolean("Fall Damage", this.fallDamage);
		tag.setBoolean("Fall Damage Immune", this.fallDamageImmune);
		tag.setBoolean("Reduced Fall Damage", this.reducedFallDamage);
		tag.setFloat("Fall Damage Reduction Amount", this.fallDamageReductionAmount);
		tag.setBoolean("Invulnerable", this.invulnerable);
		tag.setBoolean("Regenerating", this.regenerating);
		tag.setBoolean("Regenerating", this.regenerating);
		tag.setInteger("Shooting Tick", this.shootingTick);
		tag.setBoolean("Frozen", this.isFrozen);
		
		DinocraftEntityActions actions = DinocraftEntity.getEntity(this.getEntity()).getActionsModule();
		tag.setBoolean("doubleJump", actions.doubleJump);
		tag.setBoolean("hasDoubleJumped", actions.hasDoubleJumped);
		tag.setBoolean("extraMaxHealth", actions.extraMaxHealth);
		tag.setInteger("chlorophyteTick", actions.chlorophyteTick);
		tag.setFloat("chlorophyteAbsorptionAmount", actions.chlorophyteAbsorptionAmount);
		
		DinocraftEntityTicks ticks = DinocraftEntity.getEntity(this.getEntity()).getTicksModule();
		tag.setDouble("Regeneration Loop Ticks", ticks.regenerationLoopTicks);
		tag.setInteger("Regeneration Ticks", ticks.regenerationTicks);
		tag.setInteger("Regeneration Count", ticks.regenerationCount);
		tag.setFloat("Regeneration Hearts", ticks.heartsRegenerate);
		tag.setInteger("Ticks Invulnerable", ticks.ticksInvulnerable);
		tag.setDouble("Degeneration Loop Ticks", ticks.degenerationLoopTicks);
		tag.setInteger("Degeneration Ticks", ticks.degenerationTicks);
		tag.setInteger("Degeneration Count", ticks.degenerationCount);
		tag.setFloat("Degeneration Hearts", ticks.heartsDegenerate);
		tag.setInteger("RegenTick", DinocraftEntityTicks.regenTick);
	}
 
	@Override
	public void read(NBTTagCompound tag)
	{
		this.fallDamage = tag.getBoolean("Fall Damage");
		this.fallDamageImmune = tag.getBoolean("Fall Damage Immune");
		this.reducedFallDamage = tag.getBoolean("Reduced Fall Damage");
		this.fallDamageReductionAmount = tag.getFloat("Fall Damage Reduction Amount");
		this.invulnerable = tag.getBoolean("Invulnerable");
		this.regenerating = tag.getBoolean("Regenerating");
		this.shootingTick = tag.getInteger("Shooting Tick");
		this.isFrozen = tag.getBoolean("Frozen");
		
		DinocraftEntityActions actions = DinocraftEntity.getEntity(this.getEntity()).getActionsModule();
		actions.doubleJump = tag.getBoolean("doubleJump");
		actions.hasDoubleJumped = tag.getBoolean("hasDoubleJumped");
		actions.extraMaxHealth = tag.getBoolean("extraMaxHealth");
		actions.chlorophyteTick = tag.getInteger("chlorophyteTick");
		actions.chlorophyteAbsorptionAmount = tag.getFloat("chlorophyteAbsorptionAmount");
		
		DinocraftEntityTicks ticks = DinocraftEntity.getEntity(this.getEntity()).getTicksModule();
		ticks.regenerationTicks = tag.getInteger("Regeneration Ticks");
		ticks.regenerationLoopTicks = tag.getInteger("Regeneration Loop Ticks");
		ticks.regenerationCount = tag.getInteger("Regeneration Count");
		ticks.heartsRegenerate = tag.getFloat("Regeneration Hearts");
		ticks.ticksInvulnerable = tag.getInteger("Ticks Invulnerable");
		ticks.degenerationTicks = tag.getInteger("Degeneration Ticks");
		ticks.degenerationLoopTicks = tag.getDouble("Degeneration Loop Ticks");
		ticks.degenerationCount = tag.getInteger("Degeneration Count");
		ticks.heartsDegenerate = tag.getFloat("Degeneration Hearts");
		DinocraftEntityTicks.regenTick = tag.getInteger("RegenTick");
	}
}