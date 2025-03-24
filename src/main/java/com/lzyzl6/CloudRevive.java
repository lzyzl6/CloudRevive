package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.registry.*;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.ModContainer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CloudRevive.MOD_ID)
public class CloudRevive {

	public static final String MOD_ID = "cloud_revive";

	public static final Logger LOGGER = LogUtils.getLogger();

	public CloudRevive(IEventBus modEventBus, ModContainer modContainer)
	{
		modEventBus.addListener(this::commonSetup);
		NeoForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("Cloud Revive Mod Setup");
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

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event)
	{

	}

	@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents
	{
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event)
		{
			ModModelLayers.initialize();
			ModRenderers.initialize();
		}
	}
}