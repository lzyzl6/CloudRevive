package com.lzyzl6.item;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.lzyzl6.data.storage.FileWork.deleteMatchFile;

public class ChaosCage extends Item {

    public ChaosCage(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.CHAOS_QI.get());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.chaos_cage.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.chaos_cage.tooltip2"));
        list.add(Component.literal(" "));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip4"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip5"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        //副手擒气
        if (!level.isClientSide) {
            double height = player.getY();
            if (height > 128 && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE.get() && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.sky_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.SKY_QI.get()));
                damageItem(player, usedHand, 9);
                afterUse(player, usedHand);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            } else if (height < 0 && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE.get() && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.ground_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.GROUND_QI.get()));
                damageItem(player, usedHand, 9);
                afterUse(player, usedHand);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            } else if (player.isShiftKeyDown() && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE.get() && usedHand == InteractionHand.OFF_HAND) {
                if (!(player.getHealth() == player.getMaxHealth()) || player.hasEffect(MobEffects.WEAKNESS)) {
                    player.sendSystemMessage(Component.translatable("chat.cloud_revive.cage.too_weak"));
                } else {
                    player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.people_qi_captured"), true);
                    player.addItem(new ItemStack(ModItems.PEOPLE_QI.get()));
                    player.hurt(player.damageSources().mobAttack(player), player.getHealth() / 1.25f);
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 4, false, false, true));
                    damageItem(player, usedHand, 1);
                    afterUse(player, usedHand);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        if (livingEntity instanceof WanderingSpirit wanderingSpirit) {
            //主手收魂
            UUID targetUUID = wanderingSpirit.locateTargetUUID();
            if (targetUUID != null && Objects.equals(targetUUID, player.getUUID()) && itemStack.getItem() == ModItems.CHAOS_CAGE.get() && interactionHand == InteractionHand.MAIN_HAND) {
                //通知玩家成功
                player.displayClientMessage(Component.translatable("chat.cloud_revive.chaos_cage.wandering_spirit_captured"), true);
                player.sendSystemMessage(Component.translatable("chat.cloud_revive.soul_back"));
                //物品转移到玩家
                if (!(player.getInventory().getFreeSlot() == - 1) && (player.getInventory().items.size() - player.getInventory().getFreeSlot() - 1 - wanderingSpirit.getInventory().getItems().size()) >= 1 ) {
                    for (int i = 0; i < wanderingSpirit.getInventory().getItems().size(); i++) {
                        player.addItem(wanderingSpirit.getInventory().removeItem(i, wanderingSpirit.getInventory().getItem(i).getCount()));
                    }
                    player.addItem(new ItemStack(ModItems.DEAD_QI.get()));
                } else if (player.getInventory().getFreeSlot() == - 1) {
                    for (int j = 0; j < wanderingSpirit.getInventory().getItems().size(); j++) {
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), wanderingSpirit.getInventory().removeItemNoUpdate(j));
                        player.level().addFreshEntity(itemEntity);
                        itemEntity.playSound(SoundEvents.ITEM_PICKUP, 0.3f, 0.5f);
                    }
                    player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), ModItems.DEAD_QI.get().getDefaultInstance()));
                } else {
                    player.addItem(new ItemStack(ModItems.DEAD_QI.get()));
                    int i;
                    for (i = 0; i  < wanderingSpirit.getInventory().getItems().size(); i++) {
                        if (player.getInventory().getFreeSlot() == - 1) {
                            break;
                        }
                        player.addItem(wanderingSpirit.getInventory().removeItemNoUpdate(i));
                    }
                    for (int j = i; j < wanderingSpirit.getInventory().getItems().size(); j++) {
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), wanderingSpirit.getInventory().removeItemNoUpdate(j));
                        player.level().addFreshEntity(itemEntity);
                        itemEntity.playSound(SoundEvents.ITEM_PICKUP, 0.3f, 0.5f);
                    }
                }
                //事后处理
                damageItem(player, interactionHand, 1);
                deleteMatchFile(wanderingSpirit);
                wanderingSpirit.discard();
                afterUse(player, interactionHand);
                return InteractionResult.SUCCESS;
            } else if (targetUUID != null && itemStack.getItem() == ModItems.CHAOS_CAGE.get() && interactionHand == InteractionHand.MAIN_HAND) {
                //通知玩家失败
                player.displayClientMessage(Component.translatable("chat.cloud_revive.chaos_cage.wandering_spirit_others"), true);
                System.out.println("The player is: " + player.getUUID() + " The wandering spirit want: " + targetUUID);
                afterUse(player, interactionHand);
            }
            return InteractionResult.PASS;
        } else {
            return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
        }
    }

    private void damageItem(Player player, InteractionHand usedHand, int i) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!itemStack.isEmpty()) {
            if (usedHand == InteractionHand.MAIN_HAND) {
                itemStack.hurtAndBreak(i, player, EquipmentSlot.MAINHAND);
            } else {
                itemStack.hurtAndBreak(i, player, EquipmentSlot.OFFHAND);
            }
        }
    }

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.getCooldowns().addCooldown(this, 40);
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }
}
