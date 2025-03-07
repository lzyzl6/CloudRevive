package com.lzyzl6.item;

import com.lzyzl6.registry.ModItems;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StartCage extends Item {

    public StartCage(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.PEARL);
    }

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.getCooldowns().addCooldown(this, 100);
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }
}
