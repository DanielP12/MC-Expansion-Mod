package dinocraft.item;

import java.util.Random;
import java.util.function.Predicate;

import org.jline.utils.Log;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.entity.EntityRayBullet;
import dinocraft.entity.EntitySeed;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import dinocraft.util.PlayerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSeedPipe extends Item
{
	private static final Predicate<ItemStack> SEED = s -> s != null && s.getItem().equals(Items.WHEAT_SEEDS);

	public ItemSeedPipe(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(300);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		Random rand = worldIn.rand;
		
		if (!worldIn.isRemote)
		{
			if (stack != null && (playerIn.isCreative() || PlayerHelper.hasAmmo(playerIn, SEED)))
			{
				if (!playerIn.isCreative())
				{
					if (rand.nextInt(5) < 2) PlayerHelper.consumeAmmo((EntityPlayer) playerIn, SEED);
					
					stack.damageItem(1, playerIn);
				}
			
				playerIn.setActiveHand(handIn);
				playerIn.getCooldownTracker().setCooldown(this, 2);
				EntitySeed seed = new EntitySeed(playerIn, 0.05F);
				seed.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 0.0F);
				Vec3d vector = playerIn.getForward();
                double x2 = vector.x;
                double y2 = vector.y;
                double z2 = vector.z;
                seed.setVelocity(x2 * 2, y2 * 2, z2 * 2);
				worldIn.spawnEntity(seed);
				
				float j = rand.nextFloat() + 0.5F;
			
				worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.SEED_SHOT, SoundCategory.NEUTRAL, 1.0F, j);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		
			if (!PlayerHelper.hasAmmo(playerIn, SEED))
			{
				DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
				player.sendActionbarMessage(TextFormatting.RED + "Out of ammo!");
				worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.PLAYERS, 0.5F, 5.0F);
				
				return ActionResult.newResult(EnumActionResult.FAIL, stack);
			}
		
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}
