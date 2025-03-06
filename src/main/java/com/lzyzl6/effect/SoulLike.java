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
        super(mobEffectCategory, i); // 显示的颜色
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration >= 0;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 4, true, false, false));
            entity.invulnerableTime = Objects.requireNonNull(entity.getEffect(ModEffects.SOUL_LIKE)).getDuration();
            entity.setInvulnerable(entity.invulnerableTime > 0);
            entity.setInvisible(entity.invulnerableTime > 0);
            entity.setSilent(entity.invulnerableTime > 0);
            return true;
        }
        return false;
    }

@Override
    public void onEffectStarted(@NotNull LivingEntity livingEntity, int i) {
        if (livingEntity instanceof Player) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 4, true, false, false));
        }
    }
}
