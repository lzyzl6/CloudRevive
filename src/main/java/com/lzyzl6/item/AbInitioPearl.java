package com.lzyzl6.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AbInitioPearl extends Item {

    public AbInitioPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip4"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip5"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip6"));

    }
}
