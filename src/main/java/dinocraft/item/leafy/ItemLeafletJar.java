package dinocraft.item.leafy;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemLeafletJar extends Item
{
	public ItemLeafletJar()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(742);
	}

	//	@Override
	//	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	//	{
	//		if (!world.isRemote && entity instanceof EntityLivingBase)
	//		{
	//			EntityLivingBase living = (EntityLivingBase) entity;
	//
	//			if (isSelected || living.getHeldItemOffhand().getItem() == this)
	//			{
	//				List<Entity> entities = world.getEntitiesInAABBexcluding(living, living.getEntityBoundingBox().grow(10.0F),
	//						entity1 -> entity1 instanceof EntityMob && entity1.canBeCollidedWith() && ((EntityLivingBase) entity1).canEntityBeSeen(living) && entity1.posY <= living.posY + 1 && entity1.posY >= living.posY - 1);
	//
	//				if (!entities.isEmpty())
	//				{
	//					DinocraftEntity dinoEntity = DinocraftEntity.getEntity(living);
	//					dinoEntity.getTicksModule().leafletTick++;
	//				}
	//
	//			}
	//		}
	//
	//		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	//	}
	//
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}
