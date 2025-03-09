package com.lzyzl6.registry;

import com.lzyzl6.event.PlayerDieCallback;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.lzyzl6.data.storage.FileWork.*;

public class ModEvents {


    public static void initialize() {

        PlayerDieCallback.EVENT.register((player, ghost) -> {

            //生成匹配文件
            //文件路径：/rootDir/levelName/playerUUID/ghostUUID
            createMatchFile(ghost, spiltDirByString(player.getStringUUID(), spiltDirByString(getLevelName(player), rootDir())));

            //生成相遇状态文件
            //文件路径：/rootDir/levelNameGD/ghostUUID/if_meet
            String levelNameGD = getLevelName(player);
            levelNameGD += "GD";
            createMeetFile(ghost,spiltDirByString(levelNameGD, rootDir()));

            Vec3 vec3 = player.position();
            String dimension = player.level().dimensionTypeRegistration().getRegisteredName();

            //生成游魂
            if (vec3.y() < 0) {
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
            player.sendSystemMessage(Component.literal("\n--------------------------------------------------"));
            player.sendSystemMessage(Component.translatable("chat.cloud_revive.ghost_summoned"));
            player.sendSystemMessage(Component.literal("§l§e(" + (int) vec3.x() + "§r§e,§l§e" + (int) vec3.y() + "§r§e,§l§e" + (int) vec3.z() + "§l§e)"));
            player.sendSystemMessage(Component.translatable("chat.cloud_revive.ghost_summoned_dimension"));
            player.sendSystemMessage(Component.literal("§l§e" + dimension));
            player.sendSystemMessage(Component.literal("--------------------------------------------------\n"));

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

            //Accessories模组处理
            if (isAccessoriesInstalled()) {
                try {
                    Set<String> keySet = Objects.requireNonNull(AccessoriesCapability.get(player)).getContainers().keySet();
                    if (!keySet.isEmpty()) {
                        Collection<AccessoriesContainer> container = Objects.requireNonNull(AccessoriesCapability.get(player)).getContainers().values();
                        for (AccessoriesContainer accessoriesContainer : container) {
                            for (int i = 0; i < accessoriesContainer.getSize(); i++) {
                                ExpandedSimpleContainer normalContainer = accessoriesContainer.getAccessories();
                                ExpandedSimpleContainer cosmicContainer = accessoriesContainer.getCosmeticAccessories();
                                for (int j = 0; j < cosmicContainer.getContainerSize(); j++) {
                                    ItemStack cosmicStack = cosmicContainer.removeItem(j, cosmicContainer.getItem(j).getCount());
                                    if (!cosmicStack.isEmpty()) {
                                        ghost.getInventory().addItem(cosmicStack);
                                    }
                                }
                                for (int j = 0; j < normalContainer.getContainerSize(); j++) {
                                    ItemStack itemStack = normalContainer.removeItem(j, normalContainer.getItem(j).getCount());
                                    if (!itemStack.isEmpty()) {
                                        ghost.getInventory().addItem(itemStack);
                                    }
                                }
                                accessoriesContainer.hasChanged();
                                accessoriesContainer.markChanged();
                                accessoriesContainer.update();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            //Trinkets模组处理
            if (isTrinketsInstalled()) {
                try {
                    TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
                        Set<String> keySet = trinketComponent.getInventory().keySet();
                        if (!keySet.isEmpty()) {
                            Collection<Map<String, TrinketInventory>> trinketMap = trinketComponent.getInventory().values();
                            for (Map<String, TrinketInventory> map : trinketMap) {
                                Set<String> childKeySet = map.keySet();
                                if (!childKeySet.isEmpty()) {
                                    for (String key : childKeySet) {
                                        TrinketInventory trinketInventory = map.get(key);
                                        for (int i = 0; i < trinketInventory.getContainerSize(); i++) {
                                            ItemStack itemStack = trinketInventory.removeItem(i, trinketInventory.getItem(i).getCount());
                                            if (!itemStack.isEmpty()) {
                                                ghost.getInventory().addItem(itemStack);
                                            }
                                            trinketInventory.setChanged();
                                            trinketInventory.update();
                                        }
                                    }

                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            //事件执行成功
            return InteractionResult.SUCCESS;

        });
    }
}
