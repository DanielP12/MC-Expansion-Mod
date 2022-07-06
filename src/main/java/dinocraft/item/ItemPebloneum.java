package dinocraft.item;

import java.util.Collection;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.util.DinocraftConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemPebloneum extends Item
{
	public ItemPebloneum()
	{
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote)
		{
			Collection<Item> items = ForgeRegistries.ITEMS.getValuesCollection();
			Item item = (Item) items.toArray()[world.rand.nextInt(items.size())];

			if (item != Item.getItemFromBlock(Blocks.END_PORTAL) && item != Item.getItemFromBlock(Blocks.END_GATEWAY) && item != DinocraftItems.HEART)
			{
				for (String name : DinocraftConfig.FORBIDDEN_ITEMS)
				{
					if (item.getRegistryName().toString().equalsIgnoreCase(name))
					{
						return new ActionResult(EnumActionResult.FAIL, stack);
					}
				}

				DinocraftEntity.addStack(player, new ItemStack(item));
				stack.shrink(1);
				world.playSound(null, player.getPosition(), DinocraftSoundEvents.CRACK, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat());
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
		}

		return new ActionResult(EnumActionResult.FAIL, stack);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}
}