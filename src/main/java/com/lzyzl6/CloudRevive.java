package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudRevive implements ModInitializer {
	public static final String MOD_ID = "cloud_revive";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("云生模组初始化");

		ModItems.initialize();

		ModTabs.initialize();

		ModEntities.initialize();

		ModModelLayers.initialize();

		ModRenderers.initialize();

		ModEvents.initialize();

		ModSoundEvents.initialize();

		ModCommands.initialize();

		FileWork.initialize();
	}
}