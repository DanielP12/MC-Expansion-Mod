/**
 *
 */
package dinocraft.network.server;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SPacketRecoilPlayer extends AbstractPacket<SPacketRecoilPlayer>
{
	private float recoil;
	
	public SPacketRecoilPlayer()
	{
		
	}
	
	public SPacketRecoilPlayer(float recoil)
	{
		this.recoil = recoil;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeFloat(this.recoil);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.recoil = buffer.readFloat();
	}
	
	@Override
	public void handleClientSide(SPacketRecoilPlayer message, EntityPlayer player)
	{
		DinocraftEntity.recoil(player, message.recoil);
	}
	
	@Override
	public void handleServerSide(SPacketRecoilPlayer message, EntityPlayer player)
	{

	}
}