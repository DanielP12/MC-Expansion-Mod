package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;

public class CPacketDoubleJump extends AbstractPacket<CPacketDoubleJump>
{
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{

	}

	@Override
	public void handleClientSide(CPacketDoubleJump message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(CPacketDoubleJump message, EntityPlayer player)
	{
		DinocraftEntity.getEntity(player).setFallDamageReductionAmount(5.0F);
		player.fallDistance = 0.0F;
		player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat() + 0.25F);
		ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		if (stack.getItem() == DinocraftItems.LEAFY_BOOTS && player.world.rand.nextInt(4) == 0)
		{
			stack.damageItem(1, player);
		}
		
		for (int i = 0; i < 25; i++)
		{
			((WorldServer) player.world).spawnParticle(EnumParticleTypes.CLOUD, true, player.posX + player.world.rand.nextFloat() * player.width * 2.5F - 1.25F * player.width,
					player.posY - 0.1F * player.height + player.world.rand.nextFloat() * player.height * 0.25F, player.posZ + player.world.rand.nextFloat() * player.width * 2.5F - 1.25F * player.width,
					1, player.world.rand.nextGaussian() * 0.033D, player.world.rand.nextGaussian() * 0.033D, player.world.rand.nextGaussian() * 0.033D, player.world.rand.nextGaussian() * 0.033D, 1);
		}

		for (int i = 0; i < 20; i++)
		{
			((WorldServer) player.world).spawnParticle(EnumParticleTypes.TOTEM, true, player.posX + player.world.rand.nextFloat() * player.width * 2.5F - 1.25F * player.width,
					player.posY - 0.1F * player.height + player.world.rand.nextFloat() * player.height * 0.25F, player.posZ + player.world.rand.nextFloat() * player.width * 2.5F - 1.25F * player.width,
					1, player.world.rand.nextGaussian() * 0.25D, player.world.rand.nextGaussian() * 0.25D, player.world.rand.nextGaussian() * 0.25D, player.world.rand.nextGaussian() * 0.025D, 1);
		}
	}
}