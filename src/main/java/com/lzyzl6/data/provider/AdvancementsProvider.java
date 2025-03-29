package com.lzyzl6.data.provider;

import com.lzyzl6.registry.ModBlocks;
import com.lzyzl6.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class AdvancementsProvider extends FabricAdvancementProvider {

    public AdvancementsProvider(FabricDataOutput output) {
        super(output);
    }
    private Advancement createHaveItem(Consumer<Advancement> consumer,String name, Advancement parent, FrameType type, Item item) {
        return createHaveItem(consumer,name, parent, type, item, item);
    }
    private Advancement createHaveItem(Consumer<Advancement> consumer,String name, Advancement parent, FrameType type, Item got, Item show) {
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
    public void generateAdvancement(Consumer<Advancement> consumer) {

        Advancement beginning = Advancement.Builder.advancement()
                .display(
                        ModItems.PEARL, // The display icon
                        Component.translatable("advancement.beginning.title"), // The title
                        Component.translatable("advancement.beginning.description"), // The description
                        new ResourceLocation(MOD_ID, "textures/gui/advancements/backgrounds/chi_block.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        FrameType.TASK, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "beginning" is the name referenced by other advancements when they want to have "requirements."
                .addCriterion("beginning", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEARL))
                // Give the advancement an id
                .save(consumer, MOD_ID + "/beginning");

        Advancement qingdu = createHaveItem(consumer, "qingdu", beginning, FrameType.GOAL, ModItems.CORE_QI, ModBlocks.QI_FRUIT_BUSH.asItem());

        Advancement sky_goods = createHaveItem(consumer,"sky_goods", qingdu, FrameType.TASK, ModItems.SKY_QI);
        Advancement ground_goods = createHaveItem(consumer,"ground_goods", qingdu, FrameType.TASK, ModItems.GROUND_QI);
        Advancement human_life = createHaveItem(consumer,"human_life", qingdu, FrameType.TASK, ModItems.PEOPLE_QI);

        Advancement chaos_chi = createHaveItem(consumer,"chaos_chi", human_life, FrameType.GOAL, ModItems.CHAOS_QI);

        Advancement soul_rest = createHaveItem(consumer,"soul_rest", chaos_chi, FrameType.CHALLENGE, ModItems.DEAD_QI, ModItems.CHAOS_CAGE);
        Advancement advance = createHaveItem(consumer,"advance", chaos_chi, FrameType.TASK, ModItems.CHAOS_PEARL);

        Advancement enchanted_apple = createHaveItem(consumer,"enchanted_apple", soul_rest, FrameType.CHALLENGE, ModItems.SOUL_FRUIT);
        Advancement soul_bind = createHaveItem(consumer,"soul_bind", soul_rest, FrameType.GOAL, ModItems.SOUL_PEARL);

        Advancement soul_cycle = createHaveItem(consumer,"soul_cycle", soul_bind, FrameType.CHALLENGE, ModBlocks.BIRTH_BEACON.asItem());
    }
}
