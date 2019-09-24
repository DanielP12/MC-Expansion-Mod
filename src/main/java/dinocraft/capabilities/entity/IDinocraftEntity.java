package dinocraft.capabilities.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IDinocraftEntity
{
	EntityLivingBase getEntity();
	
	World getWorld();
	
	void write(NBTTagCompound tag);

	void read(NBTTagCompound tag);
}