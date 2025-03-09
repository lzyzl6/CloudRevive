package com.lzyzl6.registry;

import com.lzyzl6.effect.SoulLike;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEffects {

    public static final MobEffect SLLIKE = new SoulLike(MobEffectCategory.BENEFICIAL, 0xdcdcdc);
    public static final Holder<MobEffect> SOUL_LIKE = register(
            "soul_like", new SoulLike(MobEffectCategory.BENEFICIAL, 0xdcdcdc).withSoundOnAdded(ModSoundEvents.SOUL_LIKE)
    );

    private static Holder<MobEffect> register(String string, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(MOD_ID,string), mobEffect);
    }

    public static void initialize() {
        Registry.register(BuiltInRegistries.MOB_EFFECT,  ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_like"), SLLIKE);
    }
}
