package com.lzyzl6;

import com.lzyzl6.registry.ModModelLayers;
import com.lzyzl6.registry.ModRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

import static com.lzyzl6.registry.ModBlocks.BIRTH_BEACON;
import static com.lzyzl6.registry.ModBlocks.QI_FRUIT_BUSH;

public class CloudReviveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModModelLayers.initialize();
        ModRenderers.initialize();

        BlockRenderLayerMap.INSTANCE.putBlock(BIRTH_BEACON, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(QI_FRUIT_BUSH, RenderType.cutout());
    }
}
