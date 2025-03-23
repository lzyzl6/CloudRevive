package com.lzyzl6.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.lzyzl6.registry.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Vector;

import static net.minecraft.world.level.block.ChestBlock.getContainer;

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
        boolean shouldMove = false;
        BlockPos blockPos =  BlockPos.ZERO.above(81);
        BlockState blockState = serverPlayer2.level().getBlockState(blockPos);
        for (ItemStack itemStack : itemStacks) {
            if(serverPlayer2.getInventory().getFreeSlot() == -1) {
                if(serverPlayer2.getRespawnPosition() != null) {
                    Vec3 vec3 = serverPlayer2.getRespawnPosition().getCenter().add(0, 1, 0);
                    serverPlayer2.level().addFreshEntity(new ItemEntity(serverPlayer2.level(),vec3.x , vec3.y, vec3.z, itemStack));
                } else {
                    serverPlayer2.sendSystemMessage(Component.translatable("chat.bind.no_respawn_position"));
                    if(!serverPlayer2.level().getBlockState(blockPos).is(Blocks.CHEST)) {
                        blockState = Blocks.CHEST.defaultBlockState()
                                .setValue(ChestBlock.FACING, Direction.EAST)
                                .setValue(ChestBlock.WATERLOGGED, false)
                                .setValue(ChestBlock.TYPE, ChestType.SINGLE);
                        serverPlayer2.level().setBlock(blockPos, blockState, 83);
                    }
                    shouldMove = true;
                    break;
                }
            } else {
                serverPlayer2.getInventory().add(itemStack);
            }
        }
        if(shouldMove) {
            ChestBlock chest = (ChestBlock) blockState.getBlock();
            Container container = getContainer(chest, blockState, serverPlayer2.level(), blockPos,false);
            int j =0;
            for (int i = 0; i < itemStacks.size(); i++) {
                if (container != null) {
                    ItemStack itemStack = itemStacks.get(i);
                    if(!itemStack.isEmpty()) {
                        container.setItem(j, itemStack);
                        j++;
                    }
                } else{
                    serverPlayer2.sendSystemMessage(Component.translatable("chat.bind.container_null"));
                }
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
