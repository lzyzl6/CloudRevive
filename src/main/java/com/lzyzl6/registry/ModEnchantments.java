package com.lzyzl6.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantments {

    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_EFFECT_COMPONENT_TYPE = DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,MOD_ID);

    static UnaryOperator<DataComponentType.Builder<Unit>> unaryOperator =builder -> builder.persistent(Unit.CODEC);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Unit>> SOUL_BIND = ENCHANTMENT_EFFECT_COMPONENT_TYPE.register(
            "soul_bind",
            () -> (unaryOperator.apply(DataComponentType.builder())).build()
    );

    public static final ResourceKey<DataComponentType<?>> SOUL_BIND_KEY = ResourceKey.create(Registries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_bind"));
    public static final ResourceKey<Enchantment> BIND = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, "bind"));

    public static void initialize() {

    }
}
