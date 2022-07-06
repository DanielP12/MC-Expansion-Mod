package dinocraft.item.splicents;

import java.util.List;
import java.util.Random;

import dinocraft.entity.EntityElectricBolt;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSplicentsDisappointment extends Item
{
	private Random rand = new Random();

	public ItemSplicentsDisappointment()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 60;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count)
	{
		if (entity instanceof EntityPlayer && !entity.world.isRemote)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			if (player.getCooldownTracker().getCooldown(this, 0.0F) == 0)
			{
				int j = this.rand.nextInt(10);
				
				if (j == 0)
				{
					entity.setFire(this.rand.nextInt(2) + 1);
					player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.5F, this.rand.nextFloat());
				}
				else if (j == 1)
				{
					player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.NEUTRAL, 0.5F, this.rand.nextFloat() * 0.8F);
				}
				else
				{
					player.world.playSound(null, player.getPosition(), DinocraftSoundEvents.ZAP2, SoundCategory.NEUTRAL, 0.25F, this.rand.nextFloat() + 1.0F);
				}

				if (j > 7)
				{
					stack.damageItem(1, player);
				}

				EntityElectricBolt bolt = new EntityElectricBolt(player, 0.05F);
				Vec3d vector = player.getLookVec().normalize();
				bolt.motionX = vector.x - 0.2D + 0.4D * this.rand.nextDouble();
				bolt.motionY = vector.y - 0.1D + 0.2D * this.rand.nextDouble();
				bolt.motionZ = vector.z - 0.2D + 0.4D * this.rand.nextDouble();
				bolt.velocityChanged = true;
				player.world.spawnEntity(bolt);
				player.getCooldownTracker().setCooldown(this, 5);
			}
		}

		super.onUsingTick(stack, entity, count);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.isHandActive())
		{
			player.setActiveHand(hand);
		}
		
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
	{
		return oldStack != null;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		entity.stopActiveHand();

		if (entity instanceof EntityPlayer)
		{
			((EntityPlayer) entity).getCooldownTracker().setCooldown(this, 60);
		}

		return super.onItemUseFinish(stack, world, entity);
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;

		if (GameSettings.isKeyDown(shift))
		{

		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}

