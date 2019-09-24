package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.entity.EntityVineBall;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
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
	public ItemSlingshot(String name)
	{
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(200);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int l)
	{
		if (entity instanceof EntityPlayer)
		{
			int j = getMaxItemUseDuration(stack) - l;
			
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			
			if (f < 1.0F)
			{
				return;
			}
			
			EntityPlayer player = (EntityPlayer) entity;
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
			
			if (player.isCreative() || dinoEntity.hasAmmo(DinocraftItems.VINE_BALL) || dinoEntity.hasAmmo(DinocraftItems.VINE_BALL_BUNDLE))
			{				
				if (!world.isRemote)
				{
					player.getCooldownTracker().setCooldown(this, 100);

					Vec3d vector = player.getLookVec();
					double x = player.posX + vector.x - 0.3D;
					double y = player.posY + vector.y + 1.5D;
					double z = player.posZ + vector.z;
				
					for (int i = 0; i < 15; ++i)
					{
						DinocraftEntity.getEntity(player).spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, x, y, z, Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1, 1);
					}
					
					world.playSound(null, player.getPosition(), DinocraftSoundEvents.SHOT, SoundCategory.NEUTRAL, 10.0F, 0.5F);

					EntityVineBall ball1 = new EntityVineBall(player, 0.015F);
					EntityVineBall ball2 = new EntityVineBall(player, 0.015F);
		
					if (player.isCreative())
					{
						EntityVineBall ball = new EntityVineBall(player, 0.015F);
						ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.75F, 1F);
						world.spawnEntity(ball);
					}
					else if (dinoEntity.hasAmmo(DinocraftItems.VINE_BALL))
					{
						EntityVineBall ball = new EntityVineBall(player, 0.015F);
						dinoEntity.consumeAmmo(DinocraftItems.VINE_BALL, 1);
						stack.damageItem(1, player);
						ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.75F, 1F);
						world.spawnEntity(ball);
					} 
					else if (dinoEntity.hasAmmo(DinocraftItems.VINE_BALL_BUNDLE))
					{
						dinoEntity.consumeAmmo(DinocraftItems.VINE_BALL_BUNDLE, 1);
						stack.damageItem(1, player);
						
						for (int k = 0; k < 6; ++k)
						{
							EntityVineBall ball = new EntityVineBall(player, 0.015F);
							ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.5F, 7F);
							world.spawnEntity(ball);
						}
					}
				}
				
				DinocraftEntity.getEntity(player).recoil(0.5F, 20.0F, true);
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (stack != null)
		{
			if (player.isCreative() || dinoEntity.hasAmmo(DinocraftItems.VINE_BALL) || dinoEntity.hasAmmo(DinocraftItems.VINE_BALL_BUNDLE))
			{
				player.setActiveHand(hand);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
			
			return ActionResult.newResult(EnumActionResult.PASS, stack);	
		}
		
		return null;
	}
}