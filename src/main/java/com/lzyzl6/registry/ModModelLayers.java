package com.lzyzl6.registry;

import com.lzyzl6.model.WanderingSpiritModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.lzyzl6.CloudRevive.MOD_ID;

@Environment(EnvType.CLIENT)
public class ModModelLayers {

    public static final ModelLayerLocation GHOST = new ModelLayerLocation(new ResourceLocation(MOD_ID, "ghost"),
            "main");

    public static void initialize() {
        EntityModelLayerRegistry.registerModelLayer(GHOST, WanderingSpiritModel::getTexturedModelData);
    }
}
