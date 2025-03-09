package com.lzyzl6.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import static net.minecraft.world.level.block.entity.BeaconBlockEntity.playSound;

public class BirthBeaconEntity extends BlockEntity {

    
    public BirthBeaconEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BirthBeaconEntity BirthBeaconEntity) {
        
    }


    @Override
    public void setRemoved() {
        if (this.level != null) {
            playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
        }
        super.setRemoved();
    }
}
