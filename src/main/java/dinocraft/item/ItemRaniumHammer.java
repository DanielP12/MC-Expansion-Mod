package dinocraft.item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import dinocraft.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRaniumHammer extends ItemSword
{
	public ItemRaniumHammer(ToolMaterial material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override	 
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
        final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
        	this.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 1.5 /* find a number that will make the attack speed relatively slow */);
        }
        
        return modifiers;
    }

    private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier)
    {
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());
        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();
        
        if (modifierOptional.isPresent())
        {
            final AttributeModifier modifier = modifierOptional.get();
            modifiers.remove(modifier);
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation()));
        }
    }
	
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() == this)
		{
			event.getToolTip().add(TextFormatting.GRAY + " +1 Block Reach");
		}
	}
	
	@SubscribeEvent
	public void onLeftClickBlock(LeftClickBlock event)
	{		
		if (event.getItemStack() != null && event.getItemStack().getItem() != null)
		{
			Item item = event.getItemStack().getItem();
			EntityPlayer player = event.getEntityPlayer();

			if (item == this && !player.getCooldownTracker().hasCooldown(this))
			{
				World world = player.world;
				Random rand = new Random();
				List<Entity> entities = Lists.newArrayList(world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(5, 5, 5)));
				player.addVelocity(0.0D, 0.55D, 0.0D);

    			if (!world.isRemote) 
    			{
    				world.playSound((EntityPlayer) null, player.getPosition(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F, 0.5F);
    				player.getCooldownTracker().setCooldown(this, 100);
    			}
    			
    			for (Entity entity : entities) 
    			{
    				if (!world.isRemote) entity.attackEntityFrom(DamageSource.GENERIC, rand.nextInt(8) + 1);
    					
    				for (int i = 0; i < 100; ++i)
					{
						world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
								  entity.posX + (rand.nextDouble() - 0.5D) * (double)entity.width, 
								  entity.posY + rand.nextDouble() - (double)entity.getYOffset() + 0.25D, 
								  entity.posZ + (rand.nextDouble() - 0.5D) * (double)entity.width, 
								  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
								  Block.getIdFromBlock(Blocks.REDSTONE_BLOCK)
							   );  
						world.spawnParticle(EnumParticleTypes.CRIT,
								  entity.posX + (rand.nextDouble() - 0.5D) * (double)entity.width, 
								  entity.posY + rand.nextDouble() - (double)entity.getYOffset() + 0.25D, 
								  entity.posZ + (rand.nextDouble() - 0.5D) * (double)entity.width, 
								  Math.random() * 0.2D - 0.1D, Math.random() * 0.25D, Math.random() * 0.2D - 0.1D, 
								  0
							   ); 
					}
					
					Vec3d vector = player.getLookVec().normalize();
					entity.addVelocity(0.0D, 1.5D, 0.0D);
    				entity.motionX -= vector.x;
        			entity.motionZ -= vector.z;
				}
			}
		}
	}
}