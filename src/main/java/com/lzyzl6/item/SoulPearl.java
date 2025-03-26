package com.lzyzl6.item;

import com.lzyzl6.registry.ModItems;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.lzyzl6.data.storage.FileWork.*;
import static com.lzyzl6.registry.ModEnchantments.BIND;

public class SoulPearl extends Item {

    private boolean shouldRoll = false;
    public SoulPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.soul_pearl.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.soul_pearl.tooltip2"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack offHandItem = player.getOffhandItem();
        ItemStack mainHandItem = player.getItemInHand(interactionHand);
        Holder<Enchantment> holder = level.holderLookup(Registries.ENCHANTMENT).getOrThrow(BIND);
        if(interactionHand == InteractionHand.MAIN_HAND && mainHandItem.is(ModItems.SOUL_PEARL) && !offHandItem.isEmpty()) {
            //赋予玩家主手物品附魔（如果可以）
            if(EnchantmentHelper.getEnchantmentsForCrafting(offHandItem).keySet().stream().anyMatch(enchantment -> enchantment.is(BIND))) {
                if(!shouldRoll) {
                    shouldRoll = true;
                } else {
                    player.sendSystemMessage(Component.translatable("chat.soul_pearl.already_enchanted"));
                    shouldRoll = false;
                }
            } else if(offHandItem.canBeEnchantedWith(holder, EnchantingContext.ACCEPTABLE)) {
                    enchantItem(player, offHandItem, mainHandItem, holder);
            } else if(isBackpackedInstalled() && offHandItem.getItem().getDescriptionId().contains("backpacked") && !offHandItem.getItem().getDescriptionId().contains("shelf")) {
                    enchantItem(player, offHandItem, mainHandItem, holder);
            } else if(isSophisticatedBackpacksInstalled() && offHandItem.getItem().getDescriptionId().contains("sophisticatedbackpacks") && !offHandItem.getItem().getDescriptionId().contains("upgrade")) {
                enchantItem(player, offHandItem, mainHandItem, holder);
            }else if(isTravelersBackpackInstalled() && offHandItem.getItem().getDescriptionId().contains("travelersbackpack") && !offHandItem.getItem().getDescriptionId().contains("upgrade") && !offHandItem.getItem().getDescriptionId().contains("sleeping") && !offHandItem.getItem().getDescriptionId().contains("tank") && !offHandItem.getItem().getDescriptionId().contains("hose")) {
                enchantItem(player, offHandItem, mainHandItem, holder);
            } else {
                if(!shouldRoll) {
                    shouldRoll = true;
                } else {
                    player.sendSystemMessage(Component.translatable("chat.soul_pearl.not_enchantable"));
                    shouldRoll = false;
                }
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            player.swing(interactionHand, true);
        }
        return super.use(level, player, interactionHand);
    }


    private void enchantItem(Player player, ItemStack offHandItem, ItemStack mainHandItem, Holder<Enchantment> holder) {
        offHandItem.enchant(holder, 1);
        mainHandItem.shrink(1);
        if(!shouldRoll) {
            shouldRoll = true;
        } else {
            player.sendSystemMessage(Component.translatable("chat.soul_pearl.successfully_enchanted"));
            shouldRoll = false;
        }
        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
    }
}
