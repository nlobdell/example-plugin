package com.cabbagecheck;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class CabbageCheckPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private CabbageCheckConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("CabbageCheck started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("CabbageCheck stopped!");
	}

	private static final String MENU_OPTION_EAT = "Eat";
	private static final String ITEM_NAME_CABBAGE = "Cabbage";

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		// Check if the menu entry is "Eat" for "Cabbage"
		if (event.getOption().equals(MENU_OPTION_EAT) && event.getTarget().contains(ITEM_NAME_CABBAGE))
		{
			removeEatOption();
		}
	}

	private void removeEatOption()
	{
		MenuEntry[] menuEntries = client.getMenu().getMenuEntries();
		int newSize = 0;

		// Count how many entries will remain
		for (MenuEntry entry : menuEntries)
		{
			if (!isEatOptionForCabbage(entry))
			{
				newSize++;
			}
		}

		// Create a new array without "Eat" for "Cabbage"
		MenuEntry[] filteredEntries = new MenuEntry[newSize];
		int index = 0;
		for (MenuEntry entry : menuEntries)
		{
			if (!isEatOptionForCabbage(entry))
			{
				filteredEntries[index++] = entry;
			}
		}

		// Set the filtered menu entries
		client.getMenu().setMenuEntries(filteredEntries);
	}

	private boolean isEatOptionForCabbage(MenuEntry entry)
	{
		return MENU_OPTION_EAT.equals(entry.getOption()) && entry.getTarget().contains(ITEM_NAME_CABBAGE);
	}

	@Provides
	CabbageCheckConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CabbageCheckConfig.class);
	}
}
