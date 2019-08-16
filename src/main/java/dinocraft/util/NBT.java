package dinocraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface NBT
{
	/**
	 * Writes this object's state to a {@link NBTTagCompound}
	 * @param tag The tag to write to
	 */
	void write(NBTTagCompound tag);

	/**
	 * Reads this object's state from a {@link NBTTagCompound}
	 * @param tag The tag to write to
	 */
	void read(NBTTagCompound tag);
}
