package com.lzyzl6.block.blockentity;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.lzyzl6.block.BirthBeacon.CHARGED;
import static com.lzyzl6.data.storage.FileWork.matchBlockAndFix;
import static net.minecraft.world.level.block.entity.BeaconBlockEntity.playSound;

public class BirthBeaconEntity extends BlockEntity implements BeaconBeamBlock {

    public UUID playerUUID = null;

    public BirthBeaconEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BirthBeaconEntity birthBeaconEntity) {
        BirthBeacon beacon = (BirthBeacon) birthBeaconEntity.getBlockState().getBlock();
        BlockPos blockPosEntity = birthBeaconEntity.getBlockPos();
        if(blockState.getValue(CHARGED)) {
            if (beacon.cooldown < 0) {
                birthBeaconEntity.setChanged();
                if (birthBeaconEntity.level != null) {
                    birthBeaconEntity.level.setBlock(blockPos, birthBeaconEntity.getBlockState().setValue(CHARGED, false), 3);
                }
            } else if(beacon.cooldown > 478) {
                playSound(level, blockPos, SoundEvents.BEACON_ACTIVATE);
                level.players().forEach(player -> {
                    if(player.position().closerThan(new Vec3(blockPosEntity.getX(), blockPosEntity.getY(), blockPosEntity.getZ()), 16d)) {
                        player.addEffect(new MobEffectInstance(ModEffects.SOUL_LIKE, 200, 0, true, true, true));
                    }
                });
            }
            //匹配玩家与检索游魂
            matchBlockAndFix(birthBeaconEntity);
            UUID uuid = birthBeaconEntity.playerUUID;
            if (uuid != null) {
                AtomicBoolean shouldTell = new AtomicBoolean(false);
                level.getEntitiesOfClass(WanderingSpirit.class,AABB.ofSize(Vec3.atCenterOf(birthBeaconEntity.getBlockPos()), 59999968, 59999968, 59999968))
                .forEach(spirit -> {
                    System.out.println(spirit.locateTargetUUID());
                    if(spirit.locateTargetUUID() != null && spirit.locateTargetUUID().equals(uuid)) {
                        shouldTell.set(true);
                        spirit.setPos(blockPosEntity.getX() + new Random().nextDouble(-5,5), blockPosEntity.getY() + new Random().nextDouble(5,10), blockPosEntity.getZ() + new Random().nextDouble(-5,5));
                        spirit.playSound(SoundEvents.BELL_BLOCK);
                        spirit.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                    }
                });
                if(shouldTell.get()) {
                    level.players().forEach(player -> {
                        if(player.position().closerThan(new Vec3(blockPosEntity.getX(), blockPosEntity.getY(), blockPosEntity.getZ()), 16d)) {
                            player.displayClientMessage(Component.translatable("block.cloud_revive.call_success"), true);
                            player.sendSystemMessage(Component.literal(""));
                            player.sendSystemMessage(Component.translatable("block.cloud_revive.call_success"));
                            player.sendSystemMessage(Component.literal(""));
                        }
                    });
                } else {
                    level.players().forEach(player -> {
                        if(player.position().closerThan(new Vec3(blockPosEntity.getX(), blockPosEntity.getY(), blockPosEntity.getZ()), 16d)) {
                            player.displayClientMessage(Component.translatable("block.cloud_revive.call_fail"), true);
                            player.sendSystemMessage(Component.literal(""));
                            player.sendSystemMessage(Component.translatable("block.cloud_revive.call_fail"));
                            player.sendSystemMessage(Component.literal(""));
                        }
                    });
                }
                birthBeaconEntity.playerUUID = null;
            }
        }
    }

    @Override
    public void setRemoved() {
        if (this.level != null) {
            playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
        }
        super.setRemoved();
    }

    @Override
    public @NotNull DyeColor getColor() {
        return DyeColor.PURPLE;
    }
}
