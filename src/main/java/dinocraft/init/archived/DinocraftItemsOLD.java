package dinocraft.init.archived;

//public class DinocraftItemsOLD
//{
//	public static final List<Item> ITEMS = new ArrayList<>();
//	public static final Item POTATOSHROOM_PIE;
//	public static final Item CHUNKY_FLESH;
//	public static final Item GOOSEBERRY;
//	public static final Item FRUIT_SALAD;
//
//
//	public static final Item BLEVENT_INGOT;
//	public static final Item DREADED_INGOT;
//	public static final Item PYRITE;
//	public static final Item RANIUM;
//	public static final Item OLITROPY;
//	public static final Item WADRONITE;
//	//public static final Item DRACOLITE;
//	public static final Item ARRANIUM;
//	public static final Item MAGENTITE;
//	public static final Item PEBLONEUM;
//	public static final Item PEBBLES;
//	public static final Item LEAF;
//	public static final Item HEART;
//	public static final Item ABSORPTION_HEART;
//	public static final Item PEBBLE;
//	public static final Item FLAME_BALL;
//	public static final Item TUSK;
//	public static final Item TUSKERS_GEMSTONE;
//	public static final Item TUSKERS_AMULET;
//	public static final Item TUSKERS_CHARM;
//	public static final Item TUSKERS_HOPES_AND_WISHES;
//	public static final Item TUSKERS_BOW;
//	public static final Item TUSKERS_THROWING_STAR;
//	public static final Item SPIKE_BALL;
//	public static final Item SPIKE_BALL_BUNDLE;
//	public static final Item FLARE_GUN;
//	public static final Item FLAME_BULLET;
//	public static final Item LEAFY_PIPE;
//	public static final Item PARCHMENT;
//	public static final Item DRY_HIDE;
//	public static final Item SOUL_STAR;
//	public static final Item SHINING_STAR;
//	public static final Item DREADED_SHURIKEN;
//	public static final Item FLAME_SCYTHE;
//	public static final Item HELLISH_CINDERS;
//	public static final Item EMBLEM_OF_THE_DARKSIDE;
//	public static final Item SLINGSHOT;
//	public static final Item JESTERS_INGOT;
//	public static final Item JESTERS_SWORD;
//	public static final Item JESTERS_SCEPTRE;
//	public static final Item BOOK_OF_TRICKS;
//
//	private static class ArmorMaterial
//	{
//		private static final ItemArmor.ArmorMaterial RANIUM = EnumHelper.addArmorMaterial("ranium", Dinocraft.MODID + ":ranium", 35, new int[] {2, 6, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial MAGENTITE = EnumHelper.addArmorMaterial("magentite", Dinocraft.MODID + ":magentite", 35, new int[] {3, 6, 8, 3}, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial OLITROPY = EnumHelper.addArmorMaterial("olitropy", Dinocraft.MODID + ":olitropy", 30, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial WADRONITE = EnumHelper.addArmorMaterial("wadronite", Dinocraft.MODID + ":wadronite", 30, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial DRACOLITE = EnumHelper.addArmorMaterial("dracolite", Dinocraft.MODID + ":dracolite", 30, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial ARRANIUM = EnumHelper.addArmorMaterial("arranium", Dinocraft.MODID + ":arranium", 30, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial DREADED = EnumHelper.addArmorMaterial("dreaded", Dinocraft.MODID + ":dreaded", 30, new int[] {1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial TUSKERS = EnumHelper.addArmorMaterial("tuskers", Dinocraft.MODID + ":tuskers", 36, new int[] {0, 4, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
//		private static final ItemArmor.ArmorMaterial JESTERS = EnumHelper.addArmorMaterial("jesters", Dinocraft.MODID + ":jesters", 30, new int[] {0, 0, 0, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
//		private static final ItemArmor.ArmorMaterial LEAFY = EnumHelper.addArmorMaterial("leaf", Dinocraft.MODID + ":leaf", 30, new int[] {2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
//		private static final ItemArmor.ArmorMaterial CLOUD = EnumHelper.addArmorMaterial("cloud", Dinocraft.MODID + ":cloud", 30, new int[] {0, 0, 2, 0}, 9, SoundEvents.BLOCK_CLOTH_BREAK, 2.0F);
//	}
//
//	public static final ItemArmor RANIUM_HELMET;
//	public static final ItemArmor RANIUM_CHESTPLATE;
//	public static final ItemArmor RANIUM_LEGGINGS;
//	public static final ItemArmor RANIUM_BOOTS;
//	public static final ItemArmor MAGENTITE_HELMET;
//	public static final ItemArmor MAGENTITE_CHESTPLATE;
//	public static final ItemArmor MAGENTITE_LEGGINGS;
//	public static final ItemArmor MAGENTITE_BOOTS;
//
//	public static final ItemArmor OLITROPY_HELMET;
//	public static final ItemArmor OLITROPY_CHESTPLATE;
//	public static final ItemArmor OLITROPY_LEGGINGS;
//	public static final ItemArmor OLITROPY_BOOTS;
//
//	public static final ItemArmor WADRONITE_HELMET;
//	public static final ItemArmor WADRONITE_CHESTPLATE;
//	public static final ItemArmor WADRONITE_LEGGINGS;
//	public static final ItemArmor WADRONITE_BOOTS;
//
//	public static final ItemArmor DRACOLITE_HELMET;
//	public static final ItemArmor DRACOLITE_CHESTPLATE;
//	public static final ItemArmor DRACOLITE_LEGGINGS;
//	public static final ItemArmor DRACOLITE_BOOTS;
//
//	public static final ItemArmor ARRANIUM_HELMET;
//	public static final ItemArmor ARRANIUM_CHESTPLATE;
//	public static final ItemArmor ARRANIUM_LEGGINGS;
//	public static final ItemArmor ARRANIUM_BOOTS;
//
//	public static final ItemArmor LEAFY_BOOTS;
//	public static final ItemArmor DREADED_BOOTS;
//	public static final ItemArmor CLOUD_CHESTPLATE;
//	public static final ItemArmor TUSKERS_OLD_RAGS;
//	public static final ItemArmor JESTERS_HAT;
//
//
//	private static class ToolMaterial
//	{
//		public static final Item.ToolMaterial RANIUM = EnumHelper.addToolMaterial(Dinocraft.MODID + ":ranium", 2, 964, 6.5F, 2.5F, 10);
//		public static final Item.ToolMaterial MAGENTITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":magentite", 3, 1260, 7.5F, 2.5F, 16);
//		public static final Item.ToolMaterial OLITROPY = EnumHelper.addToolMaterial(Dinocraft.MODID + ":olitropy", 3, 964, 8.0F, 2.75F, 14);
//		public static final Item.ToolMaterial WADRONITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":wadronite", 3, 964, 8.0F, 2.75F, 14);
//		public static final Item.ToolMaterial DRACOLITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":dracolite", 3, 964, 8.0F, 2.75F, 14);
//		public static final Item.ToolMaterial ARRANIUM = EnumHelper.addToolMaterial(Dinocraft.MODID + ":arranium", 3, 964, 8.0F, 2.75F, 14);
//		public static final Item.ToolMaterial DREADED = EnumHelper.addToolMaterial(Dinocraft.MODID + ":dreaded", 2, 964, 7.25F, 1.5F, 14);
//		public static final Item.ToolMaterial TUSKERS = EnumHelper.addToolMaterial(Dinocraft.MODID + ":tuskers", 2, 964, 7.0F, 2.0F, 12);
//		public static final Item.ToolMaterial JESTERS = EnumHelper.addToolMaterial(Dinocraft.MODID + ":jesters", 2, 512, 7.0F, 2.0F, 14);
//		public static final Item.ToolMaterial LEAFY = EnumHelper.addToolMaterial(Dinocraft.MODID + ":leafy", 2, 732, 7.3F, 2.5F, 8);
//		public static final Item.ToolMaterial FLAME = EnumHelper.addToolMaterial(Dinocraft.MODID + ":flame", 2, 512, 7.0F, 2.5F, 10);
//		public static final Item.ToolMaterial KATANA = EnumHelper.addToolMaterial(Dinocraft.MODID + ":katana", 2, 512, 7.1F, 2.1F, 10);
//	}
//
//	//	  WOOD(0 /* harvest level */, 59 /* durability */, 2.0F /* efficiency */, 0.0F /* damage (0.0F = 4 attack damage) */, 15 /* enchantability */),
//	//    STONE(1, 131, 4.0F, 1.0F, 5),
//	//    IRON(2, 250, 6.0F, 2.0F, 14),
//	//    DIAMOND(3, 1561, 8.0F, 3.0F, 10),
//	//    GOLD(0, 32, 12.0F, 0.0F, 22);
//
//	public static final ItemPickaxe RANIUM_PICKAXE;
//	public static final ItemAxe RANIUM_AXE;
//	public static final ItemSpade RANIUM_SHOVEL;
//	public static final ItemSword RANIUM_SWORD;
//	public static final ItemPickaxe MAGENTITE_PICKAXE;
//	public static final ItemAxe MAGENTITE_AXE;
//	public static final ItemSpade MAGENTITE_SHOVEL;
//	public static final ItemSword MAGENTITE_SWORD;
//	public static final ItemSword MAGENTITE_SICKLE;
//	public static final ItemHoe MAGENTITE_HOE;
//	public static final ItemPickaxe OLITROPY_PICKAXE;
//	public static final ItemAxe OLITROPY_AXE;
//	public static final ItemSpade OLITROPY_SHOVEL;
//	public static final ItemSword OLITROPY_SWORD;
//	public static final ItemHoe OLITROPY_HOE;
//	public static final ItemPickaxe WADRONITE_PICKAXE;
//	public static final ItemAxe WADRONITE_AXE;
//	public static final ItemSpade WADRONITE_SHOVEL;
//	public static final ItemSword WADRONITE_SWORD;
//	public static final ItemHoe WADRONITE_HOE;
//	public static final ItemPickaxe DRACOLITE_PICKAXE;
//	public static final ItemAxe DRACOLITE_AXE;
//	public static final ItemSpade DRACOLITE_SHOVEL;
//	public static final ItemSword DRACOLITE_SWORD;
//	public static final ItemHoe DRACOLITE_HOE;
//	public static final ItemPickaxe ARRANIUM_PICKAXE;
//	public static final ItemAxe ARRANIUM_AXE;
//	public static final ItemSpade ARRANIUM_SHOVEL;
//	public static final ItemSword ARRANIUM_SWORD;
//	public static final ItemHoe ARRANIUM_HOE;
//	public static final ItemSword DREADED_SWORD;
//	public static final ItemSword TUSKERS_SWORD;
//	public static final ItemTool SOUL_SCRATCHER;
//	public static final ItemSword KATANA;
//	public static final DinocraftWeapon LEAFY_DAGGER;
//	//public static final Item LEAFERANG;
//	public static final Item LEAFY_BOOK;
//
//	static
//	{
//		POTATOSHROOM_PIE = new ItemDinocraftFood("potatoshroom_pie", 7, 6.0F, false);
//		CHUNKY_FLESH = new ItemDinocraftFood("chunky_flesh", 4, 3.0F, false, new PotionEffect(MobEffects.POISON, 200, 4, false, false));
//		GOOSEBERRY = new ItemGooseberry("gooseberry");
//		FRUIT_SALAD = new ItemFruitSalad("fruit_salad");
//
//
//		PEBBLES = new ItemPebbles("pebbles");
//		PEBLONEUM = new ItemPebloneum("pebloneum");
//		RANIUM = new ItemRanium("ranium");
//		OLITROPY = new ItemOlitropy("olitropy");
//		WADRONITE = new ItemWadronite("wadronite");
//		//DRACOLITE = new ItemDracolite("dracolite");
//		ARRANIUM = new ItemArranium("arranium");
//		MAGENTITE = new ItemMagentite("magentite");
//		DREADED_INGOT = new ItemDreadedIngot("dreaded_ingot");
//		BLEVENT_INGOT = new ItemBleventIngot("blevent_ingot");
//		JESTERS_INGOT = new ItemJestersIngot("jesters_ingot");
//		PYRITE = new ItemPyrite("pyrite");
//		TUSKERS_GEMSTONE = new ItemTuskersGemstone("tuskers_gemstone");
//		LEAF = new ItemLeaf("leaf");
//		TUSK = new ItemTusk("tusk");
//		DRY_HIDE = new ItemDryHide("dry_hide");
//		PARCHMENT = new ItemParchment("parchment");
//
//		HEART = new ItemHeart("heart");
//		ABSORPTION_HEART = new ItemAbsorptionHeart("absorption_heart");
//		SHINING_STAR = new ItemShiningStar("shining_star");
//		FLAME_BALL = new ItemFlameBall("flame_ball");
//		PEBBLE = new ItemPebble("pebble");
//
//
//		LEAFY_BOOTS = new ItemLeafyBoots(ArmorMaterial.LEAFY, 1, EntityEquipmentSlot.FEET, "leafy_boots");
//		LEAFY_DAGGER = new ItemLeafyDagger(ToolMaterial.LEAFY, "leafy_dagger");
//		LEAFY_PIPE = new ItemLeafyPipe("leafy_pipe");
//		SPIKE_BALL = new ItemSpikeBall("spike_ball");
//		SPIKE_BALL_BUNDLE = new ItemSpikeBallBundle("spike_ball_bundle");
//		//LEAFERANG = new ItemLeaferang("leaferang");
//		LEAFY_BOOK = new ItemLeafyBook("leafy_book");
//
//		TUSKERS_OLD_RAGS = new ItemTuskersOldRags(ArmorMaterial.TUSKERS, 2, EntityEquipmentSlot.LEGS, "tuskers_old_rags");
//		TUSKERS_SWORD = new ItemTuskersSword(ToolMaterial.TUSKERS, "tuskers_sword");
//		TUSKERS_CHARM = new ItemTuskersCharm("tuskers_charm");
//		TUSKERS_BOW = new ItemTuskersBow("tuskers_bow");
//		TUSKERS_HOPES_AND_WISHES = new ItemTuskersHopesAndWishes("tuskers_hopes_and_wishes");
//		TUSKERS_AMULET = new ItemTuskersAmulet("tuskers_amulet");
//		TUSKERS_THROWING_STAR = new ItemTuskersThrowingStar("tuskers_throwing_star");
//
//		DREADED_BOOTS = new ItemDreadedBoots(ArmorMaterial.DREADED, 1, EntityEquipmentSlot.FEET, "dreaded_boots");
//		DREADED_SWORD = new ItemDreadedSword(ToolMaterial.DREADED, "dreaded_sword");
//		EMBLEM_OF_THE_DARKSIDE = new ItemEmblemOfTheDarkside("emblem_of_the_darkside");
//		DREADED_SHURIKEN = new ItemDreadedShuriken("dreaded_shuriken");
//		SOUL_STAR = new ItemSoulStar("soul_star");
//
//		FLAME_SCYTHE = new ItemFlameScythe(ToolMaterial.FLAME, "flame_scythe");
//		HELLISH_CINDERS = new ItemHellishCinders("hellish_cinders");
//		FLARE_GUN = new ItemFlareGun("flare_gun");
//		FLAME_BULLET = new ItemFlameBullet("flame_bullet");
//
//		JESTERS_HAT = new ItemJestersHat(ArmorMaterial.JESTERS, 1, EntityEquipmentSlot.HEAD, "jesters_hat");
//		JESTERS_SWORD = new ItemJestersSword(ToolMaterial.JESTERS, "jesters_sword");
//		JESTERS_SCEPTRE = new ItemJestersSceptre("jesters_sceptre");
//		BOOK_OF_TRICKS = new ItemBookOfTricks("book_of_tricks");
//
//		SLINGSHOT = new ItemSlingshot("slingshot");
//		CLOUD_CHESTPLATE = new ItemCloudChestplate(ArmorMaterial.CLOUD, 1, EntityEquipmentSlot.CHEST, "cloud_chestplate");
//
//		MAGENTITE_HELMET = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.HEAD, "magentite_helmet");
//		MAGENTITE_CHESTPLATE = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.CHEST, "magentite_chestplate");
//		MAGENTITE_LEGGINGS = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 2, EntityEquipmentSlot.LEGS, "magentite_leggings");
//		MAGENTITE_BOOTS = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.FEET, "magentite_boots");
//		RANIUM_HELMET = new ItemRaniumArmor(ArmorMaterial.RANIUM, 1, EntityEquipmentSlot.HEAD, "ranium_helmet");
//		RANIUM_CHESTPLATE = new ItemRaniumArmor(ArmorMaterial.RANIUM, 1, EntityEquipmentSlot.CHEST, "ranium_chestplate");
//		RANIUM_LEGGINGS = new ItemRaniumArmor(ArmorMaterial.RANIUM, 2, EntityEquipmentSlot.LEGS, "ranium_leggings");
//		RANIUM_BOOTS = new ItemRaniumArmor(ArmorMaterial.RANIUM, 1, EntityEquipmentSlot.FEET, "ranium_boots");
//		OLITROPY_HELMET = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.HEAD, "olitropy_helmet");
//		OLITROPY_CHESTPLATE = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.CHEST, "olitropy_chestplate");
//		OLITROPY_LEGGINGS = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 2, EntityEquipmentSlot.LEGS, "olitropy_leggings");
//		OLITROPY_BOOTS = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.FEET, "olitropy_boots");
//		WADRONITE_HELMET = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.HEAD, "wadronite_helmet");
//		WADRONITE_CHESTPLATE = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.CHEST, "wadronite_chestplate");
//		WADRONITE_LEGGINGS = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 2, EntityEquipmentSlot.LEGS, "wadronite_leggings");
//		WADRONITE_BOOTS = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.FEET, "wadronite_boots");
//		DRACOLITE_HELMET = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.HEAD, "dracolite_helmet");
//		DRACOLITE_CHESTPLATE = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.CHEST, "dracolite_chestplate");
//		DRACOLITE_LEGGINGS = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 2, EntityEquipmentSlot.LEGS, "dracolite_leggings");
//		DRACOLITE_BOOTS = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.FEET, "dracolite_boots");
//		ARRANIUM_HELMET = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.HEAD, "arranium_helmet");
//		ARRANIUM_CHESTPLATE = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.CHEST, "arranium_chestplate");
//		ARRANIUM_LEGGINGS = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 2, EntityEquipmentSlot.LEGS, "arranium_leggings");
//		ARRANIUM_BOOTS = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.FEET, "arranium_boots");
//
//
//		RANIUM_PICKAXE = new ItemRaniumPickaxe(ToolMaterial.RANIUM, "ranium_pickaxe");
//		RANIUM_AXE = new ItemRaniumAxe(ToolMaterial.RANIUM, "ranium_axe");
//		RANIUM_SHOVEL = new ItemRaniumShovel(ToolMaterial.RANIUM, "ranium_shovel");
//		RANIUM_SWORD = new ItemRaniumSword(ToolMaterial.RANIUM, "ranium_sword");
//		MAGENTITE_PICKAXE = new ItemMagentitePickaxe(ToolMaterial.MAGENTITE, "magentite_pickaxe");
//		MAGENTITE_AXE = new ItemMagentiteAxe(ToolMaterial.MAGENTITE, "magentite_axe");
//		MAGENTITE_SHOVEL = new ItemMagentiteShovel(ToolMaterial.MAGENTITE, "magentite_shovel");
//		MAGENTITE_SWORD = new ItemMagentiteSword(ToolMaterial.MAGENTITE, "magentite_sword");
//		MAGENTITE_SICKLE = new ItemMagentiteSickle(ToolMaterial.MAGENTITE, "magentite_sickle");
//		MAGENTITE_HOE = new ItemMagentiteHoe(ToolMaterial.MAGENTITE, "magentite_hoe");
//		OLITROPY_PICKAXE = new ItemOlitropyPickaxe(ToolMaterial.OLITROPY, "olitropy_pickaxe");
//		OLITROPY_AXE = new ItemOlitropyAxe(ToolMaterial.OLITROPY, "olitropy_axe");
//		OLITROPY_SHOVEL = new ItemOlitropyShovel(ToolMaterial.OLITROPY, "olitropy_shovel");
//		OLITROPY_SWORD = new ItemOlitropySword(ToolMaterial.OLITROPY, "olitropy_sword");
//		OLITROPY_HOE = new ItemOlitropyHoe(ToolMaterial.OLITROPY, "olitropy_hoe");
//		WADRONITE_PICKAXE = new ItemWadronitePickaxe(ToolMaterial.WADRONITE, "wadronite_pickaxe");
//		WADRONITE_AXE = new ItemWadroniteAxe(ToolMaterial.WADRONITE, "wadronite_axe");
//		WADRONITE_SHOVEL = new ItemWadroniteShovel(ToolMaterial.WADRONITE, "wadronite_shovel");
//		WADRONITE_SWORD = new ItemWadroniteSword(ToolMaterial.WADRONITE, "wadronite_sword");
//		WADRONITE_HOE = new ItemWadroniteHoe(ToolMaterial.WADRONITE, "wadronite_hoe");
//		DRACOLITE_PICKAXE = new ItemDracolitePickaxe(ToolMaterial.DRACOLITE, "dracolite_pickaxe");
//		DRACOLITE_AXE = new ItemDracoliteAxe(ToolMaterial.DRACOLITE, "dracolite_axe");
//		DRACOLITE_SHOVEL = new ItemDracoliteShovel(ToolMaterial.DRACOLITE, "dracolite_shovel");
//		DRACOLITE_SWORD = new ItemDracoliteSword(ToolMaterial.DRACOLITE, "dracolite_sword");
//		DRACOLITE_HOE = new ItemDracoliteHoe(ToolMaterial.DRACOLITE, "dracolite_hoe");
//		ARRANIUM_PICKAXE = new ItemArraniumPickaxe(ToolMaterial.ARRANIUM, "arranium_pickaxe");
//		ARRANIUM_AXE = new ItemArraniumAxe(ToolMaterial.ARRANIUM, "arranium_axe");
//		ARRANIUM_SHOVEL = new ItemArraniumShovel(ToolMaterial.ARRANIUM, "arranium_shovel");
//		ARRANIUM_SWORD = new ItemArraniumSword(ToolMaterial.ARRANIUM, "arranium_sword");
//		ARRANIUM_HOE = new ItemArraniumHoe(ToolMaterial.ARRANIUM, "arranium_hoe");
//
//		KATANA = new ItemKatana(ToolMaterial.KATANA, "katana");
//		SOUL_SCRATCHER = new ItemSoulScratcher("soul_scratcher");
//	}
//
//
//	public static void register()
//	{
//		registerItem(POTATOSHROOM_PIE);
//		registerItem(CHUNKY_FLESH);
//		registerItem(GOOSEBERRY);
//		registerItem(FRUIT_SALAD);
//
//
//		registerItem(PEBBLES).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(PEBLONEUM).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(RANIUM).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(OLITROPY).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(WADRONITE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		//registerItem(DRACOLITE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(ARRANIUM).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(MAGENTITE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(DREADED_INGOT).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(BLEVENT_INGOT).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(JESTERS_INGOT).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(PYRITE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(TUSKERS_GEMSTONE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(LEAF).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(TUSK).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(DRY_HIDE).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//		registerItem(PARCHMENT).setCreativeTab(Dinocraft.CreativeTabs.MATERIALS);
//
//		registerItem(HEART);
//		registerItem(ABSORPTION_HEART);
//		registerItem(SHINING_STAR);
//		registerItem(FLAME_BALL);
//		registerItem(PEBBLE);
//
//
//		registerItem(LEAFY_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(LEAFY_DAGGER).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(LEAFY_PIPE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(SPIKE_BALL).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(SPIKE_BALL_BUNDLE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		//registerItem(LEAFERANG).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(LEAFY_BOOK).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(TUSKERS_OLD_RAGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_CHARM).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_BOW).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_HOPES_AND_WISHES).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_AMULET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(TUSKERS_THROWING_STAR).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(DREADED_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DREADED_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(EMBLEM_OF_THE_DARKSIDE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DREADED_SHURIKEN).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(SOUL_STAR).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(FLAME_SCYTHE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(FLARE_GUN).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(FLAME_BULLET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(HELLISH_CINDERS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(JESTERS_HAT).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(JESTERS_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(JESTERS_SCEPTRE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(BOOK_OF_TRICKS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(SLINGSHOT).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(CLOUD_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//		registerItem(RANIUM_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(RANIUM_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(RANIUM_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(RANIUM_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(OLITROPY_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(OLITROPY_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(OLITROPY_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(OLITROPY_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(WADRONITE_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(WADRONITE_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(WADRONITE_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(WADRONITE_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DRACOLITE_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DRACOLITE_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DRACOLITE_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DRACOLITE_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(ARRANIUM_HELMET).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(ARRANIUM_CHESTPLATE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(ARRANIUM_LEGGINGS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(ARRANIUM_BOOTS).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//
//
//		registerItem(RANIUM_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(RANIUM_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(RANIUM_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(RANIUM_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(MAGENTITE_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(MAGENTITE_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(MAGENTITE_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_SICKLE).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(MAGENTITE_HOE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(OLITROPY_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(OLITROPY_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(OLITROPY_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(OLITROPY_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(OLITROPY_HOE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(WADRONITE_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(WADRONITE_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(WADRONITE_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(WADRONITE_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(WADRONITE_HOE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(DRACOLITE_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(DRACOLITE_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(DRACOLITE_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(DRACOLITE_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(DRACOLITE_HOE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(ARRANIUM_PICKAXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(ARRANIUM_AXE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(ARRANIUM_SHOVEL).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//		registerItem(ARRANIUM_SWORD).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(ARRANIUM_HOE).setCreativeTab(Dinocraft.CreativeTabs.TOOLS);
//
//
//		registerItem(KATANA).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		registerItem(SOUL_SCRATCHER).setCreativeTab(Dinocraft.CreativeTabs.COMBAT);
//		Dinocraft.log().info("Registered all items");
//	}
//
//	public static void registerRenders()
//	{
//		for (Item item : ITEMS)
//		{
//			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
//		}
//	}
//
//	public static Item registerItem(Item item)
//	{
//		ITEMS.add(item);
//		ForgeRegistries.ITEMS.register(item);
//		return item;
//	}
//}
