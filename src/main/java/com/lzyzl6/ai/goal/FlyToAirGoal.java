package com.lzyzl6.ai.goal;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;

import java.util.EnumSet;

public class FlyToAirGoal extends Goal {
    private final WanderingSpirit ghost;

    public FlyToAirGoal(WanderingSpirit ghost) {
        this.ghost = ghost;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }
    

    private boolean isInAir() {
        BlockPos blockPos = ghost.blockPosition();
        boolean isAirSurrounded = false;
        for(Direction direction : Direction.values()) {
            if(ghost.level().getBlockState(blockPos.relative(direction)).getBlock() == Blocks.AIR) {
                isAirSurrounded = true;
            }
        }
        return isAirSurrounded && ghost.level().getBlockState(blockPos).getBlock() == Blocks.AIR;
    }

    @Override
    public boolean canUse() {
        //高度合适且位置正当才停止
        return !isInAir();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        if(canUse()){
            ghost.moveTo(findNearestAir());
        }
    }

    private BlockPos findNearestAir() {//Y轴上最近的空气
        BlockPos blockPos = ghost.blockPosition();
        int range = 300;
        int upAirDistance = 299, downAirDistance = 300;
        for(int i = 1; i < range; i++) {
            if(ghost.level().getBlockState(blockPos.above(i)).getBlock() == Blocks.AIR ) {
                upAirDistance = i;
                break;
            }
        }
        for(int i = 1; i < range; i++) {
            if(ghost.level().getBlockState(blockPos.below(i)).getBlock() == Blocks.AIR && blockPos.below(i).getY() > 0 )  {
                downAirDistance = i;
                break;
            }
        }
        if(upAirDistance > downAirDistance)
        {
            return blockPos.below(downAirDistance);
        } else {
            return blockPos.above(upAirDistance);
        }
    }
    


}

