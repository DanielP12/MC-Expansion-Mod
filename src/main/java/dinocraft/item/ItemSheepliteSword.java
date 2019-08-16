package dinocraft.item;

import java.util.List;
import java.util.Random;

import dinocraft.Reference;
import dinocraft.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
	public ItemSheepliteSword(ToolMaterial material, String unlocalizedName) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	/** Special Effect: Random Launch */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{		
		if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
		{
			Random random = new Random();
			Vec3d vector = attacker.getLookVec().normalize();
	        target.motionX = vector.x;
	        target.motionY = vector.y + random.nextDouble();
	        target.motionZ = vector.z;	
		}
		return super.hitEntity(stack, target, attacker);
	}
	
	/*
	@Override
    public boolean hitEntity(ItemStack i, EntityLivingBase t, EntityLivingBase a) {
       	if (t.hurtTime == t.maxHurtTime && t.deathTime <= 0) {
       	    double d = t.posX - a.posX;
		    double d1;
	    	for (d1 = t.posZ - a.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D) {
	    		d = (Math.random() - Math.random()) * 0.01D;
    		}
    		t.isAirBorne = true;
    		float f = MathHelper.sqrt_double(d * d + d1 * d1);
        	float f1 = 0.4F;
    	    t.motionX /= 2D;
        	t.motionY /= 2D;
        	t.motionZ /= 2D;
        	t.motionX += (d / (double)f) * (double)f1 * 2;
	    	t.motionY += 0.40000000596046448D;
	    	t.motionZ += (d1 / (double)f) * (double)f1 * 2;
	    	if (t.motionY > 0.40000000596046448D) {
		    	t.motionY = 0.40000000596046448D;
		    }
       	}
        return super.hitEntity(i, t, a);
	} */
	
    @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.DARK_AQUA + Utils.getLang().localize("sheeplite_sword.tooltip"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
