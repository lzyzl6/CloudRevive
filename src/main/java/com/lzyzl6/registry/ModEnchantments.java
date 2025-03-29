package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantments {

    public static final Enchantment SOUL_BIND = new BindEnchantment();

    public static void initialize() {
        Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(MOD_ID, "bind"), SOUL_BIND);
    }
}
