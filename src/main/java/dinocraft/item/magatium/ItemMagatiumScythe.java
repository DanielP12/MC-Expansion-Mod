package dinocraft.item.magatium;

import dinocraft.entity.EntityMagatiumSmallShard;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.IRarity;

public class ItemMagatiumScythe extends ItemSword
{
	public ItemMagatiumScythe(ToolMaterial material)
	{
		super(material);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (!target.world.isRemote)
		{
			int j = target.world.rand.nextInt(2) + 6;

			for (int i = 0; i < 6; i++)
			{
				EntityMagatiumSmallShard shard = new EntityMagatiumSmallShard(attacker, target, 0.1F);
				shard.setPosition(target.posX, target.posY + target.height / 2.0F, target.posZ);
				shard.motionX =  -0.25F + 0.5F * target.world.rand.nextDouble();
				shard.motionY = target.world.rand.nextDouble() * 0.25F + 0.5F;
				shard.motionZ =  -0.25F + 0.5F * target.world.rand.nextDouble();
				shard.velocityChanged = true;
				attacker.world.spawnEntity(shard);
			}

			target.world.playSound(null, target.getPosition(), DinocraftSoundEvents.ROCK_SMASH, SoundCategory.NEUTRAL, 0.5F, target.world.rand.nextFloat() + 0.5F);
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}
