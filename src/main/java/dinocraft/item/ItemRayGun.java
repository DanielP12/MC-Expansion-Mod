package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.entity.EntityRayBullet;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRayGun extends Item
{
	public ItemRayGun(String name)
	{
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (stack != null && (player.isCreative() || dinoEntity.hasAmmo(DinocraftItems.RAY_BULLET)))
		{
			if (!player.isCreative())
			{
				dinoEntity.consumeAmmo(DinocraftItems.RAY_BULLET, 1);
				stack.damageItem(1, player);
			}
			
			if (!world.isRemote)
			{
				player.setActiveHand(hand);
				EntityRayBullet ball = new EntityRayBullet(player, 0.001F);
				ball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 5.0F, 1.0F);
				Vec3d vector = player.getLookVec();
				double x2 = vector.x;
				double y2 = vector.y;
            	double z2 = vector.z;
            	ball.motionX = x2 * 3;
            	ball.motionY = y2 * 3;
            	ball.motionZ = z2 * 3;
            	ball.setPositionAndUpdate(player.posX - (x2 * 0.75D), player.posY + player.eyeHeight, player.posZ - (z2 * 0.75D));
            	world.spawnEntity(ball);
            	world.playSound(null, player.getPosition(), DinocraftSoundEvents.RAY_GUN_SHOT, SoundCategory.NEUTRAL, 3.0F, world.rand.nextFloat() + 0.5F);
			}
            
			DinocraftEntity.getEntity(player).recoil(0.1F, 1.0F, true);
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		if (!dinoEntity.hasAmmo(DinocraftItems.RAY_BULLET) && !world.isRemote)
		{
			DinocraftEntity.getEntity(player).sendActionbarMessage(TextFormatting.RED + "Out of ammo!");
			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}