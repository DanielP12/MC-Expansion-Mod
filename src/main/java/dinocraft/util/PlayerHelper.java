package dinocraft.util;

import java.util.function.Predicate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public final class PlayerHelper 
{
	/** 
	 * Checks if either of the player's hands has an item 
	 */
	public static boolean hasAnyHeldItem(EntityPlayer playerIn) 
	{
		return playerIn.getHeldItemMainhand() != null || playerIn.getHeldItemOffhand() != null;
	}

	/**
	 * Checks main hand, then off hand for this item
	 */
	public static boolean hasHeldItem(EntityPlayer playerIn, Item item) 
	{
		ItemStack mainhand = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();
		return mainhand != null && mainhand.getItem() == item || offhand != null && offhand.getItem() == item;
	}

	/** 
	 * Checks main hand, then off hand for this item class 
	 */
	public static boolean hasHeldItemClass(EntityPlayer playerIn, Item template) 
	{
		return hasHeldItemClass(playerIn, template.getClass());
	}

	/** 
	 * Checks main hand, then off hand for this item class 
	 */
	public static boolean hasHeldItemClass(EntityPlayer playerIn, Class<?> template)
	{
		ItemStack mainhand = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();
		return mainhand != null && template.isAssignableFrom(mainhand.getItem().getClass()) || offhand != null && template.isAssignableFrom(offhand.getItem().getClass());
	}

	/** 
	 * Checks main hand, then off hand for this item. Null otherwise.
	 */
	public static ItemStack getFirstHeldItem(EntityPlayer playerIn, Item item)
	{
		ItemStack mainhand = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();
		
		if (mainhand != null && item == mainhand.getItem()) return mainhand;
		else if (offhand != null && item == offhand.getItem()) return offhand;
		else return null;
	}

	/** 
	 * Checks main hand, then off hand for this item class. Null otherwise
	 */
	public static ItemStack getFirstHeldItemClass(EntityPlayer playerIn, Class<?> template) 
	{
		ItemStack mainhand = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();
		
		if (mainhand != null && template.isAssignableFrom(mainhand.getItem().getClass())) return mainhand;
		else if (offhand != null && template.isAssignableFrom(offhand.getItem().getClass())) return offhand;
		else return null;
	}

	public static ItemStack getAmmo(EntityPlayer playerIn, Predicate<ItemStack> ammoFunc) 
	{
		if (ammoFunc.test(playerIn.getHeldItem(EnumHand.OFF_HAND))) return playerIn.getHeldItem(EnumHand.OFF_HAND);
		else if (ammoFunc.test(playerIn.getHeldItem(EnumHand.MAIN_HAND))) return playerIn.getHeldItem(EnumHand.MAIN_HAND);
		else
		{
			for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i)
			{
				ItemStack stack = playerIn.inventory.getStackInSlot(i);
				if (ammoFunc.test(stack)) return stack;
			}

			return null;
		}
	}

	public static boolean hasAmmo(EntityPlayer playerIn, Predicate<ItemStack> ammoFunc)
	{
		return getAmmo(playerIn, ammoFunc) != null;
	}

	public static void consumeAmmo(EntityPlayer playerIn, Predicate<ItemStack> ammoFunc)
	{
		ItemStack ammo = getAmmo(playerIn, ammoFunc);
		
		if (ammo.getCount() != 1) ammo.setCount(ammo.getCount() - 1);
		else playerIn.inventory.deleteStack(ammo);
	}

	public static boolean hasItem(EntityPlayer playerIn, Predicate<ItemStack> itemFunc) 
	{
		for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) 
		{
			if (itemFunc.test(playerIn.inventory.getStackInSlot(i))) return true;
		}
		
		return false;
	}

	private PlayerHelper() 
	{
		
	}
}
