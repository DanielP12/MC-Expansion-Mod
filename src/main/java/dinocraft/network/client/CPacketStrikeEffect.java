package dinocraft.network.client;

import dinocraft.command.CommandStrike;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class CPacketStrikeEffect extends AbstractPacket<CPacketStrikeEffect>
{
	private boolean block;
	private int posX, posY, posZ;
	private double x, y, z;
	
	public CPacketStrikeEffect()
	{
		
	}
	
	public CPacketStrikeEffect(double x, double y, double z)
	{
		this.block = false;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public CPacketStrikeEffect(BlockPos pos)
	{
		this.block = true;
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(this.block);
		buffer.writeDouble(this.x);
		buffer.writeDouble(this.y);
		buffer.writeDouble(this.z);
		buffer.writeInt(this.posX);
		buffer.writeInt(this.posY);
		buffer.writeInt(this.posZ);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.block = buffer.readBoolean();
		this.x = buffer.readDouble();
		this.y = buffer.readDouble();
		this.z = buffer.readDouble();
		this.posX = buffer.readInt();
		this.posY = buffer.readInt();
		this.posZ = buffer.readInt();
	}

	@Override
	public void handleClientSide(CPacketStrikeEffect message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(CPacketStrikeEffect message, EntityPlayer player)
	{
		if (message.block)
		{
			Block block = player.world.getBlockState(new BlockPos(message.posX, message.posY, message.posZ)).getBlock();
			player.world.addWeatherEffect(new EntityLightningBolt(player.world, message.posX, message.posY, message.posZ, false));
			CommandBase.notifyCommandListener(player, new CommandStrike(), "commands.strike.success", block.getLocalizedName());
		}
		else
		{
			player.world.addWeatherEffect(new EntityLightningBolt(player.world, message.x, message.y, message.z, false));
			CommandBase.notifyCommandListener(player, new CommandStrike(), "commands.strike.success", player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(new BlockPos(message.x, message.y, message.z))).get(0).getName());
		}
	}
}