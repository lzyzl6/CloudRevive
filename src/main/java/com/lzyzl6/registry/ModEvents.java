package com.lzyzl6.registry;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.event.PlayerDieCallback;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.*;

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

            //转移物品
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                //魂牵与绑定诅咒
                if (!itemStack.isEmpty() && (EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND.get()) || EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP))) {
                    continue;
                }
                ghost.getInventory().addItem(player.getInventory().removeItem(i, itemStack.getCount()));
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
                                itemHandle(player, ghost, cosmicContainer);
                                itemHandle(player, ghost, normalContainer);
                                accessoriesContainer.hasChanged();
                                accessoriesContainer.markChanged();
                                accessoriesContainer.update();
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.fillInStackTrace();
                }
            }

            //Curios模组处理
            if (isCuriosInstalled()) {
                Optional<ICuriosItemHandler> maybeCuriosInventory = CuriosApi.getCuriosInventory(player);

                if (maybeCuriosInventory.isPresent()) {
                    ICuriosItemHandler curiosInventory = maybeCuriosInventory.get();
                    //装备槽
                    IItemHandlerModifiable equippedCurios = curiosInventory.getEquippedCurios();
                    for (int i = 0; i < equippedCurios.getSlots(); i++) {
                        ItemStack itemStack = equippedCurios.getStackInSlot(i);
                        if (!itemStack.isEmpty() && EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND.get())) {
                            player.getInventory().add(itemStack.copyAndClear());
                        } else if (!itemStack.isEmpty()) {
                            ghost.getInventory().addItem(itemStack.copyAndClear());
                        }
                    }
                    //装饰槽
                    Map<String, ICurioStacksHandler> curios = curiosInventory.getCurios();
                    curios.forEach((identifier, stacksHandler) -> {
                        Optional<ICurioStacksHandler> maybeCuriosStacks = curiosInventory.getStacksHandler(identifier);
                        if (maybeCuriosStacks.isPresent()) {
                            IDynamicStackHandler cosmeticStacks = maybeCuriosStacks.get().getCosmeticStacks();
                            for (int i = 0; i < cosmeticStacks.getSlots(); i++) {
                                ItemStack itemStack = cosmeticStacks.getStackInSlot(i);
                                if (!itemStack.isEmpty() && EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND.get())) {
                                    player.getInventory().add(itemStack.copyAndClear());
                                } else if (!itemStack.isEmpty()) {
                                    ghost.getInventory().addItem(itemStack.copyAndClear());
                                }
                            }
                        }
                    });
                }
            }

            //事件执行成功
            return InteractionResult.SUCCESS;

        });
    }

    private static void itemHandle(Player player, WanderingSpirit ghost, ExpandedSimpleContainer container) {
        for (int j = 0; j < container.getContainerSize(); j++) {
            ItemStack itemStack = container.removeItem(j, container.getItem(j).getCount());
            if (!itemStack.isEmpty() && EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND.get())) {
                player.getInventory().add(itemStack);
            } else if (!itemStack.isEmpty()) {
                ghost.getInventory().addItem(itemStack);
            }
        }
    }
}
