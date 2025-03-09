package com.lzyzl6.mixin;

import com.lzyzl6.registry.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ExplosionResistMixin {
    @Inject(method = "ignoreExplosion",at = @At(value = "HEAD"), cancellable = true)
    private void handleExplosionResist(final CallbackInfoReturnable<Boolean> info) {
        if((Entity)(Object) this instanceof LivingEntity entity) {
            if(entity.hasEffect(ModEffects.SOUL_LIKE)) {
                info.setReturnValue(true);
            }
        }
    }
}
