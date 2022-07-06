package dinocraft.item.leafy;

import dinocraft.item.DinocraftWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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
		if (!target.world.isRemote)
		{
			target.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 4));
			target.hurtResistantTime = 0;

			//			for (int i = 0; i < 10; i++)
			//			{
			//				DinocraftServer.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
			//						target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
			//						target.world.rand.nextGaussian() * 0.025D, target.world.rand.nextGaussian() * 0.025D, target.world.rand.nextGaussian() * 0.025D, 1);
			//				DinocraftServer.spawnParticle(EnumParticleTypes.TOTEM, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
			//						target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
			//						target.world.rand.nextGaussian() * 0.25D, target.world.rand.nextGaussian() * 0.25D, target.world.rand.nextGaussian() * 0.025D, 1);
			//			}
		}
		
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}