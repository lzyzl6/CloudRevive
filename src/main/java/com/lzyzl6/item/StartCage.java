package com.lzyzl6.item;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModBlocks;
import com.lzyzl6.registry.ModEntities;
import com.lzyzl6.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class StartCage extends Item {

    public StartCage(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.cloud_revive.start_cage.tooltip1"));
        list.add(Component.translatable("item.cloud_revive.start_cage.tooltip2"));
        list.add(Component.translatable("item.cloud_revive.start_cage.tooltip3"));
        list.add(Component.translatable("item.cloud_revive.start_cage.tooltip4"));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(ModItems.PEARL.get());
    }



    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if(!level.isClientSide) {
            boolean isSuccess;
            //副手上使用生成结构
            if(player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == ModItems.START_CAGE.get() && usedHand == InteractionHand.OFF_HAND) {
                if(canGenerate(player)){
                    generateStructure(player);
                    isSuccess = true;
                }else{
                    isSuccess = false;
                }
                //信息显示
                if(isSuccess) {
                    player.displayClientMessage(Component.translatable("item.cloud_revive.start_cage.success"), true);
                    player.sendSystemMessage(Component.translatable("item.cloud_revive.start_cage.success"));

                } else {
                    player.sendSystemMessage(Component.translatable("item.cloud_revive.start_cage.cant_generate"));
                }
                player.getCooldowns().addCooldown(this, 500);
                damageItem(player, usedHand);
                player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,0.6F,2.0F);
            }
            afterUse(player, usedHand);
        }
        return super.use(level, player, usedHand);
    }

    private void afterUse(Player player, InteractionHand interactionHand) {
        player.awardStat(Stats.ITEM_USED.get(this));
        player.swing(interactionHand, true);
    }

    private void damageItem(Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(!itemStack.isEmpty()) {
            if (usedHand == InteractionHand.MAIN_HAND) {
                itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            } else {
                itemStack.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
            }
        }
    }

    private BlockPos getOriginPos(Player player) {
        return player.blockPosition().above(new Random().nextInt(3) + 3).north(new Random().nextInt(2) + 2).east(new Random().nextInt(4) + 4);
    }

    private boolean canGenerate(Player player) {
        BlockPos originPos = getOriginPos(player);
        Level level = player.level();
        for(int height = 0; height < 6; height++) {
            for(int width = 0; width < 8; width++) {
                for(int length = 0; length < 10; length++) {
                    if(!level.isEmptyBlock(originPos.south(width).east(length).above(height))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void generateStructure(Player player) {
        //生成结构
        BlockPos originPos = getOriginPos(player);
        Level level = player.level();
        int topHeight = new Random().nextInt(2) + 4;
        int topWidth = new Random().nextInt(3) + 5;
        int topLength = new Random().nextInt(3) + 7;
        int borderHeight = topHeight/2 + new Random().nextInt(-1,1);

        for(int height = 0; height < topHeight; height++) {
            for(int width = 0; width < topWidth; width++) { //south
                for(int length = 0; length < topLength; length++) { //east
                    //生成基底方块
                    //临界层及以下只有元气块 范围内50%随机生成
                    if(height <=borderHeight) {
                        boolean shouldGenerate = new Random().nextDouble(1) < 0.5;
                        if(shouldGenerate) {
                            level.setBlock(originPos.south(width).east(length).above(height), ModBlocks.QI_BLOCK.get().defaultBlockState(),83);
                        }
                    } else {
                        //防止元气果丛上方有方块生成
                        boolean noBushUnder = true;
                        for (int i = height; i > borderHeight; i--) {
                            if (level.getBlockState(originPos.south(width).east(length).above(i)).getBlock() == ModBlocks.QI_FRUIT_BUSH.get()) {
                                noBushUnder = false;
                            }
                        }
                        if (noBushUnder && height < topHeight - 1) {
                            //在非顶层生成元气块或元气核
                            //10%的概率生成元气核
                            //若元气核不生成，50%的概率生成元气块
                            boolean shouldGenerateCore = new Random().nextDouble(1) < 0.1;
                            boolean shouldGenerateBlock = new Random().nextDouble(1) < 0.5;
                            if (shouldGenerateCore) {
                                level.setBlock(originPos.south(width).east(length).above(height), ModBlocks.QI_BLOCK_CORE.get().defaultBlockState(), 83);
                            } else if (shouldGenerateBlock) {
                                level.setBlock(originPos.south(width).east(length).above(height), ModBlocks.QI_BLOCK.get().defaultBlockState(), 83);
                            }
                        }
                    }
                    //最上面三层可能生成元气果丛
                    if(height >=topHeight - 3) {
                        //检查本方块是不是空气
                        if(level.getBlockState(originPos.south(width).east(length).above(height)).isAir()) {
                            //检测元气果丛下方是不是元气块
                            if(level.getBlockState(originPos.south(width).east(length).above(height-1)).getBlock() == ModBlocks.QI_BLOCK.get()) {
                                //按15%的概率生成元气果丛
                                boolean shouldGenerateBush = new Random().nextDouble(1) < 0.15;
                                if(shouldGenerateBush) {
                                    //生成元气果丛
                                    level.setBlock(originPos.south(width).east(length).above(height), ModBlocks.QI_FRUIT_BUSH.get().defaultBlockState(),83);
                                }
                            }
                        }
                    }
                    //顶层，5%的概率生成游魂
                    if(height == topHeight - 1) {
                        boolean shouldGenerate = new Random().nextDouble(1) < 0.05;
                        if(shouldGenerate) {
                            WanderingSpirit wanderingSpirit = new WanderingSpirit(ModEntities.GHOST.get(), player.level());
                            Vec3 ghostPos = Vec3.atCenterOf(originPos.south(width).east(length).above(height));
                            //随机化高度
                            wanderingSpirit.setPos(ghostPos.x, ghostPos.y + new Random().nextDouble(-0.3,0.3), ghostPos.z);
                            level.addFreshEntity(wanderingSpirit);
                        }
                    }
                }
            }
        }
    }


    @Override
    public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity livingEntity) {
        return 1000;
    }
}
