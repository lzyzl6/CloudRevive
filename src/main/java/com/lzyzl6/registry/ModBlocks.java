package com.lzyzl6.registry;

import com.lzyzl6.block.BirthBeacon;
import com.lzyzl6.block.QiBlock;
import com.lzyzl6.block.QiBlockCore;
import com.lzyzl6.block.QiFruitBush;
import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.lzyzl6.block.blockentity.QiBlockCoreEntity;
import com.lzyzl6.block.blockentity.QiBlockEntity;
import com.lzyzl6.block.blockentity.QiFruitBushEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModBlocks {

    private static <T extends Block> T registerBlock(String path, T block) {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, path), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, path), new BlockItem(block, new Item.Properties()));
        return block;
    }

    public static <T extends BlockEntityType<?>> T registerBlockEntity(String path, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, path), blockEntityType);
    }


    public static final Block QI_FRUIT_BUSH = registerBlock("qi_fruit_bush", new QiFruitBush(BlockBehaviour.Properties.of().noCollission().sound(SoundType.SWEET_BERRY_BUSH).strength(0.1F, 0.0F)));
    public static final BlockEntityType<QiFruitBushEntity> QI_FRUIT_BUSH_BLOCK_ENTITY = registerBlockEntity("qi_fruit_bush_block_entity", BlockEntityType.Builder.of((pos, state) -> new QiFruitBushEntity(BlockEntityType.STRUCTURE_BLOCK,pos, state),QI_FRUIT_BUSH).build());

    public static final Block QI_BLOCK = registerBlock( "qi_block", new QiBlock(BlockBehaviour.Properties.of().sound(SoundType.BONE_BLOCK).strength(0.8F, 1.0F)));
    public static final BlockEntityType<QiBlockEntity> QI_BLOCK_ENTITY = registerBlockEntity("qi_block_entity", BlockEntityType.Builder.of((pos, state) -> new QiBlockEntity(BlockEntityType.STRUCTURE_BLOCK, pos, state),QI_BLOCK).build());

    public static final Block QI_BLOCK_CORE = registerBlock("qi_block_core", new QiBlockCore(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.0F, 1.0F)));
    public static final BlockEntityType<QiBlockCoreEntity> QI_BLOCK_CORE_ENTITY = registerBlockEntity("qi_block_core_entity", BlockEntityType.Builder.of((pos, state) -> new QiBlockCoreEntity(BlockEntityType.STRUCTURE_BLOCK, pos, state),QI_BLOCK_CORE).build());

    public static final Block BIRTH_BEACON = new BirthBeacon(BlockBehaviour.Properties.of().noCollission().sound(SoundType.METAL).strength(1.5F, 6.0F));
    public static final BlockEntityType<BirthBeaconEntity> BIRTH_BEACON_ENTITY = registerBlockEntity("birth_beacon_entity", BlockEntityType.Builder.of((pos, state) -> new BirthBeaconEntity(BlockEntityType.BEACON, pos, state),BIRTH_BEACON).build());

    public static void initialize() {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "birth_beacon"), BIRTH_BEACON);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "birth_beacon"), new BlockItem(BIRTH_BEACON, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

    }
}
