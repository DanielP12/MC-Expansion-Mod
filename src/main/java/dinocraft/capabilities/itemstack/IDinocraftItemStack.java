package dinocraft.capabilities.itemstack;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IDinocraftItemStack
{
	ItemStack getItemStack();

	void write(NBTTagCompound tag);

	void read(NBTTagCompound tag);
}