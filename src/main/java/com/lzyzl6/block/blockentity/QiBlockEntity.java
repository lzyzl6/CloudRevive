package com.lzyzl6.block.blockentity;

import com.lzyzl6.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class QiBlockEntity extends BlockEntity {

    public QiBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlocks.QI_BLOCK_ENTITY, blockPos, blockState);
    }
}
