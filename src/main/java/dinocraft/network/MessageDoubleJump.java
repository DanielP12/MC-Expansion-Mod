package dinocraft.network;

import java.util.Random;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;

public class MessageDoubleJump extends AbstractPacket<MessageDoubleJump> 
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
	public void handleClientSide(MessageDoubleJump message, EntityPlayer player) 
	{

	}

	@Override
	public void handleServerSide(MessageDoubleJump message, EntityPlayer player) 
	{
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);	
		dinoEntity.setFallDamageReductionAmount(5.0F);
		
		player.fallDistance = 0.0F;

		Random rand = player.world.rand;
		float j = rand.nextFloat();
		player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.PLAYERS, 1.0F, j - (j / 1.5F));
		ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		if (stack.getItem() == DinocraftItems.LEAFY_BOOTS && rand.nextInt(2) < 1)
		{
			stack.attemptDamageItem(1, rand, null);
		}
				
		for (int i = 0; i < 25; ++i)
		{
			dinoEntity.spawnParticle(EnumParticleTypes.CLOUD, true, player.posX + (rand.nextFloat() * player.width * 2.5F) - player.width, 
					player.posY + 0.5D + (rand.nextFloat() * player.height) - 1.25D, player.posZ + (rand.nextFloat() * player.width * 2.5F) - player.width, 
					rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, 1);
		}
	}
}