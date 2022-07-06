package dinocraft.item.magatium;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.item.ItemSpellBook;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketFallingCrystalEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemFallenCrystals extends ItemSpellBook
{
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (world.isRemote && living instanceof EntityPlayer)
		{
			RayTraceResult result = DinocraftEntity.getEntityTrace(32.0D);

			if (result != null && result.entityHit != null)
			{
				PacketHandler.sendToServer(new CPacketFallingCrystalEntity(living, result.entityHit, 20));
				stack.damageItem(1, living);
			}
		}
		
		return super.onItemUseFinish(stack, world, living);
	}
}
