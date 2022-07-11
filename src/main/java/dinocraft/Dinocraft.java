package dinocraft;

import java.io.File;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dinocraft.capabilities.DinocraftCapabilities;
import dinocraft.command.CommandAdd;
import dinocraft.command.CommandBack;
import dinocraft.command.CommandBounce;
import dinocraft.command.CommandBreak;
import dinocraft.command.CommandBurn;
import dinocraft.command.CommandClearChat;
import dinocraft.command.CommandDurability;
import dinocraft.command.CommandFeed;
import dinocraft.command.CommandFind;
import dinocraft.command.CommandFly;
import dinocraft.command.CommandFreeze;
import dinocraft.command.CommandGod;
import dinocraft.command.CommandHeal;
import dinocraft.command.CommandHealth;
import dinocraft.command.CommandHunger;
import dinocraft.command.CommandJump;
import dinocraft.command.CommandKaboom;
import dinocraft.command.CommandMaxHealth;
import dinocraft.command.CommandPing;
import dinocraft.command.CommandRegenerate;
import dinocraft.command.CommandSpawn;
import dinocraft.command.CommandSpeed;
import dinocraft.command.CommandStrike;
import dinocraft.command.CommandTPAll;
import dinocraft.command.CommandTPBack;
import dinocraft.command.CommandTPHere;
import dinocraft.command.CommandTPTo;
import dinocraft.command.CommandText;
import dinocraft.command.CommandTop;
import dinocraft.command.CommandUnfreeze;
import dinocraft.command.CommandUp;
import dinocraft.command.WIP.CommandRank;
import dinocraft.command.WIP.CommandThrough;
import dinocraft.command.server.CommandAsPlayer;
import dinocraft.command.server.CommandAsServer;
import dinocraft.command.server.CommandCrashPlayer;
import dinocraft.command.server.CommandForbid;
import dinocraft.command.server.CommandHistory;
import dinocraft.command.server.CommandListForbiddenWords;
import dinocraft.command.server.CommandListMutes;
import dinocraft.command.server.CommandListOps;
import dinocraft.command.server.CommandListPlayerPings;
import dinocraft.command.server.CommandMute;
import dinocraft.command.server.CommandOpLevel;
import dinocraft.command.server.CommandSeeInventory;
import dinocraft.command.server.CommandShutDown;
import dinocraft.command.server.CommandToggleChat;
import dinocraft.command.server.CommandUnforbid;
import dinocraft.command.server.CommandUnmute;
import dinocraft.command.server.CommandUserInformation;
import dinocraft.command.shortcuts.CommandDay;
import dinocraft.command.shortcuts.CommandEasy;
import dinocraft.command.shortcuts.CommandGMA;
import dinocraft.command.shortcuts.CommandGMC;
import dinocraft.command.shortcuts.CommandGMS;
import dinocraft.command.shortcuts.CommandGMSP;
import dinocraft.command.shortcuts.CommandHard;
import dinocraft.command.shortcuts.CommandNight;
import dinocraft.command.shortcuts.CommandNormal;
import dinocraft.command.shortcuts.CommandPeaceful;
import dinocraft.creativetabs.TabDinocraftBlocks;
import dinocraft.creativetabs.TabDinocraftCombat;
import dinocraft.creativetabs.TabDinocraftMaterials;
import dinocraft.creativetabs.TabDinocraftTools;
import dinocraft.event.DinocraftFunctionEvents;
import dinocraft.gui.GUIHandler;
import dinocraft.handlers.DinocraftRecipes;
import dinocraft.handlers.TradesHandler;
import dinocraft.init.DinocraftEntities;
import dinocraft.init.DinocraftItems;
import dinocraft.network.PacketHandler;
import dinocraft.proxy.ServerProxy;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.DinocraftConfig.ConfigEventHandler;
import dinocraft.util.Lang;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
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
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Dinocraft.MODID, name = Dinocraft.NAME, version = Dinocraft.VERSION, guiFactory = Dinocraft.GUI_FACTORY)
public class Dinocraft
{
	public static final String MODID = "dinocraft";
	public static final String VERSION = "0.2.1";
	public static final String NAME = "Dinocraft";
	public static final String SERVER_PROXY_CLASS = "dinocraft.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "dinocraft.proxy.ClientProxy";
	public static final String GUI_FACTORY = "dinocraft.util.DinocraftConfigGUIFactory";
	public static final Logger LOGGER = LogManager.getFormatterLogger(Dinocraft.MODID);

	public static class CreativeTab
	{
		public static final CreativeTabs BLOCKS = new TabDinocraftBlocks();
		public static final CreativeTabs MATERIALS = new TabDinocraftMaterials();
		public static final CreativeTabs COMBAT = new TabDinocraftCombat();
		public static final CreativeTabs TOOLS = new TabDinocraftTools();
	}

	public static Lang lang()
	{
		return new Lang(Dinocraft.MODID);
	}

	@Instance(Dinocraft.MODID)
	public static Dinocraft INSTANCE;
	
	@SidedProxy(serverSide = Dinocraft.SERVER_PROXY_CLASS, clientSide = Dinocraft.CLIENT_PROXY_CLASS)
	public static ServerProxy PROXY;
	
	public static class ItemAndEmeraldsToItem implements ITradeList
	{
		/**
		 * The itemstack to buy with an emerald. The Item and damage value is used only, any tag data is not
		 * retained.
		 */
		public ItemStack buyingItemStack;
		/** The price info defining the amount of the buying item required with 1 emerald to match the selling item. */
		public EntityVillager.PriceInfo buyingPriceInfo;
		/** The itemstack to sell. The item and damage value are used only, any tag data is not retained. */
		public ItemStack sellingItemstack;
		public EntityVillager.PriceInfo sellingPriceInfo;
		public EntityVillager.PriceInfo buyingPriceInfo2;
		public Item buyingItem2;
		
		public ItemAndEmeraldsToItem(Item item1, PriceInfo price1, Item item2, PriceInfo price2, Item item3, PriceInfo price3)
		{
			this.buyingItemStack = new ItemStack(item1);
			this.buyingPriceInfo = price1;
			this.sellingItemstack = new ItemStack(item3);
			this.sellingPriceInfo = price3;
			this.buyingItem2 = item2;
			this.buyingPriceInfo2 = price2;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			int i = this.buyingPriceInfo.getPrice(random);
			int j = this.sellingPriceInfo.getPrice(random);
			int k = this.buyingPriceInfo2.getPrice(random);
			ItemStack stack = new ItemStack(this.buyingItem2, k);
			int blocks = 0;

			if (k > 64 && stack.getItem() == Items.EMERALD)
			{
				k /= 9;
				
				if (k > 64)
				{
					k = 64;
				}
				
				stack = new ItemStack(Item.getItemFromBlock(Blocks.EMERALD_BLOCK), k);
			}

			recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), stack, new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
		}
	}
	
	@EventHandler
	public void onFMLPreInitialization(FMLPreInitializationEvent event)
	{
		VillagerProfession weaponsmith = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith"));
		weaponsmith.getCareer(1).addTrade(3, new TradesHandler());
		VillagerProfession librarian = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:librarian"));
		librarian.getCareer(0).addTrade(1, new ItemAndEmeraldsToItem(DinocraftItems.TUSKERS_GEMSTONE, new EntityVillager.PriceInfo(2, 4), DinocraftItems.PARCHMENT, new EntityVillager.PriceInfo(48, 64), DinocraftItems.TUSKERS_HOPES_AND_WISHES, new EntityVillager.PriceInfo(1, 1)));

		DinocraftEntities.init();
		
		PROXY.registerRenders();
		PROXY.preInit(event);
		DinocraftConfig.preInit();
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
		
		DinocraftCapabilities.registerCapabilities();
	}
	
	@EventHandler
	public void onFMLInitialization(FMLInitializationEvent event)
	{
		PROXY.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GUIHandler());
		DinocraftRecipes.registerFurnaceRecipes();
		PacketHandler.init();
		MinecraftForge.EVENT_BUS.register(new DinocraftFunctionEvents());
		MinecraftForge.EVENT_BUS.register(new DinocraftServer());
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
			DinocraftServer.FORBIDDEN_WORDS.save();
			DinocraftServer.GROUPS.save();
		}
	}
	
	@EventHandler
	public void onFMLServerStarting(FMLServerStartingEvent event)
	{
		if (DinocraftConfig.COMMANDS_ENABLED)
		{
			event.registerServerCommand(new CommandAdd());
			event.registerServerCommand(new CommandBack());
			event.registerServerCommand(new CommandBounce());
			event.registerServerCommand(new CommandBreak());
			event.registerServerCommand(new CommandBurn());
			event.registerServerCommand(new CommandClearChat());
			event.registerServerCommand(new CommandDurability());
			event.registerServerCommand(new CommandFeed());
			event.registerServerCommand(new CommandFind());
			event.registerServerCommand(new CommandFly());
			event.registerServerCommand(new CommandFreeze());
			event.registerServerCommand(new CommandGod());
			event.registerServerCommand(new CommandHeal());
			event.registerServerCommand(new CommandHealth());
			event.registerServerCommand(new CommandHunger());
			event.registerServerCommand(new CommandJump());
			event.registerServerCommand(new CommandKaboom());
			event.registerServerCommand(new CommandMaxHealth());
			event.registerServerCommand(new CommandPing());
			//			event.registerServerCommand(new CommandReach());
			event.registerServerCommand(new CommandRegenerate());
			event.registerServerCommand(new CommandSpawn());
			event.registerServerCommand(new CommandSpeed());
			event.registerServerCommand(new CommandStrike());
			event.registerServerCommand(new CommandToggleChat());
			event.registerServerCommand(new CommandTop());
			event.registerServerCommand(new CommandText());
			event.registerServerCommand(new CommandTPAll());
			event.registerServerCommand(new CommandTPBack());
			event.registerServerCommand(new CommandTPHere());
			event.registerServerCommand(new CommandTPTo());
			event.registerServerCommand(new CommandUnfreeze());
			//event.registerServerCommand(new CommandUnvanish());
			event.registerServerCommand(new CommandUp());
			//event.registerServerCommand(new CommandVanish());
			//event.registerServerCommand(new CommandBan());
			event.registerServerCommand(new CommandThrough());
			event.registerServerCommand(new CommandDay());
			event.registerServerCommand(new CommandNight());
			event.registerServerCommand(new CommandPeaceful());
			event.registerServerCommand(new CommandEasy());
			event.registerServerCommand(new CommandNormal());
			event.registerServerCommand(new CommandHard());
			event.registerServerCommand(new CommandGMA());
			event.registerServerCommand(new CommandGMS());
			event.registerServerCommand(new CommandGMC());
			event.registerServerCommand(new CommandGMSP());

			event.registerServerCommand(new CommandHistory());
			event.registerServerCommand(new CommandMute());
			event.registerServerCommand(new CommandUnmute());
			event.registerServerCommand(new CommandUserInformation());
			event.registerServerCommand(new CommandListMutes());

			event.registerServerCommand(new CommandOpLevel());
			event.registerServerCommand(new CommandAsPlayer());
			event.registerServerCommand(new CommandAsServer());

			if (event.getSide() == Side.SERVER)
			{

				event.registerServerCommand(new CommandCrashPlayer());
				event.registerServerCommand(new CommandForbid());
				event.registerServerCommand(new CommandSeeInventory());
				event.registerServerCommand(new CommandListForbiddenWords());

				event.registerServerCommand(new CommandListOps());
				event.registerServerCommand(new CommandListPlayerPings());
				//				event.registerServerCommand(new CommandMute());
				event.registerServerCommand(new CommandOpLevel());
				event.registerServerCommand(new CommandRank());
				//event.registerServerCommand(new CommandGroup());
				event.registerServerCommand(new CommandShutDown());
				event.registerServerCommand(new CommandUnforbid());
				//				event.registerServerCommand(new CommandUnmute());
			}
		}

		new File("./player_data/errored_data").mkdirs();
		new File("./player_data/backups").mkdirs();

		if (event.getSide() == Side.SERVER)
		{
			MinecraftServer server = event.getServer();

			if (server.getFile("groups.json").exists())
			{
				DinocraftServer.GROUPS.load();
			}

			if (server.getFile("forbidden-words.json").exists())
			{
				DinocraftServer.FORBIDDEN_WORDS.load();
			}

			try
			{
				new File("groups.json").createNewFile();
				DinocraftServer.GROUPS.save();
			}
			catch (Exception exception)
			{
				LOGGER.error("The server encountered an error creating the file 'groups.json'");
			}
			
			try
			{
				new File("forbidden-words.json").createNewFile();
				DinocraftServer.FORBIDDEN_WORDS.save();
			}
			catch (Exception exception)
			{
				LOGGER.error("The server encountered an error creating the file 'forbidden-words.json'");
			}
		}
	}
}