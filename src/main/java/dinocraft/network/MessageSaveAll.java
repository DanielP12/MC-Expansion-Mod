package dinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class MessageSaveAll extends AbstractPacket<MessageSaveAll> 
{	
    @Override
    public void toBytes(ByteBuf buffer)
    {
    	
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    	
    }

	@Override
	public void handleClientSide(MessageSaveAll message, EntityPlayer player) 
	{

	}

	@Override
	public void handleServerSide(MessageSaveAll message, EntityPlayer player) 
	{
		MinecraftServer server = player.world.getMinecraftServer();
		PlayerList list = server.getPlayerList();
		
		if (list != null)
        {
			list.sendMessage(new TextComponentString(TextFormatting.GRAY + (TextFormatting.ITALIC + "[Server: Saved the world]")));
			list.saveAllPlayerData();
        }

        try
        {
            for (int i = 0; i < server.worlds.length; ++i)
            {
                if (server.worlds[i] != null)
                {
                    WorldServer worldserver = server.worlds[i];
                    boolean flag = worldserver.disableLevelSaving;
                    worldserver.disableLevelSaving = false;
                    worldserver.saveAllChunks(true, (IProgressUpdate) null);
                    worldserver.disableLevelSaving = flag;
                }
            }
        }
        catch (MinecraftException exception)
        {
        	list.sendMessage(new TextComponentString(TextFormatting.GRAY + (TextFormatting.ITALIC + "[Server: Could not save the world]")));
        	return;
        }
	}
}