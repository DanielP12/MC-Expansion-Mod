package dinocraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LockedSlot extends SlotItemHandler
{
	public LockedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
