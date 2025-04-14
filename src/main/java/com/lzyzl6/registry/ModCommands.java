package com.lzyzl6.registry;


import com.lzyzl6.entity.WanderingSpirit;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.commands.Commands.literal;

public class ModCommands {

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
                .register(literal("killghost")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(Vec3.ZERO, context.getSource().getLevel().getWorldBorder().getAbsoluteMaxSize() * 2, 8388608, context.getSource().getLevel().getWorldBorder().getAbsoluteMaxSize() * 2)).forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
                    context.getSource().sendSuccess(() -> Component.translatable("command.killghost.success"), true);

                    return 1;
                })));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
                .register(literal("givepearl")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            if(player.getInventory().getFreeSlot() < 1) {
                                player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), ModItems.PEARL.getDefaultInstance()));
                            } else {
                                player.addItem(ModItems.PEARL.getDefaultInstance());
                            }
                            context.getSource().sendSuccess(() -> Component.translatable("command.givepearl.success"), true);

                            return 1;
                        })));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
                .register(literal("queueghost")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayer();
                            if(player != null) {
                                AtomicInteger count = new AtomicInteger(1);
                                context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(Vec3.ZERO, 59999968, 59999968, 59999968)).forEach(
                                        entity -> {
                                            if(entity.locateTargetUUID().equals(player.getUUID())) {
                                                player.sendSystemMessage(Component.literal(count + " X:" + entity.getX() + " Y:" + entity.getY() + " Z:" + entity.getZ()));
                                                count.getAndIncrement();
                                            }
                                        });
                                if(count.get() == 1) {
                                    player.sendSystemMessage(Component.translatable("command.queueghost.failure"));
                                }
                            }

                            return 1;
                        })));
    }
}
