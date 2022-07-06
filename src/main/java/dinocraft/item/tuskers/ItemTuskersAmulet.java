//package dinocraft.item.tuskers;
//
//import java.util.List;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.init.DinocraftSoundEvents;
//import dinocraft.util.server.DinocraftServer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.settings.GameSettings;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.MobEffects;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.text.TextComponentString;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IRarity;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemTuskersAmulet extends Item
//{
//	public ItemTuskersAmulet()
//	{
//		this.setMaxStackSize(1);
//	}
//
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		ItemStack stack = player.getHeldItem(hand);
//
//		if (!world.isRemote)
//		{
//			if (player.getMaxHealth() < 40.0F)
//			{
//				if (player.getHealth() < 4.0F)
//				{
//					stack.shrink(1);
//					player.addExhaustion(50.0F);
//					world.playSound(null, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
//					world.playSound(null, player.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
//					world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.NEUTRAL, 1.0F, 2.0F);
//					world.playSound(null, player.getPosition(), DinocraftSoundEvents.CHARM, SoundCategory.NEUTRAL, 10.0F, 1.0F);
//					player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000, 4, false, false));
//					player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 1, false, false));
//					player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600, 0, false, false));
//					player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 580, 0, false, false));
//					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 1, false, false));
//					DinocraftEntity.addMaxHealth(player, 2.0F);
//
//					for (int i = 0; i < 20; ++i)
//					{
//						DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, true, player.world, player.posX + world.rand.nextDouble() * 2.0D - 1.0D, player.posY + world.rand.nextDouble() * 0.25D,
//								player.posZ + world.rand.nextDouble() * 2.0D - 1.0D, 0.0D, world.rand.nextDouble() * 0.025D, 0.0D, 1);
//					}
//
//					player.sendMessage(new TextComponentString(TextFormatting.GRAY + "You are cursed by the devils and blessed by the fairies..."));
//					return new ActionResult(EnumActionResult.SUCCESS, stack);
//				}
//				else
//				{
//					player.sendMessage(new TextComponentString(TextFormatting.RED + "You must be weak!"));
//				}
//			}
//			else
//			{
//				player.sendMessage(new TextComponentString(TextFormatting.RED + "You have achieved max health!"));
//			}
//		}
//
//		return new ActionResult(EnumActionResult.FAIL, stack);
//	}
//
//	@Override
//	public IRarity getForgeRarity(ItemStack stack)
//	{
//		return EnumRarity.EPIC;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
//	{
//		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
//		//tooltip.add(TextFormatting.DARK_RED + Dinocraft.lang().localize("tuskers_amulet.tooltip"));
//
//		if (GameSettings.isKeyDown(shift))
//		{
//			tooltip.add(TextFormatting.GRAY + "When used:");
//			tooltip.add(TextFormatting.GRAY + " Increases max health when the player is very weak and healthy");
//		}
//		else
//		{
//			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
//		}
//	}
//}