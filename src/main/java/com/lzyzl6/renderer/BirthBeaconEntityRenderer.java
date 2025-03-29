package com.lzyzl6.renderer;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.lzyzl6.block.BirthBeacon.CHARGED;


public class BirthBeaconEntityRenderer implements BlockEntityRenderer<BirthBeaconEntity> {

    private static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/end_gateway_beam.png");

    public BirthBeaconEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BirthBeaconEntity blockEntity, float f, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int overlay) {
        BirthBeacon beacon = (BirthBeacon) blockEntity.getBlockState().getBlock();
        long l = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();
        if (blockEntity.getBlockState().getValue(CHARGED)) {
            beacon.cooldown--;
            BeaconRenderer.renderBeaconBeam(poseStack, multiBufferSource, BEAM_LOCATION, f, 0.8f, l,-256, 1024, 0xFF55FF, 0.2F, 0.175F);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull BirthBeaconEntity beaconBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(BirthBeaconEntity beaconBlockEntity, Vec3 vec3) {
        return Vec3.atCenterOf(beaconBlockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(vec3.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }
}
