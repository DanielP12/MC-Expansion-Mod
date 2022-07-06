package dinocraft.item.dremonite;

import dinocraft.entity.EntityDarkSoul;
import dinocraft.init.DinocraftSoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.IRarity;

public class ItemDremoniteSword extends ItemSword
{
	public ItemDremoniteSword(ToolMaterial material)
	{
		super(material);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
	{
		if (!entity.isSwingInProgress && !entity.world.isRemote)
		{
			int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
			float damage = 5.5F;

			if (sharpness > 0)
			{
				damage += 1.0F + (sharpness - 1) * 0.5F;
			}

			EntityDarkSoul soul = new EntityDarkSoul(entity.world, entity, damage);
			soul.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, 0.7F, 0.0F);
			entity.world.spawnEntity(soul);
			entity.world.playSound(null, entity.getPosition(), DinocraftSoundEvents.GHOST_WHISPER, SoundCategory.NEUTRAL, 1.0F, entity.world.rand.nextFloat() * 0.5F + 0.33F);
		}
		
		return super.onEntitySwing(entity, stack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		return true;
	}
	
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}