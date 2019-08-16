package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketAchievements extends AbstractPacket<PacketAchievements> 
{
	
	private Achievement achievement;
	
	public PacketAchievements()
	{
 
	}
 
	public PacketAchievements(final Achievement achievement)
	{
		this.achievement = achievement;
	}
 
	@Override
	public void fromBytes(final ByteBuf buffer)
	{
		this.achievement = Achievement.values()[buffer.readByte()];
	}
 
	@Override
	public void toBytes(final ByteBuf buffer)
	{
		buffer.writeByte(this.achievement.ordinal());
	}
 
	public enum Achievement
	{
		DOUBLE_JUMPIN_JUMP, ONE_JUMP_FURTHER;
	}

	@Override
	public void handleClientSide(PacketAchievements message, EntityPlayer playerIn) 
	{
		
	}

	@Override
	public void handleServerSide(PacketAchievements message, EntityPlayer playerIn) 
	{
		/*
		if (message.achievement == Achievement.ONE_JUMP_FURTHER)
		{
			playerIn.addStat(DinocraftAchievements.ONE_JUMP_FURTHER);
		}
		else if (message.achievement == Achievement.DOUBLE_JUMPIN_JUMP)
		{
			playerIn.addStat(DinocraftAchievements.DOUBLE_JUMPIN_JUMP);
		}	
		*/
	}
}
