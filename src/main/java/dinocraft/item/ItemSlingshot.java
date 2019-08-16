package dinocraft.item;

import java.util.function.Predicate;

import dinocraft.Reference;
import dinocraft.entity.EntityVineBall;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.init.DinocraftItems;
import dinocraft.util.DinocraftServer;
import dinocraft.util.PlayerHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSlingshot extends Item
{
	private static final Predicate<ItemStack> VINE_BALL = s -> s != null && s.getItem().equals(DinocraftItems.VINE_BALL);
	private static final Predicate<ItemStack> VINE_BALL_BUNDLE = s -> s != null && s.getItem().equals(DinocraftItems.VINE_BALL_BUNDLE);

	public ItemSlingshot(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(200);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase livingIn, int l)
	{
		if (livingIn instanceof EntityPlayer && !worldIn.isRemote)
		{
			int j = getMaxItemUseDuration(stack) - l;
			
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			if (f < 1.0F) return;
			
			EntityPlayer playerIn = (EntityPlayer) livingIn;
			
			if (playerIn.isCreative() || PlayerHelper.hasAmmo(playerIn, VINE_BALL) || PlayerHelper.hasAmmo(playerIn,  VINE_BALL_BUNDLE))
			{
				playerIn.getCooldownTracker().setCooldown(this, 100);

				Vec3d vector = playerIn.getLookVec();
				double x = playerIn.posX + vector.x - 0.3D;
				double y = playerIn.posY + vector.y + 1.5D;
				double z = playerIn.posZ + vector.z;
			
				for (int i = 0; i < 15; ++i)
				{
					DinocraftServer.spawnParticle(EnumParticleTypes.SMOKE_LARGE, worldIn, x, y, z, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, 1); //MODIFY
				}
				
				worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), DinocraftSoundEvents.SHOT, SoundCategory.NEUTRAL, 10.0F, 0.5F);

				EntityVineBall ball1 = new EntityVineBall(playerIn, 0.015F);
				EntityVineBall ball2 = new EntityVineBall(playerIn, 0.015F);
	
				if (playerIn.isCreative())
				{
					EntityVineBall ball = new EntityVineBall(playerIn, 0.015F);
					ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 1.75F, 1F);
					worldIn.spawnEntity(ball);
				}
				else if (PlayerHelper.hasAmmo(playerIn, VINE_BALL))
				{
					EntityVineBall ball = new EntityVineBall(playerIn, 0.015F);
					PlayerHelper.consumeAmmo(playerIn, VINE_BALL);
					stack.damageItem(1, playerIn);
					ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 1.75F, 1F);
					worldIn.spawnEntity(ball);
				} 
				else if (PlayerHelper.hasAmmo(playerIn, VINE_BALL_BUNDLE))
				{
					PlayerHelper.consumeAmmo(playerIn, VINE_BALL_BUNDLE);
					stack.damageItem(1, playerIn);
					
					for (int k = 0; k < 6; ++k)
					{
						EntityVineBall ball = new EntityVineBall(playerIn, 0.015F);
						ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 1.5F, 7F);
						worldIn.spawnEntity(ball);
					}
				}
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (stack != null)
		{
			if (playerIn.isCreative() || PlayerHelper.hasAmmo(playerIn, VINE_BALL))
			{
				playerIn.setActiveHand(handIn);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
			else if (playerIn.isCreative() || PlayerHelper.hasAmmo(playerIn, VINE_BALL_BUNDLE))
			{
				playerIn.setActiveHand(handIn);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
			
			return ActionResult.newResult(EnumActionResult.PASS, stack);	
		}
		
		return null;
	}
}
