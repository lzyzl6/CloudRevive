package com.lzyzl6;

import com.lzyzl6.data.storage.FileWork;
import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.model.WanderingSpiritModel;
import com.lzyzl6.registry.*;
import com.lzyzl6.renderer.BirthBeaconEntityRenderer;
import com.lzyzl6.renderer.WanderingSpiritRenderer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.lzyzl6.registry.ModBlocks.*;
import static com.lzyzl6.registry.ModEffects.EFFECTS;
import static com.lzyzl6.registry.ModEnchantments.ENCHANTMENTS;
import static com.lzyzl6.registry.ModEntities.ENTITIES;
import static com.lzyzl6.registry.ModEntities.GHOST;
import static com.lzyzl6.registry.ModItems.ITEMS;
import static com.lzyzl6.registry.ModSoundEvents.SOUND_EVENTS;
import static com.lzyzl6.registry.ModTabs.CREATIVE_MODE_TABS;

@Mod(value = CloudRevive.MOD_ID)
public class CloudRevive
{

    public static final String MOD_ID = "cloud_revive";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ModelLayerLocation GHOST_LAYER = new ModelLayerLocation(ResourceLocation
            .fromNamespaceAndPath(MOD_ID, "ghost"),
            "main");


    public CloudRevive(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ENTITIES.register(modEventBus);
        EFFECTS.register(modEventBus);
        ENCHANTMENTS.register(modEventBus);

        //专门为forge注册
        ModEntities.initialize();
        ModBlocks.initialize();
        ModItems.initialize();
        ModTabs.initialize();
        ModSoundEvents.initialize();
        ModEffects.initialize();
        ModEnchantments.initialize();

        FileWork.initialize();

        modEventBus.addListener(this::commonSetup);
    }



    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Cloud Revive Here!");

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BIRTH_BEACON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.QI_FRUIT_BUSH.get(), RenderType.cutout());

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CloudRevive.MOD_ID)
    static class ModBusHandler {

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(GHOST.get(), WanderingSpirit.createAttributes());
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CloudRevive.MOD_ID , value = Dist.CLIENT)
    static class ModBusClientHandler {

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(GHOST_LAYER, WanderingSpiritModel::getTexturedModelData);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(GHOST.get(), WanderingSpiritRenderer::new);
            event.registerBlockEntityRenderer(BIRTH_BEACON_ENTITY.get(), BirthBeaconEntityRenderer::new);
        }
    }

    @Mod.EventBusSubscriber
    static class CommandHandler {

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {

            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

            LiteralCommandNode<CommandSourceStack> cmd1 = dispatcher.register(
                            Commands.literal("killghost")
                                    .requires(source -> source.hasPermission(2))
                                    .executes(context -> {
                                        context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(context.getSource().getPosition(), 59999968, 59999968, 59999968)).forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
                                        context.getSource().sendSuccess(() -> Component.translatable("command.killghost.success"), true);

                                        return 1;
                                    })
            );

            LiteralCommandNode<CommandSourceStack> cmd2 = dispatcher.register(
                            Commands.literal("givepearl")
                                    .requires(source -> source.hasPermission(0))
                                    .executes(context -> {
                                        Player player = context.getSource().getPlayerOrException();
                                        if(player.getInventory().getFreeSlot() < 1) {
                                            player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), ModItems.PEARL.get().getDefaultInstance()));
                                        } else {
                                            player.addItem(ModItems.PEARL.get().getDefaultInstance());
                                        }
                                        context.getSource().sendSuccess(() -> Component.translatable("command.givepearl.success"), true);

                                        return 1;
                                    })
            );
        }
    }
}
