package dinocraft.item;

import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityActions;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ItemChlorophyteArmor extends ItemArmor
{
	public ItemChlorophyteArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, String name)
	{
		super(material, renderIndex, equipmentSlot);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		
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
	
	
	
	
	/*
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END) return;
		
	    EntityPlayer player = event.player;
	    DinocraftPlayer dinoPlayer = DinocraftPlayer.getPlayer(player);
		DinocraftPlayerActions actions = dinoPlayer.getActionsModule();

	    if (dinoPlayer.isWearingItems(DinocraftArmor.CHLOROPHYTE_HELMET, DinocraftArmor.CHLOROPHYTE_CHESTPLATE, DinocraftArmor.CHLOROPHYTE_LEGGINGS, DinocraftArmor.CHLOROPHYTE_BOOTS))
		{	    	
			if (!player.isCreative() && !player.isSpectator())
			{
				float absorption = player.getAbsorptionAmount();
				
				if (absorption == 0 /* No Absorption *///|| absorption == 4 /* Golden Apple */|| /*absorption == 16 /* Enchanted Golden Apple */)
				/*{
					actions.setChlorophyteTick(actions.getChlorophyteTick() + 1);
					
					if (actions.getChlorophyteTick() == 8000)
					{
						actions.setChlorophyteTick(0);
						
			    	    Random rand = player.world.rand;
			    	    player.world.playSound((EntityPlayer) null, player.getPosition(), DinocraftSoundEvents.CRUNCH, SoundCategory.PLAYERS, 1.0F, rand.nextFloat());
						
			    		float j = rand.nextInt(3) + 1;
						actions.setChlorophyteAbsorptionAmount(j);
						player.setAbsorptionAmount(player.getAbsorptionAmount() + j);
						
						for (int i = 0; i < 50; ++i)
						{
							dinoPlayer.spawnParticle(EnumParticleTypes.FALLING_DUST, player.posX + (rand.nextDouble() - 0.5D) * player.width,
									player.posY + rand.nextDouble() - player.getYOffset() + 0.25D, player.posZ + (rand.nextDouble() - 0.5D) * player.width,
									Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, Block.getIdFromBlock(Blocks.CACTUS));
						}
					}
				}
			}
		}
	    else if (actions.getChlorophyteAbsorptionAmount() > 0.0F)
	    {
			player.setAbsorptionAmount(player.getAbsorptionAmount() - actions.getChlorophyteAbsorptionAmount());
			actions.setChlorophyteAbsorptionAmount(0.0F);
	    }
	}
	*/
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{	
		EntityLivingBase entity = event.getEntityLiving();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(entity);
		
		if (dinoEntity != null)
		{
			DinocraftEntityActions actions = dinoEntity.getActionsModule();

			if (dinoEntity.isWearing(DinocraftItems.CHLOROPHYTE_HELMET, DinocraftItems.CHLOROPHYTE_CHESTPLATE, DinocraftItems.CHLOROPHYTE_LEGGINGS, DinocraftItems.CHLOROPHYTE_BOOTS))
			{	    	
				float absorption = entity.getAbsorptionAmount();
				
				if (absorption == 0 /* No Absorption */|| absorption == 4 /* Golden Apple */|| absorption == 16 /* Enchanted Golden Apple */)
				{
					actions.setChlorophyteTick(actions.getChlorophyteTick() + 1);
					
					if (actions.getChlorophyteTick() == 8000 /* 6.66 minutes */)
					{
						actions.setChlorophyteTick(0);
						
			    	    Random rand = entity.world.rand;
			    	    entity.world.playSound((EntityPlayer) null, entity.getPosition(), DinocraftSoundEvents.CRUNCH, SoundCategory.PLAYERS, 1.0F, rand.nextFloat());
						
			    		float amount = rand.nextInt(3) + 1.0F;
						actions.setChlorophyteAbsorptionAmount(amount);
						entity.setAbsorptionAmount(entity.getAbsorptionAmount() + amount);
						
						for (int i = 0; i < 50; ++i)
						{
							dinoEntity.spawnParticle(EnumParticleTypes.FALLING_DUST, true, entity.posX + (rand.nextDouble() - 0.5D) * entity.width,
									entity.posY + rand.nextDouble() - entity.getYOffset() + 0.25D, entity.posZ + (rand.nextDouble() - 0.5D) * entity.width,
									Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, Block.getIdFromBlock(Blocks.CACTUS));
						}
					}
				}
			}
			else if (actions.getChlorophyteAbsorptionAmount() > 0.0F)
	    	{
				entity.setAbsorptionAmount(entity.getAbsorptionAmount() - actions.getChlorophyteAbsorptionAmount());
				actions.setChlorophyteAbsorptionAmount(0.0F);
	    	}
		}
	}

}