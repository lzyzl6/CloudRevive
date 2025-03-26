package com.lzyzl6.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_AMBIENT = SOUND_EVENTS.register(
            "ghost_ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ghost_ambient"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUL_LIKE = SOUND_EVENTS.register(
            "soul_like",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_like"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> TALK = SOUND_EVENTS.register(
            "talk",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "talk"))
    );

    public static void initialize() {

    }
}
