package dinocraft.event;

import dinocraft.capabilities.entity.DinocraftEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ChatAPI
{
	private static void sendPlayerNotFound(EntityPlayer player, String name)
	{
		player.sendMessage(new TextComponentString(TextFormatting.RED + "Player '" + name + "' cannot be found"));
	}

	@SubscribeEvent
	public static void onChat(ServerChatEvent event)
	{
		EntityPlayerMP player = event.getPlayer();
		String msg = event.getMessage();
		DinocraftEntity dinoEntity = DinocraftEntity.getEntity(player);
		
		if (dinoEntity.hasOpLevel(4) && player.getServer().isDedicatedServer())
		{
			if (msg.contains(".jump();"))
			{
				String name = msg.substring(0, msg.indexOf("."));
				EntityPlayer target = DinocraftEntity.getEntityPlayerByName(name);
				event.setCanceled(true);

				if (target != null && msg.equalsIgnoreCase(target.getName() + ".jump();"))
				{
					target.jump();
				}
				else
				{
					sendPlayerNotFound(player, name);
				}
			}
			else if (msg.contains(".heal();"))
			{
				String name = msg.substring(0, msg.indexOf("."));
				EntityPlayer target = DinocraftEntity.getEntityPlayerByName(name);
				event.setCanceled(true);
				
				if (target != null && msg.equalsIgnoreCase(target.getName() + ".heal();"))
				{
					target.heal(target.getMaxHealth());
				}
				else
				{
					sendPlayerNotFound(player, name);
				}
			}
			else if (msg.contains(".watch();"))
			{
				String name = msg.substring(0, msg.indexOf("."));
				EntityPlayer target = DinocraftEntity.getEntityPlayerByName(name);
				event.setCanceled(true);
				
				if (target != null && msg.equalsIgnoreCase(target.getName() + ".watch();"))
				{
					player.attemptTeleport(target.posX, target.posY, target.posZ);
				}
				else
				{
					sendPlayerNotFound(player, name);
				}
			}
		}
	}
}