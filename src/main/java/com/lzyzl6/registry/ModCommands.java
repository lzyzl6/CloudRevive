package com.lzyzl6.registry;


import com.lzyzl6.entity.WanderingSpirit;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import static net.minecraft.commands.Commands.literal;

public class ModCommands {

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
                .register(literal("killghost")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    // 对于 1.19 之前的版本，把“Text.literal”替换为“new LiteralText”。
                    // 对于 1.20 之前的版本，请移除“() ->”。
                    context.getSource().sendSuccess(() -> Component.translatable("command.killghost.success"), true);
                    context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(context.getSource().getPosition(), 20000, 20000, 20000)).forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));

                    return 1;
                })));
    }
}
