package dinocraft.item;

import java.util.Random;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.entity.EntitySeed;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSeedPipe extends Item
{
	public ItemSeedPipe(String name)
	{
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(300);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		Random rand = world.rand;
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (stack != null && (player.isCreative() || dinoEntity.hasAmmo(DinocraftItems.SEED)))
		{
			if (!player.isCreative())
			{
				if (rand.nextInt(5) < 2)
				{
					dinoEntity.consumeAmmo(DinocraftItems.SEED, 1);
				}
				
				stack.damageItem(1, player);
			}
			
			if (!world.isRemote)
			{
				player.setActiveHand(hand);
				player.getCooldownTracker().setCooldown(this, 2);
				EntitySeed seed = new EntitySeed(player, 0.05F);
				seed.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.0F, 0.0F);
				world.spawnEntity(seed);
					
				world.playSound((EntityPlayer) null, player.getPosition(), DinocraftSoundEvents.SEED_SHOT, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() + 0.5F);
			}
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		if (!dinoEntity.hasAmmo(DinocraftItems.SEED) && !world.isRemote)
		{
			dinoEntity.sendActionbarMessage(TextFormatting.RED + "Out of ammo!");
			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}
