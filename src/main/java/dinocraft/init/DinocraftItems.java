package dinocraft.init;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.item.ItemBleventIngot;
import dinocraft.item.ItemClorophyteIngot;
import dinocraft.item.ItemCrackedPebbles;
import dinocraft.item.ItemDinocraftFood;
import dinocraft.item.ItemHeart;
import dinocraft.item.ItemLeaf;
import dinocraft.item.ItemPebbeloneum;
import dinocraft.item.ItemPebbles;
import dinocraft.item.ItemRanium;
import dinocraft.item.ItemRayBullet;
import dinocraft.item.ItemRayGun;
import dinocraft.item.ItemSeed;
import dinocraft.item.ItemSeedPipe;
import dinocraft.item.ItemSheepliteIngot;
import dinocraft.item.ItemSlingshot;
import dinocraft.item.ItemTusk;
import dinocraft.item.ItemTuskerersCharm;
import dinocraft.item.ItemVineBall;
import dinocraft.item.ItemVineBallBundle;
import dinocraft.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftItems 
{
	//Food
	public static final ItemDinocraftFood POTATOSHROOM_PIE = new ItemDinocraftFood("potatoshroom_pie", 7, 6, false);
	public static final ItemDinocraftFood CHUNKY_FLESH = new ItemDinocraftFood("chunky_flesh", 4, 3, false, new PotionEffect(Potion.getPotionById(19), 200, 4, false, false));
	//Material
	public static final ItemClorophyteIngot CHLOROPHYTE_INGOT = new ItemClorophyteIngot("chlorophyte_ingot", "chlorophyte_ingot");
	public static final ItemSheepliteIngot SHEEPLITE_INGOT = new ItemSheepliteIngot("sheeplite_ingot", "sheeplite_ingot");
	public static final ItemPebbles PEBBLES = new ItemPebbles("pebbles", "pebbles");
	public static final ItemCrackedPebbles CRACKED_PEBBLES = new ItemCrackedPebbles("cracked_pebbles", "cracked_pebbles");
	public static final ItemLeaf LEAF = new ItemLeaf("leaf", "leaf");
	public static final ItemBleventIngot BLEVENT_INGOT = new ItemBleventIngot("blevent_ingot", "blevent_ingot");
	public static final ItemRanium RANIUM = new ItemRanium("ranium", "ranium");
	public static final ItemPebbeloneum PEBBELONEUM = new ItemPebbeloneum("pebbeloneum", "pebbeloneum");
	public static final ItemTusk TUSK = new ItemTusk("tusk", "tusk");
	public static final ItemTusk TUSKERERS_GEM = new ItemTusk("tuskerers_gem", "tuskerers_gem");

	//?
	public static final ItemHeart HEART = new ItemHeart("heart");
	public static final ItemTuskerersCharm TUSKERERS_CHARM = new ItemTuskerersCharm("tuskerers_charm");
	
	//WIP
	public static final Item VINE_BALL = new ItemVineBall("vine_ball");
	public static final ItemSlingshot SLINGSHOT = new ItemSlingshot("slingshot");
	public static final Item VINE_BALL_BUNDLE = new ItemVineBallBundle("vine_ball_bundle");
	public static final Item RAY_BULLET = new ItemRayBullet("ray_bullet");
	public static final Item SEED = new ItemSeed("seed");
	public static final ItemRayGun RAY_GUN = new ItemRayGun("ray_gun");
	public static final ItemSeedPipe SEED_PIPE = new ItemSeedPipe("seed_pipe");

	public static void register() 
	{
		//Food
		registerItem(POTATOSHROOM_PIE);
		registerItem(CHUNKY_FLESH);
		//Material
		registerItem(CHLOROPHYTE_INGOT);
		registerItem(SHEEPLITE_INGOT);
		registerItem(PEBBLES);
		registerItem(CRACKED_PEBBLES);
		registerItem(LEAF);
		registerItem(BLEVENT_INGOT);
		registerItem(RANIUM);
		registerItem(PEBBELONEUM);
		registerItem(TUSK);
		registerItem(TUSKERERS_GEM);
		//?
		registerItem(HEART);
		registerItem(TUSKERERS_CHARM);
		
		//WIP
		registerItem(VINE_BALL);
		registerItem(SLINGSHOT);
		registerItem(VINE_BALL_BUNDLE);
		registerItem(RAY_BULLET);
		registerItem(SEED);
		registerItem(RAY_GUN);
		registerItem(SEED_PIPE);
	}
	
	public static void registerRenders() 
	{
		//Food
		registerRender(POTATOSHROOM_PIE);
		registerRender(CHUNKY_FLESH);
		//Material
		registerRender(CHLOROPHYTE_INGOT);
		registerRender(SHEEPLITE_INGOT);
		registerRender(PEBBLES);
		registerRender(CRACKED_PEBBLES);
		registerRender(LEAF);
		registerRender(BLEVENT_INGOT);
		registerRender(RANIUM);
		registerRender(PEBBELONEUM);
		registerRender(TUSK);
		registerRender(TUSKERERS_GEM);
		//?
		registerRender(HEART);
		registerRender(TUSKERERS_CHARM);
		
		//WIP
		registerRender(VINE_BALL);
		registerRender(SLINGSHOT);
		registerRender(VINE_BALL_BUNDLE);
		registerRender(RAY_BULLET);
		registerRender(SEED);
		registerRender(RAY_GUN);
		registerRender(SEED_PIPE);
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
