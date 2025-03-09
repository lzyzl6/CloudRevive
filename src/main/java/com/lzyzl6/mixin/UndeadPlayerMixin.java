package com.lzyzl6.mixin;

import com.lzyzl6.registry.ModEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class UndeadPlayerMixin {
    @Inject(method = "isInvulnerableTo",at = @At(value = "HEAD"), cancellable = true)
    private void hurtResist( final CallbackInfoReturnable<Boolean> info) {
        Player player = (Player) (Object) this;
        if(player.hasEffect(ModEffects.SOUL_LIKE)) {
            info.setReturnValue(true);
        }
    }
}
