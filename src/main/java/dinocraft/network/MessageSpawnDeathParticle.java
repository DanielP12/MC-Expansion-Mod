package dinocraft.network;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageSpawnDeathParticle extends AbstractPacket<MessageSpawnDeathParticle>
{

	@Override
	public void fromBytes(ByteBuf buf)
	{	
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
	}

	@Override
	public void handleClientSide(MessageSpawnDeathParticle message, EntityPlayer player)
	{
	}

	@Override
	public void handleServerSide(MessageSpawnDeathParticle message, EntityPlayer player)
	{
		Random random = new Random();
		for (int cp = 0; cp <= 25; ++cp)
		{
           // DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, player.worldObj, player.posX + (random.nextDouble() - 0.5D) * (double)player.width, player.posY + random.nextDouble() - (double)player.getYOffset() + 0.25D, player.posZ + (random.nextDouble() - 0.5D) * (double)player.width, 0, 0, 0, 173);
           // DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, player.worldObj, player.posX + (random.nextDouble() - 1D) * (double)player.width, player.posY, player.posZ + (random.nextDouble() - 1D) * (double)player.width, 0, 0, 0, 173);
           // DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, player.worldObj, player.posX + (random.nextDouble() - 0.5D) * (double)player.width, player.posY + random.nextDouble() - (double)player.getYOffset() - 0.25D, player.posZ + (random.nextDouble() - 0.5D) * (double)player.width, 0, 0, 0, 173);
          //  DinocraftServer.spawnParticle(EnumParticleTypes.FALLING_DUST, player.worldObj, player.posX + (random.nextDouble() - 1D) * (double)player.width, player.posY + random.nextDouble() - (double)player.getYOffset() - 0.5D, player.posZ + (random.nextDouble() - 1D) * (double)player.width, 0, 0, 0, 173);
        }
	}
}
