package dinocraft.item.splicents;

import dinocraft.util.server.DinocraftServer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.IRarity;

public class ItemSplicentsBlade extends ItemSword
{
	public ItemSplicentsBlade(ToolMaterial material)
	{
		super(material);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (!target.world.isRemote)
		{
			EntityLightningBolt lightning = new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true);
			target.world.addWeatherEffect(lightning);
			target.onStruckByLightning(lightning);
			float f0 = target.height / 2.0F;
			DinocraftServer.spawnElectricParticles(target.world, 25, 10, 10, target.posX, target.posY + f0, target.posZ, target.width, f0, target.width);
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}