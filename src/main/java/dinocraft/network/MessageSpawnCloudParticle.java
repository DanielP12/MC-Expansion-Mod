package dinocraft.network;

import java.util.Random;

import dinocraft.util.DinocraftServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class MessageSpawnCloudParticle extends AbstractPacket<MessageSpawnCloudParticle>
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
	public void handleClientSide(MessageSpawnCloudParticle message, EntityPlayer playerIn)
	{
		
	}

	@Override
	public void handleServerSide(MessageSpawnCloudParticle message, EntityPlayer playerIn)
	{
		Random random = new Random();
		DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD,
				playerIn.world,
				playerIn.posX + (random.nextDouble() - 0.5D) * (double) playerIn.width,
				playerIn.posY + random.nextDouble() - (double) playerIn.getYOffset() - 1D,
				playerIn.posZ + (random.nextDouble() - 0.5D) * (double) playerIn.width,
				0, 0, 0, 1
			);
        DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD,
        		playerIn.world,
        		playerIn.posX + (random.nextDouble() - 1D) * (double) playerIn.width,
        		playerIn.posY + random.nextDouble() - (double) playerIn.getYOffset() - 1D,
        		playerIn.posZ + (random.nextDouble() - 1D) * (double) playerIn.width,
        		0, 0, 0, 1
        	);
	}
}

