package com.lzyzl6.registry;

import com.lzyzl6.event.PlayerDieCallback;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static com.lzyzl6.data.storage.FileWork.*;

public class ModEvents {


    public static void initialize() {
        PlayerDieCallback.EVENT.register((player, ghost) -> {
            //生成匹配文件
            //文件路径：/rootDir/levelName/playerUUID/ghostUUID
            //初设targetUUID
            ghost.targetUUID = UUID.fromString(createMatchFile(ghost,spiltDirByString(player.getStringUUID(),spiltDirByString(getLevelName(player),rootDir()))).getName());

            Vec3 vec3 = player.position();
            String dimension = player.level().dimensionTypeRegistration().getRegisteredName();

            //生成游魂
            if(vec3.y() < 0) {
                ghost.setPos(vec3.x(), 64, vec3.z());
            } else {
                ghost.setPos(vec3);
            }
            player.level().addFreshEntity(ghost);

            //设置游魂属性
            String name = player.getDisplayName().getString().concat((Component.translatable("chat.cloud_revive.genitive_case")).getString()).concat(Component.translatable("entity.cloud_revive.ghost").getString());
            ghost.setCustomName(Component.nullToEmpty(name));

            //通知玩家
            player.sendSystemMessage(Component.translatable("chat.cloud_revive.ghost_summoned"));
            player.sendSystemMessage(Component.literal("§l§e(" + (int)vec3.x() +"§l§e,"  + (int)vec3.z() + "§l§e)"));
            player.sendSystemMessage(Component.translatable("chat.cloud_revive.ghost_summoned_dimension"));
            player.sendSystemMessage(Component.literal("§l§e" + dimension));

            //消失诅咒处理
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (!itemStack.isEmpty() && EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    player.getInventory().removeItemNoUpdate(i);
                }
            }

            //转移物品
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ghost.getInventory().addItem(player.getInventory().removeItem(i, player.getInventory().getItem(i).getCount()));
            }

//          //储存物品至本地
//          ghost.listTag = ghost.getInventory().createTag(ghost.provider);
//          ghost.getInventory().fromTag(ghost.listTag, ghost.provider);
            //事件执行成功
            return InteractionResult.SUCCESS;
        });
    }
}
