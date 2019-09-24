package dinocraft.util;

import org.jline.utils.Log;

import dinocraft.network.MessageSpawnCloudParticle;
import dinocraft.network.NetworkHandler;
import dinocraft.network.PacketCapabilities;
import dinocraft.network.PacketCapabilities.Capability;
import dinocraft.network.PacketSpawnParticle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class DinocraftServer
{
	public DinocraftServer()
	{
		
	}
	
	/** Spawns particles on the player - Server-side. */
	public static void spawnParticle(EnumParticleTypes particleType, boolean ignoreRange, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int parameters)
	{
		NetworkHandler.sendToAllAround(new PacketSpawnParticle(particleType, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters), world);
	}
	
	public static void spawnCloudParticles() 
	{
		NetworkHandler.sendToServer(new MessageSpawnCloudParticle());
	}
	 
	public static void setPlayerCapability(Capability capability, boolean phase) 
	{
		NetworkHandler.sendToServer(new PacketCapabilities(capability, phase));
	}
	
	/** 
	 * Gets the running thread
	 */
	public static void getSide(World world)
	{
		if (world.isRemote)
		{
			Log.info("CLIENT");
		}
		
		if (!world.isRemote)
		{
			Log.info("SERVER");
		}
	}
}