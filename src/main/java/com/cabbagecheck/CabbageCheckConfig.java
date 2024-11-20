package com.cabbagecheck;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("cabbage")
public interface CabbageCheckConfig extends Config
{
	@ConfigItem(
			keyName = "removeEatOption",
			name = "Remove Eat Option",
			description = "Removes the 'Eat' option for cabbages.",
			position = 1
	)
	default boolean removeEatOption()
	{
		return true;
	}
}
