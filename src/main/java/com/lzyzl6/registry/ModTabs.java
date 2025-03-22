package com.lzyzl6.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import static com.lzyzl6.CloudRevive.MOD_ID;

public final class ModTabs {

    public static final CreativeModeTab MAIN_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MOD_ID, "main_tab"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CORE_QI))
            .title(Component.translatable("tab.cloud_revive.main_tab"))
            .displayItems((context, Output) -> {
                Output.accept(ModBlocks.QI_FRUIT_BUSH.asItem());
                Output.accept(ModBlocks.QI_BLOCK.asItem());
                Output.accept(ModBlocks.QI_BLOCK_CORE.asItem());
                Output.accept(ModBlocks.BIRTH_BEACON.asItem());
                Output.accept(ModItems.START_CAGE);
                Output.accept(ModItems.CAGE);
                Output.accept(ModItems.CHAOS_CAGE);
                Output.accept(ModItems.CORE_QI);
                Output.accept(ModItems.CHAOS_QI);
                Output.accept(ModItems.SKY_QI);
                Output.accept(ModItems.GROUND_QI);
                Output.accept(ModItems.PEOPLE_QI);
                Output.accept(ModItems.DEAD_QI);
                Output.accept(ModItems.PEARL);
                Output.accept(ModItems.SOUL_PEARL);
                Output.accept(ModItems.CHAOS_PEARL);
                Output.accept(ModItems.QI_FRUIT);
                Output.accept(ModItems.SOUL_FRUIT);
                context.holders().lookup(Registries.ENCHANTMENT).ifPresent(registryLookup -> {
                    registryLookup.listElements()
                            .map(reference -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(reference, (reference.value()).getMaxLevel())))
                            .forEach(itemStack -> {
                                if(EnchantmentHelper.getEnchantmentsForCrafting(itemStack).keySet().stream().anyMatch(enchantment -> enchantment.is(ModEnchantments.BIND))) {
                                    Output.accept(itemStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                                }
                            });
                });

            })
            .build());
    public static void initialize() {

    }
}
