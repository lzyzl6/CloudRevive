package com.lzyzl6.item;

import com.lzyzl6.registry.ModEnchantments;
import com.lzyzl6.registry.ModItems;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.lzyzl6.data.storage.FileWork.*;

public class SoulPearl extends Item {

    private boolean shouldRoll = false;
    public SoulPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.soul_pearl.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.soul_pearl.tooltip2"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack offHandItem = player.getOffhandItem();
        ItemStack mainHandItem = player.getItemInHand(interactionHand);
        Enchantment bind = ModEnchantments.SOUL_BIND;
        if(interactionHand == InteractionHand.MAIN_HAND && mainHandItem.is(ModItems.SOUL_PEARL) && !offHandItem.isEmpty()) {
            //赋予玩家主手物品附魔（如果可以）
            if(EnchantmentHelper.getEnchantments(mainHandItem).keySet().stream().anyMatch(enchantment -> enchantment == ModEnchantments.SOUL_BIND)) {
                if(!shouldRoll) {
                    shouldRoll = true;
                } else {
                    player.sendSystemMessage(Component.translatable("chat.soul_pearl.already_enchanted"));
                    shouldRoll = false;
                }
            } else if(ModEnchantments.SOUL_BIND.canEnchant(offHandItem)) {
                    enchantItem(player, offHandItem, mainHandItem, bind);
            } else if(isBackpackedInstalled() && offHandItem.getItem().getDescriptionId().contains("backpacked") && !offHandItem.getItem().getDescriptionId().contains("shelf")) {
                    enchantItem(player, offHandItem, mainHandItem, bind);
            } else if(isSophisticatedBackpacksInstalled() && offHandItem.getItem().getDescriptionId().contains("sophisticatedbackpacks") && !offHandItem.getItem().getDescriptionId().contains("upgrade")) {
                enchantItem(player, offHandItem, mainHandItem, bind);
            }else if(isTravelersBackpackInstalled() && offHandItem.getItem().getDescriptionId().contains("travelersbackpack") && !offHandItem.getItem().getDescriptionId().contains("upgrade") && !offHandItem.getItem().getDescriptionId().contains("sleeping") && !offHandItem.getItem().getDescriptionId().contains("tank") && !offHandItem.getItem().getDescriptionId().contains("hose")) {
                enchantItem(player, offHandItem, mainHandItem, bind);
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


    private void enchantItem(Player player, ItemStack offHandItem, ItemStack mainHandItem, Enchantment enchantment) {
        offHandItem.enchant(enchantment, 1);
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
