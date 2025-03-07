package com.lzyzl6.entity;

import com.lzyzl6.ai.goal.FlyToAirGoal;
import com.lzyzl6.ai.goal.InteractPlayerGoal;
import com.lzyzl6.ai.goal.MoveToPlayerGoal;
import com.lzyzl6.registry.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

import static com.lzyzl6.data.storage.FileWork.makeMatch;

public class WanderingSpirit extends PathfinderMob implements InventoryCarrier {

    //构造
    public WanderingSpirit(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    //动画
    public static final AnimationState idleAnimation = new AnimationState();
    int idleAnimationTimeOut = 0;

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
        if(soundTick > 10) {
            this.makeSound(this.getAmbientSound());
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSoundEvents.GHOST_AMBIENT;
    }

    //AI
    public boolean shouldBroadcastMovement = true;

    @Override
    public void tick() {
        this.noPhysics = true;
        setUpAnimation();
        super.tick();
        this.moveControl.tick();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new InteractPlayerGoal(this));
        this.goalSelector.addGoal(1, new MoveToPlayerGoal(this));
        this.goalSelector.addGoal(2, new FlyToAirGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    //属性
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 64.0d)
                .add(Attributes.MAX_HEALTH, 4.0d)
                .add(Attributes.BURNING_TIME, 0.0d)
                .add(Attributes.SCALE, 0.6d)
                .add(Attributes.GRAVITY, 0.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.2d);
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        return true;
    }


    @Override
    public boolean ignoreExplosion(@NotNull Explosion explosion) {
        return true;
    }

    //匹配
    public UUID locateTargetUUID() {
        File match = makeMatch(this);
        if(match != null) {
            try {
                return UUID.fromString(match.getName());
            } catch (IllegalArgumentException e) {
                // 处理无效UUID格式
            }
        }
        return null;
    }

    //容器
    private final SimpleContainer inventory  = new SimpleContainer(512);
    private static final EntityDataAccessor<CompoundTag> DATA_INVENTORY = SynchedEntityData.defineId(WanderingSpirit.class, EntityDataSerializers.COMPOUND_TAG);


    @Override
    public @NotNull SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_INVENTORY, new CompoundTag()).build();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.writeInventoryToTag(tag, this.level().registryAccess());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readInventoryFromTag(tag, this.level().registryAccess());
    }

    @Override
    protected void pickUpItem(@NotNull ItemEntity itemEntity) {
        InventoryCarrier.pickUpItem(this, this, itemEntity);
    }
}

