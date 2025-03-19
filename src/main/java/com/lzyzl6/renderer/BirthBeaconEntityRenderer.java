package com.lzyzl6.renderer;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.lzyzl6.block.BirthBeacon.CHARGED;

@Environment(EnvType.CLIENT)
public class BirthBeaconEntityRenderer implements BlockEntityRenderer<BirthBeaconEntity> {

    private static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/end_gateway_beam.png");

    public BirthBeaconEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BirthBeaconEntity blockEntity, float f, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int overlay) {
        BirthBeacon beacon = (BirthBeacon) blockEntity.getBlockState().getBlock();
        if (blockEntity.getBlockState().getValue(CHARGED)) {
            beacon.cooldown--;
            BeaconRenderer.renderBeaconBeam(poseStack, multiBufferSource, BEAM_LOCATION, f, 0.8f, 2,1, 1024, 0xFF55FF, 0.2F, 0.175F);
        }
    }
    

    @Override
    public int getViewDistance() {
        return 256;
    }
}
