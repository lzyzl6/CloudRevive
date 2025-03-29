package com.lzyzl6.item;

import com.lzyzl6.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AbInitioPearl extends Item {

    public AbInitioPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip4"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip5"));
        list.add(Component.translatable("item.cloud_revive.pearl.tooltip6"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide()) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if(interactionHand == InteractionHand.MAIN_HAND && itemStack.is(ModItems.PEARL)) {
                String str = "tip.pearl.";
                int randomNum = new Random().nextInt(17) + 1;
                str += randomNum;
                player.startUsingItem(interactionHand);
                player.sendSystemMessage(Component.literal("--> " + randomNum + " <--"));
                for (MutableComponent mutableComponent : Arrays.asList(Component.translatable(str), Component.literal(""))) {
                    player.sendSystemMessage(mutableComponent);
                }
                level.playSound(player, player.getX(), player.getY() + 0.9D, player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.AMBIENT, 0.7F, 0.7F);
                afterUse(player, interactionHand);
            }
        }
       return super.use(level, player, interactionHand);
    }

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.getCooldowns().addCooldown(this, 10);
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }
}
