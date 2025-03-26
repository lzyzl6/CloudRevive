package com.lzyzl6.data.provider;

import com.lzyzl6.registry.ModTags;
import com.lzyzl6.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.tag(ItemTags.VANISHING_ENCHANTABLE);
        this.tag(ModTags.BIND_ENCHANTABLE).addTag(ItemTags.VANISHING_ENCHANTABLE).add(ModItems.start_cage_key,ModItems.cage_key,ModItems.chaos_cage_key);
    }
}
