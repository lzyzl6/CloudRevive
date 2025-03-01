package com.lzyzl6.entity;

import com.lzyzl6.ai.goal.FlyToAirGoal;
import com.lzyzl6.ai.goal.MoveToPlayerGoal;
import com.lzyzl6.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static com.lzyzl6.data.storage.FileWork.checkMatchFile;

public class WanderingSpirit extends PathfinderMob implements InventoryCarrier {

    public static final AnimationState idleAnimation = new AnimationState();

    public UUID locateTargetUUID() {
        this.targetUUID = UUID.fromString(Objects.requireNonNull(checkMatchFile(this)).getName());
        return this.targetUUID;
    }

    public UUID targetUUID;
//    public HolderLookup.Provider provider;
    int idleAnimationTimeOut = 0;
    private final SimpleContainer inventory;
//    public ListTag listTag = new ListTag();


    public WanderingSpirit(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.inventory = new SimpleContainer(99);
    }

    //动画
    public void setUpAnimation() {
        if (idleAnimationTimeOut <= 0 && !idleAnimation.isStarted()) {
            idleAnimation.start(this.tickCount);
            idleAnimationTimeOut = this.random.nextInt(30) + 100;
        } else {
            idleAnimationTimeOut--;
        }
    }

    //声音
    @Override
    public void playAmbientSound() {
        int soundTick = this.random.nextInt(20);
        if(soundTick > 10) {
            this.makeSound(this.getAmbientSound());
        }
    }

    public SoundEvent getAmbientSound() {
        return ModSoundEvents.GHOST_AMBIENT;
    }

    //AI
    @Override
    public void tick() {
        super.tick();
        this.moveControl.tick();
        this.noPhysics = true;
        setInvulnerable(true);
        setUpAnimation();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MoveToPlayerGoal(this));
        this.goalSelector.addGoal(1, new FlyToAirGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public void moveTo(BlockPos blockPos) {
        Vec3 targetVec3 = blockPos.getBottomCenter();
        moveTo(targetVec3, this);
    }

    public void moveTo(Vec3 targetVec3, WanderingSpirit ghost) {
        double deltaX = targetVec3.x - ghost.getX();
        double deltaY = targetVec3.y - ghost.getY();
        double deltaZ = targetVec3.z - ghost.getZ();

        float yaw = (float) (Math.atan2(deltaZ, deltaX) * (180 / Math.PI)) - 90;
        float pitch = (float) (Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * (180 / Math.PI));

        updateWalkAnimation(this.random.nextFloat());
        ghost.moveTo(targetVec3, yaw, pitch);
    }

    //属性
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 32.0d)
                .add(Attributes.MAX_HEALTH, 4.0d)
                .add(Attributes.BURNING_TIME, 0.0d)
                .add(Attributes.SCALE, 0.6d)
                .add(Attributes.GRAVITY, 0.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.2d);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        return false;
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    //容器
    @Override
    public @NotNull SimpleContainer getInventory() {
        return this.inventory;
    }
}

