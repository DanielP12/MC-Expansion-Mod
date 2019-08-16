package dinocraft.init;

import dinocraft.Dinocraft;
import dinocraft.Reference;
import dinocraft.item.ItemChlorophyteHoe;
import dinocraft.item.ItemChlorophytePickaxe;
import dinocraft.item.ItemChlorophyteShovel;
import dinocraft.item.ItemChlorophyteSword;
import dinocraft.item.ItemKatana;
import dinocraft.item.ItemLeaferang;
import dinocraft.item.ItemRaniumHammer;
import dinocraft.item.ItemSheepliteSword;
import dinocraft.item.ItemSoulScratcher;
import dinocraft.item.ItemTuskerersSword;
import dinocraft.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DinocraftTools 
{
	//Material
	public static final ToolMaterial CHLOROPHYTE_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":chlorophyte", 2, 1400, 7.5F, 3.5F, 10);
	public static final ToolMaterial SHEEPLITE_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":sheeplite", 2, 1000, 7.25F, 3.0F, 14);
	public static final ToolMaterial TUSKERERS_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":tuskerers", 2, 900, 7.0F, 2.0F, 10);
	public static final ToolMaterial KATANA_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":katana", 2, 500, 7.1F, 2.1F, 10);
	public static final ToolMaterial RANIUM_HAMMER_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":hammer", 2, 500, 7.25F, 3.0F, 10);
	public static final ToolMaterial SMOKEN_MATERIAL = EnumHelper.addToolMaterial(Reference.MODID + ":smoken", 2, 500, 7.3F, 2.3F, 10);

	//Chlorophyte
	public static final ItemChlorophytePickaxe CHLOROPHYTE_PICKAXE = new ItemChlorophytePickaxe(CHLOROPHYTE_MATERIAL, "chlorophyte_pickaxe");
	public static final ItemChlorophyteHoe CHLOROPHYTE_HOE = new ItemChlorophyteHoe(CHLOROPHYTE_MATERIAL, "chlorophyte_hoe");
	public static final ItemChlorophyteShovel CHLOROPHYTE_SHOVEL = new ItemChlorophyteShovel(CHLOROPHYTE_MATERIAL, "chlorophyte_shovel");
	public static final ItemChlorophyteSword CHLOROPHYTE_SWORD = new ItemChlorophyteSword(CHLOROPHYTE_MATERIAL, "chlorophyte_sword");
	//Sheeplite
	public static final ItemSheepliteSword SHEEPLITE_SWORD = new ItemSheepliteSword(SHEEPLITE_MATERIAL, "sheeplite_sword");
	//Tuskerer's
	public static final ItemTuskerersSword TUSKERERS_SWORD = new ItemTuskerersSword(TUSKERERS_MATERIAL, "tuskerers_sword");
	//Utility
	public static final ItemSoulScratcher SOUL_SCRATCHER = new ItemSoulScratcher("soul_scratcher");
	//Etc.
	public static final ItemKatana KATANA = new ItemKatana(KATANA_MATERIAL, "katana");
	public static final ItemRaniumHammer RANIUM_HAMMER = new ItemRaniumHammer(RANIUM_HAMMER_MATERIAL, "dino_hammer");
	public static final ItemLeaferang LEAFERANG = new ItemLeaferang("leaferang");
	
	public static void register() 
	{
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
		registerRender(CHLOROPHYTE_PICKAXE);
		registerRender(CHLOROPHYTE_HOE);
		registerRender(CHLOROPHYTE_SHOVEL);
		registerRender(CHLOROPHYTE_SWORD);
		
		registerRender(SHEEPLITE_SWORD);
		
		registerRender(TUSKERERS_SWORD);
		
		registerRender(SOUL_SCRATCHER);
		
		registerRender(KATANA);
		registerRender(RANIUM_HAMMER);
		registerRender(LEAFERANG);
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
