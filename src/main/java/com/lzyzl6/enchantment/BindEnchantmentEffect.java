package com.lzyzl6.enchantment;

import com.lzyzl6.registry.ModEffects;
import com.lzyzl6.registry.ModItems;
import com.lzyzl6.registry.ModSoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BindEnchantmentEffect implements EnchantmentLocationBasedEffect {

    public static final MapCodec<BindEnchantmentEffect> CODEC = MapCodec.unit(BindEnchantmentEffect::new);

    @Override
    public void onChangedBlock(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3, boolean bl) {
        if (entity.isAlive() && new Random().nextInt(100) <= 1) {
            if (enchantedItemInUse.owner() != null) {
                enchantedItemInUse.owner().playSound(ModSoundEvents.TALK, 0.1f, 1.0f);
            }
            if (new Random().nextInt(100) <= 10) {
                if (enchantedItemInUse.owner() instanceof Player player) {
                    player.addEffect(new MobEffectInstance(ModEffects.SOUL_LIKE, 20, 0, false, true, true));
                    ItemStack itemStack = new ItemStack(ModItems.DEAD_QI);
                    ItemEntity itemEntity = new ItemEntity(serverLevel, player.getX(), player.getY(), player.getZ(), itemStack);
                    itemEntity.setExtendedLifetime();
                    itemEntity.setGlowingTag(true);
                    serverLevel.addFreshEntity(itemEntity);
                    itemEntity.move(MoverType.SELF, player.position());
                    player.sendSystemMessage(Component.translatable("chat.bind.bleed"));
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentLocationBasedEffect> codec() {
        return CODEC;
    }
}
