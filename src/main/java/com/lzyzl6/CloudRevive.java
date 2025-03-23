package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lzyzl6.data.storage.FileWork.isBackpackedInstalled;

public class CloudRevive implements ModInitializer {
	public static final String MOD_ID = "cloud_revive";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Cloud Revive Here!");
		if (isBackpackedInstalled())
			LOGGER.info("Backpacked is installed, switching tags for it.");

		ModTags.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModTabs.initialize();
		ModEntities.initialize();
		ModEffects.initialize();
		ModEnchantments.initialize();
		ModEnchantmentEffects.initialize();
		ModEvents.initialize();
		ModSoundEvents.initialize();
		ModCommands.initialize();
		FileWork.initialize();

	}
}