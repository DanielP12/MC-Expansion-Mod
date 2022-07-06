package dinocraft.network.server;

import dinocraft.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleItemPickup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketItemPickupEffect extends AbstractPacket<SPacketItemPickupEffect>
{
	private int itemId;
	private int livingId;

	public SPacketItemPickupEffect()
	{

	}

	public SPacketItemPickupEffect(EntityItem item, EntityLivingBase entity)
	{
		this.livingId = entity.getEntityId();
		this.itemId = item.getEntityId();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.livingId);
		buffer.writeInt(this.itemId);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.livingId = buffer.readInt();
		this.itemId = buffer.readInt();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(SPacketItemPickupEffect message, EntityPlayer player)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		WorldClient world = (WorldClient) player.world;
		Entity entity = world.getEntityByID(message.itemId);
		EntityLivingBase living = (EntityLivingBase) world.getEntityByID(message.livingId);

		if (living == null)
		{
			living = minecraft.player;
		}

		if (entity != null)
		{
			if (entity instanceof EntityItem)
			{
				ItemStack stack = ((EntityItem) entity).getItem();
				stack.setCount(stack.getCount());
			}

			minecraft.effectRenderer.addEffect(new ParticleItemPickup(world, entity, living, 0.5F));
			world.removeEntityFromWorld(entity.getEntityId());
		}
	}

	@Override
	public void handleServerSide(SPacketItemPickupEffect message, EntityPlayer player)
	{

	}
}