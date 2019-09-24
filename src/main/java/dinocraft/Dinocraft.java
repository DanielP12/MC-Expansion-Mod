package dinocraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jline.utils.Log;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.command.CommandAdd;
import dinocraft.command.CommandAsServer;
import dinocraft.command.CommandBounce;
import dinocraft.command.CommandBreak;
import dinocraft.command.CommandBurn;
import dinocraft.command.CommandClearChat;
import dinocraft.command.CommandDurability;
import dinocraft.command.CommandFeed;
import dinocraft.command.CommandFly;
import dinocraft.command.CommandFreeze;
import dinocraft.command.CommandGod;
import dinocraft.command.CommandHeal;
import dinocraft.command.CommandHealth;
import dinocraft.command.CommandJump;
import dinocraft.command.CommandKaboom;
import dinocraft.command.CommandMaxHealth;
import dinocraft.command.CommandPing;
import dinocraft.command.CommandReach;
import dinocraft.command.CommandRegenerate;
import dinocraft.command.CommandSpeed;
import dinocraft.command.CommandStrike;
import dinocraft.command.CommandUnfreeze;
import dinocraft.command.CommandUnvanish;
import dinocraft.command.CommandVanish;
import dinocraft.command.server.CommandForbid;
import dinocraft.command.server.CommandListMutes;
import dinocraft.command.server.CommandListOps;
import dinocraft.command.server.CommandMute;
import dinocraft.command.server.CommandOpLevel;
import dinocraft.command.server.CommandRank;
import dinocraft.command.server.CommandShutDown;
import dinocraft.command.server.CommandSudo;
import dinocraft.command.server.CommandUnforbid;
import dinocraft.command.server.CommandUnmute;
import dinocraft.creativetabs.TabDinocraftBlocks;
import dinocraft.creativetabs.TabDinocraftItems;
import dinocraft.event.DinocraftFunctionEvents;
import dinocraft.handlers.DinocraftRecipes;
import dinocraft.handlers.TradesHandler;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftEntities;
import dinocraft.init.DinocraftItems;
import dinocraft.network.NetworkHandler;
import dinocraft.proxy.ServerProxy;
import dinocraft.util.server.DinocraftPlayerList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "dinocraft", name = Reference.NAME, version = Reference.VERSION)
public class Dinocraft 
{	
	public static final CreativeTabs BLOCKS = new TabDinocraftBlocks();
	public static final CreativeTabs ITEMS = new TabDinocraftItems();
	
	@Instance(Reference.MODID)
	public static Dinocraft instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static ServerProxy PROXY;
	
	@EventHandler
	public void onFMLPreInitialization(FMLPreInitializationEvent event)
	{
		VillagerProfession weaponsmith = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith"));
        weaponsmith.getCareer(1).addTrade(3, new EntityVillager.ITradeList[] {new TradesHandler()});

		DinocraftItems.register();
		DinocraftBlocks.register();
		DinocraftEntities.init();

		PROXY.registerRenders();
		PROXY.preInit(event);
		
		DinocraftCapabilities.registerCapabilities();
	}
	
	@EventHandler
	public void onFMLInitialization(FMLInitializationEvent event)
	{
		PROXY.init();

		DinocraftRecipes.registerFurnaceRecipes();
		NetworkHandler.init();
		MinecraftForge.EVENT_BUS.register(new DinocraftFunctionEvents());
		MinecraftForge.EVENT_BUS.register(new DinocraftPlayerList());
	}
	
	@EventHandler
	public void onFMLPostInitialization(FMLPostInitializationEvent event)
	{

	}
	
	@EventHandler
	public void onFMLServerStopping(FMLServerStoppingEvent event)
	{
		if (event.getSide() == Side.SERVER)
		{
			try
			{
				DinocraftPlayerList.FORBIDDEN_WORDS.writeChanges();
				DinocraftPlayerList.MUTED_PLAYERS.writeChanges();
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onFMLServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandAdd());
		event.registerServerCommand(new CommandAsServer());
		event.registerServerCommand(new CommandBounce());
		event.registerServerCommand(new CommandBreak());
		event.registerServerCommand(new CommandBurn());
		event.registerServerCommand(new CommandDurability());
		event.registerServerCommand(new CommandFeed());
		event.registerServerCommand(new CommandFly());
		event.registerServerCommand(new CommandFreeze());
		event.registerServerCommand(new CommandGod());
		event.registerServerCommand(new CommandHeal());
		event.registerServerCommand(new CommandHealth());
		event.registerServerCommand(new CommandJump());
		event.registerServerCommand(new CommandKaboom());
		event.registerServerCommand(new CommandMaxHealth());
		event.registerServerCommand(new CommandPing());
		event.registerServerCommand(new CommandReach());
		event.registerServerCommand(new CommandRegenerate());
		event.registerServerCommand(new CommandSpeed());
		event.registerServerCommand(new CommandStrike());
		event.registerServerCommand(new CommandUnfreeze());
		event.registerServerCommand(new CommandUnvanish());
		event.registerServerCommand(new CommandVanish());
		event.registerServerCommand(new CommandClearChat());
		
		if (event.getSide() == Side.SERVER && event.getServer().getFile("muted-players.json").exists() && DinocraftPlayerList.MUTED_PLAYERS.getValues() != null && DinocraftPlayerList.MUTED_PLAYERS.getKeys() != null)
		{	
			try
			{
				DinocraftPlayerList.MUTED_PLAYERS.readSavedFile();
			}
			catch (FileNotFoundException exception)
			{
				exception.printStackTrace();
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		
		if (event.getSide() == Side.SERVER && event.getServer().getFile("forbidden-words.json").exists() && DinocraftPlayerList.FORBIDDEN_WORDS.getValues() != null && DinocraftPlayerList.FORBIDDEN_WORDS.getKeys() != null)
		{	
			try
			{
				DinocraftPlayerList.FORBIDDEN_WORDS.readSavedFile();
			}
			catch (FileNotFoundException exception)
			{
				exception.printStackTrace();
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		
		try
		{
			File file = new File("muted-players.json");
			file.setWritable(true);
			file.createNewFile();
		}
		catch (IOException exception)
		{
			Log.info("Error creating file 'muted-players.json'!");
		}
		
		try
		{
			File file = new File("forbidden-words.json");
			file.setWritable(true);
			file.createNewFile();
		}
		catch (IOException exception)
		{
			Log.info("Error creating file 'forbidden-words.json'!");
		}
		
		if (event.getSide() == Side.SERVER)
		{
			event.registerServerCommand(new CommandListMutes());
			event.registerServerCommand(new CommandMute());
			event.registerServerCommand(new CommandOpLevel());
			event.registerServerCommand(new CommandRank());
			event.registerServerCommand(new CommandShutDown());
			event.registerServerCommand(new CommandSudo());
			event.registerServerCommand(new CommandUnmute());
			event.registerServerCommand(new CommandForbid());
			event.registerServerCommand(new CommandUnforbid());
			event.registerServerCommand(new CommandListOps());
		}
	}
}