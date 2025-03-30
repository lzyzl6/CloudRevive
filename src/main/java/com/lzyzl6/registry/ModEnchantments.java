package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT,MOD_ID);

    public static final RegistryObject<Enchantment> SOUL_BIND = ENCHANTMENTS.register(
            "bind",
            BindEnchantment::new
    );

    public static void initialize() {

    }
}
