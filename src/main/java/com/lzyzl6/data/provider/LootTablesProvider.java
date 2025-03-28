package com.lzyzl6.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

import static com.lzyzl6.registry.ModBlocks.*;
import static com.lzyzl6.registry.ModItems.CORE_QI;
import static com.lzyzl6.registry.ModItems.QI_FRUIT;


public class LootTablesProvider extends FabricBlockLootTableProvider {

    public LootTablesProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(BIRTH_BEACON);
        add(QI_FRUIT_BUSH, LootTable.lootTable().pool(
                applyExplosionCondition(QI_FRUIT, LootPool.lootPool().setRolls(new UniformGenerator(
                        new ConstantValue(0),new ConstantValue(2)))
                        .with(LootItem.lootTableItem(QI_FRUIT).build())
                ).build()
        ));
        add(QI_BLOCK_CORE, LootTable.lootTable().pool(
                applyExplosionCondition(CORE_QI, LootPool.lootPool().setRolls(new UniformGenerator(
                                new ConstantValue(1),new ConstantValue(3)))
                        .with(LootItem.lootTableItem(CORE_QI).build())
                ).build()
        ));
    }
}
