package dinocraft.capabilities.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IDinocraftPlayer 
{
	EntityPlayer getPlayer();
	
	World getWorld();
	
	void write(NBTTagCompound tag);

	void read(NBTTagCompound tag);
}
