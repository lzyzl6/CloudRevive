package com.lzyzl6.item;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
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
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.CHAOS_QI);
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
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        //副手擒气
        if(!level.isClientSide) {
            double height = player.getY();
            if(height > 128 && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.sky_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.SKY_QI));
                damageItem(player, usedHand, 9);
            }
            else if(height < 0 && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.ground_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.GROUND_QI));
                damageItem(player, usedHand, 9);
            }
            else if(player.isShiftKeyDown() && player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.CHAOS_CAGE && usedHand == InteractionHand.OFF_HAND) {
                if(!(player.getHealth() == player.getMaxHealth()) || player.hasEffect(MobEffects.WEAKNESS)) {
                    player.sendSystemMessage(Component.translatable("chat.cloud_revive.cage.too_weak"));
                } else {
                    player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.people_qi_captured"), true);
                    player.addItem(new ItemStack(ModItems.PEOPLE_QI));
                    player.hurt(player.damageSources().mobAttack(player), player.getHealth()/1.25f);
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 4, false, false, true));
                    damageItem(player, usedHand, 1);
                }
            }
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        if(livingEntity instanceof WanderingSpirit wanderingSpirit) {
            //主手收魂
            UUID targetUUID = wanderingSpirit.locateTargetUUID();
            if( targetUUID != null && Objects.equals(targetUUID,player.getUUID()) && itemStack.getItem() == ModItems.CHAOS_CAGE && interactionHand == InteractionHand.MAIN_HAND) {
                //通知玩家成功
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.wandering_spirit_captured"), true);
                player.sendSystemMessage(Component.translatable("chat.cloud_revive.soul_back"));
                //物品转移到玩家
                if(wanderingSpirit.getInventory().items.size() <= player.getInventory().getFreeSlot() - 1 ) {
                    for (int i = 0; i < wanderingSpirit.getInventory().items.size(); i++) {
                        player.addItem(wanderingSpirit.getInventory().removeItem(i, wanderingSpirit.getInventory().getItem(i).getCount()));
                    }
                    player.addItem(new ItemStack(ModItems.DEAD_QI));
                } else if(player.getInventory().getFreeSlot() == 0) {
                    for (int j = 0; j < wanderingSpirit.getInventory().items.size(); j++) {
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getEyeY(), player.getZ(),wanderingSpirit.getInventory().getItem(j), 0.0f, 0.0f, 0.0f);
                        player.level().addFreshEntity(itemEntity);
                        itemEntity.playSound(SoundEvents.ITEM_PICKUP,0.3f, 0.5f);
                    }
                    player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getEyeY(), player.getZ(),ModItems.DEAD_QI.getDefaultInstance(), 0.0f, 0.0f, 0.0f));
                }
                else {
                    player.addItem(new ItemStack(ModItems.DEAD_QI));
                    int i;
                    for (i = 0; i < player.getInventory().getFreeSlot() - 1; i++) {
                        player.addItem(wanderingSpirit.getInventory().getItem(i));
                    }
                    for (int j = i; j < wanderingSpirit.getInventory().items.size(); j++) {
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getEyeY(), player.getZ(),wanderingSpirit.getInventory().getItem(j), 0.0f, 0.0f, 0.0f);
                        player.level().addFreshEntity(itemEntity);
                        itemEntity.playSound(SoundEvents.ITEM_PICKUP,0.3f, 0.5f);
                    }
                }
                //事后处理
                damageItem(player, interactionHand, 1);
                deleteMatchFile(wanderingSpirit);
                wanderingSpirit.discard();
                return InteractionResult.SUCCESS;
            } else if(targetUUID != null && itemStack.getItem() == ModItems.CHAOS_CAGE && interactionHand == InteractionHand.MAIN_HAND) {
                //通知玩家失败
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.wandering_spirit_others"), true);
                System.out.println("The player is: " + player.getUUID() + " The wandering spirit want: " + targetUUID);
            }
            return InteractionResult.PASS;
        }
        else {
            return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
        }
    }

    private void damageItem(Player player, InteractionHand usedHand, int i) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(!itemStack.isEmpty()) {
            if (usedHand == InteractionHand.MAIN_HAND) {
                itemStack.hurtAndBreak(i, player, EquipmentSlot.MAINHAND);
            } else {
                itemStack.hurtAndBreak(i, player, EquipmentSlot.OFFHAND);
            }
        }
    }
}
