package com.lzyzl6.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.lzyzl6.registry.ModBlocks.BIRTH_BEACON;

public class BirthBeaconItem extends BlockItem {

    public BirthBeaconItem(Properties properties) {
        super(BIRTH_BEACON.get(), properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.birth_beacon.tooltip1"));
    }
}
