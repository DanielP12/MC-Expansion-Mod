package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophyteSword extends ItemSword
{
	public ItemChlorophyteSword(ToolMaterial material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
		{
			target.addPotionEffect(new PotionEffect(MobEffects.POISON, 70, 1, false, true));
		}
		
		return super.hitEntity(stack, target, attacker);
	}
}