package com.lzyzl6.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModSoundEvents {

    public static SoundEvent GHOST_AMBIENT = Registry.register(BuiltInRegistries.SOUND_EVENT,
            new ResourceLocation(MOD_ID, "ghost_ambient"),
            SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "ghost_ambient")));

    public static SoundEvent SOUL_LIKE = Registry.register(BuiltInRegistries.SOUND_EVENT,
            new ResourceLocation(MOD_ID, "soul_like"),
            SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "soul_like")));

    public static SoundEvent TALK = Registry.register(BuiltInRegistries.SOUND_EVENT,
            new ResourceLocation(MOD_ID, "talk"),
            SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "talk")));

    public static void initialize() {

    }
}
