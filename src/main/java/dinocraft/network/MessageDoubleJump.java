package dinocraft.network;

import java.util.Random;

import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.init.DinocraftArmour;
import dinocraft.util.DinocraftServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

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
	public void handleClientSide(MessageDoubleJump message, EntityPlayer playerIn) 
	{

	}

	@Override
	public void handleServerSide(MessageDoubleJump message, EntityPlayer playerIn) 
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		if (player != null && player.hasFallDamage()) player.setFallDamageReductionAmount(10);;

		World worldIn = playerIn.world;
		worldIn.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.PLAYERS, 1.0F, 0.25F);
		
		if (playerIn.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
		{
			ItemStack stack = playerIn.getItemStackFromSlot(EntityEquipmentSlot.FEET);

			if (stack.getItem() == DinocraftArmour.LEAFY_BOOTS)
			{
				if (stack.getItemDamage() != stack.getMaxDamage() - 1)
				{
					stack.damageItem(1, playerIn);
				}
				else
				{
					playerIn.inventory.deleteStack(stack);
				}
			}
		}
		
		Random rand = worldIn.rand;
		
		for (int i = 0; i < 24; ++i)
		{
			DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD, worldIn, 
    		      playerIn.posX + (double)(rand.nextFloat() * playerIn.width * 2.5F) - (double)playerIn.width, 
    		      playerIn.posY + 0.5D + (double)(rand.nextFloat() * playerIn.height) - 1.25D, 
    		      playerIn.posZ + (double)(rand.nextFloat() * playerIn.width * 2.5F) - (double)playerIn.width, 
    		      rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, rand.nextGaussian() * 0.025D, 1
			   );
		}
		
		PotionEffect effect = playerIn.getActivePotionEffect(MobEffects.JUMP_BOOST);

		/*
		if (!playerIn.hasAchievement(effect != null ? DinocraftAchievements.DOUBLE_JUMPIN_JUMP : DinocraftAchievements.ONE_JUMP_FURTHER))
		{
			playerIn.addStat(effect != null ? DinocraftAchievements.DOUBLE_JUMPIN_JUMP : DinocraftAchievements.ONE_JUMP_FURTHER);
		}
		*/
	}
}