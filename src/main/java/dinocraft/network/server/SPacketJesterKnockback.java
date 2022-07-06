package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketJesterKnockback extends AbstractPacket<SPacketJesterKnockback>
{
	private int attackerId;
	
	public SPacketJesterKnockback()
	{
		
	}
	
	public SPacketJesterKnockback(EntityLivingBase attacker)
	{
		this.attackerId = attacker.getEntityId();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.attackerId);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.attackerId = buffer.readInt();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(SPacketJesterKnockback message, EntityPlayer player)
	{
		WorldClient world = (WorldClient) player.world;
		EntityLivingBase attacker = (EntityLivingBase) world.getEntityByID(message.attackerId);
		double d = player.posX - attacker.posX;
		double d1;
		
		for (d1 = player.posZ - attacker.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
		{
			d = (Math.random() - Math.random()) * 0.01D;
		}
		
		float f = MathHelper.sqrt(d * d + d1 * d1);
		player.motionX /= 2.0D;
		player.motionY /= 2.0D;
		player.motionZ /= 2.0D;
		player.motionX += d / f * 1.5D;
		player.motionY += 0.3D;
		player.motionZ += d1 / f * 1.5D;
	}
	
	@Override
	public void handleServerSide(SPacketJesterKnockback message, EntityPlayer player)
	{

	}
}