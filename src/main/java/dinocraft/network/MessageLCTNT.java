package dinocraft.network;

import dinocraft.capabilities.player.DinocraftPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MessageLCTNT extends AbstractPacket<MessageLCTNT> 
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
	public void handleClientSide(MessageLCTNT message, EntityPlayer playerIn) 
	{

	}

	@Override
	public void handleServerSide(MessageLCTNT message, EntityPlayer playerIn) 
	{
		DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
		RayTraceResult trace = player.getTrace(100.0D);

		World worldIn = playerIn.world;

		if (trace != null && trace.getBlockPos() != null)
		{
			BlockPos pos = trace.getBlockPos();
			Block block = worldIn.getBlockState(pos).getBlock();
	
			if (block == Blocks.TNT)
			{
				worldIn.setBlockToAir(pos);
				worldIn.spawnEntity(new EntityTNTPrimed(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn));
				playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Fire in the hole!"));
			}
		}
	}
}