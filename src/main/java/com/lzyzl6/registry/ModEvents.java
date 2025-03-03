package com.lzyzl6.registry;

import com.lzyzl6.event.PlayerDieCallback;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

import static com.lzyzl6.data.storage.FileWork.*;

public class ModEvents {


    public static void initialize() {
        PlayerDieCallback.EVENT.register((player, ghost) -> {
            //生成匹配文件
            //文件路径：/rootDir/levelName/playerUUID/ghostUUID
            createMatchFile(ghost,spiltDirByString(player.getStringUUID(),spiltDirByString(getLevelName(ghost),rootDir())));

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
            ghost.setPersistenceRequired();
            ghost.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20000, 4, false, false, false));

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

            //事件执行成功
            return InteractionResult.SUCCESS;
        });
    }
}
