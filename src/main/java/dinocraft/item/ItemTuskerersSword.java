package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.init.DinocraftItems;
import dinocraft.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersSword extends ItemSword
{
	public ItemTuskerersSword(ToolMaterial material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));		
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (!target.world.isRemote && target.world.rand.nextInt(3) < 1)
		{
		    EntityItem heart = new EntityItem(target.world, target.posX, target.posY, target.posZ, new ItemStack(DinocraftItems.HEART, 1));
			heart.setOwner(attacker.getUniqueID().toString());
			heart.addTag(attacker.getUniqueID().toString());
			target.world.spawnEntity(heart);
		}
		
		return super.hitEntity(stack, target, attacker);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) 
	{
		tooltip.add(TextFormatting.GRAY + Utils.getLang().localize("tuskerers_sword.tooltip"));
		super.addInformation(stack, world, tooltip, flag);
	}

    /*
	@Override
	public float getReach() {
		// TODO Auto-generated method stub
		return 10.0F;
	}
	*/

    /*
    @Override
	public boolean onEntitySwing(EntityLivingBase entityliving, ItemStack stack)
	{
		if (!(entityliving instanceof EntityPlayer))
		{
			return false;
		}

		EntityPlayer player = (EntityPlayer) entityliving;
		Vec3d vector = player.getLookVec();
		List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().grow(10.0D));

		Entity found = null;
		double foundLen = 0.0D;

		for (Object o : entities)
		{
			if (o == player)
			{
				continue;
			}

			Entity ent = (Entity) o;

			if (!ent.canBeCollidedWith())
			{
				continue;
			}

			Vec3d vec = new Vec3d(ent.posX - player.posX, ent.getEntityBoundingBox().minY + ent.height / 2.0F - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);
			double len = vec.lengthVector();

			if (len > 10.0F)
			{
				continue;
			}

			vec = vec.normalize();
			double dot = vector.dotProduct(vec);

			if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent))
			{
				continue;
			}

			if (foundLen == 0.0 || len < foundLen)
			{
				found = ent;
				foundLen = len;
			}
		}

		if (found != null && player.getRidingEntity() != found)
		{
			stack.attemptDamageItem(1, player.world.rand, null);
			player.attackTargetEntityWithCurrentItem(found);
		}

		return false;
	}
    
	
	/* Event fired when an item is crafted */
	/*
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event)
	{
		if (event.crafting.getItem() == DinocraftTools.TUSKERERS_SWORD)
		{
			EntityPlayer playerIn = event.player;
			
			if (!playerIn.hasAchievement(DinocraftAchievements.OUT_OF_PURE_TUSK))
			{
				playerIn.addStat(DinocraftAchievements.OUT_OF_PURE_TUSK);
			}
			
		}
	}
	*/
}