package com.lzyzl6.mixin;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Vector;

import static com.lzyzl6.mixin.MixinMethod.handle;

@Mixin(LivingEntity.class)
public class PlayerDieMixin {
	public Vector<ItemStack> itemStacks = new Vector<>();

	@Inject(method = "dropAllDeathLoot",at = @At(value = "HEAD"))
	private void onceDied(final CallbackInfo info) {
		if((LivingEntity) (Object) this instanceof Player){
			Player player = (Player) (Object) this;
			ServerLevel serverLevel = (ServerLevel) player.level();
			if(!serverLevel.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
				handle(player);
			}
		}
	}


}