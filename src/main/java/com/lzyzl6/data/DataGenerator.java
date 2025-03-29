package com.lzyzl6.data;

import com.lzyzl6.data.provider.AdvancementsProvider;
import com.lzyzl6.data.provider.LootTablesProvider;
import com.lzyzl6.data.provider.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(LootTablesProvider::new);
        pack.addProvider(AdvancementsProvider::new);
    }
}
