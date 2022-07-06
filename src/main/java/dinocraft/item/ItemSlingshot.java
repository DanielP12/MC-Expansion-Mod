package dinocraft.item;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.entity.EntityPebble;
import dinocraft.init.DinocraftItems;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketRecoilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSlingshot extends Item
{
	public ItemSlingshot()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(384);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 16;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
	{
		return oldStack != null;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) living;
			
			if (player.isCreative() || DinocraftEntity.hasAmmo(player, DinocraftItems.PEBBLES))
			{
				stack.damageItem(1, player);

				if (!player.isCreative())
				{
					DinocraftEntity.consumeAmmo(player, DinocraftItems.PEBBLES, 1);
				}
				
				if (!world.isRemote)
				{
					player.getCooldownTracker().setCooldown(this, 12);
					world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
					int j = itemRand.nextInt(3) + 2;
					
					for (int i = 0; i < j; ++i)
					{
						EntityPebble pebble = new EntityPebble(player, 0.033F);
						pebble.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 7.0F);
						world.spawnEntity(pebble);
					}
					
					PacketHandler.sendTo(new SPacketRecoilPlayer(1.05F), (EntityPlayerMP) player);
					DinocraftEntity.recoil(player, 1.05F);
				}
			}
		}
		
		return super.onItemUseFinish(stack, world, living);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if (player.isCreative() || DinocraftEntity.hasAmmo(player, DinocraftItems.PEBBLES))
		{
			player.setActiveHand(hand);
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
		else if (!world.isRemote)
		{
			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
		}
		
		return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
	}
	
	//	@Override
	//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	//	{
	//		ItemStack stack = player.getHeldItem(hand);
	//
	//		if (player.isCreative() || DinocraftEntity.hasAmmo(player, DinocraftItems.PEBBLES))
	//		{
	//			if (!player.isCreative())
	//			{
	//				stack.damageItem(1, player);
	//				DinocraftEntity.consumeAmmo(player, DinocraftItems.PEBBLES, 1);
	//			}
	//
	//			if (!world.isRemote)
	//			{
	//				player.getCooldownTracker().setCooldown(this, 10);
	//				world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
	//				int j = itemRand.nextInt(3) + 2;
	//
	//				for (int i = 0; i < j; ++i)
	//				{
	//					EntityPebble pebble = new EntityPebble(player, 0.033F);
	//					pebble.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 7.0F);
	//					world.spawnEntity(pebble);
	//				}
	//
	//				Packet.sendTo(new SPacketRecoilPlayer(0.005F, 1.05F, true), (EntityPlayerMP) player);
	//				DinocraftEntity.recoil(player, 0.005F, 1.05F, true);
	//			}
	//
	//			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	//		}
	//
	//		if (!world.isRemote)
	//		{
	//			DinocraftEntity.sendActionbarMessage(player, TextFormatting.RED + "Out of ammo!");
	//			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
	//			return ActionResult.newResult(EnumActionResult.FAIL, stack);
	//		}
	//
	//		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	//	}

	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;

		if (GameSettings.isKeyDown(shift))
		{
			tooltip.add(TextFormatting.GRAY + "Ammo: " + TextFormatting.RESET + "Pebbles");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}