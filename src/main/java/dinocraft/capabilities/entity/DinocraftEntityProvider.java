package dinocraft.capabilities.entity;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.capabilities.entity.DinocraftEntity.Storage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DinocraftEntityProvider implements ICapabilitySerializable<NBTBase>
{
	private final Storage storage = new DinocraftEntity.Storage();
	private final IDinocraftEntity entity;

	public DinocraftEntityProvider(IDinocraftEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == DinocraftCapabilities.DINOCRAFT_ENTITY && this.entity != null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return this.hasCapability(capability, facing) ? (T) this.entity : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, this.entity, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(DinocraftCapabilities.DINOCRAFT_ENTITY, this.entity, null, nbt);
	}
}