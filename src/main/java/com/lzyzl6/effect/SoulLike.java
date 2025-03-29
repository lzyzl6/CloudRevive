package com.lzyzl6.effect;

import com.lzyzl6.registry.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SoulLike extends MobEffect {

    public SoulLike(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    boolean onStart = true;

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0 && amplifier >= 0;
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int i) {
        if (livingEntity instanceof Player) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Objects.requireNonNull(livingEntity.getEffect(ModEffects.SOUL_LIKE)).getDuration(), 4, true, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, Objects.requireNonNull(livingEntity.getEffect(ModEffects.SOUL_LIKE)).getDuration(), 4, true, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING,  Objects.requireNonNull(livingEntity.getEffect(ModEffects.SOUL_LIKE)).getDuration(), 4, true, false, false));
        }
    }
}
