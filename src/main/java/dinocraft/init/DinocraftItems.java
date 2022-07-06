package dinocraft.init;

import java.util.ArrayList;
import java.util.List;

import dinocraft.Dinocraft;
import dinocraft.item.ItemAbsorptionHeart;
import dinocraft.item.ItemBleventIngot;
import dinocraft.item.ItemCloudChestplate;
import dinocraft.item.ItemDinocraftFood;
import dinocraft.item.ItemDryHide;
import dinocraft.item.ItemFruitSalad;
import dinocraft.item.ItemGooseberry;
import dinocraft.item.ItemHeart;
import dinocraft.item.ItemMagentite;
import dinocraft.item.ItemMagentiteArmor;
import dinocraft.item.ItemMagentiteAxe;
import dinocraft.item.ItemMagentiteHoe;
import dinocraft.item.ItemMagentitePickaxe;
import dinocraft.item.ItemMagentiteShovel;
import dinocraft.item.ItemMagentiteSickle;
import dinocraft.item.ItemMagentiteSword;
import dinocraft.item.ItemParchment;
import dinocraft.item.ItemPebbles;
import dinocraft.item.ItemPebloneum;
import dinocraft.item.ItemSlingshot;
import dinocraft.item.ItemSoulScratcher;
import dinocraft.item.ItemSpellBook;
import dinocraft.item.ItemSpikeBall;
import dinocraft.item.ItemSpikeBallBundle;
import dinocraft.item.ItemTusk;
import dinocraft.item.arranium.ItemArranium;
import dinocraft.item.arranium.ItemArraniumArmor;
import dinocraft.item.arranium.ItemArraniumAxe;
import dinocraft.item.arranium.ItemArraniumHoe;
import dinocraft.item.arranium.ItemArraniumPickaxe;
import dinocraft.item.arranium.ItemArraniumShovel;
import dinocraft.item.arranium.ItemArraniumSword;
import dinocraft.item.dracolite.ItemDracolite;
import dinocraft.item.dracolite.ItemDracoliteArmor;
import dinocraft.item.dracolite.ItemDracoliteAxe;
import dinocraft.item.dracolite.ItemDracoliteHoe;
import dinocraft.item.dracolite.ItemDracolitePickaxe;
import dinocraft.item.dracolite.ItemDracoliteShovel;
import dinocraft.item.dracolite.ItemDracoliteSword;
import dinocraft.item.dremonite.ItemDreadedEyeTalisman;
import dinocraft.item.dremonite.ItemDreadedEyes;
import dinocraft.item.dremonite.ItemDremoniteBoots;
import dinocraft.item.dremonite.ItemDremoniteIngot;
import dinocraft.item.dremonite.ItemDremoniteShuriken;
import dinocraft.item.dremonite.ItemDremoniteSword;
import dinocraft.item.jesters.ItemBookOfTricks;
import dinocraft.item.jesters.ItemJestersHat;
import dinocraft.item.jesters.ItemJestersIngot;
import dinocraft.item.jesters.ItemJestersSceptre;
import dinocraft.item.jesters.ItemJestersSword;
import dinocraft.item.jesters.ItemMysteriousClock;
import dinocraft.item.leafy.ItemLeaf;
import dinocraft.item.leafy.ItemLeaferang;
import dinocraft.item.leafy.ItemLeafletJar;
import dinocraft.item.leafy.ItemLeafyBoots;
import dinocraft.item.leafy.ItemLeafyDagger;
import dinocraft.item.leafy.ItemLeafyPipe;
import dinocraft.item.leafy.ItemWhirlingOak;
import dinocraft.item.magatium.ItemFallenCrystals;
import dinocraft.item.magatium.ItemMagatiumBoots;
import dinocraft.item.magatium.ItemMagatiumDomain;
import dinocraft.item.magatium.ItemMagatiumScythe;
import dinocraft.item.magatium.ItemMagatiumShard;
import dinocraft.item.magatium.ItemMagatiumStaff;
import dinocraft.item.olitropy.ItemOlitropy;
import dinocraft.item.olitropy.ItemOlitropyArmor;
import dinocraft.item.olitropy.ItemOlitropyAxe;
import dinocraft.item.olitropy.ItemOlitropyHoe;
import dinocraft.item.olitropy.ItemOlitropyPickaxe;
import dinocraft.item.olitropy.ItemOlitropyShovel;
import dinocraft.item.olitropy.ItemOlitropySword;
import dinocraft.item.splicents.ItemChainedThundrivel;
import dinocraft.item.splicents.ItemSplicentsBlade;
import dinocraft.item.splicents.ItemSplicentsChestplate;
import dinocraft.item.splicents.ItemSplicentsDisappointment;
import dinocraft.item.splicents.ItemSplicentsIngot;
import dinocraft.item.splicents.ItemSplicentsThrowingKnife;
import dinocraft.item.tuskers.ItemTuskersBow;
import dinocraft.item.tuskers.ItemTuskersGemstone;
import dinocraft.item.tuskers.ItemTuskersHopesAndWishes;
import dinocraft.item.tuskers.ItemTuskersJug;
import dinocraft.item.tuskers.ItemTuskersOldRags;
import dinocraft.item.tuskers.ItemTuskersSword;
import dinocraft.item.wadronite.ItemWadronite;
import dinocraft.item.wadronite.ItemWadroniteArmor;
import dinocraft.item.wadronite.ItemWadroniteAxe;
import dinocraft.item.wadronite.ItemWadroniteHoe;
import dinocraft.item.wadronite.ItemWadronitePickaxe;
import dinocraft.item.wadronite.ItemWadroniteShovel;
import dinocraft.item.wadronite.ItemWadroniteSword;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class DinocraftItems
{
	public static final List<Item> ITEMS = new ArrayList<>();
	
	public static final Item POTATOSHROOM_PIE = new ItemDinocraftFood(7, 6.0F, false);
	public static final Item GOOSEBERRY = new ItemGooseberry();
	public static final Item FRUIT_SALAD = new ItemFruitSalad();


	public static final Item PEBBLES = new ItemPebbles();
	public static final Item PEBLONEUM = new ItemPebloneum();
	public static final Item OLITROPY = new ItemOlitropy();
	public static final Item WADRONITE = new ItemWadronite();
	public static final Item DRACOLITE = new ItemDracolite();
	public static final Item ARRANIUM = new ItemArranium();
	public static final Item MAGENTITE = new ItemMagentite();
	public static final Item DREMONITE_INGOT = new ItemDremoniteIngot();
	public static final Item BLEVENT_INGOT = new ItemBleventIngot();
	public static final Item JESTERS_INGOT = new ItemJestersIngot();
	public static final Item SPLICENTS_INGOT = new ItemSplicentsIngot();
	public static final Item MAGATIUM_SHARD = new ItemMagatiumShard();
	public static final Item TUSKERS_GEMSTONE = new ItemTuskersGemstone();
	public static final Item LEAF = new ItemLeaf();
	public static final Item TUSK = new ItemTusk();
	public static final Item DRY_HIDE = new ItemDryHide();
	public static final Item PARCHMENT = new ItemParchment();

	public static final Item HEART = new ItemHeart();
	public static final Item ABSORPTION_HEART = new ItemAbsorptionHeart();
	public static final Item DREADED_EYE = new Item();
	public static final Item FALLING_CRYSTAL = new Item();
	public static final Item MAGATIUM_BOLT = new Item();
	public static final Item PEBBLE = new Item();
	public static final Item ELECTRIC_BOLT = new Item();
	public static final Item MAGATIUM_SMALL_SHARD = new Item();


	public static final ItemSword TUSKERS_SWORD = new ItemTuskersSword(ToolMaterial.TUSKERS);
	public static final ItemArmor TUSKERS_OLD_RAGS = new ItemTuskersOldRags(ArmorMaterial.TUSKERS, 2, EntityEquipmentSlot.LEGS);
	public static final Item TUSKERS_JUG = new ItemTuskersJug();
	public static final ItemBow TUSKERS_BOW = new ItemTuskersBow();
	public static final ItemSpellBook TUSKERS_HOPES_AND_WISHES = new ItemTuskersHopesAndWishes();

	public static final ItemSword LEAFY_DAGGER = new ItemLeafyDagger(ToolMaterial.LEAFY);
	public static final ItemArmor LEAFY_BOOTS = new ItemLeafyBoots(ArmorMaterial.LEAFY, 1, EntityEquipmentSlot.FEET);
	public static final Item LEAFLET_JAR = new ItemLeafletJar();
	public static final Item LEAFY_PIPE = new ItemLeafyPipe();
	public static final Item SPIKE_BALL = new ItemSpikeBall();
	public static final Item SPIKE_BALL_BUNDLE = new ItemSpikeBallBundle();
	public static final Item LEAFERANG = new ItemLeaferang();
	public static final ItemSpellBook WHIRLING_OAK = new ItemWhirlingOak();

	public static final ItemSword MAGATIUM_SCYTHE = new ItemMagatiumScythe(ToolMaterial.MAGATIUM);
	public static final ItemArmor MAGATIUM_BOOTS = new ItemMagatiumBoots(ArmorMaterial.MAGATIUM, 1, EntityEquipmentSlot.FEET);
	public static final Item MAGATIUM_DOMAIN = new ItemMagatiumDomain();
	public static final Item MAGATIUM_STAFF = new ItemMagatiumStaff();
	public static final ItemSpellBook FALLEN_CRYSTALS = new ItemFallenCrystals();
	
	public static final ItemSword SPLICENTS_BLADE = new ItemSplicentsBlade(ToolMaterial.SPLICENTS);
	public static final ItemArmor SPLICENTS_CHESTPLATE = new ItemSplicentsChestplate(ArmorMaterial.SPLICENTS, 1, EntityEquipmentSlot.CHEST);
	public static final Item SPLICENTS_DISAPPOINTMENT = new ItemSplicentsDisappointment();
	public static final Item SPLICENTS_THROWING_KNIFE = new ItemSplicentsThrowingKnife();
	public static final ItemSpellBook CHAINED_THUNDRIVEL = new ItemChainedThundrivel();

	public static final ItemSword JESTERS_SWORD = new ItemJestersSword(ToolMaterial.JESTERS);
	public static final ItemArmor JESTERS_HAT = new ItemJestersHat(ArmorMaterial.JESTERS, 1, EntityEquipmentSlot.HEAD);
	public static final Item MYSTERIOUS_CLOCK = new ItemMysteriousClock();
	public static final Item JESTERS_SCEPTRE = new ItemJestersSceptre();
	public static final Item BOOK_OF_TRICKS = new ItemBookOfTricks();

	public static final ItemSword DREMONITE_SWORD = new ItemDremoniteSword(ToolMaterial.DREMONITE);
	public static final ItemArmor DREMONITE_BOOTS = new ItemDremoniteBoots(ArmorMaterial.DREMONITE, 1, EntityEquipmentSlot.FEET);
	public static final Item DREADED_EYE_TALISMAN = new ItemDreadedEyeTalisman();
	public static final Item DREMONITE_SHURIKEN = new ItemDremoniteShuriken();
	public static final ItemSpellBook DREADED_EYES = new ItemDreadedEyes();

	public static final Item SLINGSHOT = new ItemSlingshot();
	public static final ItemArmor CLOUD_CHESTPLATE = new ItemCloudChestplate(ArmorMaterial.CLOUD, 1, EntityEquipmentSlot.CHEST);

	public static final ItemArmor MAGENTITE_HELMET = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor MAGENTITE_CHESTPLATE = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor MAGENTITE_LEGGINGS = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor MAGENTITE_BOOTS = new ItemMagentiteArmor(ArmorMaterial.MAGENTITE, 1, EntityEquipmentSlot.FEET);
	public static final ItemArmor OLITROPY_HELMET = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor OLITROPY_CHESTPLATE = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor OLITROPY_LEGGINGS = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor OLITROPY_BOOTS = new ItemOlitropyArmor(ArmorMaterial.OLITROPY, 1, EntityEquipmentSlot.FEET);
	public static final ItemArmor WADRONITE_HELMET = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor WADRONITE_CHESTPLATE = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor WADRONITE_LEGGINGS = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor WADRONITE_BOOTS = new ItemWadroniteArmor(ArmorMaterial.WADRONITE, 1, EntityEquipmentSlot.FEET);
	public static final ItemArmor DRACOLITE_HELMET = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor DRACOLITE_CHESTPLATE = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor DRACOLITE_LEGGINGS = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor DRACOLITE_BOOTS = new ItemDracoliteArmor(ArmorMaterial.DRACOLITE, 1, EntityEquipmentSlot.FEET);
	public static final ItemArmor ARRANIUM_HELMET = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.HEAD);
	public static final ItemArmor ARRANIUM_CHESTPLATE = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.CHEST);
	public static final ItemArmor ARRANIUM_LEGGINGS = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 2, EntityEquipmentSlot.LEGS);
	public static final ItemArmor ARRANIUM_BOOTS = new ItemArraniumArmor(ArmorMaterial.ARRANIUM, 1, EntityEquipmentSlot.FEET);


	public static final Item MAGENTITE_SHOVEL = new ItemMagentiteShovel(ToolMaterial.MAGENTITE);
	public static final Item MAGENTITE_PICKAXE = new ItemMagentitePickaxe(ToolMaterial.MAGENTITE);
	public static final Item MAGENTITE_AXE = new ItemMagentiteAxe(ToolMaterial.MAGENTITE);
	public static final Item MAGENTITE_HOE = new ItemMagentiteHoe(ToolMaterial.MAGENTITE);
	public static final ItemSword MAGENTITE_SWORD = new ItemMagentiteSword(ToolMaterial.MAGENTITE);
	public static final ItemSword MAGENTITE_SICKLE = new ItemMagentiteSickle(ToolMaterial.MAGENTITE);
	public static final Item OLITROPY_SHOVEL = new ItemOlitropyShovel(ToolMaterial.OLITROPY);
	public static final Item OLITROPY_PICKAXE = new ItemOlitropyPickaxe(ToolMaterial.OLITROPY);
	public static final Item OLITROPY_AXE = new ItemOlitropyAxe(ToolMaterial.OLITROPY);
	public static final Item OLITROPY_HOE = new ItemOlitropyHoe(ToolMaterial.OLITROPY);
	public static final ItemSword OLITROPY_SWORD = new ItemOlitropySword(ToolMaterial.OLITROPY);
	public static final Item WADRONITE_SHOVEL = new ItemWadroniteShovel(ToolMaterial.WADRONITE);
	public static final Item WADRONITE_PICKAXE = new ItemWadronitePickaxe(ToolMaterial.WADRONITE);
	public static final Item WADRONITE_AXE = new ItemWadroniteAxe(ToolMaterial.WADRONITE);
	public static final Item WADRONITE_HOE = new ItemWadroniteHoe(ToolMaterial.WADRONITE);
	public static final ItemSword WADRONITE_SWORD = new ItemWadroniteSword(ToolMaterial.WADRONITE);
	public static final Item DRACOLITE_SHOVEL = new ItemDracoliteShovel(ToolMaterial.DRACOLITE);
	public static final Item DRACOLITE_PICKAXE = new ItemDracolitePickaxe(ToolMaterial.DRACOLITE);
	public static final Item DRACOLITE_AXE = new ItemDracoliteAxe(ToolMaterial.DRACOLITE);
	public static final Item DRACOLITE_HOE = new ItemDracoliteHoe(ToolMaterial.DRACOLITE);
	public static final ItemSword DRACOLITE_SWORD = new ItemDracoliteSword(ToolMaterial.DRACOLITE);
	public static final Item ARRANIUM_SHOVEL = new ItemArraniumShovel(ToolMaterial.ARRANIUM);
	public static final Item ARRANIUM_PICKAXE = new ItemArraniumPickaxe(ToolMaterial.ARRANIUM);
	public static final Item ARRANIUM_AXE = new ItemArraniumAxe(ToolMaterial.ARRANIUM);
	public static final Item ARRANIUM_HOE = new ItemArraniumHoe(ToolMaterial.ARRANIUM);
	public static final ItemSword ARRANIUM_SWORD = new ItemArraniumSword(ToolMaterial.ARRANIUM);
	
	public static final ItemTool SOUL_SCRATCHER = new ItemSoulScratcher();

	private static class ToolMaterial
	{
		public static final Item.ToolMaterial RANIUM = EnumHelper.addToolMaterial(Dinocraft.MODID + ":ranium", 2, 964, 6.5F, 2.5F, 10);
		public static final Item.ToolMaterial MAGENTITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":magentite", 3, 1249, 8.5F, 3.25F, 14);
		public static final Item.ToolMaterial OLITROPY = EnumHelper.addToolMaterial(Dinocraft.MODID + ":olitropy", 3, 1717, 8.25F, 3.0F, 12);
		public static final Item.ToolMaterial WADRONITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":wadronite", 3, 1717, 8.25F, 3.0F, 12);
		public static final Item.ToolMaterial DRACOLITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":dracolite", 3, 1717, 8.25F, 3.0F, 12);
		public static final Item.ToolMaterial ARRANIUM = EnumHelper.addToolMaterial(Dinocraft.MODID + ":arranium", 3, 1717, 8.25F, 3.0F, 12);
		public static final Item.ToolMaterial TUSKERS = EnumHelper.addToolMaterial(Dinocraft.MODID + ":tuskers", 2, 937, 6.0F, 2.25F, 12);
		public static final Item.ToolMaterial LEAFY = EnumHelper.addToolMaterial(Dinocraft.MODID + ":leafy", 2, 781, 6.0F, 2.25F, 8);
		public static final Item.ToolMaterial MAGATIUM = EnumHelper.addToolMaterial(Dinocraft.MODID + ":magatium", 3, 1171, 7.0F, 2.5F, 10);
		public static final Item.ToolMaterial SPLICENTS = EnumHelper.addToolMaterial(Dinocraft.MODID + ":splicents", 2, 828, 6.0F, 2.25F, 10);
		public static final Item.ToolMaterial JESTERS = EnumHelper.addToolMaterial(Dinocraft.MODID + ":jesters", 1, 624, 5.0F, 2.0F, 16);
		public static final Item.ToolMaterial DREMONITE = EnumHelper.addToolMaterial(Dinocraft.MODID + ":dremonite", 1, 468, 4.0F, 1.5F, 14);
	}

	/*								TOOLS/WEAPONS

				HARVEST		DURABILITY	EFFICIENCY	DAMAGE	 		ENCHANTABILITY
				LEVEL								(0F = 4 damage)
		--------------------------------------------------------------------------
		WOOD	(0, 		59, 		2.0F, 		0.0F, 			15);
	    STONE	(1, 		131, 		4.0F, 		1.0F, 			5);
	    IRON	(2, 		250, 		6.0F, 		2.0F, 			14);
	    DIAMOND	(3, 		1561, 		8.0F, 		3.0F, 			10);
	    GOLD	(0, 		32, 		12.0F, 		0.0F, 			22);
	    Bow		(N/A,		384,		N/A,		N/A,			N/A);
	 */

	/*									TOOLS/WEAPONS

					HARVEST		DURABILITY	EFFICIENCY	DAMAGE	 		ENCHANTABILITY
					LEVEL								(0F = 4 damage)
		------------------------------------------------------------------------------
		RANIUM
		OLITROPY	(3, 		1717, 		8.25F, 		3.0F, 			12);
		WADRONITE	(3, 		1717, 		8.25F, 		3.0F, 			12);
		DRACOLITE	(3, 		1717, 		8.25F, 		3.0F, 			12);
		ARRANIUM	(3, 		1717, 		8.25F, 		3.0F, 			12);
		MAGENTITE	(3, 		1249, 		8.5F, 		3.25F, 			14);
		FLARE		(3, 		1171, 		7.0F, 		2.5F, 			10);
		TUSKERS		(2, 		937, 		6.0F, 		2.25F, 			12);
		LEAFY		(2, 		781, 		6.0F, 		2.25F, 			8);
		JESTERS		(1, 		624, 		5.0F, 		2.0F, 			16);
		DREADED		(1, 		468, 		4.0F, 		1.5F, 			14);
	 */


	/*
	 										ARMOR

									  B	  L	  C	  H
		MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};

				DURABILITY	DAMAGE REDUCTION AMOUNTS	ENCHANTABILITY	TOUGHNESS
				FACTOR
		-------------------------------------------------------------------------
		LEATHER	(5, 		new int[] {1, 2, 3, 1}, 	15, 			0.0F);
    	CHAIN	(15, 		new int[] {1, 4, 5, 2}, 	12, 			0.0F);
    	IRON	(15, 		new int[] {2, 5, 6, 2}, 	9, 				0.0F);
    	GOLD	(7, 		new int[] {1, 3, 5, 2}, 	25, 			0.0F);
    	DIAMOND	(33, 		new int[] {3, 6, 8, 3}, 	10, 			2.0F);
	 */

	private static class ArmorMaterial
	{
		private static final ItemArmor.ArmorMaterial RANIUM = EnumHelper.addArmorMaterial("ranium", Dinocraft.MODID + ":ranium", 35, new int[] {2, 6, 7, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		private static final ItemArmor.ArmorMaterial MAGENTITE = EnumHelper.addArmorMaterial("magentite", Dinocraft.MODID + ":magentite", 31, new int[] {3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5F);
		private static final ItemArmor.ArmorMaterial OLITROPY = EnumHelper.addArmorMaterial("olitropy", Dinocraft.MODID + ":olitropy", 36, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		private static final ItemArmor.ArmorMaterial WADRONITE = EnumHelper.addArmorMaterial("wadronite", Dinocraft.MODID + ":wadronite", 36, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		private static final ItemArmor.ArmorMaterial DRACOLITE = EnumHelper.addArmorMaterial("dracolite", Dinocraft.MODID + ":dracolite", 36, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		private static final ItemArmor.ArmorMaterial ARRANIUM = EnumHelper.addArmorMaterial("arranium", Dinocraft.MODID + ":arranium", 36, new int[] {3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		private static final ItemArmor.ArmorMaterial TUSKERS = EnumHelper.addArmorMaterial("tuskers", Dinocraft.MODID + ":tuskers", 30, new int[] {0, 4, 0, 0}, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F);
		private static final ItemArmor.ArmorMaterial LEAFY = EnumHelper.addArmorMaterial("leaf", Dinocraft.MODID + ":leaf", 30, new int[] {2, 0, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
		private static final ItemArmor.ArmorMaterial MAGATIUM = EnumHelper.addArmorMaterial("magatium", Dinocraft.MODID + ":magatium", 30, new int[] {2, 0, 0, 0}, 11, SoundEvents.BLOCK_GLASS_PLACE, 1.0F);
		private static final ItemArmor.ArmorMaterial SPLICENTS = EnumHelper.addArmorMaterial("splicents", Dinocraft.MODID + ":splicents", 30, new int[] {0, 0, 6, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F);
		private static final ItemArmor.ArmorMaterial JESTERS = EnumHelper.addArmorMaterial("jesters", Dinocraft.MODID + ":jesters", 28, new int[] {0, 0, 0, 2}, 18, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
		private static final ItemArmor.ArmorMaterial DREMONITE = EnumHelper.addArmorMaterial("dremonite", Dinocraft.MODID + ":dremonite", 28, new int[] {1, 0, 0, 0}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
		private static final ItemArmor.ArmorMaterial CLOUD = EnumHelper.addArmorMaterial("cloud", Dinocraft.MODID + ":cloud", 28, new int[] {0, 0, 2, 0}, 9, SoundEvents.BLOCK_CLOTH_BREAK, 2.0F);
	}

	/*
												ARMOR

									  B	  L	  C	  H
		MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};

					DURABILITY	DAMAGE REDUCTION AMOUNTS	ENCHANTABILITY	TOUGHNESS
					FACTOR
		-----------------------------------------------------------------------------
		RANIUM
		OLITROPY	(36, 		new int[] {3, 6, 8, 3}, 	12, 			2.0F);
		WADRONITE	(36, 		new int[] {3, 6, 8, 3}, 	12, 			2.0F);
		DRACOLITE	(36, 		new int[] {3, 6, 8, 3}, 	12, 			2.0F);
		ARRANIUM	(36, 		new int[] {3, 6, 8, 3}, 	12, 			2.0F);
		MAGENTITE	(31, 		new int[] {3, 6, 8, 3}, 	15, 			2.5F);
		FLARE		(30, 		new int[] {2, 0, 0, 0}, 	11, 			1.0F);
		TUSKERS		(30, 		new int[] {0, 4, 0, 0}, 	12, 			1.0F);
		LEAFY		(30, 		new int[] {2, 0, 0, 0}, 	9, 				0.0F);
		JESTERS		(28, 		new int[] {0, 0, 0, 2}, 	18, 			1.0F);
		DREADED		(28, 		new int[] {1, 0, 0, 0}, 	14, 			0.0F);
	 */

	@EventBusSubscriber
	public static class RegistrationHandler
	{
		public static Item getUnregisteredItem(Item item, String name, CreativeTabs tab)
		{
			item.setUnlocalizedName(name);
			item.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
			item.setCreativeTab(tab);
			ITEMS.add(item);
			return item;
		}
		
		public static Item getUnregisteredItem(Item item, String name)
		{
			item.setUnlocalizedName(name);
			item.setRegistryName(new ResourceLocation(Dinocraft.MODID, name));
			ITEMS.add(item);
			return item;
		}
		
		@SubscribeEvent
		public static void onEvent(Register<Item> event)
		{
			IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(getUnregisteredItem(POTATOSHROOM_PIE, "potatoshroom_pie"));
			registry.register(getUnregisteredItem(GOOSEBERRY, "gooseberry"));
			registry.register(getUnregisteredItem(FRUIT_SALAD, "fruit_salad"));


			registry.register(getUnregisteredItem(PEBBLES, "pebbles", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(PEBLONEUM, "pebloneum", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(OLITROPY, "olitropy", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(WADRONITE, "wadronite", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(DRACOLITE, "dracolite", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(ARRANIUM, "arranium", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(MAGENTITE, "magentite", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(DREMONITE_INGOT, "dremonite_ingot", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(BLEVENT_INGOT, "blevent_ingot", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(JESTERS_INGOT, "jesters_ingot", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(SPLICENTS_INGOT, "splicents_ingot", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(MAGATIUM_SHARD, "magatium_shard", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(TUSKERS_GEMSTONE, "tuskers_gemstone", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(LEAF, "leaf", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(TUSK, "tusk", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(DRY_HIDE, "dry_hide", Dinocraft.CreativeTab.MATERIALS));
			registry.register(getUnregisteredItem(PARCHMENT, "parchment", Dinocraft.CreativeTab.MATERIALS));

			registry.register(getUnregisteredItem(HEART, "heart"));
			registry.register(getUnregisteredItem(ABSORPTION_HEART, "absorption_heart"));
			registry.register(getUnregisteredItem(DREADED_EYE, "dreaded_eye"));
			registry.register(getUnregisteredItem(FALLING_CRYSTAL, "falling_crystal"));
			registry.register(getUnregisteredItem(MAGATIUM_BOLT, "magatium_bolt"));
			registry.register(getUnregisteredItem(PEBBLE, "pebble"));
			registry.register(getUnregisteredItem(ELECTRIC_BOLT, "electric_bolt"));
			registry.register(getUnregisteredItem(MAGATIUM_SMALL_SHARD, "magatium_small_shard"));
			

			registry.register(getUnregisteredItem(TUSKERS_OLD_RAGS, "tuskers_old_rags", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(LEAFY_BOOTS, "leafy_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGATIUM_BOOTS, "magatium_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPLICENTS_CHESTPLATE, "splicents_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(JESTERS_HAT, "jesters_hat", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DREMONITE_BOOTS, "dremonite_boots", Dinocraft.CreativeTab.COMBAT));

			registry.register(getUnregisteredItem(TUSKERS_SWORD, "tuskers_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(LEAFY_DAGGER, "leafy_dagger", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGATIUM_SCYTHE, "magatium_scythe", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPLICENTS_BLADE, "splicents_blade", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(JESTERS_SWORD, "jesters_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DREMONITE_SWORD, "dremonite_sword", Dinocraft.CreativeTab.COMBAT));
			
			registry.register(getUnregisteredItem(TUSKERS_HOPES_AND_WISHES, "tuskers_hopes_and_wishes", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WHIRLING_OAK, "whirling_oak", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(FALLEN_CRYSTALS, "fallen_crystals", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(CHAINED_THUNDRIVEL, "chained_thundrivel", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(BOOK_OF_TRICKS, "book_of_tricks", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DREADED_EYES, "dreaded_eyes", Dinocraft.CreativeTab.COMBAT));

			registry.register(getUnregisteredItem(TUSKERS_BOW, "tuskers_bow", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(LEAFERANG, "leaferang", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGATIUM_STAFF, "magatium_staff", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPLICENTS_THROWING_KNIFE, "splicents_throwing_knife", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(JESTERS_SCEPTRE, "jesters_sceptre", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DREMONITE_SHURIKEN, "dremonite_shuriken", Dinocraft.CreativeTab.COMBAT));
			
			registry.register(getUnregisteredItem(TUSKERS_JUG, "tuskers_jug", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(LEAFLET_JAR, "leaflet_jar", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(LEAFY_PIPE, "leafy_pipe", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPIKE_BALL, "spike_ball", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPIKE_BALL_BUNDLE, "spike_ball_bundle", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGATIUM_DOMAIN, "magatium_domain", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(SPLICENTS_DISAPPOINTMENT, "splicents_disappointment", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MYSTERIOUS_CLOCK, "mysterious_clock", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DREADED_EYE_TALISMAN, "dreaded_eye_talisman", Dinocraft.CreativeTab.COMBAT));

			registry.register(getUnregisteredItem(SLINGSHOT, "slingshot", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(CLOUD_CHESTPLATE, "cloud_chestplate", Dinocraft.CreativeTab.COMBAT));
			
			registry.register(getUnregisteredItem(MAGENTITE_HELMET, "magentite_helmet", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGENTITE_CHESTPLATE, "magentite_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGENTITE_LEGGINGS, "magentite_leggings", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGENTITE_BOOTS, "magentite_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(OLITROPY_HELMET, "olitropy_helmet", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(OLITROPY_CHESTPLATE, "olitropy_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(OLITROPY_LEGGINGS, "olitropy_leggings", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(OLITROPY_BOOTS, "olitropy_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WADRONITE_HELMET, "wadronite_helmet", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WADRONITE_CHESTPLATE, "wadronite_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WADRONITE_LEGGINGS, "wadronite_leggings", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WADRONITE_BOOTS, "wadronite_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DRACOLITE_HELMET, "dracolite_helmet", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DRACOLITE_CHESTPLATE, "dracolite_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DRACOLITE_LEGGINGS, "dracolite_leggings", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DRACOLITE_BOOTS, "dracolite_boots", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(ARRANIUM_HELMET, "arranium_helmet", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(ARRANIUM_CHESTPLATE, "arranium_chestplate", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(ARRANIUM_LEGGINGS, "arranium_leggings", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(ARRANIUM_BOOTS, "arranium_boots", Dinocraft.CreativeTab.COMBAT));

			
			registry.register(getUnregisteredItem(MAGENTITE_SHOVEL, "magentite_shovel", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(MAGENTITE_PICKAXE, "magentite_pickaxe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(MAGENTITE_AXE, "magentite_axe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(MAGENTITE_HOE, "magentite_hoe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(MAGENTITE_SWORD, "magentite_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(MAGENTITE_SICKLE, "magentite_sickle", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(OLITROPY_SHOVEL, "olitropy_shovel", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(OLITROPY_PICKAXE, "olitropy_pickaxe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(OLITROPY_AXE, "olitropy_axe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(OLITROPY_HOE, "olitropy_hoe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(OLITROPY_SWORD, "olitropy_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(WADRONITE_SHOVEL, "wadronite_shovel", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(WADRONITE_PICKAXE, "wadronite_pickaxe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(WADRONITE_AXE, "wadronite_axe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(WADRONITE_HOE, "wadronite_hoe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(WADRONITE_SWORD, "wadronite_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(DRACOLITE_SHOVEL, "dracolite_shovel", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(DRACOLITE_PICKAXE, "dracolite_pickaxe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(DRACOLITE_AXE, "dracolite_axe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(DRACOLITE_HOE, "dracolite_hoe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(DRACOLITE_SWORD, "dracolite_sword", Dinocraft.CreativeTab.COMBAT));
			registry.register(getUnregisteredItem(ARRANIUM_SHOVEL, "arranium_shovel", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(ARRANIUM_PICKAXE, "arranium_pickaxe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(ARRANIUM_AXE, "arranium_axe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(ARRANIUM_HOE, "arranium_hoe", Dinocraft.CreativeTab.TOOLS));
			registry.register(getUnregisteredItem(ARRANIUM_SWORD, "arranium_sword", Dinocraft.CreativeTab.COMBAT));


			registry.register(getUnregisteredItem(SOUL_SCRATCHER, "soul_scratcher", Dinocraft.CreativeTab.COMBAT));
			Dinocraft.LOGGER.info("Registered all items");
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onModelRegistry(ModelRegistryEvent event)
		{
			for (Item item : ITEMS)
			{
				registerItemModel(item);
			}
			
			Dinocraft.LOGGER.info("Registered all item models");
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onModelBake(ModelBakeEvent event)
		{

		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item)
	{
		registerItemModel(item, 0);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
