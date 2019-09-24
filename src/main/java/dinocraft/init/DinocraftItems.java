package dinocraft.init;

import java.util.ArrayList;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.item.ItemBleventBoots;
import dinocraft.item.ItemBleventIngot;
import dinocraft.item.ItemChlorophyteArmor;
import dinocraft.item.ItemChlorophyteHoe;
import dinocraft.item.ItemChlorophytePickaxe;
import dinocraft.item.ItemChlorophyteShovel;
import dinocraft.item.ItemChlorophyteSword;
import dinocraft.item.ItemClorophyteIngot;
import dinocraft.item.ItemCloudChestplate;
import dinocraft.item.ItemCrackedPebbles;
import dinocraft.item.ItemDinocraftFood;
import dinocraft.item.ItemFeatheryUnderwear;
import dinocraft.item.ItemHeart;
import dinocraft.item.ItemKatana;
import dinocraft.item.ItemLeaf;
import dinocraft.item.ItemLeaferang;
import dinocraft.item.ItemLeafyBoots;
import dinocraft.item.ItemMerchantsLuckyBoots;
import dinocraft.item.ItemPebbeloneum;
import dinocraft.item.ItemPebbles;
import dinocraft.item.ItemRanium;
import dinocraft.item.ItemRaniumArmor;
import dinocraft.item.ItemRaniumHammer;
import dinocraft.item.ItemRayBullet;
import dinocraft.item.ItemRayGun;
import dinocraft.item.ItemSeed;
import dinocraft.item.ItemSeedPipe;
import dinocraft.item.ItemSheepliteIngot;
import dinocraft.item.ItemSheepliteSword;
import dinocraft.item.ItemSlingshot;
import dinocraft.item.ItemSoulScratcher;
import dinocraft.item.ItemTusk;
import dinocraft.item.ItemTuskerersAmulet;
import dinocraft.item.ItemTuskerersCharm;
import dinocraft.item.ItemTuskerersGemstone;
import dinocraft.item.ItemTuskerersOldRags;
import dinocraft.item.ItemTuskerersSword;
import dinocraft.item.ItemUmbrellaHat;
import dinocraft.item.ItemVineBall;
import dinocraft.item.ItemVineBallBundle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftItems 
{
	public static final ArrayList<Item> ITEMS = new ArrayList<Item>();
	public static final Item POTATOSHROOM_PIE;
	public static final Item CHUNKY_FLESH;
	public static final Item BLEVENT_INGOT;
	public static final Item CHLOROPHYTE_INGOT;
	public static final Item SHEEPLITE_INGOT;
	public static final Item RANIUM;
	public static final Item PEBBELONEUM;
	public static final Item PEBBLES;
	public static final Item CRACKED_PEBBLES;
	public static final Item LEAF;
	public static final Item HEART;
	public static final Item TUSK;
	public static final Item TUSKERERS_GEMSTONE;
	public static final Item TUSKERERS_AMULET;
	public static final Item TUSKERERS_CHARM;
	public static final Item SLINGSHOT;
	public static final Item VINE_BALL;
	public static final Item VINE_BALL_BUNDLE;
	public static final Item RAY_GUN;
	public static final Item RAY_BULLET;
	public static final Item SEED_PIPE;
	public static final Item SEED;
	
	private static final ArmorMaterial CHLOROPHYTE_MATERIAL = EnumHelper.addArmorMaterial("chlorophyte", Reference.MODID + ":chlorophyte", 35, new int[] {3,6,8,3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	private static final ArmorMaterial RANIUM_MATERIAL = EnumHelper.addArmorMaterial("ranium", Reference.MODID + ":ranium", 35, new int[] {3,6,8,3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	private static final ArmorMaterial LEAF_MATERIAL = EnumHelper.addArmorMaterial("leaf", Reference.MODID + ":leaf", 30, new int[] {2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	private static final ArmorMaterial BLEVENT_MATERIAL = EnumHelper.addArmorMaterial("blevent", Reference.MODID + ":blevent", 30, new int[] {1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	private static final ArmorMaterial UMBRELLA_MATERIAL = EnumHelper.addArmorMaterial("umbrella", Reference.MODID + ":umbrella", 30, new int[] {0, 0, 0, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	private static final ArmorMaterial CLOUD_MATERIAL = EnumHelper.addArmorMaterial("cloud", Reference.MODID + ":cloud", 30, new int[] {0, 0, 2, 0}, 9, SoundEvents.BLOCK_CLOTH_BREAK, 2.0F);
	private static final ArmorMaterial TUSKERER_MATERIAL = EnumHelper.addArmorMaterial("tuskerer", Reference.MODID + ":tuskerer", 36, new int[] {0, 4, 0, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	private static final ArmorMaterial UNDERWEAR_MATERIAL = EnumHelper.addArmorMaterial("underwear", Reference.MODID + ":underwear", 30, new int[] {0,0,1,0}, 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F);
	private static final ArmorMaterial MERCHANT_MATERIAL = EnumHelper.addArmorMaterial("merchant", Reference.MODID + ":merchant", 30, new int[] {2}, 14, SoundEvents.BLOCK_WOOD_BREAK, 2.0F);

	public static final ItemArmor CHLOROPHYTE_HELMET;
	public static final ItemArmor CHLOROPHYTE_CHESTPLATE;
	public static final ItemArmor CHLOROPHYTE_LEGGINGS;
	public static final ItemArmor CHLOROPHYTE_BOOTS;
	public static final ItemArmor RANIUM_HELMET;
	public static final ItemArmor RANIUM_CHESTPLATE;
	public static final ItemArmor RANIUM_LEGGINGS;
	public static final ItemArmor RANIUM_BOOTS;
	public static final ItemArmor LEAFY_BOOTS;
	public static final ItemArmor BLEVENT_BOOTS;
	public static final ItemArmor UMBRELLA_HAT;
	public static final ItemArmor CLOUD_CHESTPLATE;
	public static final ItemArmor TUSKERERS_OLD_RAGS;
	public static final ItemArmor FEATHERY_UNDERWEAR;
	public static final ItemArmor MERCHANTS_LUCKY_BOOTS;
	
	public static final ToolMaterial CHLOROPHYTE_TOOL_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":chlorophyte", 2, 1400, 6.9F, 2.75F, 10);
	public static final ToolMaterial SHEEPLITE_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":sheeplite", 2, 1000, 7.25F, 3.0F, 14);
	public static final ToolMaterial TUSKERER_TOOL_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":tuskerers", 2, 900, 7.0F, 2.0F, 10);
	public static final ToolMaterial KATANA_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":katana", 2, 500, 7.1F, 2.1F, 10);
	public static final ToolMaterial RANIUM_HAMMER_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":hammer", 2, 500, 7.25F, 3.0F, 10);
	public static final ToolMaterial SMOKEN_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":smoken", 2, 500, 7.3F, 2.3F, 10);
	
	public static final ItemPickaxe CHLOROPHYTE_PICKAXE;
	public static final ItemHoe CHLOROPHYTE_HOE;
	public static final ItemSpade CHLOROPHYTE_SHOVEL;
	public static final ItemSword CHLOROPHYTE_SWORD;
	public static final ItemSword SHEEPLITE_SWORD;
	public static final ItemSword TUSKERERS_SWORD;
	public static final ItemTool SOUL_SCRATCHER;
	public static final ItemSword KATANA;
	public static final ItemSword RANIUM_HAMMER;
	public static final Item LEAFERANG;
	
	static
	{
		POTATOSHROOM_PIE = new ItemDinocraftFood("potatoshroom_pie", 7, 6.0F, false);
	 	CHUNKY_FLESH = new ItemDinocraftFood("chunky_flesh", 4, 3.0F, false, new PotionEffect(Potion.getPotionById(19), 200, 4, false, false));
	    BLEVENT_INGOT = new ItemBleventIngot("blevent_ingot");
	 	CHLOROPHYTE_INGOT = new ItemClorophyteIngot("chlorophyte_ingot");
	    SHEEPLITE_INGOT = new ItemSheepliteIngot("sheeplite_ingot");
	    RANIUM = new ItemRanium("ranium");
	    PEBBELONEUM = new ItemPebbeloneum("pebbeloneum");
	    PEBBLES = new ItemPebbles("pebbles");
	    CRACKED_PEBBLES = new ItemCrackedPebbles("cracked_pebbles");
	    LEAF = new ItemLeaf("leaf");
	    HEART = new ItemHeart("heart");
	    TUSK = new ItemTusk("tusk");
	    TUSKERERS_GEMSTONE = new ItemTuskerersGemstone("tuskerers_gemstone");
	    TUSKERERS_AMULET = new ItemTuskerersAmulet("tuskerers_amulet");
	    TUSKERERS_CHARM = new ItemTuskerersCharm("tuskerers_charm");
	    SLINGSHOT = new ItemSlingshot("slingshot");
	    VINE_BALL = new ItemVineBall("vine_ball");
	    VINE_BALL_BUNDLE = new ItemVineBallBundle("vine_ball_bundle");
	    RAY_GUN = new ItemRayGun("ray_gun");
	    RAY_BULLET = new ItemRayBullet("ray_bullet");
	 	SEED_PIPE = new ItemSeedPipe("seed_pipe");
	 	SEED = new ItemSeed("seed");
	 	
	 	CHLOROPHYTE_HELMET = new ItemChlorophyteArmor(CHLOROPHYTE_MATERIAL, 1, EntityEquipmentSlot.HEAD, "chlorophyte_helmet");
	 	CHLOROPHYTE_CHESTPLATE = new ItemChlorophyteArmor(CHLOROPHYTE_MATERIAL, 1, EntityEquipmentSlot.CHEST, "chlorophyte_chestplate");
		CHLOROPHYTE_LEGGINGS = new ItemChlorophyteArmor(CHLOROPHYTE_MATERIAL, 2, EntityEquipmentSlot.LEGS, "chlorophyte_leggings");
		CHLOROPHYTE_BOOTS = new ItemChlorophyteArmor(CHLOROPHYTE_MATERIAL, 1, EntityEquipmentSlot.FEET, "chlorophyte_boots");
		RANIUM_HELMET = new ItemRaniumArmor(RANIUM_MATERIAL, 1, EntityEquipmentSlot.HEAD, "ranium_helmet");
		RANIUM_CHESTPLATE = new ItemRaniumArmor(RANIUM_MATERIAL, 1, EntityEquipmentSlot.CHEST, "ranium_chestplate");
		RANIUM_LEGGINGS = new ItemRaniumArmor(RANIUM_MATERIAL, 2, EntityEquipmentSlot.LEGS, "ranium_leggings");
		RANIUM_BOOTS = new ItemRaniumArmor(RANIUM_MATERIAL, 1, EntityEquipmentSlot.FEET, "ranium_boots");
		LEAFY_BOOTS = new ItemLeafyBoots(LEAF_MATERIAL, 1, EntityEquipmentSlot.FEET, "leafy_boots");
		BLEVENT_BOOTS = new ItemBleventBoots(BLEVENT_MATERIAL, 1, EntityEquipmentSlot.FEET, "blevent_boots");
		UMBRELLA_HAT = new ItemUmbrellaHat(UMBRELLA_MATERIAL, 1, EntityEquipmentSlot.HEAD, "umbrella_hat");
		CLOUD_CHESTPLATE = new ItemCloudChestplate(CLOUD_MATERIAL, 1, EntityEquipmentSlot.CHEST, "cloud_chestplate");
		TUSKERERS_OLD_RAGS = new ItemTuskerersOldRags(TUSKERER_MATERIAL, 2, EntityEquipmentSlot.LEGS, "tuskerers_old_rags");
		FEATHERY_UNDERWEAR = new ItemFeatheryUnderwear(UNDERWEAR_MATERIAL, 2, EntityEquipmentSlot.LEGS, "feathery_underwear");
		MERCHANTS_LUCKY_BOOTS = new ItemMerchantsLuckyBoots(MERCHANT_MATERIAL, 1, EntityEquipmentSlot.FEET, "merchants_lucky_boots");
		
		CHLOROPHYTE_PICKAXE = new ItemChlorophytePickaxe(CHLOROPHYTE_TOOL_MATERIAL, "chlorophyte_pickaxe");
		CHLOROPHYTE_HOE = new ItemChlorophyteHoe(CHLOROPHYTE_TOOL_MATERIAL, "chlorophyte_hoe");
		CHLOROPHYTE_SHOVEL = new ItemChlorophyteShovel(CHLOROPHYTE_TOOL_MATERIAL, "chlorophyte_shovel");
		CHLOROPHYTE_SWORD = new ItemChlorophyteSword(CHLOROPHYTE_TOOL_MATERIAL, "chlorophyte_sword");
		SHEEPLITE_SWORD = new ItemSheepliteSword(SHEEPLITE_MATERIAL, "sheeplite_sword");
		TUSKERERS_SWORD = new ItemTuskerersSword(TUSKERER_TOOL_MATERIAL, "tuskerers_sword");
		SOUL_SCRATCHER = new ItemSoulScratcher("soul_scratcher");
		KATANA = new ItemKatana(KATANA_MATERIAL, "katana");
		RANIUM_HAMMER = new ItemRaniumHammer(RANIUM_HAMMER_MATERIAL, "dino_hammer");
		LEAFERANG = new ItemLeaferang("leaferang");
	}
	

	public static void register() 
	{
		registerItem(POTATOSHROOM_PIE);
		registerItem(CHUNKY_FLESH);
		registerItem(CHLOROPHYTE_INGOT);
		registerItem(SHEEPLITE_INGOT);
		registerItem(PEBBLES);
		registerItem(CRACKED_PEBBLES);
		registerItem(LEAF);
		registerItem(BLEVENT_INGOT);
		registerItem(RANIUM);
		registerItem(PEBBELONEUM);
		registerItem(TUSK);
		registerItem(TUSKERERS_GEMSTONE);
		registerItem(HEART);
		registerItem(TUSKERERS_AMULET);
		registerItem(TUSKERERS_CHARM);
		registerItem(SLINGSHOT);
		registerItem(VINE_BALL);
		registerItem(VINE_BALL_BUNDLE);
		registerItem(RAY_GUN);
		registerItem(RAY_BULLET);
		registerItem(SEED_PIPE);
		registerItem(SEED);

		registerItem(CHLOROPHYTE_HELMET);
		registerItem(CHLOROPHYTE_CHESTPLATE);
		registerItem(CHLOROPHYTE_LEGGINGS);
		registerItem(CHLOROPHYTE_BOOTS);
		registerItem(RANIUM_HELMET);
		registerItem(RANIUM_CHESTPLATE);
		registerItem(RANIUM_LEGGINGS);
		registerItem(RANIUM_BOOTS);
		registerItem(LEAFY_BOOTS);
		registerItem(BLEVENT_BOOTS);
		registerItem(UMBRELLA_HAT);
		registerItem(CLOUD_CHESTPLATE);
		registerItem(TUSKERERS_OLD_RAGS);
		registerItem(FEATHERY_UNDERWEAR);
		registerItem(MERCHANTS_LUCKY_BOOTS);
		
		registerItem(CHLOROPHYTE_PICKAXE);
		registerItem(CHLOROPHYTE_HOE);
		registerItem(CHLOROPHYTE_SHOVEL);
		registerItem(CHLOROPHYTE_SWORD);
		registerItem(SHEEPLITE_SWORD);
		registerItem(TUSKERERS_SWORD);
		registerItem(SOUL_SCRATCHER);
		registerItem(KATANA);
		registerItem(RANIUM_HAMMER);
		registerItem(LEAFERANG);
	}
	
	public static void registerRenders() 
	{
		for (Item item : ITEMS)
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));		
		}
	}
	
	public static void registerItem(Item item)
	{
		ITEMS.add(item);
		item.setCreativeTab(Dinocraft.ITEMS);
		ForgeRegistries.ITEMS.register(item);
	}
	
	
	
	
	
	
	/*	
	@EventBusSubscriber
	public static class RegistrationHandler
	{
	    @SubscribeEvent(priority = EventPriority.LOW)
	    public static void registerItems(RegistryEvent.Register<Item> event)
	    {
	    	event.getRegistry().registerAll(new Item[] {POTATOSHROOM_PIE, CHUNKY_FLESH, BLEVENT_INGOT, CHLOROPHYTE_INGOT, 
		        	SHEEPLITE_INGOT, RANIUM, PEBBELONEUM, LEAF, HEART, TUSK, TUSKERERS_GEM, TUSKERERS_CHARM, PEBBLES, 
		        	CRACKED_PEBBLES, SLINGSHOT, VINE_BALL, VINE_BALL_BUNDLE, RAY_GUN, RAY_BULLET});
	    }
	}
	*/
	
	
	
	
	/*
	@EventBusSubscriber
	public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void register(Register<Item> event)
        {
            register();
        	
            event.getRegistry().registerAll(POTATOSHROOM_PIE,
            		CHUNKY_FLESH,
            		BLEVENT_INGOT,
            		CHLOROPHYTE_INGOT, 
		        	SHEEPLITE_INGOT,
		        	RANIUM,
		        	PEBBELONEUM,
		        	LEAF,
		        	HEART,
		        	TUSK,
		        	TUSKERERS_GEM,
		        	TUSKERERS_AMULET,
		        	PEBBLES, 
		        	CRACKED_PEBBLES,
		        	SLINGSHOT,
		        	VINE_BALL,
		        	VINE_BALL_BUNDLE,
		        	RAY_GUN,
		        	RAY_BULLET,
		        	
		        	CHLOROPHYTE_HELMET,
		        	CHLOROPHYTE_CHESTPLATE,
		        	CHLOROPHYTE_LEGGINGS,
		        	CHLOROPHYTE_BOOTS,
		        	RANIUM_HELMET,
		        	RANIUM_CHESTPLATE,
		        	RANIUM_LEGGINGS,
		        	RANIUM_BOOTS,
		        	LEAFY_BOOTS,
		        	BLEVENT_BOOTS,
		        	UMBRELLA_HAT,
		        	CLOUD_CHESTPLATE,
		        	TUSKERERS_OLD_RAGS,
		        	FEATHERY_UNDERWEAR,
		        	MERCHANTS_LUCKY_BOOTS,
		        	
		        	CHLOROPHYTE_PICKAXE,
		        	CHLOROPHYTE_HOE,
		        	CHLOROPHYTE_SHOVEL,
		        	CHLOROPHYTE_SWORD,
		        	SHEEPLITE_SWORD,
		        	TUSKERERS_SWORD,
		        	SOUL_SCRATCHER,
		        	KATANA,
		        	RANIUM_HAMMER,
		        	LEAFERANG
            	);
            Utils.getLogger().info("Registered all items");
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onModelEvent(ModelRegistryEvent event)
        {
            registerRenders();
        }
    }
	
    public static void registerItem(Item item)
	{
		items.add(item);
		item.setCreativeTab(Dinocraft.ITEMS);
		ForgeRegistries.ITEMS.register(item);
	}
	
    @SideOnly(Side.CLIENT)
	public static void registerRenders() 
	{
		for (Item item : items)
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}		
	}
	*/
}
