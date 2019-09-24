package dinocraft.capabilities.player;

/*
@EventBusSubscriber
public class DinocraftPlayer implements IDinocraftPlayer
{
	static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
	private final EntityPlayer player;
	private final DinocraftPlayerActions actions;
	private final DinocraftPlayerTicks ticks;
	
	public DinocraftPlayer(EntityPlayer player)
	{
		this.actions = new DinocraftPlayerActions(this);
		this.ticks = new DinocraftPlayerTicks(this);
		this.player = player;
	}
 
	/**
	 * Gets this capability data's corresponding player
	 */
/*
	@Override
	public EntityPlayer getPlayer() 
	{
		return this.player;
	}
	
	/**
	 * Gets this capability data's corresponding player by their name
	 */
/*
	public static EntityPlayer getEntityPlayerByName(String name)
	{
		/* for (EntityPlayer player : DinocraftPlayer.SERVER.getPlayerList().getPlayers())
		{
			if (player.getName().equals(name))
			{
				return player;
			}
		}
		
		return null;
		*/
/*
		return SERVER.getPlayerList().getPlayerByUsername(name);
	}
	
	/**
	 * Gets the capability data for the player by their name
	 */
/*
	public static DinocraftPlayer getPlayer(String name)
	{
		return DinocraftPlayer.getPlayer(DinocraftPlayer.getEntityPlayerByName(name));
	}
	
	/**
	 * Gets the capability data for specified player
	 */
/*
	public static DinocraftPlayer getPlayer(EntityPlayer player)
	{
		return !DinocraftPlayer.hasCapability(player) ? null : (DinocraftPlayer) player.getCapability(DinocraftCapabilities.DINOCRAFT_PLAYER, null);
	}
 
	/** 
	 * Returns if this player has this capability
	 */
/*
	public static boolean hasCapability(EntityPlayer player)
	{
		return player.hasCapability(DinocraftCapabilities.DINOCRAFT_PLAYER, null);
	}
 
	/**
	 * Gets this player's actions module
	 */
/*
	public DinocraftPlayerActions getActionsModule()
	{
		return this.actions;
	}

	/** Gets this player's ticks module */
/*
	private DinocraftPlayerTicks getTicksModule()
	{
		return this.ticks;
	}

	/**
	 * Returns if specified items are equipped on this player. In this case, null means nothing OR any other item equipped.
	 */
/*
	public boolean isWearingItems(@Nullable Item helmet, @Nullable Item chestplate, @Nullable Item leggings, @Nullable Item boots)
	{
		EntityPlayer player = this.getPlayer();
		ItemStack helmet2 = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chestplate2 = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack leggings2 = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack boots2 = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		return (helmet != null ? helmet2 != null && helmet2.getItem() == helmet : (helmet2 == null || helmet2.getItem() != helmet))
				&& (chestplate != null ? chestplate2 != null && chestplate2.getItem() == chestplate : (chestplate2 == null || chestplate2.getItem() != chestplate))
				&& (leggings != null ? leggings2 != null && leggings2.getItem() == leggings : (leggings2 == null || leggings2.getItem() != leggings))
				&& (boots != null ? boots2 != null && boots2.getItem() == boots : (boots2 == null || boots2.getItem() != boots));
	}
	
	/**
	 * Returns if this player is jumping 
	 */
/*
	@SideOnly(Side.CLIENT)
	public boolean isJumping()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
	}
 
	/** 
	 * The direction that this player is moving towards
	 */
/*
	public enum Direction
	{
		LEFT, RIGHT, FORWARD, BACKWARD
	}
 
	/**
	 * Returns if this player is moving in specified direction 
	 */
/*
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
	 * Returns if this player is moving to any direction
	 */
/*
	@SideOnly(Side.CLIENT)
	public boolean isMoving()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
        return DinocraftPlayer.getPlayer(this.getPlayer()).isStrafing() || settings.keyBindForward.isKeyDown() || settings.keyBindBack.isKeyDown();
	}
 
	/** 
	 * Returns if this player is strafing to any side
	 */
/*
	@SideOnly(Side.CLIENT)
	public boolean isStrafing()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		return settings.keyBindLeft.isKeyDown() || settings.keyBindRight.isKeyDown();
	}
 
	/**
	 * Spawns particles around this player
	 */
/*
	public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int parameters)
	{
		NetworkHandler.sendToAllAround(new PacketSpawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters), this.getPlayer().world);
	}
	
	/** Whether this player takes fall damage on their next fall or not */
/*
	private boolean fallDamage = true;
 
	/** 
	 * Sets if this player takes fall damage on their next fall.
	 */
/*
	public void setFallDamage(boolean fallDamage)
	{
		if (!DinocraftPlayer.getPlayer(this.getPlayer()).isFallDamageImmune())
		{
			this.fallDamage = fallDamage;
		}
	}
 
	/** 
	 * Returns if this player takes fall damage on their next fall
	 */
/*
	public boolean hasFallDamage()
	{
		DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(this.getPlayer());
		return (dinoPlayer.isFallDamageImmune() || dinoPlayer.isInvulnerable()) ? false : this.fallDamage;
	}
 
	/** Whether this player is immune to fall damage */
/*
	private boolean fallDamageImmune = false;
 
	/** 
	 * Sets if this player is immune to fall damage
	 */
/*
	public void setFallDamageImmune(boolean immune)
	{
		this.fallDamageImmune = immune;
	}
 
	/** 
	 * Returns if this player is immune to fall damage
	 */
/*
	public boolean isFallDamageImmune()
	{
		return (DinocraftPlayer.getPlayer(this.getPlayer()).isInvulnerable()) ? true : this.fallDamageImmune;
	}
 
	/** Whether this player has reduced fall damage */
/*
	private boolean reducedFallDamage = false;
 
	/**
	 * Returns if this player has reduced fall damage on their next fall
	 */
/*
	public boolean hasReducedFallDamage()
	{
		return this.reducedFallDamage;
	}
 
	/** Sets if this player has reduced fall damage on their next fall */
/*
	private void setReducedFallDamage(boolean reducedFallDamage)
	{
		this.reducedFallDamage = reducedFallDamage;
	}
 
	/** This player's fall damage reduction amount */
/*
	private float fallDamageReductionAmount = 0.0F;
 
	/**
	 * Sets this player's fall damage reduction amount on their next fall (param: amount of half-hearts)
	 */
/*
	public void setFallDamageReductionAmount(float fallDamageReductionAmount)
	{
		this.fallDamageReductionAmount = fallDamageReductionAmount;
		DinocraftPlayer.getPlayer(this.getPlayer()).setReducedFallDamage(true);
	}
 
	/**
	 * Returns this player's fall damage reduction amount (amount of half-hearts).
	 */
/*
	public float getFallDamageReductionAmount()
	{
		return this.fallDamageReductionAmount;
	}
	
	/** Whether this player has permanent reduced fall damage */
/*
    private boolean fallDamageReducer = false;
	
    /**
	 * Sets this player's permanent fall damage reduction amount (param: amount of half-hearts)
	 */
/*
	public void setFallDamageReducer(float fallDamageReductionAmount)
	{
        this.fallDamageReductionAmount = fallDamageReductionAmount;
        DinocraftPlayer.getPlayer(this.getPlayer()).setFallDamageReducer(true);
    }
	
	/** Sets if this player has permanent reduced fall damage. */
/*
	public boolean hasFallDamageReducer() 
	{
        return this.fallDamageReducer;
	}
	   
	/**
	 * Sets this player's permanent fall damage reduction amount (param: amount of half-hearts)
	 */
/*
    private void setFallDamageReducer(boolean reducedFallDamage) 
    {
        this.fallDamageReducer = reducedFallDamage;
    }
	
	@SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) 
	{
        if (event.getEntity() instanceof EntityPlayer) 
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(player);
            
            if (!dinoPlayer.hasFallDamage() || dinoPlayer.isFallDamageImmune()) 
            {
                if (player.fallDistance < 5.0F || (player.fallDistance >= 5.0F && !player.world.isRemote))
                {
                	event.setCanceled(true);
                }
                
                if (!dinoPlayer.hasFallDamage())
                {
                	dinoPlayer.setFallDamage(true);
                }
                
                return;
            }
            
            if (dinoPlayer.hasReducedFallDamage() || dinoPlayer.hasFallDamageReducer())
            {
                if (player.fallDistance < 5.0F || (player.fallDistance >= 5.0F && !player.world.isRemote))
                {
                	event.setCanceled(true);
                }
                
                PotionEffect effect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
				float modifier = effect == null ? 0.0F : effect.getAmplifier() + 1.0F;
				float damage = MathHelper.ceil((player.fallDistance - 3.0F - modifier - dinoPlayer.getFallDamageReductionAmount()));
				
                if (damage > 0.0F)
                {
                	player.attackEntityFrom(DamageSource.FALL, damage);
                }
                
                dinoPlayer.setReducedFallDamage(false);
            }
        }
    }
	
    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("B04DB08B-ED8A-4B82-B1EF-ADB425174925");
	
	/** 
	 * Sets this player's max health to specified amount 
	 */
/*
	public void setMaxHealth(float amount)
    {
		EntityPlayer player = this.getPlayer();
        IAttributeInstance instance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Setter", amount - 20.0D, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/** 
	 * Adds specified amount to this player's max health 
	 */
/*
	public void addMaxHealth(float amount)
    {
		EntityPlayer player = this.getPlayer();
        IAttributeInstance instance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = instance.getModifier(MAX_HEALTH_MODIFIER_ID);
        double hearts = modifier != null ? modifier.getAmount() : 0.0D;

        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Max Health Adder", hearts + amount, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }
 
	/**
	 * Feeds this player to specified amounts
	 */
/*
	public void feed(int amount, float saturation)
	{
		this.getPlayer().getFoodStats().addStats(amount, saturation);
	}
	
	/**
	 * Hurts this player (param: amount of half-hearts)
	 */
/*
	public void hurt(float amount)
	{
		if (amount <= 0.0F)
		{
			return;
		}
		
		EntityPlayer player = this.getPlayer();
        float health = player.getHealth();
        
        if (health > 0.0F)
        {
        	player.setHealth(health - amount);
        }
	}
 
	/** 
	 * Stops the specified sound for all players
	 */
/*
	public void stopSound(SoundEvent sound)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString("");
        buffer.writeString(sound.getRegistryName().toString());
        this.SERVER.getPlayerList().sendPacketToAllPlayers(new SPacketCustomPayload("MC|StopSound", buffer));
	}
 
	/** 
	 * Stops the specified sound for the specified player
	 */
/*
	public void stopSound(EntityPlayer player, SoundEvent sound)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString("");
        buffer.writeString(sound.getRegistryName().toString());
        ((EntityPlayerMP) player).connection.sendPacket(new SPacketCustomPayload("MC|StopSound", buffer));
	}
 
	/**
	 * Gets a RayTraceResult describing whatever block this player is looking at within specified distance. DOESN'T DETECT ENTITIES.
	 */
/*
	public RayTraceResult getTrace(double distance) 
	{
		EntityPlayer player = this.getPlayer();
		Vec3d vector = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		return player.world.rayTraceBlocks(vector, vector.add(player.getLookVec().scale(distance)));
	}
 
	/**
	 * Sets whether this player is allowed to fly when they double jump
	 */
/*
	public void setAllowFlight(boolean allowFlight)
	{
		this.getPlayer().capabilities.allowFlying = allowFlight;
	}
 
	/**
	 * Returns if this player is allowed to fly when they double jump
	 */
/*
	public boolean canFly()
	{
		return this.getPlayer().capabilities.allowFlying;
	}
 
	/**
	 * Sets whether this player is flying
	 */
/*
	public void setFlight(boolean flight)
	{
		this.getPlayer().capabilities.isFlying = flight;
	}
	
	/**
	 * Returns whether this player is flying
	 */
/*
	public boolean isFlying()
	{
		return this.getPlayer().capabilities.isFlying;
	}
 
	/**
	 * Gets the world that this player is in
	 */
/*
	@Override
	public World getWorld()
	{
		return this.getPlayer().world;
	}
 
	/** Whether this player is invulnerable or not */
/*
	private boolean invulnerable = false;
 
	/** 
	 * Returns if this player is currently invulnerable 
	 */
/*
    public boolean isInvulnerable()
    {
        return this.invulnerable;
    }
 
    /** Sets whether this player is invulnerable or not */
/*
    private void setInvulnerable(boolean invulnerable)
    {
        this.invulnerable = invulnerable;
    }
 
    /**
     * Sets this player invulnerable for the specified time (seconds)
     */
/*
    public void setInvulnerable(int time)
	{
		DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(this.getPlayer());
		dinoPlayer.getTicksModule().setTicksInvulnerable(time * 20);
		dinoPlayer.setInvulnerable(true);
	}
 
	/** Whether this player is degenerating */
/*
	private boolean degenerating = false;
    
	/** 
	 * Returns if this player is degenerating
	 */
/*
    public boolean isDegenerating()
    {
        return this.degenerating;
    }
    
    /** Sets whether this player is degenerating or not */
/*
    private void setDegenerating(boolean degenerating)
    {
        this.degenerating = degenerating;
    }

    /**
     * Sets this player degenerating
     * @param time the amount of time to degenerate for (seconds)
     * @param loopTime the amount of time in between each degeneration loop (seconds)
     * @param health the amount of health to degenerate for each loop (half-hearts)
     */
/*
    public void setDegenerating(int time, double loopTime, float health)
	{
		DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(this.getPlayer());
		dinoPlayer.setDegenerating(true);

		DinocraftPlayerTicks ticks = dinoPlayer.getTicksModule();
		ticks.setDegenerationTicks(time * 20);
		ticks.setDegenerationLoopTicks(loopTime * 20);
		ticks.setDegenerationHearts(health);
	}
    
    /** Whether this player is regenerating */
/*
	private boolean regenerating = false;
    
	/** 
	 * Returns if this player is regenerating
	 */
/*
    public boolean isRegenerating()
    {   
        return this.getPlayer().getActivePotionEffect(MobEffects.REGENERATION) != null ? true : this.regenerating;
    }
    
    /** Sets whether this player is regenerating or not */
/*
    private void setRegenerating(boolean regenerating)
    {
        this.regenerating = regenerating;
    }

    /**
     * Sets this player regenerating
     * @param time the amount of time to regenerate for (seconds)
     * @param loopTime the amount of time in between each regeneration loop (seconds)
     * @param health the amount of health to regenerate for each loop (half-hearts)
     */
/*
    public void setRegenerating(int time, double loopTime, float health)
	{
		DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(this.getPlayer());
		dinoPlayer.setRegenerating(true);

		DinocraftPlayerTicks ticks = dinoPlayer.getTicksModule();
		ticks.setRegenerationTicks(time * 20);
		ticks.setRegenerationLoopTicks(loopTime * 20);
		ticks.setRegenerationHearts(health);
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase != Phase.END)
		{
			return;
		}
		
		EntityPlayer player = event.player;
    	DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(player);
    	DinocraftPlayerTicks ticks = dinoPlayer.getTicksModule();
    	
    	if (!player.world.isRemote)
    	{	
    		if (dinoPlayer.isRegenerating())
    		{
    			if (ticks.getRegenerationTicks() <= 0)
    			{
    				dinoPlayer.setRegenerating(false);
    				ticks.setRegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setRegenerationTicks(ticks.getRegenerationTicks() - 1);
    	    		ticks.setRegenerationCount(ticks.getRegenerationCount() + 1);
        		
    		    	if (ticks.getRegenerationCount() == ticks.getRegenerationLoopTicks())
    	    		{
    		   			ticks.setRegenerationCount(0);
    		   			
    		   			if (player.getHealth() != player.getMaxHealth()) 
    		   			{
    		   				player.heal(ticks.getRegenerationHearts());
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
    		
    		if (dinoPlayer.isDegenerating())
    		{
    			if (ticks.getDegenerationTicks() <= 0)
    			{
    				dinoPlayer.setDegenerating(false);
    				ticks.setDegenerationCount(0);
        		}
    	    	else
    	    	{
    	    		ticks.setDegenerationTicks(ticks.getDegenerationTicks() - 1);
    	    		ticks.setDegenerationCount(ticks.getDegenerationCount() + 1);
        		
    		    	if (ticks.getDegenerationCount() == ticks.getDegenerationLoopTicks())
    	    		{
    		   			ticks.setDegenerationCount(0);
    		   			dinoPlayer.hurt(ticks.getDegenerationHearts());
    	    		}
    	    	}
    		}
    		
    		if (dinoPlayer.isInvulnerable())
    		{
    			if (ticks.getTicksInvulnerable() <= 0)
    			{
    				dinoPlayer.setInvulnerable(false);
    				player.setEntityInvulnerable(false);
    			}
    			else
    			{
    				ticks.setTicksInvulnerable(ticks.getTicksInvulnerable() - 1);
    				player.setEntityInvulnerable(true);
    			}
    		}
    	}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
		if (event.getEntityPlayer() != null)
		{
			EntityPlayer player = event.getEntityPlayer();
			//player.setInvulnerable(3);
			//player.setRegenerating(1000, .05, 1);
			IAttributeInstance oldMaxHealth = event.getOriginal().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
	        AttributeModifier modifier = oldMaxHealth.getModifier(MAX_HEALTH_MODIFIER_ID);
	        
	        if (modifier != null)
	        {
	            Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
	            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
	            player.getAttributeMap().applyAttributeModifiers(multimap);
	        }
	        
			DinocraftPlayer newPlayer = DinocraftPlayer.getPlayer(player);
	        DinocraftPlayer oldPlayer = DinocraftPlayer.getPlayer(event.getOriginal());			

			final IStorage<IDinocraftPlayer> storage = DinocraftCapabilities.DINOCRAFT_PLAYER.getStorage();
    		final NBTBase state = storage.writeNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, oldPlayer, null);
    		storage.readNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, newPlayer, null, state);
		}
    }
    
    /**
     * Sends a raw chat message to this player
     */
/*
    public void sendChatMessage(String msg)
    {
    	this.getPlayer().sendMessage(new TextComponentString(msg));
    }
    
    /**
     * Sends a raw actionbar message to this player
     */
/*
    public void sendActionbarMessage(String msg)
    {
    	((EntityPlayerMP) this.getPlayer()).connection.sendPacket(new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString(msg)));
    }
    
    /**
     * Forces this player to say specified message
     */
/*
    public void say(String msg)
    {
    	this.SERVER.getPlayerList().sendMessage(new TextComponentString("<" + this.getPlayer().getName() + "> " + msg));
    }
    
    /**
     * Kicks this player from the game with specified message
     */
/*
    public void kick(String msg)
    {
        ((EntityPlayerMP) this.getPlayer()).connection.disconnect(new TextComponentString(msg));
    }
    
    /**
     * Returns if this player has the specified permission level or higher
     */
/*
    public boolean hasOpLevel(int level)
    {
		return (this.getOpLevel() >= level) ? true : false;
    }
    
    /**
     * Gets the operator level of this player
     */
/*
    public int getOpLevel()
    {
    	EntityPlayer player = this.getPlayer();
    	return player.canUseCommand(4, "") ? 4 : player.canUseCommand(3, "") ? 3 : player.canUseCommand(2, "") ? 2 : player.canUseCommand(1, "") ? 1 : 0;
    }
    
    /**
     * Ops this player
     */
/*
    public void op()
    {
		this.SERVER.getPlayerList().addOp(((EntityPlayerMP) this.getPlayer()).getGameProfile());
    }
    
    /**
     * Checks if this player has the specified ammunition
     */
/*
    public boolean hasAmmo(Predicate<ItemStack> ammo)
    {
    	return PlayerHelper.hasAmmo(this.getPlayer(), ammo);
    }

    /**
     * Consumes the specified ammunition from the player's inventory
     */
/*
    public void consumeAmmo(Predicate<ItemStack> ammo)
    {
    	PlayerHelper.consumeAmmo(this.getPlayer(), ammo);
    }
    
    /**
     * Adds the specified item to the player's inventory
     */
/*
    public void addStack(Item item, int amount)
    {
    	ItemStack stack = new ItemStack(item, amount);
    	EntityPlayer player = this.getPlayer();
		boolean flag = player.inventory.addItemStackToInventory(stack);
		
        if (flag)
        {
        	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
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
    
    /**
     * Removes the specified item from the player's inventory
     */
/*
    public void removeStack(Item item, int amount)
    {
    	EntityPlayer player = this.getPlayer();
    	InventoryPlayer inventory = player.inventory;

    	for (ItemStack stack : player.inventoryContainer.getInventory())
    	{
    		if (stack.getItem() == item)
    		{
    			stack.setCount(stack.getCount() - amount);;
    		}
    	}
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
    	return ((EntityPlayerMP) this.getPlayer()).interactionManager.getBlockReachDistance();
    }
    
    public void setBlockReach(double reach)
    {
    	((EntityPlayerMP) this.getPlayer()).interactionManager.setBlockReachDistance(reach);
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
		tag.setBoolean("Extra Reach", this.hasExtraReach);
		tag.setDouble("Reach", this.attackReach);
		
		DinocraftPlayerTicks ticks = DinocraftPlayer.getPlayer(this.getPlayer()).getTicksModule();
		tag.setDouble("Regeneration Loop Ticks", ticks.regenerationLoopTicks);
		tag.setInteger("Regeneration Ticks", ticks.regenerationTicks);
		tag.setInteger("Regeneration Count", ticks.regenerationCount);
		tag.setFloat("Regeneration Hearts", ticks.heartsRegenerate);
		tag.setInteger("Ticks Invulnerable", ticks.ticksInvulnerable);
		tag.setDouble("Degeneration Loop Ticks", ticks.degenerationLoopTicks);
		tag.setInteger("Degeneration Ticks", ticks.degenerationTicks);
		tag.setInteger("Degeneration Count", ticks.degenerationCount);
		tag.setFloat("Degeneration Hearts", ticks.heartsDegenerate);
		
		DinocraftPlayerActions actions = DinocraftPlayer.getPlayer(this.getPlayer()).getActionsModule();
		tag.setBoolean("doubleJump", actions.doubleJump);
		tag.setBoolean("hasDoubleJumped", actions.hasDoubleJumped);
		tag.setBoolean("jumpCooldown", actions.longJump);
		tag.setBoolean("extraMaxHealth", actions.extraMaxHealth);
		tag.setBoolean("extraMaxHealth2", actions.extraMaxHealth2);
		tag.setInteger("chlorophyteTick", actions.chlorophyteTick);
		tag.setFloat("chlorophyteAbsorptionAmount", actions.chlorophyteAbsorptionAmount);
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
		this.hasExtraReach = tag.getBoolean("Extra Reach");
		this.attackReach = tag.getDouble("Reach");
		
		DinocraftPlayerTicks ticks = DinocraftPlayer.getPlayer(this.getPlayer()).getTicksModule();
		ticks.regenerationTicks = tag.getInteger("Regeneration Ticks");
		ticks.regenerationLoopTicks = tag.getInteger("Regeneration Loop Ticks");
		ticks.regenerationCount = tag.getInteger("Regeneration Count");
		ticks.heartsRegenerate = tag.getFloat("Regeneration Hearts");
		ticks.ticksInvulnerable = tag.getInteger("Ticks Invulnerable");
		ticks.degenerationTicks = tag.getInteger("Degeneration Ticks");
		ticks.degenerationLoopTicks = tag.getDouble("Degeneration Loop Ticks");
		ticks.degenerationCount = tag.getInteger("Degeneration Count");
		ticks.heartsDegenerate = tag.getFloat("Degeneration Hearts");
		
		DinocraftPlayerActions actions = DinocraftPlayer.getPlayer(this.getPlayer()).getActionsModule();
		actions.doubleJump = tag.getBoolean("doubleJump");
		actions.hasDoubleJumped = tag.getBoolean("hasDoubleJumped");
		actions.longJump = tag.getBoolean("jumpCooldown");
		actions.extraMaxHealth = tag.getBoolean("extraMaxHealth");
		actions.extraMaxHealth2 = tag.getBoolean("extraMaxHealth2");
		actions.chlorophyteTick = tag.getInteger("chlorophyteTick");
		actions.chlorophyteAbsorptionAmount = tag.getFloat("chlorophyteAbsorptionAmount");
	}
}
*/