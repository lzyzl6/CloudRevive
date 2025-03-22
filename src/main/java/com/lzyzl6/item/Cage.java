package com.lzyzl6.item;


import com.lzyzl6.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class Cage extends Item {

    public Cage(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.cage.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip4"));
        list.add(Component.translatable("item.cloud_revive.cage.tooltip5"));
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.CORE_QI);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        //副手擒气
        if(!level.isClientSide) {
            double height = player.getY();
            if(height > 128 && player.getOffhandItem().getItem() == ModItems.CAGE && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.sky_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.SKY_QI));
                damageItem(player, usedHand, 9);
                afterUse(player, usedHand);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
            else if(height < 0 && player.getOffhandItem().getItem() == ModItems.CAGE && usedHand == InteractionHand.OFF_HAND) {
                player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.ground_qi_captured"), true);
                player.addItem(new ItemStack(ModItems.GROUND_QI));
                damageItem(player, usedHand, 9);
                afterUse(player, usedHand);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
            else if(player.isShiftKeyDown() && player.getOffhandItem().getItem() == ModItems.CAGE && usedHand == InteractionHand.OFF_HAND) {
                if(!(player.getHealth() == player.getMaxHealth()) || player.hasEffect(MobEffects.WEAKNESS)) {
                    player.sendSystemMessage(Component.translatable("chat.cloud_revive.cage.too_weak"));
                } else {
                    player.displayClientMessage(Component.translatable("chat.cloud_revive.cage.people_qi_captured"), true);
                    player.addItem(new ItemStack(ModItems.PEOPLE_QI));
                    player.hurt(player.damageSources().mobAttack(player), player.getHealth()/1.25f);
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

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.getCooldowns().addCooldown(this, 40);
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }
}
