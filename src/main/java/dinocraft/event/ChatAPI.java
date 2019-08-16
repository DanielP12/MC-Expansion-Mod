/*
@EventBusSubscriber
public class ChatAPI
{
	@SubscribeEvent
	public static void onChat(ServerChatEvent event)
	{
		String msg = event.getMessage();
		
		if (msg.contains(".jump();"))
		{			
			DinocraftPlayer player = DinocraftPlayer.getPlayer(event.getPlayer());
			/*
			if (player.isOpped())
			{
				for (EntityPlayer playerIn : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
				{
					String name = playerIn.getName();
					
					if (msg.equals(name + ".jump();"))
					{
						if (name.equals(msg.substring(0, msg.indexOf("."))))
						{
							event.setCanceled(true);
							NetworkHandler.sendTo(new MessageJump(), (EntityPlayerMP) playerIn);
						}
					}
				}
			}*/
			/*
			if (player.isOpped())
			{
				EntityPlayer playerIn = DinocraftPlayer.getEntityPlayerByName(msg.substring(0, msg.indexOf(".")));
				
				if (msg.equals(playerIn.getName() + ".jump();"))
				{
					event.setCanceled(true);
					NetworkHandler.sendTo(new MessageJump(), (EntityPlayerMP) playerIn);
				}
			}
			else
			{
				event.setCanceled(true);
				player.addChatMessage(TextFormatting.RED + "You do not have permission to do this!");
			}
			*/
			/*
			if (player.isOpped())
			{
				EntityPlayer playerIn = DinocraftPlayer.findEntityPlayerInString(msg);
				
				if (msg.equals(playerIn.getName() + ".jump();"))
				{
					event.setCanceled(true);
					NetworkHandler.sendTo(new MessageJump(), (EntityPlayerMP) playerIn);
				}
			}
			else
			{
				event.setCanceled(true);
				player.addChatMessage(TextFormatting.RED + "You do not have permission to do this!");
			}
		}
	}
}*/