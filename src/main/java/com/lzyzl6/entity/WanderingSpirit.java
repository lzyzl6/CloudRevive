package com.lzyzl6.entity;

import com.lzyzl6.ai.goal.FlyToAirGoal;
import com.lzyzl6.ai.goal.InteractPlayerGoal;
import com.lzyzl6.ai.goal.MoveToPlayerGoal;
import com.lzyzl6.registry.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

import static com.lzyzl6.data.storage.FileWork.makeMatch;

public class WanderingSpirit extends PathfinderMob implements InventoryCarrier {

    //动画
    public static final AnimationState idleAnimation = new AnimationState();
    //容器
    private static final EntityDataAccessor<CompoundTag> DATA_INVENTORY = SynchedEntityData.defineId(WanderingSpirit.class, EntityDataSerializers.COMPOUND_TAG);
    private final SimpleContainer inventory = new SimpleContainer(512);
    //AI
    public boolean shouldBroadcastMovement = true;
    int idleAnimationTimeOut = 0;

    //构造
    public WanderingSpirit(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    //属性
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 64.0d)
                .add(Attributes.MAX_HEALTH, 4.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.2d);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }


    public void setUpAnimation() {
        if (idleAnimationTimeOut <= 0 && !idleAnimation.isStarted()) {
            idleAnimation.start(this.tickCount);
            idleAnimationTimeOut = this.random.nextInt(30) + 120;
        } else {
            idleAnimationTimeOut--;
        }
    }

    //声音
    @Override
    public void playAmbientSound() {
        int soundTick = this.random.nextInt(20);
        SoundEvent sound = this.getAmbientSound();
        if (soundTick > 10 &&  sound!= null) {
            this.playSound(sound);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSoundEvents.GHOST_AMBIENT;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            ChunkPos chunkPos = this.chunkPosition();
            ServerLevel serverLevel = (ServerLevel) this.level();
            serverLevel.setChunkForced(chunkPos.x, chunkPos.z, this.isAlive());
        }
        this.noPhysics = true;
        setViewScale(0.6d);
        setUpAnimation();
        super.tick();
        this.moveControl.tick();
    }

    public void remove(Entity.@NotNull RemovalReason removalReason) {
        if (removalReason == RemovalReason.UNLOADED_TO_CHUNK || removalReason == RemovalReason.UNLOADED_WITH_PLAYER) {
            if (!this.level().isClientSide) {
                ChunkPos chunkPos = this.chunkPosition();
                ServerLevel serverLevel = (ServerLevel) this.level();
                serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true);
            }
        } else if (!this.level().isClientSide) {
            ChunkPos chunkPos = this.chunkPosition();
            ServerLevel serverLevel = (ServerLevel) this.level();
            if (serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true)) {
                serverLevel.setChunkForced(chunkPos.x, chunkPos.z, false);
            }
        }
        super.setRemoved(removalReason);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, getTargetPlayer(this), 8.0F, 1.0F));
        this.goalSelector.addGoal(1, new InteractPlayerGoal(this));
        this.goalSelector.addGoal(2, new MoveToPlayerGoal(this));
        this.goalSelector.addGoal(3, new FlyToAirGoal(this));
    }

    Class<? extends Player> getTargetPlayer(WanderingSpirit wanderingSpirit) {
        if (wanderingSpirit.locateTargetUUID() != null) {
            Player player = wanderingSpirit.level().getPlayerByUUID(wanderingSpirit.locateTargetUUID());
            if (player != null) {
                return player.getClass();
            }
        }
        return Player.class;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    //匹配
    public UUID locateTargetUUID() {
        File match = makeMatch(this);
        if (match != null) {
            try {
                return UUID.fromString(match.getName());
            } catch (IllegalArgumentException e) {
                // 处理无效UUID格式
            }
        }
        return null;
    }

    @Override
    public @NotNull SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_INVENTORY, new CompoundTag());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.writeInventoryToTag(tag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readInventoryFromTag(tag);
    }
}

