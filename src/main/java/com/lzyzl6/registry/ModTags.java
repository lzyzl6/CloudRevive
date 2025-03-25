package com.lzyzl6.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.lzyzl6.CloudRevive.MOD_ID;
import static com.lzyzl6.data.storage.FileWork.isBackpackedInstalled;

public class ModTags {

    public static TagKey<Item> BIND_ENCHANTABLE =  bind("enchantable/bind");

    private static TagKey<Item> bind(String string) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    public static void initialize() {

    }
}
