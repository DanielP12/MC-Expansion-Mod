package dinocraft.item;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketCapabilities;
import dinocraft.network.PacketCapabilities.Capability;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBleventBoots extends ItemArmor
{
    public ItemBleventBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
    {
        super(material, renderIndex, equipmentSlot);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setMaxDamage(200);
        
		MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onLivingFall(LivingFallEvent event)
    {
    	EntityLivingBase entityliving = event.getEntityLiving();

    	if (entityliving instanceof EntityPlayer && entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this && entityliving.isSneaking() && entityliving.fallDistance >= 4.0F)
    	{
    		EntityPlayer player = (EntityPlayer) entityliving;
    		List<Entity> entities = Lists.newArrayList(player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D)));
    		Random rand = player.world.rand;
    		entityliving.getItemStackFromSlot(EntityEquipmentSlot.FEET).attemptDamageItem(1, rand, null);
    		
			if (!player.world.isRemote) 
			{
				player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F, 0.25F);
			}
			
			for (Entity entity : entities) 
			{
				if (!player.world.isRemote) entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 20.0F);
					
				for (int i = 0; i < 100; ++i)
				{
					player.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
							  entity.posX + (rand.nextDouble() - 0.5D) * entity.width, 
							  entity.posY + rand.nextDouble() - entity.getYOffset() + 0.25D, 
							  entity.posZ + (rand.nextDouble() - 0.5D) * entity.width, 
							  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
							  Block.getIdFromBlock(Blocks.REDSTONE_BLOCK)
						   );  
					player.world.spawnParticle(EnumParticleTypes.CRIT,
							  entity.posX + (rand.nextDouble() - 0.5D) * entity.width, 
							  entity.posY + rand.nextDouble() - entity.getYOffset() + 0.25D, 
							  entity.posZ + (rand.nextDouble() - 0.5D) * entity.width, 
							  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
							  0
						   ); 
				}
			}
    	}
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
    	if (player.isSneaking() && world.isRemote && player.motionY < 0.0D && !player.isCreative() && !player.capabilities.isFlying && !player.isInWater() && !player.isOnLadder() && !player.isInLava() && player.isAirBorne)
    	{
    		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
    		
    		if (!dinoEntity.hasReducedFallDamage())
    		{
    			dinoEntity.setFallDamageReductionAmount(4.0F);
    			NetworkHandler.sendToServer(new PacketCapabilities(Capability.REDUCTION));
    		}
    		
    		player.motionY = player.motionY - 0.25D;
    	}
    	
    	super.onArmorTick(world, player, stack);
    }
    
    /*
    private int int1, int2, int3;
 
	@Override
    public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stack)
    {
    	if (worldIn.isRemote && !playerIn.isCreative() && !playerIn.isSpectator() && !playerIn.isElytraFlying())
    	{
    		if (playerIn.onGround || playerIn.isCollided || playerIn.isInLava() || playerIn.isInWater())
            {
                this.int1 = 1;
                this.int2 = 0;
                this.int3 = 0;
                
                DinocraftServer.removeInvisibility();
            }
            
            if (playerIn.isAirBorne)
            {
            	boolean forward = Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
            	
            	if (!playerIn.isSneaking() && this.int2 == 0)
            	{
                    this.int1 = 3;
                    this.int2 = 1;
                }
            	
                if (playerIn.isSneaking() && this.int1 == 3 && this.int2 == 1)
                {
                    this.int1 = 1;
                    this.int2 = 1;
                    this.int3 = 3;
                }
                
                if (!forward && this.int2 < this.int3)
                {
                	this.int3 = 0;
                	this.int1 = 1;
                	this.int2 = 2;
                }
                
                if (forward && this.int2 == 2 && this.int1 == 1)
                {
                    Vec3d vector = playerIn.getLookVec().normalize();
                    
                    this.int2 = 3;
                	this.int1 = 0;
                	
    	        	playerIn.motionX += vector.xCoord;
    	        	playerIn.motionY = vector.yCoord + 0.75;
    	        	playerIn.motionZ += vector.zCoord;
                    playerIn.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 1F, 1F);
                    playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.001F);   
                    DinocraftServer.addPotionEffect(14, 10000000, 0);
                }
            }
            
            if (this.int1 == this.int3) 
            {
            	Random random = new Random();
            	
                int var1 = random.nextInt(3);

            	if (playerIn.motionY < 0.01)
            	{
            		playerIn.addVelocity(0, 0.075, 0);
            	}
            	
            	if (playerIn.isSneaking())
            	{
            		playerIn.motionY = -1D;
            	}
                
                if (playerIn.ticksExisted % 2 == 0)
                {
            		if (var1 < 1)
            		{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 1F);
            		}
            		else if (var1 < 2)
            		{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 0.75F);
            		}
            		else if (var1 < 3)
                	{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 0.5F);
                	}
            		
            		playerIn.motionX *= 1.0375D;
            		playerIn.motionZ *= 1.0375D;
            		
                    DinocraftServer.spawnGoldParticles();
                }
                
            	DinocraftServer.cancelPlayerFallDamage();
            	DinocraftServer.addPotionEffect(18, 900, 0);
            }
    	}
    } */
	
    /*
    private boolean doubleJumped;
    
    private boolean cooldown;
    
    @Override
    public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stack)
    {
    	if (worldIn.isRemote && !playerIn.isCreative() && !playerIn.isSpectator() && !playerIn.isElytraFlying())
    	{
    		if (playerIn.onGround || playerIn.collided || playerIn.isInLava() || playerIn.isInWater())
            {
    			this.doubleJumped = true;
    			this.cooldown = false;
    			//remove invis
            }
            
            if (playerIn.isAirBorne)
            {
            	boolean isTDown = org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_T);
            	
            	if (!isTDown && !this.cooldown && playerIn.isSprinting())
            	{
            		this.doubleJumped = false;
            		this.cooldown = true;
                }
            	
                if (isTDown && !this.doubleJumped)
                {
                	this.doubleJumped = true;
                    Vec3d vector = playerIn.getLookVec().normalize();
                	
    	        	playerIn.motionX += vector.x;
    	        	playerIn.motionY = vector.y + 0.75;
    	        	playerIn.motionZ += vector.z;
                    playerIn.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 1F, 1F);
                    playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.001F);
                    
                    DinocraftServer.addPotionEffect(14, 10000000, 0);
                }
            }
            
            if (this.doubleJumped == this.cooldown) 
            {
            	Random random = new Random();
            	
                int var1 = random.nextInt(3);

            	if (playerIn.motionY < 0.01)
            	{
            		playerIn.motionY += 0.075;
            	}
            	
            	if (playerIn.isSneaking())
            	{
            		playerIn.motionY += -0.025D;
            	}
                
                if (playerIn.ticksExisted % 2 == 0)
                {
            		if (var1 < 1)
            		{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 1F);
            		}
            		else if (var1 < 2)
            		{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 0.75F);
            		}
            		else if (var1 < 3)
                	{
            			playerIn.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1F, 0.5F);
                	}
            		
            		playerIn.motionX *= 1.0375D;
            		playerIn.motionZ *= 1.0375D;
            		
                    DinocraftServer.spawnGoldParticles();
                }
                
            	DinocraftServer.cancelPlayerFallDamage();
            	DinocraftServer.addPotionEffect(18, 900, 0);
            }
    	}
    }
   
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.DARK_GRAY + Utils.getLang().localize("blevent_boots.tooltip"));
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    */
}