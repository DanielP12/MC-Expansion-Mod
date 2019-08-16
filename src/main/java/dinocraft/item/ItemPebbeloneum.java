package dinocraft.item;

import java.util.List;
import java.util.Random;

import org.jline.utils.Log;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemPebbeloneum extends Item 
{
	public ItemPebbeloneum(String unlocalizedName, String registryName) 
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
		this.setMaxStackSize(1);
	}
	
	Random rand = new Random();
/*
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		List<Item> items = ForgeRegistries.ITEMS.getValues();
		Item item = items.get(rand.nextInt(items.size()));
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (item != Items.NETHER_STAR && item != Item.getItemFromBlock(Blocks.END_PORTAL)
				&& item != Item.getItemFromBlock(Blocks.DRAGON_EGG) && item != Item.getItemFromBlock(Blocks.END_PORTAL_FRAME)
				&& item != Item.getItemFromBlock(Blocks.END_GATEWAY) && item != Items.SPAWN_EGG) //Check for unacceptable overpowered items that should NEVER come out
		{
			playerIn.playSound(DinocraftSoundEvents.CRACK, 1.0F, rand.nextFloat());
			playerIn.inventory.addItemStackToInventory(item.getDefaultInstance());
			stack.shrink(1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
	*/
	
	/*
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		List<Item> items = ForgeRegistries.ITEMS.getValues();
		Item item = items.get(rand.nextInt(items.size()));
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (item != Items.NETHER_STAR && item != Item.getItemFromBlock(Blocks.END_PORTAL)
				&& item != Item.getItemFromBlock(Blocks.DRAGON_EGG) && item != Item.getItemFromBlock(Blocks.END_PORTAL_FRAME)
				&& item != Item.getItemFromBlock(Blocks.END_GATEWAY) && item != Items.SPAWN_EGG
				&& item != Item.getItemFromBlock(Blocks.MOB_SPAWNER)) //Check for unacceptable overpowered items that should NEVER come out
		{
			ItemStack itemstack = new ItemStack(item);
			boolean flag = playerIn.inventory.addItemStackToInventory(itemstack);

	        if (flag)
	        {
	        	playerIn.world.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerIn.getRNG().nextFloat() - playerIn.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
	        	playerIn.inventoryContainer.detectAndSendChanges();
	        }

	        if (flag && itemstack.isEmpty())
	        {
	            itemstack.setCount(1);
	            EntityItem entityitem = playerIn.dropItem(itemstack, false);
	            if (entityitem != null) entityitem.makeFakeItem();
	        }
	        else
	        {
	            EntityItem entityitem = playerIn.dropItem(itemstack, false);

	            if (entityitem != null)
	            {
	                entityitem.setNoPickupDelay();
	                entityitem.setOwner(playerIn.getName());
	            }
	        }
	        
			stack.shrink(1);
			playerIn.playSound(DinocraftSoundEvents.CRACK, 1.0F, rand.nextFloat());

			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
	*/
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		List<Item> items = ForgeRegistries.ITEMS.getValues();
		Item item = items.get(rand.nextInt(items.size()));
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (item != Items.NETHER_STAR && item != Item.getItemFromBlock(Blocks.END_PORTAL)
				&& item != Item.getItemFromBlock(Blocks.DRAGON_EGG) && item != Item.getItemFromBlock(Blocks.END_PORTAL_FRAME)
				&& item != Item.getItemFromBlock(Blocks.END_GATEWAY) && item != Items.SPAWN_EGG
				&& item != Item.getItemFromBlock(Blocks.MOB_SPAWNER) && item != Item.getItemFromBlock(Blocks.BARRIER)
				&& item != Items.AIR && item != Item.getItemFromBlock(Blocks.STRUCTURE_VOID) && item != Item.getItemFromBlock(Blocks.STRUCTURE_BLOCK)
				&& item != DinocraftItems.SEED && item != DinocraftItems.HEART && item != Item.getItemFromBlock(Blocks.COMMAND_BLOCK)
				&& item != Item.getItemFromBlock(Blocks.CHAIN_COMMAND_BLOCK) && item != Item.getItemFromBlock(Blocks.REPEATING_COMMAND_BLOCK)) //Check for unacceptable overpowered items that should NEVER come out
		{
			DinocraftPlayer.getPlayer(playerIn).addStack(item, 1);
			
			stack.shrink(1);
			playerIn.playSound(DinocraftSoundEvents.CRACK, 1.0F, rand.nextFloat());

			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}