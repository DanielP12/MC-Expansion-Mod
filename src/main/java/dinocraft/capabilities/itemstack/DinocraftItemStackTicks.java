package dinocraft.capabilities.itemstack;

import dinocraft.init.DinocraftItems;
import net.minecraft.nbt.NBTTagCompound;

public class DinocraftItemStackTicks extends DinocraftItemStackModule
{
	public DinocraftItemStackTicks(DinocraftItemStack stack)
	{
		super(stack);
	}
	
	protected int mysteriousClockTick = 0;
	
	public void incrementMysteriousClockTick()
	{
		if (this.getItemStack().getItem() == DinocraftItems.MYSTERIOUS_CLOCK)
		{
			this.mysteriousClockTick = (this.mysteriousClockTick + 1) % 200;
		}
	}

	public int getMysteriousClockTick()
	{
		return this.mysteriousClockTick;
	}
	
	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Ticks", root);
		root.setInteger("MC1", this.mysteriousClockTick);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Ticks");
		this.mysteriousClockTick = root.getInteger("MC1");
	}
}