package com.lzyzl6.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.lzyzl6.registry.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Vector;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    private final Vector<ItemStack> itemStacks = new Vector<>();

    @Inject(method = "respawn",at = @At(value = "HEAD"))
    public void getItems(final CallbackInfoReturnable<ServerPlayer> info , @Local ServerPlayer serverPlayer) {
        itemStacks.clear();
        for(int i = 0; i < serverPlayer.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = serverPlayer.getInventory().getItem(i);
            if (EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND)) {
                itemStacks.add(itemStack);
            }
        }
    }

    @Inject(method = "respawn",at = @At(value = "RETURN"))
    public void giveItems(final CallbackInfoReturnable<ServerPlayer> info , @Local(ordinal = 1) ServerPlayer serverPlayer2) {
        for (ItemStack itemStack : itemStacks) {
            if(serverPlayer2.getInventory().getFreeSlot() < itemStacks.size()) {
                serverPlayer2.level().addFreshEntity(new ItemEntity(serverPlayer2.level(), serverPlayer2.getX(), serverPlayer2.getY(), serverPlayer2.getZ(), itemStack));
            }
            else
            {
                serverPlayer2.getInventory().add(itemStack);
            }
        }
    }

    @Inject(method = "placeNewPlayer",at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;initInventoryMenu()V", shift = At.Shift.AFTER))
    public void infoNew(final CallbackInfo info ,@Local ServerPlayer serverPlayer) {
        serverPlayer.sendSystemMessage(Component.literal(""));
        serverPlayer.sendSystemMessage(Component.translatable("chat.cloud_revive.player_connect1"));
        serverPlayer.sendSystemMessage(Component.translatable("chat.cloud_revive.player_connect2"));
        serverPlayer.sendSystemMessage(Component.translatable("chat.cloud_revive.player_connect3"));
        serverPlayer.sendSystemMessage(Component.literal(""));
    }
}
