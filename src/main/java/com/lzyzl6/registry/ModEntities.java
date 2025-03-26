package com.lzyzl6.registry;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<WanderingSpirit>> GHOST = ENTITIES.register(
            "ghost",
            () -> EntityType.Builder.of(WanderingSpirit::new, MobCategory.CREATURE)
                    .sized(0.55f, 1.0f)
                    .build("ghost")
    );


    public static void initialize() {

    }
}
