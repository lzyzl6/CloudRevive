package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;

public class ModEnchantmentEffects {

    private static MapCodec<? extends EnchantmentLocationBasedEffect> bind = register("bind", BindEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentLocationBasedEffect> register(String name, MapCodec<? extends EnchantmentLocationBasedEffect> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, name, codec);
    }

    public static void initialize() {
    }
}
