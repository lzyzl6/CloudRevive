package com.lzyzl6;

import com.lzyzl6.registry.ModModelLayers;
import com.lzyzl6.registry.ModRenderers;
import net.fabricmc.api.ClientModInitializer;

public class CloudReviveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModModelLayers.initialize();
        ModRenderers.initialize();
    }
}
