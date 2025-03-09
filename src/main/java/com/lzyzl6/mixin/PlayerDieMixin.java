package com.lzyzl6.mixin;


import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.event.PlayerDieCallback;
import com.lzyzl6.registry.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(LivingEntity.class)
public class PlayerDieMixin {
	@Inject(method = "dropAllDeathLoot",at = @At(value = "HEAD"))
	private void onceDied(final CallbackInfo info) throws IOException {
		if((LivingEntity) (Object) this instanceof Player){
			Player player = (Player) (Object) this;
			ServerLevel serverLevel = (ServerLevel) player.level();
			if(!serverLevel.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
				PlayerDieCallback.EVENT.invoker().summonGhost(player, new WanderingSpirit(ModEntities.GHOST, player.level()));
			}
		}
	}
}