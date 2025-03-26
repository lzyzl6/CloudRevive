package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.model.WanderingSpiritModel;
import com.lzyzl6.registry.*;
import com.lzyzl6.renderer.BirthBeaconEntityRenderer;
import com.lzyzl6.renderer.WanderingSpiritRenderer;
import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import static com.lzyzl6.registry.ModBlocks.*;
import static com.lzyzl6.registry.ModEffects.EFFECTS;
import static com.lzyzl6.registry.ModEnchantmentEffects.LB_ENCHANTMENT_EFFECTS;
import static com.lzyzl6.registry.ModEnchantments.ENCHANTMENT_EFFECT_COMPONENT_TYPE;
import static com.lzyzl6.registry.ModEntities.ENTITIES;
import static com.lzyzl6.registry.ModEntities.GHOST;
import static com.lzyzl6.registry.ModItems.ITEMS;
import static com.lzyzl6.registry.ModSoundEvents.SOUND_EVENTS;
import static com.lzyzl6.registry.ModTabs.CREATIVE_MODE_TABS;

@Mod(CloudRevive.MOD_ID)
public class CloudRevive
{

    public static final String MOD_ID = "cloud_revive";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ModelLayerLocation GHOST_LAYER = new ModelLayerLocation(ResourceLocation
            .fromNamespaceAndPath(MOD_ID, "ghost"),
            "main");


    public CloudRevive(IEventBus modEventBus)
    {

        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ENTITIES.register(modEventBus);
        EFFECTS.register(modEventBus);
        LB_ENCHANTMENT_EFFECTS.register(modEventBus);
        ENCHANTMENT_EFFECT_COMPONENT_TYPE.register(modEventBus);

        //专门为Neoforge注册
        ModEntities.initialize();
        ModBlocks.initialize();
        ModItems.initialize();
        ModTabs.initialize();
        ModSoundEvents.initialize();
        ModEffects.initialize();
        ModEnchantmentEffects.initialize();
        ModEnchantments.initialize();

        ModEvents.initialize();
        ModCommands.initialize();
        FileWork.initialize();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Cloud Revive Here!");

        FabricDefaultAttributeRegistry.register(GHOST.get(), WanderingSpirit.createAttributes());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BIRTH_BEACON.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QI_FRUIT_BUSH.get(), RenderType.cutout());
    }


    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CloudRevive.MOD_ID , value = Dist.CLIENT)
    static class EventHandler {

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(GHOST_LAYER, WanderingSpiritModel::getTexturedModelData);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(GHOST.get(), WanderingSpiritRenderer::new);
            event.registerBlockEntityRenderer(BIRTH_BEACON_ENTITY.get(), BirthBeaconEntityRenderer::new);
        }
    }
}
