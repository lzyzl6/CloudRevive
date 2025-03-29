package com.lzyzl6.ai.goal;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

public class MoveToPlayerGoal extends Goal {

    private final WanderingSpirit ghost;
    private Player player ;

    public MoveToPlayerGoal(WanderingSpirit ghost) {
        this.ghost = ghost;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK ,Flag.TARGET));
    }


    @Override
    public boolean canUse() {
        if(ghost.locateTargetUUID() != null) {
            player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
            if (player != null && player.isAlive()) {
                return ghost.position().distanceTo(player.position()) < 32.0d;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse() && ghost.position().distanceTo(player.position()) > 6.0d;
    }

    @Override
    public void tick() {
        if (canContinueToUse()) {
            if(player != null) {
                double ranX = new Random().nextDouble(-1.0d, 1.0d);
                double ranY = new Random().nextDouble(-0.5d, 0.2d);
                double ranZ = new Random().nextDouble(-1.0d, 1.0d);
                Vec3 targetVec3 = new Vec3(player.position().x + ranX, player.getEyeY() + ranY , player.position().z + ranZ);
                ghost.getWalkTargetValue(BlockPos.containing(targetVec3));
                ghost.getMoveControl().setWantedPosition(targetVec3.x, targetVec3.y, targetVec3.z, 1.0d);
                ghost.moveTo(BlockPos.containing(targetVec3), ghost.getMaxHeadYRot() + 20, ghost.getMaxHeadXRot());
            }
        }
    }

    @Override
    public void start() {
        player = ghost.level().getPlayerByUUID(ghost.locateTargetUUID());
        if (player != null && ghost.shouldBroadcastMovement) {
            player.sendSystemMessage(Component.translatable("chat.goal.move_to_player.start"));
            ghost.shouldBroadcastMovement = false;
        }
    }
}
