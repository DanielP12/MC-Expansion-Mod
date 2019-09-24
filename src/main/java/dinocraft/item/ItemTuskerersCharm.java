package dinocraft.item;

import java.util.List;

import dinocraft.Reference;
import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.capabilities.entity.DinocraftEntityTicks;
import dinocraft.network.MessageRegenerate;
import dinocraft.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersCharm extends Item
{
	public ItemTuskerersCharm(String name)
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);

			if (player.getHeldItemMainhand().getItem() == this || player.getHeldItemOffhand().getItem() == this)
			{
				ItemStack stack = player.getHeldItemMainhand();
				player.setActiveHand(EnumHand.MAIN_HAND);
			
				if (player.world.isRemote)
				{
					if (!dinoEntity.isMoving() && !player.isSprinting())
					{
						DinocraftEntityTicks.setRegenTick(DinocraftEntityTicks.getRegenTick() + 1);
					}
					
					if (dinoEntity.isMoving() || player.isSprinting())
					{
						DinocraftEntityTicks.setRegenTick(0);
						NetworkHandler.sendToServer(new MessageRegenerate(0, 0.0F, 0.0F));
					}
					
					if (DinocraftEntityTicks.getRegenTick() == 60)
					{
						stack.damageItem(1, player);
						NetworkHandler.sendToServer(new MessageRegenerate(1000, 0.5F, 1.0F));
					}
				}
			}
			else if (dinoEntity.isRegenerating())
			{
				dinoEntity.setRegenerating(0, 0.0F, 0.0F);
				DinocraftEntityTicks.setRegenTick(0);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		KeyBinding shift = Minecraft.getMinecraft().gameSettings.keyBindSneak;

        if (GameSettings.isKeyDown(shift))
        {
        	tooltip.add(TextFormatting.GRAY + "Regenerates the player after three seconds of standing still");
        }
        else
        {
        	tooltip.add(TextFormatting.GRAY + "Press " + TextFormatting.DARK_GRAY + "[SHIFT] " + TextFormatting.GRAY + "for more!");
        }
	}
}
