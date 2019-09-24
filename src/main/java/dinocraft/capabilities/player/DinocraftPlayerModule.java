/*
package dinocraft.capabilities.player;

import dinocraft.util.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class DinocraftPlayerModule implements NBT
{
	private final DinocraftPlayer player;

	public DinocraftPlayerModule(DinocraftPlayer player)
	{
		this.player = player;
	}
	
	public final DinocraftPlayer getDinocraftPlayer()
	{
		return this.player;
	}

	public final EntityPlayer getEntityPlayer()
	{
		return this.player.getPlayer();
	}

	public final World getWorld()
	{
		return this.getEntityPlayer().world;
	}
}
*/