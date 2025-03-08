package com.lzyzl6.block;

import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.lzyzl6.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BirthBeacon extends BaseEntityBlock {

    public static final MapCodec<BirthBeacon> CODEC = simpleCodec(BirthBeacon::new);
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    public BirthBeacon(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(CHARGED, false));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
        level.setBlockState(pos, state.with(CHARGED, true));
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull MapCodec<BirthBeacon> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BirthBeaconEntity(ModBlocks.BIRTH_BEACON_ENTITY,blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlocks.BIRTH_BEACON_ENTITY, BirthBeaconEntity::tick);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }
}
