package dinocraft.network.client;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.init.DinocraftItems;
import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class CPacketDreadedFlight extends AbstractPacket<CPacketDreadedFlight>
{
	private boolean state;

	public CPacketDreadedFlight()
	{
		
	}
	
	public CPacketDreadedFlight(boolean state)
	{
		this.state = state;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.state = buffer.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(this.state);
	}

	@Override
	public void handleClientSide(CPacketDreadedFlight message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(CPacketDreadedFlight message, EntityPlayer player)
	{
		DinocraftEntity.getEntity(player).getActionsModule().setDreadedFlying(message.state);

		if (message.state)
		{
			for (int i = 0; i < 4; i++)
			{
				ItemStack stack = player.inventory.armorInventory.get(i);
				
				if (i == 0)
				{
					stack.damageItem(1, player);
					continue;
				}
				
				if (!stack.isEmpty())
				{
					player.inventory.armorInventory.set(i, ItemStack.EMPTY);
					
					if (!player.inventory.addItemStackToInventory(stack))
					{
						EntityItem entity = new EntityItem(player.world, player.posX, player.posY, player.posZ, stack);
						entity.setOwner(player.getName());
						entity.setNoDespawn();
						entity.setGlowing(true);
						player.world.spawnEntity(entity);
					}
				}
			}

			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 0, true, false));
		}
		else
		{
			player.getCooldownTracker().setCooldown(DinocraftItems.DREMONITE_BOOTS, 120);
			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 0, 1, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 120, 1, false, false));
		}
	}
}