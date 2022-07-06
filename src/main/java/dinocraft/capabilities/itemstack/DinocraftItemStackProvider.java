package dinocraft.capabilities.itemstack;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.capabilities.itemstack.DinocraftItemStack.Storage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DinocraftItemStackProvider implements ICapabilitySerializable<NBTBase>
{
	private final Storage storage = new DinocraftItemStack.Storage();
	private final IDinocraftItemStack stack;
	
	public DinocraftItemStackProvider(IDinocraftItemStack stack)
	{
		this.stack = stack;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == DinocraftCapabilities.DINOCRAFT_ITEM_STACK && this.stack != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return this.hasCapability(capability, facing) ? (T) this.stack : null;
	}
	
	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(DinocraftCapabilities.DINOCRAFT_ITEM_STACK, this.stack, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(DinocraftCapabilities.DINOCRAFT_ITEM_STACK, this.stack, null, nbt);
	}
}