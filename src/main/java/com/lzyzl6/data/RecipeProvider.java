package com.lzyzl6.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.concurrent.CompletableFuture;

import static com.lzyzl6.registry.ModItems.CHAOS_QI;
import static net.minecraft.world.item.Items.*;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NETHER_STAR,3).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', NETHER_STAR)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(NETHER_STAR),
                        FabricRecipeProvider.has(NETHER_STAR))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NETHERITE_INGOT,3).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', NETHERITE_INGOT)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(NETHERITE_INGOT),
                        FabricRecipeProvider.has(NETHERITE_INGOT))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HEAVY_CORE,3).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', HEAVY_CORE)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(HEAVY_CORE),
                        FabricRecipeProvider.has(HEAVY_CORE))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ENCHANTED_GOLDEN_APPLE,3).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', ENCHANTED_GOLDEN_APPLE)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(ENCHANTED_GOLDEN_APPLE),
                        FabricRecipeProvider.has(ENCHANTED_GOLDEN_APPLE))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NAUTILUS_SHELL,5).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', NAUTILUS_SHELL)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(NAUTILUS_SHELL),
                        FabricRecipeProvider.has(NAUTILUS_SHELL))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GHAST_TEAR,9).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', GHAST_TEAR)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(GHAST_TEAR),
                        FabricRecipeProvider.has(GHAST_TEAR))
                .showNotification(true)
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PHANTOM_MEMBRANE,9).pattern("bbb").pattern("bib").pattern("bbb")
                .define('b', CHAOS_QI)
                .define('i', PHANTOM_MEMBRANE)
                .unlockedBy(FabricRecipeProvider.getHasName(CHAOS_QI),
                        FabricRecipeProvider.has(CHAOS_QI))
                .unlockedBy(FabricRecipeProvider.getHasName(PHANTOM_MEMBRANE),
                        FabricRecipeProvider.has(PHANTOM_MEMBRANE))
                .showNotification(true)
                .save(recipeOutput);
    }

}
