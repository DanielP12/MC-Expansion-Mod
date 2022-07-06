package dinocraft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dinocraft.Dinocraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class DinocraftConfigGUIFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance)
	{
		
	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}
	
	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new DinocraftConfigGUI(parentScreen);
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	
	public static class DinocraftConfigGUI extends GuiConfig
	{
		public DinocraftConfigGUI(GuiScreen parentScreen)
		{
			super(parentScreen, getConfigElements(), Dinocraft.MODID, false, false, I18n.format("gui.config.main_title"));
		}
		
		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<>();
			list.add(new DummyCategoryElement(I18n.format("gui.config.category.commands"), "gui.config.category.commands", CategoryEntryCommands.class));
			list.add(new DummyCategoryElement(I18n.format("gui.config.category.entities"), "gui.config.category.entities", CategoryEntryEntities.class));
			list.add(new DummyCategoryElement(I18n.format("gui.config.category.miscellaneous"), "gui.config.category.miscellaneous", CategoryEntryMiscellaneous.class));
			return list;
		}

		public static class CategoryEntryCommands extends CategoryEntry
		{
			public CategoryEntryCommands(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				Configuration config = DinocraftConfig.getConfig();
				ConfigElement categoryCommands = new ConfigElement(config.getCategory(DinocraftConfig.COMMANDS));
				List<IConfigElement> properties = categoryCommands.getChildElements();
				String window = I18n.format("gui.config.category.commands");
				return new GuiConfig(this.owningScreen, properties, this.owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, window);
			}
		}

		public static class CategoryEntryEntities extends CategoryEntry
		{
			public CategoryEntryEntities(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				Configuration config = DinocraftConfig.getConfig();
				ConfigElement categoryEntities = new ConfigElement(config.getCategory(DinocraftConfig.ENTITIES));
				List<IConfigElement> properties = categoryEntities.getChildElements();
				String window = I18n.format("gui.config.category.entities");
				return new GuiConfig(this.owningScreen, properties, this.owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, window);
			}
		}

		public static class CategoryEntryMiscellaneous extends CategoryEntry
		{
			public CategoryEntryMiscellaneous(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				Configuration config = DinocraftConfig.getConfig();
				ConfigElement categoryMiscellaneous = new ConfigElement(config.getCategory(DinocraftConfig.MISCELLANEOUS));
				List<IConfigElement> properties = categoryMiscellaneous.getChildElements();
				String window = I18n.format("gui.config.category.miscellaneous");
				return new GuiConfig(this.owningScreen, properties, this.owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, window);
			}
		}
	}
}