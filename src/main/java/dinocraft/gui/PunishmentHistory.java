package dinocraft.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class PunishmentHistory extends Container
{
	private ArrayList<ItemStack> allEntries = new ArrayList<>();
	private ArrayList<ItemStack> muteEntries = new ArrayList<>();
	private ArrayList<ItemStack> warningEntries = new ArrayList<>();
	private boolean initialized;

	public PunishmentHistory(IItemHandler container)
	{
		int x = 0;

		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				this.addSlotToContainer(new LockedSlot(container, j + i * 13, 12 + j * 18, 10 + i * 18));
				x++;
			}
		}
	}

	public void initialize()
	{
		if (!this.initialized)
		{
			this.initialized = true;

			for (int i = 0; i < this.getSize(); i++)
			{
				ItemStack stack = this.getSlot(i).getStack();
				
				if (!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE))
				{
					int meta = stack.getMetadata();
					
					if (meta == 1)
					{
						this.warningEntries.add(stack);
					}
					else if (meta == 14)
					{
						this.muteEntries.add(stack);
					}
					
					this.allEntries.add(stack);
				}
			}
		}
	}

	public void clear()
	{
		for (int i = 0; i < this.getSize(); i++)
		{
			this.putStackInSlot(i, ItemStack.EMPTY);
		}
	}
	
	public void display(Type type)
	{
		if (!this.initialized)
		{
			this.initialize();
		}
		
		this.clear();

		switch (type)
		{
			case ALL:
			{
				for (int i = 0; i < this.allEntries.size(); i++)
				{
					this.putStackInSlot(i, this.allEntries.get(i));
				}

				break;
			}
			case MUTES:
			{
				for (int i = 0; i < this.muteEntries.size(); i++)
				{
					this.putStackInSlot(i, this.muteEntries.get(i));
				}

				break;
			}
			case WARNINGS:
			{
				for (int i = 0; i < this.warningEntries.size(); i++)
				{
					this.putStackInSlot(i, this.warningEntries.get(i));
				}
				
				break;
			}
		}
	}

	public enum Type
	{
		ALL, MUTES, WARNINGS;
		
		public static Type from(String str)
		{
			if (str.equalsIgnoreCase("all"))
			{
				return Type.ALL;
			}
			else if (str.equalsIgnoreCase("mutes"))
			{
				return Type.MUTES;
			}
			else if (str.equalsIgnoreCase("warnings"))
			{
				return Type.WARNINGS;
			}
			
			return null;
		}
	}
	
	public int getSize()
	{
		return 156;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public boolean canDragIntoSlot(Slot slot)
	{
		return false;
	}
}
