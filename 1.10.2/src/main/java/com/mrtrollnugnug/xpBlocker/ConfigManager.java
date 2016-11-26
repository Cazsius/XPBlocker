package com.mrtrollnugnug.xpBlocker;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigManager {
	public static Configuration config;
	public static boolean BreakBlockXP = true;
	public static boolean SmeltXP = true;

	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			load();

		}
	}

	public static void load() {
		BreakBlockXP = config.getBoolean("BreakBlockXP", Configuration.CATEGORY_GENERAL, false, "Toggles whether you get XP from breaking blocks, such as ores.");
		SmeltXP = config.getBoolean("SmeltXP", Configuration.CATEGORY_GENERAL, false, "Toggles whether you get XP from smelting items in a Furnace such as ores to ingots.");
		
		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(XPBlocker.MODID)) {
			load();
		}
	}
}
