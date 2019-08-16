package dinocraft.api;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

public class MaxHealth 
{
    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("B04DB08B-ED8A-4B82-B1EF-ADB425174925");
    
    public static void addMaxHealth(EntityPlayer playerIn, float healthIn)
    {
        IAttributeInstance maxHealthInstance = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = maxHealthInstance.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
        double existingHearts = modifier != null ? modifier.getAmount() : 0.0D;

        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID, "Max Health Adder", existingHearts + healthIn, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        playerIn.getAttributeMap().applyAttributeModifiers(multimap);
    }
    
    public static void setMaxHealth(EntityPlayer playerIn, float healthIn)
    {
        IAttributeInstance maxHealthInstance = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = maxHealthInstance.getModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID);
        
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        modifier = new AttributeModifier(MaxHealth.MAX_HEALTH_MODIFIER_ID, "Max Health Setter", healthIn - 20D, 0);
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), modifier);
        playerIn.getAttributeMap().applyAttributeModifiers(multimap);
    }
}
