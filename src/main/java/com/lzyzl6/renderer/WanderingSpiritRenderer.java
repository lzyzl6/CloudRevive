package com.lzyzl6.renderer;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.model.WanderingSpiritModel;
import com.lzyzl6.registry.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class WanderingSpiritRenderer extends MobRenderer<WanderingSpirit, WanderingSpiritModel>   {

    public static final ResourceLocation GHOST_TEXTURE = new ResourceLocation(MOD_ID,"textures/entity/wandering_spirit/wandering_spirit.png");

    public WanderingSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new WanderingSpiritModel(context.bakeLayer(ModModelLayers.GHOST)), 0.6f);
    }

    @Override
    protected int getBlockLightLevel(WanderingSpirit ghost, BlockPos blockPos) {
        return 15;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(WanderingSpirit entity) {
        return GHOST_TEXTURE;
    }

}
