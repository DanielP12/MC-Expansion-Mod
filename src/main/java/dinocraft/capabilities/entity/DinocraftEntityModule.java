package dinocraft.capabilities.entity;

import dinocraft.util.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class DinocraftEntityModule implements NBT
{
	private final DinocraftEntity entity;
	
	public DinocraftEntityModule(DinocraftEntity entity)
	{
		this.entity = entity;
	}

	public DinocraftEntity getDinocraftEntity()
	{
		return this.entity;
	}
	
	public EntityLivingBase getEntity()
	{
		return this.entity.getEntity();
	}
	
	public World getWorld()
	{
		return this.getEntity().world;
	}
}