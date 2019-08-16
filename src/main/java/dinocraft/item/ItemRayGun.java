package dinocraft.item;

import java.util.Random;
import java.util.function.Predicate;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.entity.EntityRayBullet;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import dinocraft.util.PlayerHelper;
import net.minecraft.entity.player.EntityPlayer;
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

public class ItemRayGun extends Item
{
	private static final Predicate<ItemStack> RAY_BULLET = s -> s != null && s.getItem().equals(DinocraftItems.RAY_BULLET);

	public ItemRayGun(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (!worldIn.isRemote)
		{
			if (stack != null && (playerIn.isCreative() || PlayerHelper.hasAmmo(playerIn, RAY_BULLET)))
			{
				if (!playerIn.isCreative())
				{
					PlayerHelper.consumeAmmo((EntityPlayer) playerIn, RAY_BULLET);
					stack.damageItem(1, playerIn);
				}
			
				playerIn.setActiveHand(handIn);
				playerIn.getCooldownTracker().getCooldown(this, 5);
				EntityRayBullet ball = new EntityRayBullet(playerIn, 0.001F);
				ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 10.0F, 0.0F);
				Vec3d vector = playerIn.getForward();
                double x2 = vector.x;
                double y2 = vector.y;
                double z2 = vector.z;
                ball.setVelocity(x2 * 3, y2 * 3, z2 * 3);
				worldIn.spawnEntity(ball);
				
				Random rand = worldIn.rand;
				float j = rand.nextFloat() + 0.5F;
			
				worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.RAY_GUN_SHOT, SoundCategory.NEUTRAL, 5F, j);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		
			if (!PlayerHelper.hasAmmo(playerIn, RAY_BULLET))
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