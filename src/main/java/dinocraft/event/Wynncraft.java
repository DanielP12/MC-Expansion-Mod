package dinocraft.event;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Wynncraft
{
	public static String makePerfect(String string)
	{
		String[] perfectColors = new String[]
				{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
						TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
						TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
						TextFormatting.YELLOW + "" + TextFormatting.BOLD};
		string = "Perfect " + string;
		String perfectString = "";

		for (int i = 0, j = cycle / 10; i < string.length(); i++)
		{
			j %= 7;
			perfectString += perfectColors[j++] + string.charAt(i);
		}

		return perfectString;
	}
	
	private static int cycle = 70;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event)
	{
		//		if (event.getItemStack().getItem() == Items.DIAMOND_SHOVEL)
		//		{
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			tooltip.add(TextFormatting.GREEN + "[88%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+6%" + TextFormatting.GRAY + " Stealing " + TextFormatting.GREEN + "[80%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "-4200" + TextFormatting.GRAY + " Health " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+22%" + TextFormatting.GRAY + " Thunder Damage " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+21%" + TextFormatting.GRAY + " Thorns " + TextFormatting.YELLOW + "[71%]");
		
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Discoverer");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "" + TextFormatting.BOLD + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 89");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+46%" + TextFormatting.GRAY + " Loot Bonus " + TextFormatting.RED + "[0%]");
		//			tooltip.add(TextFormatting.GREEN + "+5%" + TextFormatting.GRAY + " Xp Bonus " + TextFormatting.RED + "[0%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item " + TextFormatting.GREEN + "[1,692²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Wealth and riches...but at ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "what cost?");
		//		}
		
		//
		if (event.getItemStack().getItem() == Items.STICK)
		{
			List<String> tooltip = event.getToolTip();
			tooltip.clear();
			String[] c = new String[]
					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
			tooltip.add(TextFormatting.DARK_PURPLE + "Stratiformis " + TextFormatting.GREEN + "[83%]");
			tooltip.add(TextFormatting.GRAY + "Fast Attack Speed");
			tooltip.add("");
			tooltip.add(TextFormatting.GOLD + "✣ Neutral Damage: 120-230");
			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Damage: 220-390");
			tooltip.add("");
			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Class Req: Archer/Hunter");
			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 99");
			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Agility Min: 120");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+20" + TextFormatting.GRAY + " Agility");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+6%" + TextFormatting.GRAY + " Main Attack Damage " + TextFormatting.YELLOW + "[50%]");
			tooltip.add(TextFormatting.GREEN + "+9%" + TextFormatting.GRAY + " Spell Damage " + TextFormatting.GREEN + "[87%]");
			tooltip.add("");
			tooltip.add(TextFormatting.RED + "-2150" + TextFormatting.GRAY + " Health " + TextFormatting.AQUA + "[99%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+14%" + TextFormatting.GRAY + " Reflection " + TextFormatting.GREEN + "[83%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+99%" + TextFormatting.GRAY + " Walk Speed " + TextFormatting.AQUA + "[100%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item [4] " + TextFormatting.GREEN + "[1,170,000²]");
			tooltip.add(TextFormatting.DARK_GRAY + "The Elves managed to ");
			tooltip.add(TextFormatting.DARK_GRAY + "harness the power of air ");
			tooltip.add(TextFormatting.DARK_GRAY + "itself, forming a bow made ");
			tooltip.add(TextFormatting.DARK_GRAY + "entirely of air currents, ");
			tooltip.add(TextFormatting.DARK_GRAY + "allowing the user to move swift ");
			tooltip.add(TextFormatting.DARK_GRAY + "like the wind, but vulnerable to ");
			tooltip.add(TextFormatting.DARK_GRAY + "attacks.");
		}
		//
		//
		//
		//		if (event.getItemStack().getItem() == Items.ARROW)
		//		{
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Divzer " + TextFormatting.YELLOW + "[74%]");
		//			tooltip.add(TextFormatting.GRAY + "Super Fast Attack Speed");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GOLD + "✣ Neutral Damage: 41-43");
		//			tooltip.add(TextFormatting.YELLOW + "✦ Thunder " + TextFormatting.GRAY + "Damage: 275-275");
		//			tooltip.add("");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Class Req: Archer/Hunter");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 97");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Dexterity Min: 115");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+37" + TextFormatting.GRAY + " Dexterity");
		//			tooltip.add(TextFormatting.RED + "-550" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+1" + TextFormatting.GRAY + " Tier Attack Speed");
		//			tooltip.add(TextFormatting.GREEN + "+465" + TextFormatting.GRAY + " Main Attack Neutral Damage " + TextFormatting.YELLOW + "[33%]");
		//			tooltip.add(TextFormatting.GREEN + "+441" + TextFormatting.GRAY + " Neutral Spell Damage " + TextFormatting.AQUA + "[99%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+1236/4" + TextFormatting.GRAY + " Life Steal " + TextFormatting.AQUA + "[97%]");
		//			tooltip.add(TextFormatting.GREEN + "+8/4" + TextFormatting.GRAY + " Mana Steal " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "-522%" + TextFormatting.GRAY + " Water Damage " + TextFormatting.YELLOW + "[58%]");
		//			tooltip.add(TextFormatting.RED + "-528%" + TextFormatting.GRAY + " Fire Damage " + TextFormatting.YELLOW + "[56%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item " + TextFormatting.GREEN + "[1,836²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Unruly magic of void and ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "darkness are tamed within ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "this bow's string. While those ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "energies are never to be ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "released, arrows flying from ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "it fire at such blinding ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "speeds so as to phase out");
		//			tooltip.add(TextFormatting.DARK_GRAY + "of existence when they hit");
		//			tooltip.add(TextFormatting.DARK_GRAY + "their target.");
		//		}
		//
		//
		cycle--;
		
		if (cycle <= 0)
		{
			cycle = 70;
		}
		
		if (event.getItemStack().getItem() == Items.DIAMOND_LEGGINGS)
		{
			List<String> tooltip = event.getToolTip();
			tooltip.clear();
			tooltip.add(makePerfect("The Jingling Jester"));
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_RED + "❤ Health: +2325");
			tooltip.add(TextFormatting.RED + "✹ Fire " + TextFormatting.GRAY + "Defence: +1");
			tooltip.add(TextFormatting.AQUA + "" + TextFormatting.BOLD + "✽ " + TextFormatting.RESET + "" + TextFormatting.AQUA + "Water " + TextFormatting.GRAY + "Defence: +1");
			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Defence: +1");
			tooltip.add(TextFormatting.YELLOW + "✦ Thunder " + TextFormatting.GRAY + "Defence: +1");
			tooltip.add(TextFormatting.DARK_GREEN + "✤ Earth " + TextFormatting.GRAY + "Defence: +1");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "" + TextFormatting.BOLD + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 69");
			tooltip.add("");
			tooltip.add(TextFormatting.RED + "-71" + TextFormatting.GRAY + " Health Regen " + TextFormatting.AQUA + "[100%]");
			tooltip.add(TextFormatting.GREEN + "+195/4s" + TextFormatting.GRAY + " Life Steal " + TextFormatting.AQUA + "[100%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+3" + TextFormatting.GRAY + " Jump Height " + TextFormatting.AQUA + "[100%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "+33%" + TextFormatting.GRAY + " Loot Bonus " + TextFormatting.AQUA + "[100%]");
			tooltip.add(TextFormatting.GREEN + "+33%" + TextFormatting.GRAY + " Xp Bonus " + TextFormatting.AQUA + "[100%]");
			tooltip.add("");
			tooltip.add(TextFormatting.GREEN + "-58%" + TextFormatting.GRAY + " 2nd Spell (Teleport) Cost " + TextFormatting.AQUA + "[100%]");
			tooltip.add(TextFormatting.GREEN + "-19%" + TextFormatting.GRAY + " 4th Spell (Ice Snake) Cost " + TextFormatting.AQUA + "[100%]");
			tooltip.add("");
			tooltip.add(TextFormatting.AQUA + "+Greed: " + TextFormatting.DARK_AQUA + "Picking up emeralds");
			tooltip.add(TextFormatting.DARK_AQUA + "heals you and nearby");
			tooltip.add(TextFormatting.DARK_AQUA + "players for 15% max health");
			tooltip.add("");
			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
			tooltip.add(TextFormatting.RED + "Fabled Item " + TextFormatting.GREEN + "[4,440²]");
			tooltip.add(TextFormatting.DARK_GRAY + "Jingle jangle, while shaking ");
			tooltip.add(TextFormatting.DARK_GRAY + "your bells. Jingle jangle ");
			tooltip.add(TextFormatting.DARK_GRAY + "come the pockets of cash... ");
			tooltip.add(TextFormatting.DARK_GRAY + "Catch the trickster, send ");
			tooltip.add(TextFormatting.DARK_GRAY + "them to hell,\" their money is ");
			tooltip.add(TextFormatting.DARK_GRAY + "gone and off you've dashed!");
		}
		//
		//		if (event.getItemStack().getItem() == Items.DIAMOND_HOE)
		//		{
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			tooltip.add(c[0] + "P" + c[1] + "e" + c[2] + "r" + c[3]
		//					+ "f" + c[4] + "e" + c[5] + "c" + c[6] + "t " + c[7]
		//							+ "A" + c[8] + "l" + c[9] + "k" + c[10] + "a" + c[11]
		//									+ "t" + c[12] + "r" + c[13] + "a" + c[14] + "z");
		//			tooltip.add(TextFormatting.GRAY + "Super Slow Attack Speed");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.DARK_GREEN + "✤ Earth " + TextFormatting.GRAY + "Damage: 1850-2000");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Class Req: Warrior/Knight");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 94");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Strength Min: 120");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+30" + TextFormatting.GRAY + " Strength");
		//			tooltip.add(TextFormatting.RED + "-15" + TextFormatting.GRAY + " Dexterity");
		//			tooltip.add(TextFormatting.RED + "-15" + TextFormatting.GRAY + " Intelligence");
		//			tooltip.add(TextFormatting.RED + "-15" + TextFormatting.GRAY + " Defence");
		//			tooltip.add(TextFormatting.RED + "-15" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+39%" + TextFormatting.GRAY + " Main Attack Damage " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+39%" + TextFormatting.GRAY + " Earth Damage " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+39%" + TextFormatting.GRAY + " Exploding " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item [2] " + TextFormatting.GREEN + "[44,550²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Soul-locking magic is one ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "outlawed by Gavellian ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "governments, only used by ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "the most insidious and cruel ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "of mages. One such ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "enchantment looms dormant in ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "this mundane-looking maul.");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Continued usage of the");
		//			tooltip.add(TextFormatting.DARK_GRAY + "weapon threatens to release");
		//			tooltip.add(TextFormatting.DARK_GRAY + "it upon the wielder,");
		//			tooltip.add(TextFormatting.DARK_GRAY + "permanently binding them to");
		//			tooltip.add(TextFormatting.DARK_GRAY + "the weapon.");
		//		}
		//
		//
		//
		//
		//		if (event.getItemStack().getItem() == Items.APPLE)
		//		{
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			tooltip.add(c[0] + "P" + c[1] + "e" + c[2] + "r" + c[3]
		//					+ "f" + c[4] + "e" + c[5] + "c" + c[6] + "t " + c[7]
		//							+ "M" + c[8] + "o" + c[9] + "o" + c[10] + "n" + c[11]
		//									+ "t" + c[12] + "o" + c[13] + "w" + c[14] + "e" + c[15]
		//											+ "r");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.DARK_RED + "❤ Health: +3850");
		//			tooltip.add(TextFormatting.RED + "✹ Fire " + TextFormatting.GRAY + "Defence: +75");
		//			tooltip.add(TextFormatting.AQUA + "✽ Water " + TextFormatting.GRAY + "Defence: +125");
		//			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Defence: +125");
		//			tooltip.add(TextFormatting.YELLOW + "✦ Thunder " + TextFormatting.GRAY + "Defence: +75");
		//			tooltip.add(TextFormatting.DARK_GREEN + "✤ Earth " + TextFormatting.GRAY + "Defence: +75");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Combat Lv. Min: 95");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Agility Min: 90");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Intelligence Min: 80");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "-20" + TextFormatting.GRAY + " Strength");
		//			tooltip.add(TextFormatting.RED + "-20" + TextFormatting.GRAY + " Dexterity");
		//			tooltip.add(TextFormatting.GREEN + "+50" + TextFormatting.GRAY + " Intelligence");
		//			tooltip.add(TextFormatting.RED + "-20" + TextFormatting.GRAY + " Defence");
		//			tooltip.add(TextFormatting.GREEN + "+60" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+52%" + TextFormatting.GRAY + " Water Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add(TextFormatting.GREEN + "+52%" + TextFormatting.GRAY + " Air Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+33%" + TextFormatting.GRAY + " Walk Speed " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item " + TextFormatting.GREEN + "[1,800²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "For the belief of the cosmos ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "defilement, the moon was ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "noted to remain unchanged. ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Those with this belief live ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "only for the light of the ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "moon, desperately seeking to ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "maintain it through the means that ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "would only draken the soul ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "of the most devout. Such ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "from them comes the word ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "lunacy.");
		//		}
		//
		//		if (event.getItemStack().getItem() == Items.BONE)
		//		{
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Warp " + TextFormatting.GREEN + "[85%]");
		//			tooltip.add(TextFormatting.GRAY + "Very Fast Attack Speed");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GOLD + "✣ Neutral Damage: 55-90");
		//			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Damage: 200-240");
		//			tooltip.add("");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Class Req: Mage/Dark Wizard");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 99");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Agility Min: 130");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+20" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "-300" + TextFormatting.GRAY + " Health Regen " + TextFormatting.AQUA + "[92%]");
		//			tooltip.add(TextFormatting.RED + "-153%" + TextFormatting.GRAY + " Health Regen " + TextFormatting.GREEN + "[89%]");
		//			tooltip.add(TextFormatting.RED + "-6/4s" + TextFormatting.GRAY + " Mana Regen " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+18%" + TextFormatting.GRAY + " Air Damage " + TextFormatting.GREEN + "[86%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+59%" + TextFormatting.GRAY + " Exploding " + TextFormatting.GREEN + "[88%]");
		//			tooltip.add(TextFormatting.GREEN + "+89%" + TextFormatting.GRAY + " Reflection " + TextFormatting.YELLOW + "[68%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+233%" + TextFormatting.GRAY + " Walk Speed " + TextFormatting.AQUA + "[99%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "+1" + TextFormatting.GRAY + " 1st Spell (Heal Spell) Cost");
		//			tooltip.add(TextFormatting.GREEN + "-107" + TextFormatting.GRAY + " 2nd Spell (Teleport Spell) Cost " + TextFormatting.YELLOW + "[61%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item " + TextFormatting.GREEN + "[1,872²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "The notorious and erratic ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "magical inventor Gawrick once ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "attempted to make ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "teleportation scrolls ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "obsolete, by crafting items ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "which would allow the user to ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "phase into an ethereal state ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "to move unhindered. He did ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "not account for the physical ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "ramifications of this process, ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "however.");
		//		}
		//
		//		if (event.getItemStack().getItem() == Items.CLAY_BALL)
		//		{
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Hero " + TextFormatting.AQUA + "[97%]");
		//			tooltip.add(TextFormatting.GRAY + "Very Fast Attack Speed");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GOLD + "✣ Neutral Damage: 80-100");
		//			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Damage: 160-190");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.RED + "✖" + TextFormatting.GRAY + " Class Req: Warrior/Knight");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 91");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Agility Min: 110");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+20" + TextFormatting.GRAY + " Strength");
		//			tooltip.add(TextFormatting.GREEN + "+20" + TextFormatting.GRAY + " Dexterity");
		//			tooltip.add(TextFormatting.GREEN + "+40" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+51%" + TextFormatting.GRAY + " Main Attack Damage " + TextFormatting.AQUA + "[97%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+50%" + TextFormatting.GRAY + " Health Regen " + TextFormatting.GREEN + "[93%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+52%" + TextFormatting.GRAY + " Walk Speed " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.AQUA + "+Saviour's Sacrifice: " + TextFormatting.DARK_AQUA +
		//					"While");
		//			tooltip.add(TextFormatting.DARK_AQUA + "under 30% maximum health,");
		//			tooltip.add(TextFormatting.DARK_AQUA + "nearby allies gain 50% bonus");
		//			tooltip.add(TextFormatting.DARK_AQUA + "damage and defence");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/4] Powder Slots");
		//			tooltip.add(TextFormatting.DARK_PURPLE + "Mythic Item [2] " + TextFormatting.GREEN + "[1,872²]");
		//			tooltip.add(TextFormatting.DARK_GRAY + "Enigmatically, this weapon will ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "only show itself to those who ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "believe themselves to be ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "worth nothing, or to be weak. ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "While utilizing this weapon will ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "greatly strengthen one's ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "resolve, it will disappear ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "when it is no longer needed, ");
		//			tooltip.add(TextFormatting.DARK_GRAY + "going to the next wielder. ");
		//		}
		//
		//		if (event.getItemStack().getItem() == Items.IRON_CHESTPLATE)
		//		{
		//			List<String> tooltip = event.getToolTip();
		//			tooltip.clear();
		//			String[] c = new String[]
		//					{TextFormatting.GREEN + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.AQUA + "" + TextFormatting.BOLD,
		//							TextFormatting.BLUE + "" + TextFormatting.BOLD, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD,
		//							TextFormatting.RED + "" + TextFormatting.BOLD, TextFormatting.GOLD + "" + TextFormatting.BOLD,
		//							TextFormatting.YELLOW + "" + TextFormatting.BOLD, TextFormatting.GREEN + "" + TextFormatting.BOLD,
		//							TextFormatting.AQUA + "" + TextFormatting.BOLD, TextFormatting.BLUE + "" + TextFormatting.BOLD,
		//							TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD};
		//			tooltip.add(c[0] + "P" + c[1] + "e" + c[2] + "r" + c[3]
		//					+ "f" + c[4] + "e" + c[5] + "c" + c[6] + "t " + c[7]
		//							+ "M" + c[8] + "o" + c[9] + "r" + c[10] + "p" + c[11]
		//									+ "h" + c[12] + "-" + c[13] + "S" + c[14] + "t" + c[15]
		//											+ "e" + c[16] + "e" + c[4] + "l");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.DARK_RED + "❤ Health: +1750");
		//			tooltip.add(TextFormatting.RED + "✹ Fire " + TextFormatting.GRAY + "Defence: +40");
		//			tooltip.add(TextFormatting.AQUA + "✽ Water " + TextFormatting.GRAY + "Defence: +40");
		//			tooltip.add(TextFormatting.WHITE + "❋ Air " + TextFormatting.GRAY + "Defence: +40");
		//			tooltip.add(TextFormatting.YELLOW + "✦ Thunder " + TextFormatting.GRAY + "Defence: +40");
		//			tooltip.add(TextFormatting.DARK_GREEN + "✤ Earth " + TextFormatting.GRAY + "Defence: +40");
		//			tooltip.add("");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Combat Lv. Min: 75");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Strength Min: 32");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Agility Min: 32");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Defence Min: 32");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Intelligence Min: 32");
		//			tooltip.add(c[0] + "✓" + TextFormatting.GRAY + " Dexterity Min: 32");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+4" + TextFormatting.GRAY + " Strength");
		//			tooltip.add(TextFormatting.GREEN + "+4" + TextFormatting.GRAY + " Dexterity");
		//			tooltip.add(TextFormatting.GREEN + "+4" + TextFormatting.GRAY + " Intelligence");
		//			tooltip.add(TextFormatting.GREEN + "+4" + TextFormatting.GRAY + " Defence");
		//			tooltip.add(TextFormatting.GREEN + "+4" + TextFormatting.GRAY + " Agility");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GREEN + "+20%" + TextFormatting.GRAY + " Earth Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add(TextFormatting.GREEN + "+20%" + TextFormatting.GRAY + " Thunder Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add(TextFormatting.GREEN + "+20%" + TextFormatting.GRAY + " Water Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add(TextFormatting.GREEN + "+20%" + TextFormatting.GRAY + " Fire Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add(TextFormatting.GREEN + "+20%" + TextFormatting.GRAY + " Air Defence " + TextFormatting.AQUA + "[100%]");
		//			tooltip.add("");
		//			tooltip.add(TextFormatting.GRAY + "[0/3] Powder Slots");
		//			tooltip.add(TextFormatting.GREEN + "Set Item " + TextFormatting.GREEN + "[132²]");
		//		}
	}
}
