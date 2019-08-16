package dinocraft.init;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.item.ItemBleventBoots;
import dinocraft.item.ItemChlorophyteArmour;
import dinocraft.item.ItemCloudChestplate;
import dinocraft.item.ItemLeafyBoots;
import dinocraft.item.ItemRaniumArmour;
import dinocraft.item.ItemTuskerersOldRags;
import dinocraft.item.ItemUmbrellaHat;
import dinocraft.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftArmour
{
	//Armour Material
	public static final ArmorMaterial SHEEPLITE = EnumHelper.addArmorMaterial("sheeplite", Reference.MODID + ":sheeplite", 35, new int[] {2,6,8,3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	public static final ArmorMaterial CHLOROPHYTE = EnumHelper.addArmorMaterial("chlorophyte", Reference.MODID + ":chlorophyte", 35, new int[] {3,6,8,3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	public static final ArmorMaterial RANIUM = EnumHelper.addArmorMaterial("ranium", Reference.MODID + ":ranium", 35, new int[] {3,6,8,3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	public static final ArmorMaterial LEAF = EnumHelper.addArmorMaterial("leaf", Reference.MODID + ":leaf", 30, new int[] {2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	public static final ArmorMaterial BLEVENT = EnumHelper.addArmorMaterial("blevent", Reference.MODID + ":blevent", 30, new int[] {1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	public static final ArmorMaterial UMBRELLA = EnumHelper.addArmorMaterial("umbrella", Reference.MODID + ":umbrella", 30, new int[] {0, 0, 0, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	public static final ArmorMaterial CLOUD = EnumHelper.addArmorMaterial("cloud", Reference.MODID + ":cloud", 30, new int[] {0, 0, 2, 0}, 9, SoundEvents.BLOCK_CLOTH_BREAK, 2.0F);
	public static final ArmorMaterial TUSKERER = EnumHelper.addArmorMaterial("tuskerer", Reference.MODID + ":tuskerer", 30, new int[] {0, 0, 2, 0}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);

	//Armour
	public static final ItemChlorophyteArmour CHLOROPHYTE_HELMET = new ItemChlorophyteArmour(CHLOROPHYTE, 1, EntityEquipmentSlot.HEAD, "chlorophyte_helmet");
	public static final ItemChlorophyteArmour CHLOROPHYTE_CHESTPLATE = new ItemChlorophyteArmour(CHLOROPHYTE, 1, EntityEquipmentSlot.CHEST, "chlorophyte_chestplate");
	public static final ItemChlorophyteArmour CHLOROPHYTE_LEGGINGS = new ItemChlorophyteArmour(CHLOROPHYTE, 2, EntityEquipmentSlot.LEGS, "chlorophyte_leggings");
	public static final ItemChlorophyteArmour CHLOROPHYTE_BOOTS = new ItemChlorophyteArmour(CHLOROPHYTE, 1, EntityEquipmentSlot.FEET, "chlorophyte_boots");
	public static final ItemRaniumArmour RANIUM_HELMET = new ItemRaniumArmour(RANIUM, 1, EntityEquipmentSlot.HEAD, "ranium_helmet");
	public static final ItemRaniumArmour RANIUM_CHESTPLATE = new ItemRaniumArmour(RANIUM, 1, EntityEquipmentSlot.CHEST, "ranium_chestplate");
	public static final ItemRaniumArmour RANIUM_LEGGINGS = new ItemRaniumArmour(RANIUM, 2, EntityEquipmentSlot.LEGS, "ranium_leggings");
	public static final ItemRaniumArmour RANIUM_BOOTS = new ItemRaniumArmour(RANIUM, 1, EntityEquipmentSlot.FEET, "ranium_boots");
	//Rare
	public static final ItemLeafyBoots LEAFY_BOOTS = new ItemLeafyBoots(LEAF, 1, EntityEquipmentSlot.FEET, "leafy_boots");
	public static final ItemBleventBoots BLEVENT_BOOTS = new ItemBleventBoots(BLEVENT, 1, EntityEquipmentSlot.FEET, "blevent_boots");
	public static final ItemUmbrellaHat UMBRELLA_HAT = new ItemUmbrellaHat(UMBRELLA, 1, EntityEquipmentSlot.HEAD, "umbrella_hat");
	public static final ItemCloudChestplate CLOUD_CHESTPLATE = new ItemCloudChestplate(CLOUD, 1, EntityEquipmentSlot.CHEST, "cloud_chestplate");
	public static final ItemTuskerersOldRags TUSKERERS_OLD_RAGS = new ItemTuskerersOldRags(TUSKERER, 2, EntityEquipmentSlot.LEGS, "tuskerers_old_rags");

	//public static final ItemBowBoots BOW_BOOTS = new ItemBowBoots(LEAF, 1, EntityEquipmentSlot.FEET, "bow_boots");
	
	public static void register()
	{
		//Armour
		registerItem(CHLOROPHYTE_HELMET);
		registerItem(CHLOROPHYTE_CHESTPLATE);
		registerItem(CHLOROPHYTE_LEGGINGS);
		registerItem(CHLOROPHYTE_BOOTS);
		registerItem(RANIUM_HELMET);
		registerItem(RANIUM_CHESTPLATE);
		registerItem(RANIUM_LEGGINGS);
		registerItem(RANIUM_BOOTS);
		//Rare
		registerItem(LEAFY_BOOTS);
		registerItem(BLEVENT_BOOTS);
		registerItem(UMBRELLA_HAT);
		registerItem(CLOUD_CHESTPLATE);
		registerItem(TUSKERERS_OLD_RAGS);
		
		//registerItem(BOW_BOOTS); 
	}

	public static void registerRenders()
	{
		//Armour
		registerRender(CHLOROPHYTE_HELMET);
		registerRender(CHLOROPHYTE_CHESTPLATE);
		registerRender(CHLOROPHYTE_LEGGINGS);
		registerRender(CHLOROPHYTE_BOOTS);
		registerRender(RANIUM_HELMET);
		registerRender(RANIUM_CHESTPLATE);
		registerRender(RANIUM_LEGGINGS);
		registerRender(RANIUM_BOOTS);
		//Rare
		registerRender(LEAFY_BOOTS);
		registerRender(BLEVENT_BOOTS);
		registerRender(UMBRELLA_HAT);
		registerRender(CLOUD_CHESTPLATE);
		registerRender(TUSKERERS_OLD_RAGS);
		
		//registerRender(BOW_BOOTS);
	}
	
	public static void registerItem(Item item) 
	{
		item.setCreativeTab(Dinocraft.ITEMS);
		ForgeRegistries.ITEMS.register(item);
		Utils.getLogger().info("Registered item: " + item.getUnlocalizedName().substring(5));
	}
	
	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Registered render for " + item.getUnlocalizedName().substring(5));
	}
}