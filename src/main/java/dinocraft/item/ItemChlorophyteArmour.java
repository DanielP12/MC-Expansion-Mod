package dinocraft.item;

import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerActions;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftArmour;
import dinocraft.util.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class ItemChlorophyteArmour extends ItemArmor
{
	public ItemChlorophyteArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/* Event fired when an entity hits the ground */
	/*
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onChlorophyteArmor(LivingFallEvent event) 
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer playerIn = (EntityPlayer) event.getEntity();
			World worldIn = playerIn.world;
			DinocraftPlayer player = DinocraftPlayer.getEntityPlayer(playerIn);
				
			if (player != null)
			{
				if (player.isWearingItems(
						DinocraftArmour.CHLOROPHYTE_HELMET, DinocraftArmour.CHLOROPHYTE_CHESTPLATE, 
						DinocraftArmour.CHLOROPHYTE_LEGGINGS, DinocraftArmour.CHLOROPHYTE_BOOTS))
				{
					if (!playerIn.capabilities.isFlying && !playerIn.isCreative() && !playerIn.isSpectator())
					{
						if (playerIn.fallDistance > 3.0F)
						{
							BlockPos pos = playerIn.getPosition();
							Block block = worldIn.getBlockState(pos.down()).getBlock();
								
							if (block == Blocks.LEAVES || block == Blocks.LEAVES2 || block == DinocraftBlocks.HAY)
							{
								if (!worldIn.isRemote)
								{
									playerIn.setPositionAndUpdate((double) pos.getX(), (double) pos.getY() - 1.75D, (double) pos.getZ());
									worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.PLAYERS, 1.0F, 0.25F);
										
									if (block == DinocraftBlocks.HAY)
									{
										worldIn.playSound(null, pos, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 2.0F, 1.0F);
									}
								}
									
								event.setCanceled(true);
							}
						}
					}
				}
			}
		}
	}
	*/
	
	@SubscribeEvent
	public void onChlorophyteArmor(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END) return;
		
	    EntityPlayer playerIn = event.player;
	    DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		DinocraftPlayerActions actions = player.getActions();
    	World worldIn = playerIn.world;

	    if (worldIn.isRemote && player != null && player.isWearingItems(
				DinocraftArmour.CHLOROPHYTE_HELMET, DinocraftArmour.CHLOROPHYTE_CHESTPLATE, 
				DinocraftArmour.CHLOROPHYTE_LEGGINGS, DinocraftArmour.CHLOROPHYTE_BOOTS))
		{	    	
			if (!playerIn.isCreative() && !playerIn.isSpectator())
			{
				float absorption = playerIn.getAbsorptionAmount();
				
				if (absorption == 0 /* No Absorption */|| absorption == 4 /* Golden Apple */|| absorption == 16 /* Enchanted Golden Apple */)
				{
					actions.setChlorophyteTick(actions.getChlorophyteTick() + 1);
					
					if (actions.getChlorophyteTick() == 6000)
					{
						actions.setChlorophyteTick(0);
						
			    	    Random rand = new Random();
			    	    worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.CRUNCH, SoundCategory.PLAYERS, 1.0F, rand.nextFloat());
						
			    		float j = rand.nextInt(3) + 1;
						actions.setChlorophyteAbsorptionAmount(j);
						playerIn.setAbsorptionAmount(playerIn.getAbsorptionAmount() + j);
						
						for (int i = 0; i < 100; ++i)
						{
							DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, worldIn,
									playerIn.posX + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 
									playerIn.posY + rand.nextDouble() - (double)playerIn.getYOffset() + 0.25D, 
									playerIn.posZ + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 
									Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
									Block.getIdFromBlock(Blocks.CACTUS)
								);
						}
					}
				}
			}
		}
	    else if (actions.getChlorophyteAbsorptionAmount() > 0.0F)
	    {
			playerIn.setAbsorptionAmount(playerIn.getAbsorptionAmount() - actions.getChlorophyteAbsorptionAmount());
			actions.setChlorophyteAbsorptionAmount(0.0F);
	    }
	}
}