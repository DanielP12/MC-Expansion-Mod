package dinocraft.capabilities.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IDinocraftEntity
{
	EntityLivingBase getEntity();
	
	void write(NBTTagCompound tag);
	
	void read(NBTTagCompound tag);
}