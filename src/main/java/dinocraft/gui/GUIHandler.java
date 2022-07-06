package dinocraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.ItemStackHandler;

public class GUIHandler implements IGuiHandler
{
	public static final int GUI_LOCKED_CONTAINER_ID = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_LOCKED_CONTAINER_ID:
			{
				return new PunishmentHistory(new ItemStackHandler(156));
			}
		}

		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_LOCKED_CONTAINER_ID:
			{
				return new GUIPunishmentHistory(new PunishmentHistory(new ItemStackHandler(156)));
			}
		}

		return null;
	}

}
