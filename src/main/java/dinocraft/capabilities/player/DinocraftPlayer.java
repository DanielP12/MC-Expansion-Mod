package dinocraft.capabilities.player;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.jline.utils.Log;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import dinocraft.api.MaxHealth;
import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.util.PlayerHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class DinocraftPlayer implements IDinocraftPlayer
{
	static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
	private final EntityPlayer player;
	private final DinocraftPlayerActions actions;
	private final DinocraftPlayerTicks ticks;
	
	public DinocraftPlayer(EntityPlayer playerIn)
	{
		this.actions = new DinocraftPlayerActions(this);
		this.ticks = new DinocraftPlayerTicks(this);
		this.player = playerIn;
	}
 
	/**
	 * Gets this capability data's corresponding player by their name
	 */
	public static EntityPlayer getEntityPlayerByName(String name)
	{
		/* for (EntityPlayer playerIn : DinocraftPlayer.SERVER.getPlayerList().getPlayers())
		{
			if (playerIn.getName().equals(name)) return playerIn;
		}
		
		return null;
		*/
		return SERVER.getPlayerList().getPlayerByUsername(name);
	}
	
	/**
	 * Gets the capability data for the player by their name
	 */
	public static DinocraftPlayer getPlayer(String name)
	{
		return DinocraftPlayer.getPlayer(DinocraftPlayer.getEntityPlayerByName(name));
	}
	
	/**
	 * Gets the capability data for specified player
	 */
	public static DinocraftPlayer getPlayer(EntityPlayer playerIn)
	{
		if (!DinocraftPlayer.hasCapability(playerIn)) return null;
		return (DinocraftPlayer) playerIn.getCapability(DinocraftCapabilities.DINOCRAFT_PLAYER, null);
	}
 
	/** 
	 * Returns if this player has this capability
	 */
	public static boolean hasCapability(EntityPlayer playerIn)
	{
		return playerIn.hasCapability(DinocraftCapabilities.DINOCRAFT_PLAYER, null);
	}
 
	/**
	 * Gets this capability data's corresponding player
	 */
	@Override
	public EntityPlayer getPlayer() 
	{
		return this.player;
	}
 
	/**
	 * Gets this player's actions module
	 */
	public DinocraftPlayerActions getActions()
	{
		return this.actions;
	}

	/** Gets this player's ticks module */
	private DinocraftPlayerTicks getTicks()
	{
		return this.ticks;
	}

	/**
	 * Returns if specified items are equipped on the player
	 */
	public boolean isWearingItems(@Nonnull Item helmetIn, @Nonnull Item chestplateIn, @Nonnull Item leggingsIn, @Nonnull Item bootsIn)
	{
		InventoryPlayer inv = this.getPlayer().inventory;
		ItemStack helmet = inv.armorItemInSlot(3);
		ItemStack chestplate = inv.armorItemInSlot(2);
		ItemStack leggings = inv.armorItemInSlot(1);
		ItemStack boots = inv.armorItemInSlot(0);
		return (helmetIn != null ? helmet != null && helmet.getItem() == helmetIn : (helmet == null || helmet.getItem() != helmetIn))
				&& (chestplateIn != null ? chestplate != null && chestplate.getItem() == chestplateIn : (chestplate == null || chestplate.getItem() != chestplateIn))
				&& (leggingsIn != null ? leggings != null && leggings.getItem() == leggingsIn : (leggings == null || leggings.getItem() != leggingsIn))
				&& (bootsIn != null ? boots != null && boots.getItem() == bootsIn : (boots == null || boots.getItem() != bootsIn));
	}
 
	/**
	 * Returns if this player is jumping 
	 */
	public boolean isJumping()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
	}
 
	/** 
	 * The direction that this player is moving towards
	 */
	public enum Direction
	{
		LEFT, RIGHT, FORWARD, BACKWARD
	}
 
	/**
	 * Returns if this player is moving in specified direction 
	 */
	public boolean isMoving(Direction direction)
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
 
		switch (direction)
        {
        	case LEFT: return settings.keyBindLeft.isKeyDown();
 
        	case RIGHT: return settings.keyBindRight.isKeyDown();
 
        	case FORWARD: return settings.keyBindForward.isKeyDown();
 
        	case BACKWARD: return settings.keyBindBack.isKeyDown();
 
        	default: return false;
        }
	}
 
	/** 
	 * Returns if this player is moving to any direction
	 */
	public boolean isMoving()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
        return DinocraftPlayer.getPlayer(this.getPlayer()).isStrafing() || settings.keyBindForward.isKeyDown() || settings.keyBindBack.isKeyDown();
	}
 
	/** 
	 * Returns if this player is strafing to any side
	 */
	public boolean isStrafing()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		return settings.keyBindLeft.isKeyDown() || settings.keyBindRight.isKeyDown();
	}
 
	/** Whether this player takes fall damage on their next fall or not */
	private boolean fallDamage = true;
 
	/** 
	 * Sets if this player takes fall damage on their next fall.
	 */
	public void setFallDamage(boolean fallDamage)
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(this.getPlayer());	
		if (!player.isFallDamageImmune()) this.fallDamage = fallDamage;
	}
 
	/** 
	 * Returns if this player takes fall damage on their next fall.
	 */
	public boolean hasFallDamage()
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(this.getPlayer());
		if (player.isFallDamageImmune() || player.isInvulnerable()) return false;
		return this.fallDamage;
	}
 
	/** Whether this player is immune to fall damage */
	private boolean fallDamageImmune = false;
 
	/** 
	 * Sets if this player is immune to fall damage.
	 */
	public void setFallDamageImmune(boolean immune)
	{
		this.fallDamageImmune = immune;
	}
 
	/** 
	 * Returns if this player is immune to fall damage.
	 */
	public boolean isFallDamageImmune()
	{
		if (DinocraftPlayer.getPlayer(this.getPlayer()).isInvulnerable()) return true;
		return this.fallDamageImmune;
	}
 
	/** Whether this player has reduced fall damage */
	private boolean reducedFallDamage = false;
 
	/**
	 * Returns if this player has reduced fall damage on their next fall.
	 */
	public boolean hasReducedFallDamage()
	{
		return this.reducedFallDamage;
	}
 
	/** Sets if this player has reduced fall damage on their next fall. */
	private void setReducedFallDamage(boolean reducedFallDamage)
	{
		this.reducedFallDamage = reducedFallDamage;
	}
 
	/** This player's fall damage reduction amount */
	private float fallDamageReductionAmount = 0.0F;
 
	/**
	 * Sets this player's fall damage reduction amount on their next fall (param: amount of half-hearts).
	 */
	public void setFallDamageReductionAmount(float fallDamageReductionAmount)
	{
		this.fallDamageReductionAmount = fallDamageReductionAmount;
		DinocraftPlayer.getPlayer(this.getPlayer()).setReducedFallDamage(true);
	}
 
	/**
	 * Returns this player's fall damage reduction amount (amount of half-hearts).
	 */
	public float getFallDamageReductionAmount()
	{
		return this.fallDamageReductionAmount;
	}
	
	/** Whether this player has permanent reduced fall damage */
    private boolean fallDamageReducer = false;
	
    /**
	 * Sets this player's permanent fall damage reduction amount (param: amount of half-hearts).
	 */
	public void setFallDamageReducer(float fallDamageReductionAmount)
	{
        this.fallDamageReductionAmount = fallDamageReductionAmount;
        DinocraftPlayer.getPlayer(this.getPlayer()).setFallDamageReducer(true);
    }
	
	/** Sets if this player has permanent reduced fall damage. */
	public boolean hasFallDamageReducer() 
	{
        return this.fallDamageReducer;
	}
	   
	/**
	 * Sets this player's permanent fall damage reduction amount (param: amount of half-hearts).
	 */
    private void setFallDamageReducer(boolean reducedFallDamage) 
    {
        this.fallDamageReducer = reducedFallDamage;
    }
 
	
	@SubscribeEvent
    public static void onFall(LivingFallEvent event) 
	{
        if (event.getEntity() instanceof EntityPlayer) 
        {
            EntityPlayer playerIn = (EntityPlayer) event.getEntity();
            DinocraftPlayer player = getPlayer(playerIn);
            
            if (!player.hasFallDamage() || player.isFallDamageImmune()) 
            {
                if (playerIn.fallDistance < 5.0F) event.setCanceled(true);
                if (playerIn.fallDistance >= 5.0F && !playerIn.world.isRemote) event.setCanceled(true);
                if (!player.hasFallDamage()) player.setFallDamage(true);
                
                return;
            }
            if (player.hasReducedFallDamage() || player.hasFallDamageReducer())
            {
                if (playerIn.fallDistance < 5.0F) event.setCanceled(true);
                if (playerIn.fallDistance >= 5.0F && !playerIn.world.isRemote) event.setCanceled(true);
                
                PotionEffect effect = playerIn.getActivePotionEffect(MobEffects.JUMP_BOOST);
				float modifier = effect == null ? 0.0F : (float) effect.getAmplifier() + 1.0F;
				int damage = MathHelper.ceil((playerIn.fallDistance - 3.0F - modifier - player.getFallDamageReductionAmount()));
				
                if (damage > 0) playerIn.attackEntityFrom(DamageSource.FALL, (float) damage);
                
                player.setReducedFallDamage(false);
            }
        }
    }
	
    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("B04DB08B-ED8A-4B82-B1EF-ADB425174925");
	
	/** 
	 * Sets this player's max health to specified amount 
	 */
	public void setMaxHealth(float amount)
    {
		EntityPlayer playerIn = this.getPlayer();
        IAttributeInstance maxHealthInstance = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = maxHealthInstance.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID, "Max Health Setter", amount - 20D, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        playerIn.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/** 
	 * Adds specified amount to this player's max health 
	 */
	public void addMaxHealth(float amount)
    {
		EntityPlayer playerIn = this.getPlayer();
        IAttributeInstance maxHealthInstance = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = maxHealthInstance.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
        double existingHearts = modifier != null ? modifier.getAmount() : 0.0D;

        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID, "Max Health Adder", existingHearts + amount, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        playerIn.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/**
	 * Feeds this player to specified amounts
	 */
	public void feed(int amount, float saturation)
	{
		this.getPlayer().getFoodStats().addStats(amount, saturation);
	}
	
	/**
	 * Hurts this player (param: amount of half-hearts)
	 */
	public void hurt(float amount)
	{
		if (amount <= 0.0F) return;
		
		EntityPlayer playerIn = this.getPlayer();
        float health = playerIn.getHealth();
        
        if (health > 0.0F) playerIn.setHealth(health - amount);
	}
 
	/** 
	 * Stops the specified sound for all players
	 */
	public void stopSound(SoundEvent soundIn)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString("");
        buffer.writeString(soundIn.getRegistryName().toString());
        this.SERVER.getPlayerList().sendPacketToAllPlayers(new SPacketCustomPayload("MC|StopSound", buffer));
	}
 
	/** 
	 * Stops the specified sound for the specified player
	 */
	public void stopSound(EntityPlayer playerIn, SoundEvent soundIn)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString("");
        buffer.writeString(soundIn.getRegistryName().toString());
        ((EntityPlayerMP) playerIn).connection.sendPacket(new SPacketCustomPayload("MC|StopSound", buffer));
	}
 
	/**
	 * Gets a RayTraceResult describing whatever the player's looking at within specified distance
	 */
	public RayTraceResult getTrace(double distance) 
	{
		EntityPlayer playerIn = this.getPlayer();
		Vec3d posVec = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
		return playerIn.world.rayTraceBlocks(posVec, posVec.add(playerIn.getLookVec().scale(distance)));
	}
 
	/**
	 * Sets whether this player is allowed to fly when they double jump
	 */
	public void setAllowFlight(boolean allowFlight)
	{
		this.getPlayer().capabilities.allowFlying = allowFlight;
	}
 
	/**
	 * Returns if this player is allowed to fly when they double jump
	 */
	public boolean canFly()
	{
		return this.getPlayer().capabilities.allowFlying;
	}
 
	/**
	 * Sets whether this player is flying
	 */
	public void setFlight(boolean flight)
	{
		this.getPlayer().capabilities.isFlying = flight;
	}
	
	/**
	 * Returns whether this player is flying
	 */
	public boolean isFlying()
	{
		return this.getPlayer().capabilities.isFlying;
	}
 
	/**
	 * Gets the world that this player is in
	 */
	@Override
	public World getWorld()
	{
		return this.getPlayer().world;
	}
 
	/** Whether this player is invulnerable or not */
	private boolean invulnerable = false;
 
	/** 
	 * Returns if this player is currently invulnerable 
	 */
    public boolean isInvulnerable()
    {
        return this.invulnerable;
    }
 
    /** Sets whether this player is invulnerable or not */
    private void setInvulnerable(boolean invulnerable)
    {
        this.invulnerable = invulnerable;
    }
 
    /**
     * Sets this player invulnerable for the specified time (seconds)
     */
    public void setInvulnerable(int time)
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(this.getPlayer());
		player.getTicks().setTicksInvulnerable(time * 20);
		player.setInvulnerable(true);
	}
 
	/** Whether this player is degenerating */
	private boolean degenerating = false;
    
	/** 
	 * Returns if this player is degenerating
	 */
    public boolean isDegenerating()
    {
        return this.degenerating;
    }
    
    /** Sets whether this player is degenerating or not */
    private void setDegenerating(boolean degenerating)
    {
        this.degenerating = degenerating;
    }

    /**
     * Sets this player degenerating
     * @param degenerationTicks the amount of ticks to degenerate
     * @param degenerationLoopTicks the amount of ticks in between each degeneration loop
     * @param heartsDegenerate the amount of half-hearts to hurt for each degeneration loop
     */
    public void setDegenerating(int degenerationTicks, int degenerationLoopTicks, float heartsDegenerate)
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(this.getPlayer());
		DinocraftPlayerTicks ticks = player.getTicks();
		ticks.setDegenerationTicks(degenerationTicks);
		ticks.setDegenerationLoopTicks(degenerationLoopTicks);
		ticks.setDegenerationHearts(heartsDegenerate);
		player.setDegenerating(true);
	}
    
    /** Whether this player is regenerating */
	private boolean regenerating = false;
    
	/** 
	 * Returns if this player is regenerating
	 */
    public boolean isRegenerating()
    {
    	if (this.getPlayer().getActivePotionEffect(MobEffects.REGENERATION) != null) return true;
        return this.regenerating;
    }
    
    /** Sets whether this player is regenerating or not */
    private void setRegenerating(boolean regenerating)
    {
        this.regenerating = regenerating;
    }

    /**
     * Sets this player regenerating
     * @param time the amount of time to regenerate for (seconds)
     * @param loopTime the amount of time in between each regeneration loop (seconds)
     * @param health the amount of health to heal for each loop (half-hearts)
     */
    public void setRegenerating(int time, double loopTime, float health)
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(this.getPlayer());
		DinocraftPlayerTicks ticks = player.getTicks();
		ticks.setRegenerationTicks(time * 20);
		ticks.setRegenerationLoopTicks(loopTime * 20);
		ticks.setRegenerationHearts(health);
		player.setRegenerating(true);
	}
	
	@SubscribeEvent
	public static void onTickingPlayer(PlayerTickEvent event)
	{
		if (event.phase != Phase.END) return;
		
		EntityPlayer playerIn = event.player;
    	DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
    	DinocraftPlayerTicks ticks = player.getTicks();
    	
    	if (!playerIn.world.isRemote)
    	{	
    		if (player.isRegenerating())
    		{
    			if (ticks.getRegenerationTicks() <= 0)
    			{
    				player.setRegenerating(false);
    				ticks.setRegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setRegenerationTicks(ticks.getRegenerationTicks() - 1);
    	    		ticks.setRegenerationCount(ticks.getRegenerationCount() + 1);
        		
    		    	if (ticks.getRegenerationCount() == ticks.getRegenerationLoopTicks())
    	    		{
    		   			ticks.setRegenerationCount(0);
    		   			
    		   			if (playerIn.getHealth() != playerIn.getMaxHealth()) 
    		   			{
    		   				playerIn.heal(ticks.getRegenerationHearts());
    		   			}
    	    		}
    	    	}
    		}
    		
    		if (player.isDegenerating())
    		{
    			if (ticks.getDegenerationTicks() <= 0)
    			{
    				player.setDegenerating(false);
    				ticks.setDegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setDegenerationTicks(ticks.getDegenerationTicks() - 1);
    	    		ticks.setDegenerationCount(ticks.getDegenerationCount() + 1);
        		
    		    	if (ticks.getDegenerationCount() == ticks.getDegenerationLoopTicks())
    	    		{
    		   			ticks.setDegenerationCount(0);
    		   			player.hurt(ticks.getDegenerationHearts());
    	    		}
    	    	}
    		}
    		
    		if (player.isInvulnerable())
    		{
    			if (ticks.getTicksInvulnerable() <= 0)
    			{
    				player.setInvulnerable(false);
    				playerIn.setEntityInvulnerable(false);
    			}
    			else
    			{
    				ticks.setTicksInvulnerable(ticks.getTicksInvulnerable() - 1);
    				playerIn.setEntityInvulnerable(true);
    			}
    		}
    	}
	}
	
	@SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
		if (event.getEntityPlayer() != null)
		{
			EntityPlayer playerIn = event.getEntityPlayer();
			DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
			
			player.setInvulnerable(3);
			player.setRegenerating(1000, .05, 1);
			
			IAttributeInstance oldMaxHealth = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
	        AttributeModifier modifier = oldMaxHealth.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
	        
	        if (modifier != null)
	        {
	            Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
	            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
	            playerIn.getAttributeMap().applyAttributeModifiers(multimap);
	        }
	        
	        DinocraftPlayer oldPlayer = DinocraftPlayer.getPlayer(event.getOriginal());			
			DinocraftPlayer newPlayer = DinocraftPlayer.getPlayer(playerIn);
			/*final IStorage<IDinocraftPlayer> storage = DinocraftCapabilities.DINOCRAFT_PLAYER.getStorage();

    		final NBTBase state = storage.writeNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, oldPlayer, null);
    		storage.readNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, newPlayer, null, state);*/
			newPlayer.setFallDamageImmune(oldPlayer.isFallDamageImmune());
            newPlayer.setInvulnerable(oldPlayer.isInvulnerable());
            newPlayer.getActions().setHasExtraMaxHealth(oldPlayer.getActions().hasExtraMaxHealth());
		}
    }
    
    /**
     * Sends a raw chat message to this player
     */
    public void sendChatMessage(String msg)
    {
    	this.getPlayer().sendMessage(new TextComponentString(msg));
    }
    
    /**
     * Sends a raw actionbar message to this player
     */
    public void sendActionbarMessage(String msg)
    {
    	((EntityPlayerMP) this.getPlayer()).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString(msg)));
    }
    
    /**
     * Forces this player to say specified message
     */
    public void say(String msg)
    {
    	this.SERVER.getPlayerList().sendMessage(new TextComponentString("<" + this.getPlayer().getName() + "> " + msg));
    }
    
    /**
     * Kicks this player from the game
     */
    public void kick(String msg)
    {
        ((EntityPlayerMP) this.getPlayer()).connection.disconnect(new TextComponentString(msg));
    }
    
    /**
     * Returns if this player is an operator
     */
    public boolean isOpped()
    {
		return this.SERVER.getPlayerList().canSendCommands(this.getPlayer().getGameProfile());
    }
    
    /**
     * Gets the operator level of this player
     */
    public int getOpLevel()
    {
    	EntityPlayer playerIn = this.getPlayer();
    	return playerIn.canUseCommand(4, "/heal") ? 4 : playerIn.canUseCommand(3, "/jump") ? 3 : playerIn.canUseCommand(2, "/ban") ? 2 : playerIn.canUseCommand(1, "/say") ? 1 : 0;
    }
    
    /**
     * Ops this player
     */
    public void op()
    {
		this.SERVER.getPlayerList().addOp(((EntityPlayerMP) this.getPlayer()).getGameProfile());
    }
    
    /**
     * Checks if this player has the specified ammunition
     */
    public boolean hasAmmo(Predicate<ItemStack> ammo)
    {
    	return PlayerHelper.hasAmmo(this.getPlayer(), ammo);
    }

    /**
     * Consumes the specified ammunition from the player's inventory
     */
    public void consumeAmmo(Predicate<ItemStack> ammo)
    {
    	PlayerHelper.consumeAmmo(this.getPlayer(), ammo);
    }
    
    /**
     * Adds the specified item to the player's inventory
     */
    public void addStack(Item item, int amount)
    {
    	ItemStack stack = new ItemStack(item, amount);
    	EntityPlayer playerIn = this.getPlayer();
		boolean flag = playerIn.inventory.addItemStackToInventory(stack);
		
        if (flag)
        {
        	playerIn.world.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerIn.getRNG().nextFloat() - playerIn.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        	playerIn.inventoryContainer.detectAndSendChanges();
        }

        if (flag && stack.isEmpty())
        {
        	stack.setCount(1);
            EntityItem entityitem = playerIn.dropItem(stack, false);
            if (entityitem != null) entityitem.makeFakeItem();
        }
        else
        {
            EntityItem entityitem = playerIn.dropItem(stack, false);

            if (entityitem != null)
            {
                entityitem.setNoPickupDelay();
                entityitem.setOwner(playerIn.getName());
            }
        }
    }
    
    /**
     * Removes the specified item from the player's inventory
     */
    public void removeStack(Item item, int amount)
    {
    	EntityPlayer playerIn = this.getPlayer();
    	InventoryPlayer inventory = playerIn.inventory;

    	for (ItemStack stack : playerIn.inventoryContainer.getInventory())
    	{
    		if (stack.getItem() == item) stack.setCount(stack.getCount() - amount);;
    	}
    }
    
	public static class Storage implements IStorage<IDinocraftPlayer>
	{
		@Override
		public NBTBase writeNBT(Capability<IDinocraftPlayer> capability, IDinocraftPlayer instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();
			instance.write(compound);
			return compound;
		}
 
		@Override
		public void readNBT(Capability<IDinocraftPlayer> capability, IDinocraftPlayer instance, EnumFacing side, NBTBase nbt)
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
	}
}