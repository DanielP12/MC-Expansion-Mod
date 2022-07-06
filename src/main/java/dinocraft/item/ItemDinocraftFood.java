package dinocraft.item;

import dinocraft.Dinocraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemDinocraftFood extends ItemFood
{
	private PotionEffect[] effects;
	
	public ItemDinocraftFood(int amount, float saturation, boolean isWolfFood, PotionEffect... potionEffects)
	{
		super(amount, saturation, isWolfFood);
		this.effects = potionEffects;
	}

	public ItemDinocraftFood(String name, int amount, boolean isWolfFood, PotionEffect... potionEffects)
	{
		super(amount, isWolfFood);
		this.effects = potionEffects;
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		for (PotionEffect effect : this.effects)
		{
			player.addPotionEffect(new PotionEffect(effect));
		}

		super.onFoodEaten(stack, world, player);
	}
}