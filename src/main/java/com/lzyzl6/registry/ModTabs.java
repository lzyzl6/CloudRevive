package com.lzyzl6.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.lzyzl6.CloudRevive.MOD_ID;

public final class ModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab",  () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.CORE_QI.get()))
            .title(Component.translatable("tab.cloud_revive.main_tab"))
            .displayItems((context, Output) -> {
                Output.accept(ModItems.QI_FRUIT_BUSH_ITEM.get());
                Output.accept(ModItems.QI_BLOCK_ITEM.get());
                Output.accept(ModItems.QI_BLOCK_CORE_ITEM.get());
                Output.accept(ModItems.BIRTH_BEACON_ITEM.get());
                Output.accept(ModItems.START_CAGE.get());
                Output.accept(ModItems.CAGE.get());
                Output.accept(ModItems.CHAOS_CAGE.get());
                Output.accept(ModItems.CORE_QI.get());
                Output.accept(ModItems.CHAOS_QI.get());
                Output.accept(ModItems.SKY_QI.get());
                Output.accept(ModItems.GROUND_QI.get());
                Output.accept(ModItems.PEOPLE_QI.get());
                Output.accept(ModItems.DEAD_QI.get());
                Output.accept(ModItems.PEARL.get());
                Output.accept(ModItems.SOUL_PEARL.get());
                Output.accept(ModItems.CHAOS_PEARL.get());
                Output.accept(ModItems.QI_FRUIT.get());
                Output.accept(ModItems.SOUL_FRUIT.get());
                Output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.SOUL_BIND.get(),1)));
            })
            .build()
    );

    public static void initialize() {

    }
}
