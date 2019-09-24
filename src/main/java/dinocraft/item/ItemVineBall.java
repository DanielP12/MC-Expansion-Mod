package dinocraft.item;

import dinocraft.Reference;
import dinocraft.entity.EntityVineBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemVineBall extends Item
{
	public ItemVineBall(String name)
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		ItemStack stack = player.getHeldItem(hand);	
		
		if (!player.isCreative())
		{
			stack.shrink(1);
		}
				
		if (!world.isRemote)
		{
			EntityVineBall ball = new EntityVineBall(player, 0.1F);
        	ball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 7.0F);
        	world.spawnEntity(ball);
		}
		
		world.playSound((EntityPlayer) null, player.getPosition(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 1.0F, 0.05F);
		player.addStat(StatList.getObjectUseStats(this));
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
}