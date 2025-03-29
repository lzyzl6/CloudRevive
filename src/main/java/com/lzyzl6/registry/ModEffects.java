package com.lzyzl6.registry;

import com.lzyzl6.effect.SoulLike;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEffects {

    public static final MobEffect SOUL_LIKE = new SoulLike(MobEffectCategory.BENEFICIAL, 0xdcdcdc);

    public static void initialize() {
        Registry.register(BuiltInRegistries.MOB_EFFECT,  new ResourceLocation(MOD_ID, "soul_like"), SOUL_LIKE);
    }
}
