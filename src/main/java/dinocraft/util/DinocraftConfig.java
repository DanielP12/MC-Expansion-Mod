package dinocraft.util;

import java.io.File;

import dinocraft.Dinocraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DinocraftConfig
{
	private static Configuration config = null;
	public static final String COMMANDS = "commands";
	public static final String ENTITIES = "entities";
	public static final String MISCELLANEOUS = "miscellaneous";
	public static final String MUTES = "mutes";
	public static final String BANS = "bans";
	public static final String PLAYER = "player";

	public static boolean COMMANDS_ENABLED;
	public static int PERMISSION_LEVEL_ASPLAYER;
	public static int PERMISSION_LEVEL_ASSERVER;
	public static int PERMISSION_LEVEL_BAN;
	public static int PERMISSION_LEVEL_BAN_FULL;
	public static int PERMISSION_LEVEL_FORBID;
	public static int PERMISSION_LEVEL_INVENTORY;
	public static int PERMISSION_LEVEL_FORBIDDENLIST;
	public static int PERMISSION_LEVEL_MUTELIST;
	public static int PERMISSION_LEVEL_OPLIST;
	public static int PERMISSION_LEVEL_PINGLIST;
	public static int PERMISSION_LEVEL_MUTE;
	public static int PERMISSION_LEVEL_MUTE_FULL;
	public static int PERMISSION_LEVEL_HISTORY;
	public static int PERMISSION_LEVEL_HISTORY_FULL;
	public static int PERMISSION_LEVEL_OPLEVEL;
	public static int PERMISSION_LEVEL_RANK;
	public static int PERMISSION_LEVEL_SHUTDOWN;
	public static int PERMISSION_LEVEL_TOGGLECHAT;
	public static int PERMISSION_LEVEL_UNFORBID;
	public static int PERMISSION_LEVEL_UNMUTE;
	public static int PERMISSION_LEVEL_USER_INFORMATION;
	public static int PERMISSION_LEVEL_ADD;
	public static int PERMISSION_LEVEL_BACK;
	public static int PERMISSION_LEVEL_BOUNCE;
	public static int PERMISSION_LEVEL_BREAK;
	public static int PERMISSION_LEVEL_BURN;
	public static int PERMISSION_LEVEL_CLEARCHAT;
	public static int PERMISSION_LEVEL_DURABILITY;
	public static int PERMISSION_LEVEL_FEED;
	public static int PERMISSION_LEVEL_FIND;
	public static int PERMISSION_LEVEL_FLY;
	public static int PERMISSION_LEVEL_FREEZE;
	public static int PERMISSION_LEVEL_GOD;
	public static int PERMISSION_LEVEL_HEAL;
	public static int PERMISSION_LEVEL_HEALTH;
	public static int PERMISSION_LEVEL_HUNGER;
	public static int PERMISSION_LEVEL_JUMP;
	public static int PERMISSION_LEVEL_KABOOM;
	public static int PERMISSION_LEVEL_MAXHEALTH;
	public static int PERMISSION_LEVEL_PING;
	public static int PERMISSION_LEVEL_REACH;
	public static int PERMISSION_LEVEL_REGENERATE;
	public static int PERMISSION_LEVEL_SPAWN;
	public static int PERMISSION_LEVEL_SPEED;
	public static int PERMISSION_LEVEL_STRIKE;
	public static int PERMISSION_LEVEL_TEXT;
	public static int PERMISSION_LEVEL_TOP;
	public static int PERMISSION_LEVEL_TPALL;
	public static int PERMISSION_LEVEL_TPBACK;
	public static int PERMISSION_LEVEL_TPHERE;
	public static int PERMISSION_LEVEL_TPTO;
	public static int PERMISSION_LEVEL_UNFREEZE;
	public static int PERMISSION_LEVEL_UNVANISH;
	public static int PERMISSION_LEVEL_UP;
	public static int PERMISSION_LEVEL_VANISH;

	public static boolean WORLD_SAVING;
	public static int WORLD_SAVING_INTERVAL;
	public static boolean WEAPON_RECOIL;
	public static String[] FORBIDDEN_ITEMS;

	public static int MUTE_DECAY_TIME;

	public static String BAN_REASON1;
	public static String BAN_REPLACEMENT1;
	public static String BAN_CATEGORY1;
	public static int BAN_STANDINGS1;
	public static String BAN_REASON2;
	public static String BAN_REPLACEMENT2;
	public static String BAN_CATEGORY2;
	public static int BAN_STANDINGS2;
	public static String BAN_REASON3;
	public static String BAN_REPLACEMENT3;
	public static String BAN_CATEGORY3;
	public static int BAN_STANDINGS3;
	public static String BAN_REASON4;
	public static String BAN_REPLACEMENT4;
	public static String BAN_CATEGORY4;
	public static int BAN_STANDINGS4;
	public static String BAN_REASON5;
	public static String BAN_REPLACEMENT5;
	public static String BAN_CATEGORY5;
	public static int BAN_STANDINGS5;
	public static String BAN_REASON6;
	public static String BAN_REPLACEMENT6;
	public static String BAN_CATEGORY6;
	public static int BAN_STANDINGS6;
	public static String BAN_REASON7;
	public static String BAN_REPLACEMENT7;
	public static String BAN_CATEGORY7;
	public static int BAN_STANDINGS7;
	public static String BAN_REASON8;
	public static String BAN_REPLACEMENT8;
	public static String BAN_CATEGORY8;
	public static int BAN_STANDINGS8;
	public static String BAN_REASON9;
	public static String BAN_REPLACEMENT9;
	public static String BAN_CATEGORY9;
	public static int BAN_STANDINGS9;
	public static String BAN_REASON10;
	public static String BAN_REPLACEMENT10;
	public static String BAN_CATEGORY10;
	public static int BAN_STANDINGS10;
	public static String BAN_REASON11;
	public static String BAN_REPLACEMENT11;
	public static String BAN_CATEGORY11;
	public static int BAN_STANDINGS11;
	public static String BAN_REASON12;
	public static String BAN_REPLACEMENT12;
	public static String BAN_CATEGORY12;
	public static int BAN_STANDINGS12;
	public static String BAN_REASON13;
	public static String BAN_REPLACEMENT13;
	public static String BAN_CATEGORY13;
	public static int BAN_STANDINGS13;
	public static String BAN_REASON14;
	public static String BAN_REPLACEMENT14;
	public static String BAN_CATEGORY14;
	public static int BAN_STANDINGS14;
	public static String BAN_REASON15;
	public static String BAN_REPLACEMENT15;
	public static String BAN_CATEGORY15;
	public static int BAN_STANDINGS15;
	public static String BAN_REASON16;
	public static String BAN_REPLACEMENT16;
	public static String BAN_CATEGORY16;
	public static int BAN_STANDINGS16;
	public static String BAN_REASON17;
	public static String BAN_REPLACEMENT17;
	public static String BAN_CATEGORY17;
	public static int BAN_STANDINGS17;
	public static String BAN_REASON18;
	public static String BAN_REPLACEMENT18;
	public static String BAN_CATEGORY18;
	public static int BAN_STANDINGS18;
	public static String BAN_REASON19;
	public static String BAN_REPLACEMENT19;
	public static String BAN_CATEGORY19;
	public static int BAN_STANDINGS19;
	public static String BAN_REASON20;
	public static String BAN_REPLACEMENT20;
	public static String BAN_CATEGORY20;
	public static int BAN_STANDINGS20;

	public static String BAN_TIME1;
	public static String BAN_TIME2;
	public static String BAN_TIME3;
	public static String BAN_TIME4;
	public static String BAN_TIME5;
	public static String BAN_TIME6;
	public static String BAN_TIME7;
	public static String BAN_TIME8;
	public static String BAN_TIME9;
	public static String BAN_TIME10;
	public static String BAN_TIME11;
	public static String BAN_TIME12;
	public static String BAN_TIME13;
	public static String BAN_TIME14;
	public static String BAN_TIME15;
	public static String BAN_TIME16;
	public static String BAN_TIME17;
	public static String BAN_TIME18;
	public static String BAN_TIME19;
	public static String BAN_TIME20;
	public static String BAN_TIME_MAX;
	public static int BAN_LEVEL_MAX;

	public static boolean ENTITY_ITEMS;
	public static boolean ENTITY_BLOOD_EFFECTS;
	public static boolean PLAYER_BLOOD_EFFECTS;
	public static String[] MUTE_CODES;
	public static String[] MUTE_REASONS;
	public static String[] MUTE_CATEGORIES;
	public static String[] MUTE_STANDINGS;
	public static String[] MUTE_TIMES;

	public static String[] BAN_CODES;
	public static String[] BAN_REASONS;
	public static String[] BAN_CATEGORIES;
	public static String[] BAN_STANDINGS;
	public static String[] BAN_TIMES;

	public static void preInit()
	{
		File saveFile = new File(Loader.instance().getConfigDir(), "Dinocraft.cfg");
		config = new Configuration(saveFile);
		syncFromFiles();
	}

	public static Configuration getConfig()
	{
		return config;
	}

	public static void syncFromFiles()
	{
		syncConfig(true, true);
	}

	public static void syncFromGUI()
	{
		syncConfig(false, true);
	}

	public static void syncFromFields()
	{
		syncConfig(false, false);
	}

	private static void syncConfig(boolean load, boolean read)
	{
		if (load)
		{
			config.load();
		}

		COMMANDS_ENABLED = config.getBoolean("Commands Enabled", COMMANDS, true, "Whether mod commands are enabled or not.");

		//1: PLAYER; 2: HELPER; 3: MOD; 4: ADMIN; 5: OWNER
		config.setCategoryComment(COMMANDS, "For each command, specify the permission level necessary to run the command. [range: 0 ~ 20]");
		Property asPlayer = config.get(COMMANDS, "'asPlayer' Command Permission Level", 4, "", 0, 20);
		Property asServer = config.get(COMMANDS, "'asServer' Command Permission Level", 4, "", 0, 20);
		Property ban = config.get(COMMANDS, "'ban' Command Permission Level", 3, "", 0, 20);
		Property banFull = config.get(COMMANDS, "'ban' Full Command Permission Level", 4, "", 0, 20);
		Property forbid = config.get(COMMANDS, "'forbid' Command Permission Level", 4, "", 0, 20);
		Property inventory = config.get(COMMANDS, "'inventory' Command Permission Level", 4, "", 0, 20);
		Property forbiddenlist = config.get(COMMANDS, "'forbiddenlist' Command Permission Level", 4, "", 0, 20);
		Property mutelist = config.get(COMMANDS, "'mutelist' Command Permission Level", 4, "", 0, 20);
		Property oplist = config.get(COMMANDS, "'oplist' Command Permission Level", 4, "", 0, 20);
		Property pinglist = config.get(COMMANDS, "'pinglist' Command Permission Level", 3, "", 0, 20);
		Property mute = config.get(COMMANDS, "'mute' Command Permission Level", 2, "", 0, 20);
		Property muteFull = config.get(COMMANDS, "'mute' Full Command Permission Level", 4, "", 0, 20);
		Property history = config.get(COMMANDS, "'history' Command Permission Level", 3, "", 0, 20);
		Property historyFull = config.get(COMMANDS, "'history' Full Command Permission Level", 4, "", 0, 20);
		Property oplevel = config.get(COMMANDS, "'oplevel' Command Permission Level", 4, "", 0, 20);
		Property rank = config.get(COMMANDS, "'rank' Command Permission Level", 4, "", 0, 20);
		Property shutdown = config.get(COMMANDS, "'shutdown' Command Permission Level", 4, "", 0, 20);
		Property togglechat = config.get(COMMANDS, "'togglechat' Command Permission Level", 3, "", 0, 20);
		Property unforbid = config.get(COMMANDS, "'unforbid' Command Permission Level", 4, "", 0, 20);
		Property unmute = config.get(COMMANDS, "'unmute' Command Permission Level", 2, "", 0, 20);
		Property userinfo = config.get(COMMANDS, "'userinfo' Command Permission Level", 2, "", 0, 20);

		Property add = config.get(COMMANDS, "'add' Command Permission Level", 4, "", 0, 20);
		Property back = config.get(COMMANDS, "'back' Command Permission Level", 3, "", 0, 20);
		Property bounce = config.get(COMMANDS, "'bounce' Command Permission Level", 4, "", 0, 20);
		Property break1 = config.get(COMMANDS, "'break' Command Permission Level", 4, "", 0, 20);
		Property burn = config.get(COMMANDS, "'burn' Command Permission Level", 4, "", 0, 20);
		Property clearchat = config.get(COMMANDS, "'clearchat' Command Permission Level", 3, "", 0, 20);
		Property durability = config.get(COMMANDS, "'durability' Command Permission Level", 4, "", 0, 20);
		Property feed = config.get(COMMANDS, "'feed' Command Permission Level", 4, "", 0, 20);
		Property find = config.get(COMMANDS, "'find' Command Permission Level", 2, "", 0, 20);
		Property fly = config.get(COMMANDS, "'fly' Command Permission Level", 3, "", 0, 20);
		Property freeze = config.get(COMMANDS, "'freeze' Command Permission Level", 3, "", 0, 20);
		Property god = config.get(COMMANDS, "'god' Command Permission Level", 4, "", 0, 20);
		Property heal = config.get(COMMANDS, "'heal' Command Permission Level", 4, "", 0, 20);
		Property health = config.get(COMMANDS, "'health' Command Permission Level", 4, "", 0, 20);
		Property hunger = config.get(COMMANDS, "'hunger' Command Permission Level", 4, "", 0, 20);
		Property jump = config.get(COMMANDS, "'jump' Command Permission Level", 3, "", 0, 20);
		Property kaboom = config.get(COMMANDS, "'kaboom' Command Permission Level", 4, "", 0, 20);
		Property maxhealth = config.get(COMMANDS, "'maxhealth' Command Permission Level", 4, "", 0, 20);
		Property ping = config.get(COMMANDS, "'ping' Command Permission Level", 2, "", 0, 20);
		Property reach = config.get(COMMANDS, "'reach' Command Permission Level", 4, "", 0, 20);
		Property regenerate = config.get(COMMANDS, "'regenerate' Command Permission Level", 4, "", 0, 20);
		Property spawn = config.get(COMMANDS, "'spawn' Command Permission Level", 4, "", 0, 20);
		Property speed = config.get(COMMANDS, "'speed' Command Permission Level", 4, "", 0, 20);
		Property strike = config.get(COMMANDS, "'strike' Command Permission Level", 4, "", 0, 20);
		Property text = config.get(COMMANDS, "'text' Command Permission Level", 4, "", 0, 20);
		Property top = config.get(COMMANDS, "'top' Command Permission Level", 3, "", 0, 20);
		Property tpall = config.get(COMMANDS, "'tpall' Command Permission Level", 4, "", 0, 20);
		Property tpback = config.get(COMMANDS, "'tpback' Command Permission Level", 3, "", 0, 20);
		Property tphere = config.get(COMMANDS, "'tphere' Command Permission Level", 3, "", 0, 20);
		Property tpto = config.get(COMMANDS, "'tpto' Command Permission Level", 3, "", 0, 20);
		Property unfreeze = config.get(COMMANDS, "'unfreeze' Command Permission Level", 3, "", 0, 20);
		Property unvanish = config.get(COMMANDS, "'unvanish' Command Permission Level", 3, "", 0, 20);
		Property up = config.get(COMMANDS, "'up' Command Permission Level", 3, "", 0, 20);
		Property vanish = config.get(COMMANDS, "'vanish' Command Permission Level", 3, "", 0, 20);

		FORBIDDEN_ITEMS = config.getStringList("Pebbeloneum Forbidden Items", MISCELLANEOUS, new String[] {"minecraft:nether_star", "minecraft:dragon_egg",
				"minecraft:end_portal_frame", "minecraft:end_portal_frame", "minecraft:end_crystal", "minecraft:spawn_egg", "minecraft:barrier",
				"minecraft:air", "minecraft:mob_spawner", "minecraft:structure_block", "minecraft:structure_void", "minecraft:command_block",
				"minecraft:chain_command_block", "minecraft:repeating_command_block", "minecraft:command_block_minecart", "minecraft:bedrock"}, "Items that cannot come out of Pebbeloneum");

		ENTITY_ITEMS = config.getBoolean("Entities With Mod Items", ENTITIES, true, "Whether mobs are able to spawn with mod items and armor.");
		ENTITY_BLOOD_EFFECTS = config.getBoolean("Entity Blood Effects", ENTITIES, true, "Whether entities should shed blood when they die.");
		PLAYER_BLOOD_EFFECTS = config.getBoolean("Player Blood Effects", ENTITIES, true, "Whether players should shed blood when hit.");

		WORLD_SAVING = config.getBoolean("World Saving", MISCELLANEOUS, true, "Whether the server saves the world. [only works on a server]");
		WORLD_SAVING_INTERVAL = config.getInt("World Saving Interval", MISCELLANEOUS, 600, 1, 2147483647, "How often the server will save the world (seconds). [only works on a server]");
		WEAPON_RECOIL = config.getBoolean("Weapon Recoil", MISCELLANEOUS, true, "Whether weapons inflict recoil when used.");
		
		//TODO: Add ability to input "NEVER"
		MUTE_DECAY_TIME = config.getInt("Mute Decay Time", MUTES, 30, 0, 2147483647, "The time (in days) after a punishment expires that a user must wait until their punishment level decreases by 1");

		MUTE_CODES = config.getStringList("Mute Codes", MUTES, new String[] {"MA", "PS", "SP", "MI", "RU", "US", "ES", "EC1", "UI1", "IC1", "DI", "UD", "NR"}, "All mute codes");
		MUTE_CATEGORIES = config.getStringList("Mute Categories", MUTES, new String[] {"Media Advertising", "Public Shaming", "Excessive Spamming", "Misleading Information",
				"Rude", "Unnecessary Spoilers", "Excessive/Targetted Swearing", "Encouraging Cheating 1", "Unintentional/Intentional Distress 1", "Inappropriate Content 1",
				"Discrimination", "User Disrespect", "Negative Reference"}, "All mute categories");
		MUTE_REASONS = config.getStringList("Mute Reasons", MUTES, new String[] {"Advertising social media.", "Publicly revealing information about a player.", "Repeatedly posting unnecessary messages or content.",
				"Misleading other players to carry out actions that disrupts their game.", "Being rude or inappropriate.", "Giving spoilers, revealing important storylines of popular movies or TV shows.",
				"Excessive use of swearing in chat.", "Discussing or actively promoting cheating or rule-breaking on the server.", "Causing unintentional/intentional distress.", "Using inappropriate concepts on the server.",
				"Discrimination of a player or group of people.", "Acting in a manner that is disrespectful to members within the community.", "Discussing important people or world events in a negative way."}, "All mute reasons");
		MUTE_STANDINGS = config.getStringList("Mute Standings", MUTES, new String[] {"1", "1", "1", "1", "1", "1", "2", "2", "2", "3", "3", "4", "4"}, "All mute standings. Note: each number must be greater than 0");
		MUTE_TIMES = config.getStringList("Mute Times", MUTES, new String[] {"WARNING", "1d", "3d", "7d", "14d", "30d", "90d"}, "All mute times");



		BAN_CODES = config.getStringList("Ban Codes", BANS, new String[] {"CT", "TG", "IB", "IN", "IU", "SI", "SC", "EC2", "EC3", "EUD", "IA", "IC2", "BM", "WD", "SB", "UI2", "PL", "AS", "ASA", "SA", "EX", "FI"}, "All ban codes");
		BAN_CATEGORIES = config.getStringList("Ban Categories", BANS, new String[] {"Cross Teaming", "Team Griefing", "Inappropriate Build/Drawing", "Inappropriate Item Name",
				"Inappropriate Item Usage", "Staff/YouTuber Impersonation", "Scamming", "Encouraging Cheating 2", "Encouraging Cheating 3", "Extreme User Disrespect", "Inappropriate Skin/Cape", "Inappropriate Content 2",
				"Blacklisted Modifications", "Watchdog", "Boosting", "Unintentional/Intentional Distress 2", "Phishing Links", "Account Selling", "Account Security Alert", "Secured Account", "Exploiting", "Fake/Inaccurate Information"}, "All ban categories");
		BAN_REASONS = config.getStringList("Ban Reasons", BANS, new String[] {"Cross teaming, working with another team or player.", "Negatively affecting your fellow team members.", "Creating a build or drawing which is not appropriate on the server.",
				"Creating or using an item that has an inappropriate name.", "Using pets or cosmetics in an inappropriate way.", "Misleading others to believe you are a YouTuber or staff member.", "Attempting to obtain information or something of value from players.",
				"Discussing or acting in a manner which encourages cheating or rule-breaking.", "Discussing or acting in a manner which encourages cheating or rule-breaking.", "Acting in a manner that is extremely disrespectful to members within the community.",
				"Using inappropriate skins or capes on the server.", "Talking about or sharing inappropriate content with explicit themes on the server.", "Cheating through the use of unfair game advantages.", "WATCHDOG CHEAT DETECTION",
				"Boosting your account to improve your stats.", "Causing unintentional/intentional distress.", "Attempting to gain access to other users' accounts/information.", "Attempting to sell Minecraft accounts.", "Your account has a security alert, please secure it and contact appeals.",
				"Your account's security appeal was processed and the account has entered a recovery phase and will be able to access the server again after 30 days. Use this time to change passwords, emails, and security questions.",
				"Exploiting a bug or issue within the server and using it to your advantage.", "Making or sharing fake information."}, "All ban reasons");
		BAN_STANDINGS = config.getStringList("Ban Standings", BANS, new String[] {"1", "1", "1", "1", "1", "1", "2", "2", "4", "2", "2", "2", "3", "3", "3", "3", "4", "4", "-1", "3", "4", "3"}, "All ban standings");
		BAN_TIMES = config.getStringList("Ban Times", BANS, new String[] {"7d", "14d", "30d", "90d", "180d", "360d", "PERMANENT"}, "All ban times");

		if (read)
		{
			PERMISSION_LEVEL_ASSERVER = asServer.getInt();
			PERMISSION_LEVEL_ASPLAYER = asPlayer.getInt();
			PERMISSION_LEVEL_BAN = ban.getInt();
			PERMISSION_LEVEL_BAN_FULL = banFull.getInt();
			PERMISSION_LEVEL_FORBID = forbid.getInt();
			PERMISSION_LEVEL_INVENTORY = inventory.getInt();
			PERMISSION_LEVEL_FORBIDDENLIST = forbiddenlist.getInt();
			PERMISSION_LEVEL_MUTELIST = mutelist.getInt();
			PERMISSION_LEVEL_OPLIST = oplist.getInt();
			PERMISSION_LEVEL_PINGLIST = pinglist.getInt();
			PERMISSION_LEVEL_MUTE = mute.getInt();
			PERMISSION_LEVEL_MUTE_FULL = muteFull.getInt();
			PERMISSION_LEVEL_HISTORY = history.getInt();
			PERMISSION_LEVEL_HISTORY_FULL = historyFull.getInt();
			PERMISSION_LEVEL_OPLEVEL = oplevel.getInt();
			PERMISSION_LEVEL_RANK = rank.getInt();
			PERMISSION_LEVEL_SHUTDOWN = shutdown.getInt();
			PERMISSION_LEVEL_TOGGLECHAT = togglechat.getInt();
			PERMISSION_LEVEL_UNFORBID = unforbid.getInt();
			PERMISSION_LEVEL_UNMUTE = unmute.getInt();
			PERMISSION_LEVEL_USER_INFORMATION = userinfo.getInt();
			PERMISSION_LEVEL_ADD = add.getInt();
			PERMISSION_LEVEL_BACK = back.getInt();
			PERMISSION_LEVEL_BOUNCE = bounce.getInt();
			PERMISSION_LEVEL_BREAK = break1.getInt();
			PERMISSION_LEVEL_BURN = burn.getInt();
			PERMISSION_LEVEL_CLEARCHAT = clearchat.getInt();
			PERMISSION_LEVEL_DURABILITY = durability.getInt();
			PERMISSION_LEVEL_FEED = feed.getInt();
			PERMISSION_LEVEL_FIND = find.getInt();
			PERMISSION_LEVEL_FLY = fly.getInt();
			PERMISSION_LEVEL_FREEZE = freeze.getInt();
			PERMISSION_LEVEL_GOD = god.getInt();
			PERMISSION_LEVEL_HEAL = heal.getInt();
			PERMISSION_LEVEL_HEALTH = health.getInt();
			PERMISSION_LEVEL_HUNGER = hunger.getInt();
			PERMISSION_LEVEL_JUMP = jump.getInt();
			PERMISSION_LEVEL_KABOOM = kaboom.getInt();
			PERMISSION_LEVEL_MAXHEALTH = maxhealth.getInt();
			PERMISSION_LEVEL_PING = ping.getInt();
			PERMISSION_LEVEL_REACH = reach.getInt();
			PERMISSION_LEVEL_REGENERATE = regenerate.getInt();
			PERMISSION_LEVEL_SPAWN = spawn.getInt();
			PERMISSION_LEVEL_SPEED = speed.getInt();
			PERMISSION_LEVEL_STRIKE = strike.getInt();
			PERMISSION_LEVEL_TEXT = text.getInt();
			PERMISSION_LEVEL_TOP = top.getInt();
			PERMISSION_LEVEL_TPALL = tpall.getInt();
			PERMISSION_LEVEL_TPBACK = tpback.getInt();
			PERMISSION_LEVEL_TPHERE = tphere.getInt();
			PERMISSION_LEVEL_TPTO = tpto.getInt();
			PERMISSION_LEVEL_UNFREEZE = unfreeze.getInt();
			PERMISSION_LEVEL_UNVANISH = unvanish.getInt();
			PERMISSION_LEVEL_UP = up.getInt();
			PERMISSION_LEVEL_VANISH = vanish.getInt();
		}

		if (config.hasChanged())
		{
			config.save();
		}
	}

	@EventBusSubscriber
	public static class ConfigEventHandler
	{
		@SubscribeEvent
		public static void onConfigChanged(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(Dinocraft.MODID))
			{
				syncFromGUI();
			}
		}
	}
}
