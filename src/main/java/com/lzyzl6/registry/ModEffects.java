package com.lzyzl6.registry;

import com.lzyzl6.effect.SoulLike;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> SOUL_LIKE = EFFECTS.register(
            "soul_like",
            () -> new SoulLike(MobEffectCategory.BENEFICIAL, 0xdcdcdc)
                    .withSoundOnAdded(ModSoundEvents.SOUL_LIKE.get()
                    )
    );

    public static void initialize() {

    }
}
