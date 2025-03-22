package com.lzyzl6.data;

import com.lzyzl6.data.provider.AdvancementsProvider;
import com.lzyzl6.data.provider.LootTablesProvider;
import com.lzyzl6.data.provider.RecipeProvider;
import com.lzyzl6.data.provider.DynamicProvider;
import com.lzyzl6.registry.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(DynamicProvider::new);
        pack.addProvider(LootTablesProvider::new);
        pack.addProvider(AdvancementsProvider::new);
    }

    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
    }
}
