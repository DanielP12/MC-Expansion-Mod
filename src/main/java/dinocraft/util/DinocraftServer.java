package dinocraft.util;

import org.jline.utils.Log;

import dinocraft.network.MessageAbsorption;
import dinocraft.network.MessageEffect;
import dinocraft.network.MessageNoFall;
import dinocraft.network.MessageSaveAll;
import dinocraft.network.MessageSpawnCloudParticle;
import dinocraft.network.MessageSpawnDeathParticle;
import dinocraft.network.MessageSpawnGoldParticle;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketAchievements;
import dinocraft.network.PacketAchievements.Achievement;
import dinocraft.network.PacketCapabilities;
import dinocraft.network.PacketCapabilities.Capability;
import dinocraft.network.PacketSpawnParticle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DinocraftServer
{
	public DinocraftServer()
	{
		
	}
	
	/** Spawns particles on the player - Server-side. */
	public static void spawnParticle(EnumParticleTypes particleType, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int parameters)
	{
		NetworkHandler.sendToAllAround(new PacketSpawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters), world);
	}
	 
	/** Sets player's absorption amount - Server-side. */
	public static void setPlayerAbsorptionAmount(float amount)
	{
		NetworkHandler.sendToServer(new MessageAbsorption(amount));
	}
	 
	/** Adds a potion effect to the player - Server-side. */
	public static void addPotionEffect(int potionID, int duration, int amplifier)
	{
		NetworkHandler.sendToServer(new MessageEffect(potionID, duration, amplifier));
	}
	 
	/** Cancels player's fall damage - Server-side. */
	public static void cancelPlayerFallDamage() 
	{
		NetworkHandler.sendToServer(new MessageNoFall());
	}
	 
	public static void spawnDeathParticles() 
	{
		NetworkHandler.sendToServer(new MessageSpawnDeathParticle());
	}
	 
	public static void spawnCloudParticles() 
	{
		NetworkHandler.sendToServer(new MessageSpawnCloudParticle());
	}
	 
	public static void spawnGoldParticles() 
	{
		NetworkHandler.sendToServer(new MessageSpawnGoldParticle());
	}
	 
	public static void addPlayerAchievement(Achievement achievement) 
	{
		NetworkHandler.sendToServer(new PacketAchievements(achievement));
	}
	 
	public static void setPlayerCapability(Capability capability, boolean phase) 
	{
		NetworkHandler.sendToServer(new PacketCapabilities(capability, phase));
	}
	
	/** 
	 * Gets the side that is running
	 */
	public static void getSide(World worldIn)
	{
		if (worldIn.isRemote) Log.info("CLIENT");
		if (!worldIn.isRemote) Log.info("SERVER");
	}
	
	/** 
	 * Saves the world 
	 */
	public static void saveAll(World worldIn)
	{
		if (!worldIn.isRemote)
		{
			MinecraftServer server = worldIn.getMinecraftServer();
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
		else if (worldIn.isRemote) NetworkHandler.sendToServer(new MessageSaveAll());
	}
}
