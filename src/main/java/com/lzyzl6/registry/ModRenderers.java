package com.lzyzl6.registry;

import com.lzyzl6.renderer.WanderingSpiritRenderer;
import com.lzyzl6.renderer.BirthBeaconEntityRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;

@Environment(EnvType.CLIENT)
public class ModRenderers {


    public static void initialize() {
        EntityRendererRegistry.register(ModEntities.GHOST, WanderingSpiritRenderer::new);
        BlockEntityRendererRegistryImpl.register(ModBlocks.BIRTH_BEACON_ENTITY,BirthBeaconEntityRenderer::new);
    }

}
