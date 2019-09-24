package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSheepliteSword extends ItemSword 
{
	public ItemSheepliteSword(ToolMaterial material, String name) 
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{		
		if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
		{
			Vec3d vector = attacker.getLookVec().normalize();
	        target.motionX = vector.x;
	        target.motionY = vector.y + target.world.rand.nextDouble();
	        target.motionZ = vector.z;	
		}
		
		return super.hitEntity(stack, target, attacker);
	}
	
	/*
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
    {
       	if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0) 
       	{
       	    double d = target.posX - attacker.posX;
		    double d1;
	    	
	    	for (d1 = target.posZ - attacker.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
	    	{
	    		d = (Math.random() - Math.random()) * 0.01D;
    		}
    		
    		target.isAirBorne = true;
    		float f = MathHelper.sqrt_double(d * d + d1 * d1);
        	float f1 = 0.4F;
    	    target.motionX /= 2D;
        	target.motionY /= 2D;
        	target.motionZ /= 2D;
        	target.motionX += (d / (double) f) * (double) f1 * 2;
	    	target.motionY += 0.40000000596046448D;
	    	target.motionZ += (d1 / (double) f) * (double) f1 * 2;
	    	
	    	if (target.motionY > 0.40000000596046448D)
	    	 {
		    	target.motionY = 0.40000000596046448D;
		    }
       	}
       	
        return super.hitEntity(stack, target, attacker);
	} */
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.DARK_AQUA + Utils.getLang().localize("sheeplite_sword.tooltip"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}