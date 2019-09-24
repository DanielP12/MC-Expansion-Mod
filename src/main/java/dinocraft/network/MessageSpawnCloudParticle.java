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
	public void handleClientSide(MessageSpawnCloudParticle message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(MessageSpawnCloudParticle message, EntityPlayer player)
	{
		Random rand = new Random();
		DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD, true, player.world,
				player.posX + (rand.nextDouble() - 0.5D) * player.width,
				player.posY + rand.nextDouble() - player.getYOffset() - 1.0D,
				player.posZ + (rand.nextDouble() - 0.5D) * player.width,
				0.0D, 0.0D, 0.0D, 1
			);
        DinocraftServer.spawnParticle(EnumParticleTypes.CLOUD, true, player.world,
        		player.posX + (rand.nextDouble() - 1.0D) * player.width,
        		player.posY + rand.nextDouble() - player.getYOffset() - 1.0D,
        		player.posZ + (rand.nextDouble() - 1.0D) * player.width,
        		0.0D, 0.0D, 0.0D, 1
        	);
	}
}