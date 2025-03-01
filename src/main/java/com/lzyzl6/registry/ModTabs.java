package com.lzyzl6.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static com.lzyzl6.CloudRevive.MOD_ID;

public final class ModTabs {

    public static final CreativeModeTab MAIN_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MOD_ID, "main_tab"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CORE_QI))
            .title(Component.translatable("tab.cloud_revive.main_tab"))
            .displayItems((context, Output) -> {
                Output.accept(ModItems.CAGE);
                Output.accept(ModItems.PEARL);
                Output.accept(ModItems.CORE_QI);
                Output.accept(ModItems.SKY_QI);
                Output.accept(ModItems.GROUND_QI);
                Output.accept(ModItems.PEOPLE_QI);
                Output.accept(ModItems.DEAD_QI);
                Output.accept(ModItems.QI_FRUIT);
            })
            .build());

    public static void initialize() {

    }
}
