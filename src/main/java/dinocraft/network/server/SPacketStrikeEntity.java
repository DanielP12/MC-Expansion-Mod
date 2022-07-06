package dinocraft.network.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.AbstractPacket;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketStrikeEffect;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class SPacketStrikeEntity extends AbstractPacket<SPacketStrikeEntity>
{
	public SPacketStrikeEntity()
	{
		
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		
	}
	
	@Override
	public void handleClientSide(SPacketStrikeEntity message, EntityPlayer player)
	{
		RayTraceResult result = DinocraftEntity.getEntity(player).getEntityTrace(10000.0D);
		
		if (result == null || result.typeOfHit == RayTraceResult.Type.MISS)
		{
			player.sendMessage(new TextComponentTranslation("commands.strike.failed").setStyle(new Style().setColor(TextFormatting.RED)));
			return;
		}

		switch (result.typeOfHit)
		{
			case ENTITY:
			{
				PacketHandler.sendToServer(new CPacketStrikeEffect(result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ));
				break;
			}
			case BLOCK:
			{
				PacketHandler.sendToServer(new CPacketStrikeEffect(result.getBlockPos()));
				break;
			}
			
			default: break;
		}
	}
	
	@Override
	public void handleServerSide(SPacketStrikeEntity message, EntityPlayer player)
	{
		
	}
}