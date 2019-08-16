package dinocraft.event;

import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import dinocraft.Reference;
import dinocraft.api.MaxHealth;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerProvider;
import dinocraft.init.DinocraftArmour;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
import dinocraft.util.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@EventBusSubscriber
public class DinocraftFunctionEvents
{
	/** The server's existed ticks */
	private static int serverTick = 0;
	
	private static int getServerTick()
	{
		return serverTick;
	}
	
	private static void setServerTick(int tick)
	{
		serverTick = tick;
	}
	
	/** Saves the world every 10 minutes */
	/* Event fired when the server is ticking */
	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event)
	{
		if (event.phase != Phase.END) return;
		
		setServerTick(getServerTick() + 1);
		
		if (getServerTick() == 12000 /* 10 minutes */)
		{
			setServerTick(0);

			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
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
	}
	
	/* Event fired when an entity dies */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if (event.getEntityLiving() instanceof EntityLiving)
		{
			EntityLiving entity = (EntityLiving) event.getEntityLiving();
			entity.playSound(SoundEvents.BLOCK_METAL_BREAK, 1.0F, 1.0F);

			World worldIn = entity.world;
			
			for (int i = 0; i < 50; ++i)
			{
				worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, 
					  entity.posX, entity.posY + (entity.height - 0.25D), entity.posZ, 
					  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
					  Block.getIdFromBlock(Blocks.REDSTONE_BLOCK)
				   );
			}
		}
	}
	
	/* Event fired when a player respawns*/
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
		if (event.getEntityPlayer() != null)
		{
			EntityPlayer playerIn = event.getEntityPlayer();
			IAttributeInstance oldMaxHealth = event.getOriginal().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        	AttributeModifier modifier = oldMaxHealth.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
        	
        	if (modifier != null)
        	{
        		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        		multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        		playerIn.getAttributeMap().applyAttributeModifiers(multimap);
        	}
		}
	} 
	
	/* Event fired when an entity is hurt by any damage source - SERVER */
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
	public static void onLivingHurt(LivingHurtEvent event)
	{	
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer playerIn = (EntityPlayer) event.getEntity();
			World worldIn = playerIn.world;
			Random rand = worldIn.rand;
		
			for (int i = 0; i < 100; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.BLOCK_CRACK, worldIn, 
						playerIn.posX + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 
						playerIn.posY + rand.nextDouble() - (double)playerIn.getYOffset() + 0.25D, 
						playerIn.posZ + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 
						Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
						Block.getIdFromBlock(Blocks.REDSTONE_BLOCK)
					);
			}
			
			worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 2.0F, 1.25F);
		}
	}

	/*
	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event)
	{		
		if (event.getItemStack() != null && event.getItemStack().getItem() != null)
		{
			Item item = event.getItemStack().getItem();

			if (item == Items.STRING)
			{
				event.setCanceled(true);
			}
		}
	}*/
	
	
	/** Chicken Fireworks */
	/*
	@SubscribeEvent
	public static void onBlockBreak(BreakEvent event)
	{		
		if (event.getState().getBlock() == Blocks.STONE)
		{
			World worldIn = event.getWorld();

			Random rand = new Random();

			for (int i = 0; i < 10; ++i)
			{
				EntityChicken chicken = new EntityChicken(worldIn);
				BlockPos pos = event.getPos();
				chicken.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
				chicken.setVelocity(rand.nextDouble() - 0.5, rand.nextDouble() / 2.0, rand.nextDouble() - 0.5);
				worldIn.spawnEntity(chicken);
			}
		}
	}
	
	/** Remote TNT */
	/*
	@SubscribeEvent
	public static void onLeftClickBlock(LeftClickBlock event)
	{		
		if (event.getItemStack() != null && event.getItemStack().getItem() != null && !event.getWorld().isRemote)
		{	
			if (event.getItemStack().getItem() == Item.getItemFromBlock(Blocks.LEVER))
			{
				EntityPlayer playerIn = event.getEntityPlayer();
				DinocraftPlayer player = DinocraftPlayer.getEntityPlayer(playerIn);
				RayTraceResult trace = player.getTrace(100.0D);
				
				World worldIn = event.getWorld();
				
				if (trace != null && trace.getBlockPos() != null)
				{
					BlockPos pos = trace.getBlockPos();
					Block block = worldIn.getBlockState(pos).getBlock();
					
					if (block == Blocks.TNT)
					{
						worldIn.setBlockToAir(pos);
						worldIn.spawnEntity(new EntityTNTPrimed(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn));
						playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Fire in the hole!"));
					}
				}
			}
		}
	}
	
	/** Remote TNT */
	/*
	@SubscribeEvent
	public static void onLeftClickAir(LeftClickEmpty event)
	{
		if (event.getItemStack() != null && event.getItemStack().getItem() != null)
		{	
			if (event.getItemStack().getItem() == Item.getItemFromBlock(Blocks.LEVER))
			{
				NetworkHandler.sendToServer(new MessageLCTNT());
			}
		}
	}
	*/
	
	/*
	@SubscribeEvent
	public static void onAttack(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			Entity attackerIn = event.getSource().getImmediateSource();
			EntityPlayer targetIn = (EntityPlayer) event.getEntity();
			
			World worldIn = targetIn.world;
			worldIn.playSound((EntityPlayer) null, targetIn.getPosition(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 2.0F, 1.25F);
			
			Random rand = worldIn.rand;
    		
			for (int i = 0; i < 75; ++i)
			{
				DinocraftServer.spawnParticle(EnumParticleTypes.BLOCK_CRACK, worldIn, 
					  targetIn.posX + (rand.nextDouble() - 0.5D) * (double)targetIn.width, 
					  targetIn.posY + rand.nextDouble() - (double)targetIn.getYOffset() + 0.25D, 
					  targetIn.posZ + (rand.nextDouble() - 0.5D) * (double)targetIn.width, 
					  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
					  Block.getIdFromBlock(Blocks.REDSTONE_BLOCK)
				   );
			}
		}
	}
	*/
	
	@SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) 
	{
        Entity entity = event.getEntity();
        World worldIn = entity.world;
        Random rand = worldIn.rand;
        BlockPos pos = entity.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        
        if (entity instanceof EntityPig || entity instanceof EntityPigZombie)
        {
            if (rand.nextInt(10) < 3)
            {
               event.getDrops().add(new EntityItem(worldIn, x, y, z, new ItemStack(DinocraftItems.TUSK, rand.nextInt(2) + 1)));
            }
        }
        else if (entity instanceof EntityCreeper) 
        {
            if (rand.nextInt(1000) < 2)
            {
                event.getDrops().add(new EntityItem(worldIn, x, y, z, new ItemStack(DinocraftArmour.CLOUD_CHESTPLATE)));
            }
        }
        else if (entity instanceof EntitySkeleton) 
        {
            if (rand.nextInt(2300) < 2)
            {
                event.getDrops().add(new EntityItem(worldIn, x, y, z, new ItemStack(DinocraftTools.KATANA)));
            }
        }
        else if (entity instanceof EntityZombie && rand.nextInt(2000) < 4)
        {
            event.getDrops().add(new EntityItem(worldIn, x, y, z, new ItemStack(DinocraftTools.SOUL_SCRATCHER)));
        }
    }
	
	@SubscribeEvent
	public void renderNames(PlayerEvent.NameFormat event) 
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		String name = event.getEntityPlayer().getName();
		String baum = "[Admin]Baum";
		String abcd = "Player221";
		
		if (!event.getEntityPlayer().canUseCommand(4, "/asServer"))
		{
			event.setDisplayname(TextFormatting.BLUE + "[HELPER] " + event.getUsername());
			
			if (!event.getDisplayname().equals(TextFormatting.BLUE + "[HELPER] " + event.getUsername()))
			{
				event.getEntityPlayer().refreshDisplayName();
			}
		}
		else
		{
			event.setDisplayname(TextFormatting.RED + "[ADMIN] " + event.getUsername());
			
			if (!event.getDisplayname().equals(TextFormatting.RED + "[ADMIN] " + event.getUsername()))
			{
				event.getEntityPlayer().refreshDisplayName();
			}
		}
		/*if (name == baum || name == abcd)
		{
			event.setDisplayname(TextFormatting.BLUE + "[HELPER] " + event.getUsername());
		}*/
	}
}