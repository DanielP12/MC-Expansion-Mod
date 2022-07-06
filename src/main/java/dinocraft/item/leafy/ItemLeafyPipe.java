package dinocraft.item.leafy;

import java.util.List;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.entity.EntitySpikeBall;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftSoundEvents;
import dinocraft.network.PacketHandler;
import dinocraft.network.server.SPacketRecoilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
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

public class ItemLeafyPipe extends Item
{
	public ItemLeafyPipe()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote)
		{
			if (player.isCreative() || DinocraftEntity.hasAmmo(player, DinocraftItems.SPIKE_BALL) || DinocraftEntity.hasAmmo(player, DinocraftItems.SPIKE_BALL_BUNDLE))
			{
				//player.getCooldownTracker().setCooldown(this, 10);
				world.playSound(null, player.getPosition(), DinocraftSoundEvents.SEED_SHOT, SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() + 0.5F);
				
				if (player.isCreative())
				{
					EntitySpikeBall ball = new EntitySpikeBall(player, 0.033F);
					ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.75F, 1F);
					world.spawnEntity(ball);
				}
				else if (DinocraftEntity.hasAmmo(player, DinocraftItems.SPIKE_BALL))
				{
					EntitySpikeBall ball = new EntitySpikeBall(player, 0.033F);
					DinocraftEntity.consumeAmmo(player, DinocraftItems.SPIKE_BALL, 1);
					ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1.75F, 1F);
					world.spawnEntity(ball);
				}
				else if (DinocraftEntity.hasAmmo(player, DinocraftItems.SPIKE_BALL_BUNDLE))
				{
					DinocraftEntity.consumeAmmo(player, DinocraftItems.SPIKE_BALL_BUNDLE, 1);
					
					for (int i = 0; i < 5; ++i)
					{
						EntitySpikeBall ball = new EntitySpikeBall(player, 0.033F);
						ball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 7.0F);
						world.spawnEntity(ball);
					}
				}
				
				PacketHandler.sendTo(new SPacketRecoilPlayer(1.05F), (EntityPlayerMP) player);
				DinocraftEntity.recoil(player, 1.05F);
				stack.damageItem(1, player);
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			
			world.playSound(null, player.getPosition(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.NEUTRAL, 0.5F, 5.0F);
		}
		
		return new ActionResult(EnumActionResult.FAIL, stack);
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
			tooltip.add(TextFormatting.GRAY + "Ammo: " + TextFormatting.RESET + "Spike Ball" + TextFormatting.GRAY + ", " + TextFormatting.RESET + "Spike Ball Bundle");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}
