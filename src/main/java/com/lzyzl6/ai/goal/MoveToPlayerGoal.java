package com.lzyzl6.ai.goal;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MoveToPlayerGoal extends Goal {

    private final WanderingSpirit ghost;
    Player player ;

    public MoveToPlayerGoal(WanderingSpirit ghost) {
        this.ghost = ghost;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public boolean canUse() {
        if(ghost.locateTargetUUID() != null) {
            player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
            if (player != null && player.isAlive()) {
                return ghost.position().distanceTo(player.position()) < 64.0d;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override/// e
    public void tick() {
        if(canContinueToUse()) {
            player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
            if(player != null) {
                Vec3 targetVec3 = new Vec3(player.position().x + 1.0d, player.position().y + 1.0d , player.position().z + 1.0d);
                ghost.getLookControl().setLookAt(this.player, ghost.getMaxHeadYRot() + 20, ghost.getMaxHeadXRot());
                if(!ghost.getLookControl().isLookingAtTarget()) {
                    ghost.getLookControl().tick();
                }
                ghost.moveTo(targetVec3, ghost.getMaxHeadYRot() + 10, ghost.getMaxHeadXRot());
            }

        }
    }


    @Override
    public void start() {
        if (player != null) {
            player.sendSystemMessage(Component.translatable("chat.goal.move_to_player.start"));
        }
    }

    @Override
    public void stop() {
        this.player = null;
        ghost.getNavigation().stop();
    }
}
