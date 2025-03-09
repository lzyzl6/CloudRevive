package com.lzyzl6.ai.goal;

import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModItems;
import com.lzyzl6.registry.ModSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import static com.lzyzl6.data.storage.FileWork.checkIfMeetAndFix;

public class InteractPlayerGoal extends Goal {

    private final WanderingSpirit ghost;
    private Player player;

    public InteractPlayerGoal(WanderingSpirit ghost) {
        this.ghost = ghost;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if(ghost.locateTargetUUID() != null) {
            player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
            if (player != null && player.isAlive()) {
                return ghost.position().distanceTo(player.position()) < 2.0d && !ghost.walkAnimation.isMoving();
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void tick() {
        String str = "chat.goal.interact_player.";
        player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
        //检测是否第一次相遇
        if(checkIfMeetAndFix(ghost)) {
            boolean ifHelpful = false;
            /*检查游魂物品栏中是否有一始宝珠、一始宝灯笼、游元擒气笼、混元纳魂笼、
            混沌宝气、一始元气、高天阳气、玄地阴气、生人和气、绿宝石块、灯笼*/
            List<Item> targetItems = List.of(ModItems.PEARL, ModItems.START_CAGE,ModItems.CAGE, ModItems.CHAOS_CAGE,
                    ModItems.CHAOS_QI,ModItems.CORE_QI,ModItems.SKY_QI,ModItems.GROUND_QI,ModItems.PEOPLE_QI,
                    Items.EMERALD_BLOCK,Items.LANTERN);

            for (int i = 0; i < ghost.getInventory().items.size(); i++) {
                if (targetItems.contains(ghost.getInventory().getItem(i).getItem())) {
                    //交换物品
                    if(player.getInventory().getFreeSlot() >= 1) {
                        //当前物品有富余槽位，直接添加
                        player.addItem(ghost.getInventory().removeItem(i, ghost.getInventory().getItem(i).getCount()));
                    } else {
                        //当前物品无富余槽位，转为玩家位置生成物品实体
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getEyeY(),
                                player.getZ(), ghost.getInventory().removeItem(i, ghost.getInventory().getItem(i).getCount()));
                        player.level().addFreshEntity(itemEntity);
                        itemEntity.playSound(SoundEvents.ITEM_PICKUP,0.3f, 0.5f);
                    }
                    ifHelpful = true;
                }
            }
            int randomInt1 = new Random().nextInt(10) + 1;
            int randomInt2 = new Random().nextInt(10) + 1;
            if(ifHelpful) {
                //给予物品交流
                str += "give_stuff";
                str += randomInt1;
                ghost.playSound(ModSoundEvents.TALK, 1.5f, 1.2f);
                player.sendSystemMessage(Component.literal(""));
                player.sendSystemMessage(Component.translatable(str));
                //受伤交流
                ghost.playSound(SoundEvents.PLAYER_HURT,2.0f,1.0f);
                ghost.setHealth(1.0f);
                String str1 = "chat.goal.interact_player.hurt";
                str1 += randomInt2;
                player.sendSystemMessage(Component.translatable(str1));
            } else {
                str += "no_helpful_stuff";
                str += randomInt1;
                ghost.playSound(ModSoundEvents.TALK, 1.5f, 1.2f);
                player.sendSystemMessage(Component.translatable(str));
            }
        } else {
            double randomDouble = new Random().nextDouble(100);
            boolean shouldTalk = randomDouble <= 0.01d;

            if(shouldTalk) {
                int randomInt = new Random().nextInt(3) + 1;

                //在不同情形下交流
                if (player.getHealth() < player.getMaxHealth() / 5.0f) { //濒死
                    str += "player_death_near";
                    str += randomInt;
                    ghost.playSound(ModSoundEvents.TALK, 1.7f, 1.4f);
                    player.sendSystemMessage(Component.translatable(str));
                } else if (player.isHurt() && player.getLastHurtByMobTimestamp() < player.tickCount - 10) {//受伤
                    str += "player_hurt";
                    str += randomInt;
                    ghost.playSound(ModSoundEvents.TALK, 1.6f, 1.3f);
                    player.sendSystemMessage(Component.translatable(str));
                } else if (player.isCurrentlyGlowing()) {//发光
                    str += "player_glowing";
                    str += randomInt;
                    ghost.playSound(ModSoundEvents.TALK, 1.5f, 1.2f);
                    player.sendSystemMessage(Component.translatable(str));
                } else if (player.level().dimension() == Level.NETHER) {//在下界
                    str += "at_nether";
                    str += randomInt;
                    ghost.playSound(ModSoundEvents.TALK, 1.5f, 1.2f);
                    player.sendSystemMessage(Component.translatable(str));
                } else if (player.level().dimension() == Level.END) {//在末地
                    str += "at_end";
                    str += randomInt;
                    ghost.playSound(ModSoundEvents.TALK, 1.5f, 1.2f);
                    player.sendSystemMessage(Component.translatable(str));
                }
            }
        }
    }


}
