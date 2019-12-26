package com.mrtrollnugnug.xpBlocker;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigManager {

	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec COMMON_SPEC;

	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		COMMON_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static class ServerConfig {

		public static ForgeConfigSpec.BooleanValue smelt_xp;

		ServerConfig(ForgeConfigSpec.Builder builder) {

			builder.push("general");
			smelt_xp = builder
							.comment("Toggles whether you get XP from smelting.")
							.define("smelt_xp",false);
			builder.pop();
		}
	}
}
