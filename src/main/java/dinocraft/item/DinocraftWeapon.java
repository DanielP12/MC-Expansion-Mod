package dinocraft.item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketReachAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DinocraftWeapon extends ItemSword
{
	/** The speed at which a player can attack using this weapon (Minecraft default: 1.6D) */
	private float attackSpeed = 1.6F;
	/** The range (in blocks) a player has to be from an entity to attack it using this weapon (Minecraft default: 3.0D) */
	private double range = 3.0D;
	/** The damage (in half-hearts) a player inflicts when using this weapon */
	private float attackDamage = 5.0F;

	public DinocraftWeapon(ToolMaterial material, float attackDamage, float attackSpeed, double range)
	{
		super(material);
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.range = range;
	}

	public DinocraftWeapon(ToolMaterial material, float attackSpeed, double range)
	{
		super(material);
		this.attackSpeed = attackSpeed;
		this.range = range;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
		
		if (this.attackSpeed != 1.6D)
		{
			//			if (slot == EntityEquipmentSlot.MAINHAND)
			//			{
			this.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, this.attackSpeed - 4.0D);
			//			}
		}

		if (this.attackDamage != 5.0F)
		{
			//			if (slot == EntityEquipmentSlot.MAINHAND)
			//			{
			this.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, this.attackDamage - 1.0D);
			//			}
		}

		return modifiers;
	}
	
	private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier)
	{
		Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());
		Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if (modifierOptional.isPresent())
		{
			AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier);
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), multiplier, modifier.getOperation()));
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (this.range >= 3.0D)
		{
			return super.onLeftClickEntity(stack, player, entity);
		}
		
		Vec3d pos = player.getPositionEyes(1.0F);
		Vec3d lookvec = player.getLookVec();
		Vec3d vec3d2 = pos.addVector(lookvec.x * this.range, lookvec.y * this.range, lookvec.z * this.range);
		RayTraceResult result = player.world.rayTraceBlocks(pos, vec3d2, false, false, true);
		double calcdist = this.range;
		
		if (result != null && result.typeOfHit == Type.BLOCK)
		{
			calcdist = result.hitVec.distanceTo(pos);
		}
		
		Vec3d vec3d = pos.addVector(lookvec.x * this.range, lookvec.y * this.range, lookvec.z * this.range);
		Entity pointedEntity = null;
		double d = calcdist;
		AxisAlignedBB aabb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
		RayTraceResult result2 = aabb.calculateIntercept(pos, vec3d);
		
		if (aabb.contains(pos))
		{
			if (d >= 0.0D)
			{
				pointedEntity = entity;
				d = 0.0D;
			}
		}
		else if (result2 != null)
		{
			double d1 = pos.distanceTo(result2.hitVec);

			if (d1 < d || d == 0.0D)
			{
				if (entity.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity.canRiderInteract())
				{
					if (d == 0.0D)
					{
						pointedEntity = entity;
					}
				}
				else
				{
					pointedEntity = entity;
					d = d1;
				}
			}
		}

		if (pointedEntity != null && (d < calcdist || result == null))
		{
			return super.onLeftClickEntity(stack, player, entity);
		}
		
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityliving, ItemStack stack)
	{
		if (!(entityliving instanceof EntityPlayer) || this.range <= 3.0D)
		{
			return super.onEntitySwing(entityliving, stack);
		}

		EntityPlayer player = (EntityPlayer) entityliving;
		Vec3d pos = player.getPositionEyes(1.0F);
		Vec3d lookvec = player.getLookVec();
		Vec3d vec3d2 = pos.addVector(lookvec.x * this.range, lookvec.y * this.range, lookvec.z * this.range);
		RayTraceResult result = player.world.rayTraceBlocks(pos, vec3d2, false, false, true);
		double calcdist = this.range;
		
		if (result != null && result.typeOfHit == Type.BLOCK)
		{
			calcdist = result.hitVec.distanceTo(pos);
		}
		
		Vec3d vec3d = pos.addVector(lookvec.x * this.range, lookvec.y * this.range, lookvec.z * this.range);
		Entity pointedEntity = null;
		List<Entity> list = player.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand(lookvec.x * this.range, lookvec.y * this.range, lookvec.z * this.range).grow(1.0F, 1.0F, 1.0F),
				Predicates.and(EntitySelectors.NOT_SPECTATING, entity1 -> entity1 != null && entity1.canBeCollidedWith()));
		double d = calcdist;
		
		for (Entity entity1 : list)
		{
			AxisAlignedBB aabb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
			RayTraceResult result2 = aabb.calculateIntercept(pos, vec3d);
			
			if (aabb.contains(pos))
			{
				if (d >= 0.0D)
				{
					pointedEntity = entity1;
					d = 0.0D;
				}
			}
			else if (result2 != null)
			{
				double d1 = pos.distanceTo(result2.hitVec);
				
				if (d1 < d || d == 0.0D)
				{
					if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity1.canRiderInteract())
					{
						if (d == 0.0D)
						{
							pointedEntity = entity1;
						}
					}
					else
					{
						pointedEntity = entity1;
						d = d1;
					}
				}
			}
		}
		
		if (d <= 3.0D)
		{
			return super.onEntitySwing(entityliving, stack);
		}
		//
		//		if (pointedEntity != null && (d < calcdist || result == null))
		//		{
		//			player.attackTargetEntityWithCurrentItem(pointedEntity);
		//		}
		if (entityliving.world.isRemote)
		{
			RayTraceResult result2 = DinocraftEntity.getEntityTrace(this.range);

			if (result2 != null && result2.entityHit != null)
			{
				PacketHandler.sendToServer(new CPacketReachAttack(result2.entityHit));
			}
		}
		
		return super.onEntitySwing(entityliving, stack);
	}

	public double getRange()
	{
		return this.range;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this.range != 3.0D)
		{
			KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;
			
			if (GameSettings.isKeyDown(shift))
			{
				tooltip.add(TextFormatting.GRAY + "When in main hand: ");
				tooltip.add(" " + this.range + " Attack Reach");

				if (this.range < 3.0D)
				{
					tooltip.add(TextFormatting.DARK_RED + " (-" + (3.0D - this.range) + " blocks)");
				}
				else
				{
					tooltip.add(TextFormatting.DARK_GREEN + " (+" + (this.range - 3.0D) + " blocks)");
				}
			}
			else
			{
				tooltip.add(TextFormatting.GRAY + "Press" + TextFormatting.DARK_GRAY + " [SHIFT] " + TextFormatting.GRAY + "for more!");
			}
		}
	}
}
