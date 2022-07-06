package dinocraft.item.dremonite;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDreadedEyeTalisman extends Item
{
	public ItemDreadedEyeTalisman()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
	}
	
	//	@Override
	//	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	//	{
	//		if (!world.isRemote && entity instanceof EntityPlayer)
	//		{
	//			EntityPlayer player = (EntityPlayer) entity;
	//
	//			if (!player.getCooldownTracker().hasCooldown(this) && (isSelected || player.getHeldItemOffhand().getItem() == this))
	//			{
	//				DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	//
	//				if (dinoEntity != null)
	//				{
	//					DinocraftEntityActions actions = dinoEntity.getActionsModule();
	//
	//					if (!actions.hasTalismanHealth())
	//					{
	//						if (world.rand.nextInt(40) == 0)
	//						{
	//							EntityLivingBase target1 = null;
	//							EntityLivingBase target2 = null;
	//							EntityLivingBase target3 = null;
	//							double range = 12.0D;
	//							List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().grow(range),
	//									entity1 -> entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith()
	//									&& player.getRidingEntity() != entity1 && ((EntityLivingBase) entity1).canEntityBeSeen(player)
	//									&& (((EntityLivingBase) entity1).getRevengeTarget() == player || entity1.isCreatureType(EnumCreatureType.MONSTER, false)));
	//							int size = Math.min(entities.size(), 3);
	//
	//							for (int i = 0; i < size; i++)
	//							{
	//								for (Entity entity1 : entities)
	//								{
	//									double len = new Vec3d(entity1.posX - player.posX, entity1.getEntityBoundingBox().minY + entity1.height / 2.0F - player.posY - player.getEyeHeight(), entity1.posZ - player.posZ).lengthVector();
	//
	//									if (len < range)
	//									{
	//										switch (i)
	//										{
	//											case 0:
	//												target1 = (EntityLivingBase) entity1;
	//												break;
	//											case 1:
	//												target2 = (EntityLivingBase) entity1;
	//												break;
	//											default:
	//												target3 = (EntityLivingBase) entity1;
	//												break;
	//										}
	//
	//										range = len;
	//									}
	//								}
	//
	//								switch (i)
	//								{
	//									case 0:
	//										if (target1 != null)
	//										{
	//											entities.remove(target1);
	//										}
	//
	//										break;
	//									case 1:
	//										if (target2 != null)
	//										{
	//											entities.remove(target2);
	//										}
	//
	//										break;
	//									default:
	//										if (target3 != null)
	//										{
	//											entities.remove(target3);
	//										}
	//
	//										break;
	//								}
	//
	//								range = 12.0D;
	//							}
	//
	//							if (target1 != null)
	//							{
	//								EntityDreadedEyeBolt bolt = new EntityDreadedEyeBolt(world, player, target1);
	//								bolt.shoot(player, world.rand.nextFloat() * 360.0F, world.rand.nextFloat() * 360.0F, 0.0F, itemRand.nextFloat() * 0.5F, 3.0F);
	//								world.spawnEntity(bolt);
	//							}
	//
	//							if (target2 != null)
	//							{
	//								EntityDreadedEyeBolt bolt = new EntityDreadedEyeBolt(world, player, target2);
	//								bolt.shoot(player, world.rand.nextFloat() * 360.0F, world.rand.nextFloat() * 360.0F, 0.0F, itemRand.nextFloat() * 0.5F, 3.0F);
	//								world.spawnEntity(bolt);
	//							}
	//
	//							if (target3 != null)
	//							{
	//								EntityDreadedEyeBolt bolt = new EntityDreadedEyeBolt(world, player, target3);
	//								bolt.shoot(player, world.rand.nextFloat() * 360.0F, world.rand.nextFloat() * 360.0F, 0.0F, itemRand.nextFloat() * 0.5F, 3.0F);
	//								world.spawnEntity(bolt);
	//							}
	//						}
	//					}
	//				}
	//			}
	//		}
	//
	//		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	//	}
	//
	//	@Override
	//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	//	{
	//		if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this))
	//		{
	//			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
	//
	//			if (dinoEntity != null)
	//			{
	//				DinocraftEntityActions actions = dinoEntity.getActionsModule();
	//
	//				if (!actions.hasTalismanHealth())
	//				{
	//					EntityLivingBase[] targets = new EntityLivingBase[3];
	//					double range = 12.0D;
	//					List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().grow(range),
	//							entity1 -> entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith()
	//							&& player.getRidingEntity() != entity1 && ((EntityLivingBase) entity1).canEntityBeSeen(player)
	//							&& (((EntityLivingBase) entity1).getRevengeTarget() == player || entity1.isCreatureType(EnumCreatureType.MONSTER, false)));
	//					int size = Math.min(entities.size(), 3);
	//					int i;
	//
	//					for (i = 0; i < size; i++)
	//					{
	//						for (Entity entity1 : entities)
	//						{
	//							double len = new Vec3d(entity1.posX - player.posX, entity1.getEntityBoundingBox().minY + entity1.height / 2.0F - player.posY - player.getEyeHeight(), entity1.posZ - player.posZ).lengthVector();
	//
	//							if (len < range)
	//							{
	//								targets[i] = (EntityLivingBase) entity1;
	//								range = len;
	//							}
	//						}
	//
	//						entities.remove(targets[i]);
	//						range = 12.0D;
	//					}
	//
	//					i = 0;
	//
	//					while (i < targets.length && targets[i] != null)
	//					{
	//						EntityDreadedEye eye = new EntityDreadedEye(world, player, targets[i++]);
	//						eye.shoot(player, player.rotationPitch - 30 + 60 * world.rand.nextFloat(), player.rotationYaw - 30 + 60 * world.rand.nextFloat(), 0.0F, itemRand.nextFloat() * 0.1F + 0.25F, 3.0F);
	//						world.spawnEntity(eye);
	//
	//						if (i == 1)
	//						{
	//							player.getHeldItem(hand).damageItem(1, player);
	//							player.getCooldownTracker().setCooldown(this, 200);
	//							//							player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat());
	//							player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_HUSK_AMBIENT, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat() * 0.5F);
	//						}
	//					}
	//				}
	//			}
	//		}
	//
	//		return super.onItemRightClick(world, player, hand);
	//	}
	
	//	@Override
	//	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	//	{
	//		if (!world.isRemote && entity instanceof EntityLivingBase)
	//		{
	//			EntityLivingBase living = (EntityLivingBase) entity;
	//
	//			if (isSelected || living.getHeldItemOffhand().getItem() == this)
	//			{
	//				if (!living.isPotionActive(MobEffects.WITHER))
	//				{
	//					living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0, false, true));
	//				}
	//
	//				if (!living.isPotionActive(MobEffects.NAUSEA) || living.isPotionActive(MobEffects.NAUSEA) && living.getActivePotionEffect(MobEffects.NAUSEA).getDuration() <= 30)
	//				{
	//					living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160, 0, false, true));
	//				}
	//
	//				if (!living.isPotionActive(MobEffects.BLINDNESS))
	//				{
	//					living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0, false, true));
	//				}
	//			}
	//		}
	//
	//		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	//	}
	//
	//	@SubscribeEvent
	//	public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	//	{
	//		EntityLivingBase target = event.getTarget();
	//		EntityLivingBase entity = event.getEntityLiving();
	//
	//		if (target != null && entity instanceof EntityLiving && (target.getHeldItemMainhand().getItem() == this || target.getHeldItemOffhand().getItem() == this))
	//		{
	//			((EntityLiving) entity).setAttackTarget(null);
	//		}
	//	}
	
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
			tooltip.add(TextFormatting.GRAY + "When held:");
			tooltip.add(TextFormatting.GRAY + " The player becomes invisible to hostile mobs");
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
		}
	}
}
