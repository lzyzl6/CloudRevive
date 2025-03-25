package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantmentEffects {

    public static MapCodec<? extends EnchantmentLocationBasedEffect> BIND_CODEC = register("bind", BindEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentLocationBasedEffect> register(String name, MapCodec<? extends EnchantmentLocationBasedEffect> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), codec);
    }

    public static void initialize() {
    }
}
