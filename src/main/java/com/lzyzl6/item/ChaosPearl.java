package com.lzyzl6.item;

import com.lzyzl6.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class ChaosPearl extends Item {

    private boolean shouldRoll = true;

    public ChaosPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.chaos_pearl.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.chaos_pearl.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.chaos_pearl.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.chaos_pearl.tooltip4"));
        list.add(Component.translatable("item.cloud_revive.chaos_pearl.tooltip5"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!shouldRoll) {
            shouldRoll = true;
        } else if(interactionHand == InteractionHand.OFF_HAND && itemStack.is(ModItems.CHAOS_PEARL)) {
            String str = "tip.chaos_pearl.";
            int randomNum = new Random().nextInt(10) + 1;
            str += randomNum;
            player.startUsingItem(interactionHand);
            player.sendSystemMessage(Component.translatable(str));
            player.sendSystemMessage(Component.literal(""));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.7F, 0.7F);
            afterUse(player, interactionHand);
            shouldRoll = false;
        }
        return super.use(level, player, interactionHand);
    }

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }
}
