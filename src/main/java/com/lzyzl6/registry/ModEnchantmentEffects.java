package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LB_ENCHANTMENT_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE,MOD_ID);

    public static final DeferredHolder<MapCodec<? extends EnchantmentLocationBasedEffect>,MapCodec<? extends EnchantmentLocationBasedEffect>> BIND_CODEC = LB_ENCHANTMENT_EFFECTS.register(
            "bind", () -> BindEnchantmentEffect.CODEC);

    public static void initialize() {

    }
}
