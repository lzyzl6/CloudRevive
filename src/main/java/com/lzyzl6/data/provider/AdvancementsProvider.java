package com.lzyzl6.data.provider;

import com.lzyzl6.registry.ModBlocks;
import com.lzyzl6.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class AdvancementsProvider extends FabricAdvancementProvider {

    public AdvancementsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }
    private AdvancementHolder createHaveItem(Consumer<AdvancementHolder> consumer,String name, AdvancementHolder parent, AdvancementType type, Item item) {
        return createHaveItem(consumer,name, parent, type, item, item);
    }
    private AdvancementHolder createHaveItem(Consumer<AdvancementHolder> consumer,String name, AdvancementHolder parent, AdvancementType type, Item got, Item show) {
        String str1 = "advancement." + name + ".title";
        String str2 = "advancement." + name + ".description";
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(
                        show,
                        Component.translatable(str1),
                        Component.translatable(str2),
                        null,
                        type,
                        true,
                        true,
                        false
                )
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(got))
                .save(consumer, MOD_ID + "/" + name);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {

        AdvancementHolder beginning = Advancement.Builder.advancement()
                .display(
                        ModItems.PEARL, // The display icon
                        Component.translatable("advancement.beginning.title"), // The title
                        Component.translatable("advancement.beginning.description"), // The description
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/advancements/backgrounds/chi_block.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        AdvancementType.TASK, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "beginning" is the name referenced by other advancements when they want to have "requirements."
                .addCriterion("beginning", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEARL))
                // Give the advancement an id
                .save(consumer, MOD_ID + "/beginning");

        AdvancementHolder qingdu = createHaveItem(consumer, "qingdu", beginning, AdvancementType.GOAL, ModItems.CORE_QI, ModBlocks.QI_FRUIT_BUSH.asItem());

        AdvancementHolder sky_goods = createHaveItem(consumer,"sky_goods", qingdu, AdvancementType.TASK, ModItems.SKY_QI);
        AdvancementHolder ground_goods = createHaveItem(consumer,"ground_goods", qingdu, AdvancementType.TASK, ModItems.GROUND_QI);
        AdvancementHolder human_life = createHaveItem(consumer,"human_life", qingdu, AdvancementType.TASK, ModItems.PEOPLE_QI);

        AdvancementHolder chaos_chi = createHaveItem(consumer,"chaos_chi", human_life, AdvancementType.GOAL, ModItems.CHAOS_QI);

        AdvancementHolder soul_rest = createHaveItem(consumer,"soul_rest", chaos_chi, AdvancementType.CHALLENGE, ModItems.DEAD_QI, ModItems.CHAOS_CAGE);
        AdvancementHolder advance = createHaveItem(consumer,"advance", chaos_chi, AdvancementType.TASK, ModItems.CHAOS_PEARL);

        AdvancementHolder enchanted_apple = createHaveItem(consumer,"enchanted_apple", soul_rest, AdvancementType.CHALLENGE, ModItems.SOUL_FRUIT);
        AdvancementHolder soul_bind = createHaveItem(consumer,"soul_bind", soul_rest, AdvancementType.GOAL, ModItems.SOUL_PEARL);

        AdvancementHolder soul_cycle = createHaveItem(consumer,"soul_cycle", soul_bind, AdvancementType.CHALLENGE, ModBlocks.BIRTH_BEACON.asItem());
    }
}
