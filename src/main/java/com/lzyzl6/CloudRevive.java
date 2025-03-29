package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CloudRevive implements ModInitializer {
	public static final String MOD_ID = "cloud_revive";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Cloud Revive Here!");

		ModBlocks.initialize();
		ModItems.initialize();
		ModTabs.initialize();
		ModEntities.initialize();
		ModEffects.initialize();
		ModEnchantments.initialize();
		ModEvents.initialize();
		ModSoundEvents.initialize();
		ModCommands.initialize();
		FileWork.initialize();

	}
}