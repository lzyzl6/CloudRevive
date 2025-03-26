package com.lzyzl6.registry;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.block.QiBlock;
import com.lzyzl6.block.QiBlockCore;
import com.lzyzl6.block.QiFruitBush;
import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.lzyzl6.block.blockentity.QiBlockCoreEntity;
import com.lzyzl6.block.blockentity.QiBlockEntity;
import com.lzyzl6.block.blockentity.QiFruitBushEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final DeferredBlock<Block> QI_BLOCK_CORE = BLOCKS.register("qi_block_core", () -> new QiBlockCore(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.GILDED_BLACKSTONE)
            .strength(1.0F, 1.0F)
    ));

    public static final DeferredBlock<Block> QI_FRUIT_BUSH = BLOCKS.register("qi_fruit_bush", () -> new QiFruitBush(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .instabreak()
            .noCollission()
            .noOcclusion()
            .sound(SoundType.SWEET_BERRY_BUSH)
            .strength(0.1F, 0.0F)
            .pushReaction(PushReaction.DESTROY)
    ));

    public static final DeferredBlock<Block> QI_BLOCK = BLOCKS.register("qi_block", () -> new QiBlock(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BELL)
            .sound(SoundType.SPONGE)
            .strength(0.8F, 1.0F)
    ));

    public static final DeferredBlock<Block> BIRTH_BEACON = BLOCKS.register("birth_beacon", () -> new BirthBeacon(BlockBehaviour.Properties.of()
            .mapColor(MapColor.DIAMOND)
            .sound(SoundType.METAL)
            .strength(3.0F, 6.0F)
            .noOcclusion()
            .instrument(NoteBlockInstrument.BANJO)
            .lightLevel(state -> 15)
    ));

    public static final Supplier<BlockEntityType<QiFruitBushEntity>> QI_FRUIT_BUSH_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "my_block_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            QiFruitBushEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            QI_FRUIT_BUSH.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );

    public static final Supplier<BlockEntityType<QiBlockEntity>> QI_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "qi_block_entity",
            () -> BlockEntityType.Builder.of(
                            QiBlockEntity::new,
                            QI_BLOCK.get()
                    )
                    .build(null)
    );

    public static final Supplier<BlockEntityType<QiBlockCoreEntity>> QI_BLOCK_CORE_ENTITY = BLOCK_ENTITY_TYPES.register(
            "qi_block_core_entity",
            () -> BlockEntityType.Builder.of(
                            QiBlockCoreEntity::new,
                            QI_BLOCK_CORE.get()
                    )
                    .build(null)
    );

    public static final Supplier<BlockEntityType<BirthBeaconEntity>> BIRTH_BEACON_ENTITY = BLOCK_ENTITY_TYPES.register(
            "birth_beacon_entity",
            () -> BlockEntityType.Builder.of(
                            BirthBeaconEntity::new,
                            BIRTH_BEACON.get()
                    )
                    .build(null)
    );

    public static void initialize() {

    }
}
