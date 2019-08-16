package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ItemChlorophyteSword extends ItemSword
{
	public ItemChlorophyteSword(ToolMaterial material, String unlocalizedName)
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (attacker.isSneaking() && target.hurtTime == target.maxHurtTime && target.deathTime <= 0) target.addVelocity(0.0D, 0.5D, 0.0D);
		return super.hitEntity(stack, target, attacker);
	}
	
	/*
	Random rand = new Random();
	EntityFallingBlock block = new EntityFallingBlock(target.world, target.posX, target.posY, target.posZ, target.world.getBlockState(target.getPosition().down()));
	BlockPos pos = target.getPosition();
	block.addVelocity(rand.nextDouble() + 0.5, rand.nextDouble(), rand.nextDouble() + 0.5);
	
	target.world.spawnEntity(block);
	*/
}