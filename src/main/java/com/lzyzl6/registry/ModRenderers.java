package com.lzyzl6.registry;

import com.lzyzl6.renderer.WanderingSpiritRenderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModRenderers {


    public static void initialize() {
        EntityRendererRegistry.register(ModEntities.GHOST, WanderingSpiritRenderer::new);
    }

}
