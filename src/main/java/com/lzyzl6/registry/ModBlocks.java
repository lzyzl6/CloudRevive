package com.lzyzl6.registry;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.block.QiBlock;
import com.lzyzl6.block.QiBlockCore;
import com.lzyzl6.block.QiFruitBush;
import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.lzyzl6.block.blockentity.QiBlockCoreEntity;
import com.lzyzl6.block.blockentity.QiBlockEntity;
import com.lzyzl6.block.blockentity.QiFruitBushEntity;
import com.lzyzl6.item.BirthBeaconItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModBlocks {

    private static <T extends Block> T registerBlock(String path, T block) {
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation(MOD_ID, path), block);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, path), new BlockItem(block, new Item.Properties()));
        return block;
    }

    public static <T extends BlockEntityType<?>> T registerBlockEntity(String path, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, path), blockEntityType);
    }



    public static final Block QI_FRUIT_BUSH = registerBlock("qi_fruit_bush", new QiFruitBush(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().noCollission().noOcclusion().sound(SoundType.SWEET_BERRY_BUSH).strength(0.1F, 0.0F).pushReaction(PushReaction.DESTROY)));
    public static final BlockEntityType<QiFruitBushEntity> QI_FRUIT_BUSH_BLOCK_ENTITY = registerBlockEntity("qi_fruit_bush_block_entity", BlockEntityType.Builder.of(QiFruitBushEntity::new, QI_FRUIT_BUSH).build(null));

    public static final Block QI_BLOCK = registerBlock( "qi_block", new QiBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BELL).sound(SoundType.GRASS).strength(0.8F, 1.0F)));
    public static final BlockEntityType<QiBlockEntity> QI_BLOCK_ENTITY = registerBlockEntity("qi_block_entity", BlockEntityType.Builder.of(QiBlockEntity::new, QI_BLOCK).build(null));

    public static final Block QI_BLOCK_CORE = registerBlock("qi_block_core", new QiBlockCore(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).sound(SoundType.GILDED_BLACKSTONE).strength(1.0F, 1.0F)));
    public static final BlockEntityType<QiBlockCoreEntity> QI_BLOCK_CORE_ENTITY = registerBlockEntity("qi_block_core_entity", BlockEntityType.Builder.of(QiBlockCoreEntity::new, QI_BLOCK_CORE).build(null));

    public static final Block BIRTH_BEACON = new BirthBeacon(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).sound(SoundType.METAL).strength(3.0F, 6.0F).noOcclusion()
            .instrument(NoteBlockInstrument.BANJO).explosionResistance(1.1f).lightLevel(state -> 15).isRedstoneConductor(Blocks::never));
    public static final BlockEntityType<BirthBeaconEntity> BIRTH_BEACON_ENTITY = registerBlockEntity("birth_beacon_entity", BlockEntityType.Builder.of(BirthBeaconEntity::new, BIRTH_BEACON).build(null));

    public static void initialize() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MOD_ID, "birth_beacon"), BIRTH_BEACON);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "birth_beacon"), new BirthBeaconItem(BIRTH_BEACON,new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant()));


    }
}
