package dinocraft.capabilities.player;

import dinocraft.capabilities.DinocraftCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DinocraftPlayerProvider implements ICapabilitySerializable<NBTBase>
{
	private final DinocraftPlayer.Storage storage = new DinocraftPlayer.Storage();
	private final IDinocraftPlayer player;

	public DinocraftPlayerProvider(IDinocraftPlayer playerIn)
	{
		this.player = playerIn;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == DinocraftCapabilities.DINOCRAFT_PLAYER && this.player != null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing)) return (T) this.player;
		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, this.player, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(DinocraftCapabilities.DINOCRAFT_PLAYER, this.player, null, nbt);
	}
}
