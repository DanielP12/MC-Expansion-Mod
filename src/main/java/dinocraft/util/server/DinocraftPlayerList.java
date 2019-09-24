package dinocraft.util.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.mojang.authlib.GameProfile;

import net.minecraft.command.ICommand;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftPlayerList
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    public static final File FILE_PLAYERMUTES = new File("muted-players.json");
    public static final File FILE_FORBIDDENWORDS = new File("forbidden-words.json");
    public static final ListForbiddenWords FORBIDDEN_WORDS = new ListForbiddenWords(FILE_FORBIDDENWORDS);
    public static final UserListMutes MUTED_PLAYERS = new UserListMutes(FILE_PLAYERMUTES);
    
	public DinocraftPlayerList()
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		//try removing this
		if (server != null && server.isDedicatedServer())
		{
			try
			{
				MUTED_PLAYERS.readSavedFile();
				FORBIDDEN_WORDS.readSavedFile();
			}
			catch (FileNotFoundException exception)
			{
				exception.printStackTrace();
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		
			FORBIDDEN_WORDS.setLanServer(false);
			MUTED_PLAYERS.setLanServer(false);
		}
	}
	
	public UserListMutes getMutedPlayers()
	{
		return MUTED_PLAYERS;
	}
	
	public ListForbiddenWords getForbiddenWords()
	{
		return FORBIDDEN_WORDS;
	}
	
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		EntityPlayerMP player = event.getPlayer();
		GameProfile profile = player.getGameProfile();
		
		if (this.getMutedPlayers().isMuted(profile))
        {
			UserListMutesEntry entry = this.getMutedPlayers().getEntry(profile);

	        if (entry.getMuteEndDate() != null)
	        {
				player.sendMessage(new TextComponentString(TextFormatting.RED + "You are currently muted until " + DATE_FORMAT.format(entry.getMuteEndDate())));
	        }
	        else
	        {
				player.sendMessage(new TextComponentString(TextFormatting.RED + "You are currently muted"));
	        }
	        
			player.sendMessage(new TextComponentString(TextFormatting.RED + "Reason: " + entry.getMuteReason()));
			event.setCanceled(true);
        }
		
		if (this.getForbiddenWords().getKeys() != null)
		{	
			for (String word : this.getForbiddenWords().getKeys())
			{
				if (event.getMessage().contains(word))
				{
					String s = "";
					
					for (int i = 0; i < word.length(); ++i)
					{
						s += "*";
					}
					
					event.setComponent(new TextComponentString(event.getComponent().getFormattedText().replaceAll(word, s)));
					//continue;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onCommand(CommandEvent event)
	{
		ICommand command = event.getCommand();
		
		if (command instanceof CommandBroadcast || command instanceof CommandEmote)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.getSender();
			GameProfile profile = player.getGameProfile();
			
			if (this.getMutedPlayers().isMuted(profile))
	        {
				UserListMutesEntry entry = this.getMutedPlayers().getEntry(profile);

		        if (entry.getMuteEndDate() != null)
		        {
					player.sendMessage(new TextComponentString(TextFormatting.RED + "You are currently muted until " + DATE_FORMAT.format(entry.getMuteEndDate())));
		        }
		        else
		        {
					player.sendMessage(new TextComponentString(TextFormatting.RED + "You are currently muted"));
		        }
		        
				player.sendMessage(new TextComponentString(TextFormatting.RED + "Reason: " + entry.getMuteReason()));
				event.setCanceled(true);
	        }
		}
	}
}