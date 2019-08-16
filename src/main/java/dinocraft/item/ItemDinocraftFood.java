package dinocraft.item;

import dinocraft.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemDinocraftFood extends ItemFood 
{
	private PotionEffect[] effects;

	public ItemDinocraftFood(String unlocalizedName, int amount, float saturation, boolean isWolfFood, PotionEffect...potionEffects) 
	{
		super(amount, saturation, isWolfFood);
		this.effects = potionEffects;
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	public ItemDinocraftFood(String unlocalizedName, int amount, boolean isWolfFood, PotionEffect...potionEffects)
	{
		super(amount, isWolfFood);
		this.effects = potionEffects;
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) 
	{
		for (PotionEffect effect : this.effects) 
		{
			player.addPotionEffect(new PotionEffect(effect));
		}
	}
}