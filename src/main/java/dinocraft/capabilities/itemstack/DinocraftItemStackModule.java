package dinocraft.capabilities.itemstack;

import dinocraft.util.NBT;
import net.minecraft.item.ItemStack;

public abstract class DinocraftItemStackModule implements NBT
{
	private final DinocraftItemStack stack;
	
	public DinocraftItemStackModule(DinocraftItemStack stack)
	{
		this.stack = stack;
	}

	public DinocraftItemStack getDinocraftItemStack()
	{
		return this.stack;
	}
	
	public ItemStack getItemStack()
	{
		return this.stack.getItemStack();
	}
}