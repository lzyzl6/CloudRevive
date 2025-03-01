package com.lzyzl6.ai.goal;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.UUID;

public class MoveToPlayerGoal extends Goal {

    private final WanderingSpirit ghost;
    private static UUID targetUUID;

    public MoveToPlayerGoal(WanderingSpirit ghost) {
        targetUUID = ghost.locateTargetUUID();
        this.ghost = ghost;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (targetUUID != null) {
            Player player = ghost.level().getPlayerByUUID(targetUUID);
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

    @Override
    public void tick() {
        if(canContinueToUse()) {
            Player player = ghost.level().getPlayerByUUID(targetUUID);
            if (player != null) {
                ghost.getWalkTargetValue(player.blockPosition());
                ghost.getNavigation().moveTo(player, 0.2);
                ghost.lookAt(player, 1.0f, 1.0f);
                // 假设 targetVec3 是目标的 Vec3 坐标
                Vec3 targetVec3 = player.position();
                ghost.moveTo(targetVec3, ghost);
            }
        }
    }
    @Override
    public void start() {
        super.start();
        Player player = ghost.level().getPlayerByUUID(targetUUID);
        if (player != null) {
            player.sendSystemMessage(Component.translatable("chat.goal.move_to_player.start"));
        }
    }

}
