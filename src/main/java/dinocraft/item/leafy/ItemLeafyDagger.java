package dinocraft.item.leafy;

import dinocraft.item.DinocraftWeapon;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.IRarity;

public class ItemLeafyDagger extends DinocraftWeapon
{
	public ItemLeafyDagger(ToolMaterial material)
	{
		super(material, 2.5F, 2.5F);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		target.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 2));
		target.hurtResistantTime = 0;
		float f0 = target.height / 2.0F;
		DinocraftServer.spawnParticles(EnumParticleTypes.CLOUD, target.world, 10, target.posX, target.posY + f0, target.posZ, target.width, f0, target.width,
				target.world.rand.nextGaussian() * 0.033D, target.world.rand.nextGaussian() * 0.033D, target.world.rand.nextGaussian() * 0.033D);
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}