package dinocraft.item.jesters;

import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketJesterKnockback;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.IRarity;

public class ItemJestersSword extends ItemSword
{
	public ItemJestersSword(ToolMaterial material)
	{
		super(material);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		float f0 = target.height / 2.0F;
		DinocraftServer.spawnJesterParticles(target.world, 25, 10, target.posX, target.posY + f0, target.posZ, target.width, f0, target.width);
		int j = target.world.rand.nextInt(3);

		if (j == 0)
		{
			target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40, 2, true, false));
		}
		else if (j == 1)
		{
			double x = target.world.rand.nextDouble() - 0.5D;
			double y = target.world.rand.nextDouble() * 0.2D + 0.7D;
			double z = target.world.rand.nextDouble() - 0.5D;
			
			if (target instanceof EntityPlayer)
			{
				((EntityPlayerMP) target).connection.sendPacket(new SPacketEntityVelocity(target.getEntityId(), x, y, z));
			}
			
			target.addVelocity(x, y, z);
		}
		else
		{
			if (target instanceof EntityPlayer)
			{
				PacketHandler.sendTo(new SPacketJesterKnockback(attacker), (EntityPlayerMP) target);
			}

			double d = target.posX - attacker.posX;
			double d1;

			for (d1 = target.posZ - attacker.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
			{
				d = (Math.random() - Math.random()) * 0.01D;
			}

			float f = MathHelper.sqrt(d * d + d1 * d1);
			target.motionX /= 2.0D;
			target.motionY /= 2.0D;
			target.motionZ /= 2.0D;
			target.motionX += d / f * 1.5D;
			target.motionY += 0.3D;
			target.motionZ += d1 / f * 1.5D;
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}