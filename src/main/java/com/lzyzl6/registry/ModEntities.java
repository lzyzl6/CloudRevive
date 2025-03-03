package com.lzyzl6.registry;

import com.lzyzl6.entity.WanderingSpirit;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEntities {

    public static final EntityType<WanderingSpirit> GHOST = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "ghost"),
            EntityType.Builder.of((WanderingSpirit::new),MobCategory.CREATURE).sized(0.55f, 1.0f).build("ghost")
    );

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(GHOST, WanderingSpirit.createAttributes());
    }
}
