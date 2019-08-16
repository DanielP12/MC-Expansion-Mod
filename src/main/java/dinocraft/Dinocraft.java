package dinocraft;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.command.CommandAsServer;
import dinocraft.command.CommandBoing;
import dinocraft.command.CommandBreak;
import dinocraft.command.CommandFeed;
import dinocraft.command.CommandFly;
import dinocraft.command.CommandGod;
import dinocraft.command.CommandHeal;
import dinocraft.command.CommandHealth;
import dinocraft.command.CommandJump;
import dinocraft.command.CommandKaboom;
import dinocraft.command.CommandMaxHealth;
import dinocraft.command.CommandMoreLess;
import dinocraft.command.CommandPing;
import dinocraft.command.CommandStrike;
import dinocraft.creativetabs.TabDinocraftBlocks;
import dinocraft.creativetabs.TabDinocraftItems;
import dinocraft.handlers.DinocraftSoundEvents;
import dinocraft.handlers.RecipeHandler;
import dinocraft.init.DinocraftArmour;
import dinocraft.init.DinocraftBlocks;
import dinocraft.init.DinocraftItems;
import dinocraft.init.DinocraftTools;
import dinocraft.network.NetworkHandler;
import dinocraft.proxy.ServerProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "dinocraft", name = Reference.NAME, version = Reference.VERSION)
public class Dinocraft 
{
	dinocraft.handlers.EventHandler eventHandler = new dinocraft.handlers.EventHandler();
	
	public static final CreativeTabs BLOCKS = new TabDinocraftBlocks();
	public static final CreativeTabs ITEMS = new TabDinocraftItems();
	
	@Mod.Instance(Reference.MODID)
	public static Dinocraft instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static ServerProxy PROXY;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		DinocraftItems.register();
		DinocraftBlocks.register();
		DinocraftTools.register();
		DinocraftArmour.register();
		//DinocraftAchievements.register();
		
		PROXY.registerRenders();
		PROXY.preInit(event);
		
		DinocraftCapabilities.registerCapabilities();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PROXY.init();
		RecipeHandler.registerCraftingRecipes();
		RecipeHandler.registerFurnaceRecipes();
		MinecraftForge.EVENT_BUS.register(DinocraftSoundEvents.class);
		MinecraftForge.EVENT_BUS.register(RecipeHandler.class);
		NetworkHandler.init();
		eventHandler.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandMaxHealth());
		event.registerServerCommand(new CommandFeed());
		event.registerServerCommand(new CommandHeal());
		event.registerServerCommand(new CommandBoing());
		event.registerServerCommand(new CommandAsServer());
		event.registerServerCommand(new CommandPing());
		event.registerServerCommand(new CommandBreak());
		event.registerServerCommand(new CommandJump());
		event.registerServerCommand(new CommandFly());
		event.registerServerCommand(new CommandKaboom());
		event.registerServerCommand(new CommandGod());
		event.registerServerCommand(new CommandHealth());
		event.registerServerCommand(new CommandMoreLess());
		event.registerServerCommand(new CommandStrike());
	}
}