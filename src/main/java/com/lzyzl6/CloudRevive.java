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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static com.lzyzl6.registry.ModBlocks.*;
import static com.lzyzl6.registry.ModEffects.EFFECTS;
import static com.lzyzl6.registry.ModEnchantmentEffects.LB_ENCHANTMENT_EFFECTS;
import static com.lzyzl6.registry.ModEnchantments.ENCHANTMENT_EFFECT_COMPONENT_TYPE;
import static com.lzyzl6.registry.ModEntities.ENTITIES;
import static com.lzyzl6.registry.ModEntities.GHOST;
import static com.lzyzl6.registry.ModItems.ITEMS;
import static com.lzyzl6.registry.ModSoundEvents.SOUND_EVENTS;
import static com.lzyzl6.registry.ModTabs.CREATIVE_MODE_TABS;

@Mod(CloudRevive.MOD_ID)
public class CloudRevive
{

    public static final String MOD_ID = "cloud_revive";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ModelLayerLocation GHOST_LAYER = new ModelLayerLocation(ResourceLocation
            .fromNamespaceAndPath(MOD_ID, "ghost"),
            "main");


    public CloudRevive(IEventBus modEventBus)
    {

        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ENTITIES.register(modEventBus);
        EFFECTS.register(modEventBus);
        LB_ENCHANTMENT_EFFECTS.register(modEventBus);
        ENCHANTMENT_EFFECT_COMPONENT_TYPE.register(modEventBus);

        ModEntities.initialize();
        ModBlocks.initialize();
        ModItems.initialize();
        ModTabs.initialize();
        ModSoundEvents.initialize();
        ModEffects.initialize();
        ModEnchantmentEffects.initialize();
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

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CloudRevive.MOD_ID)
    static class ModBusHandler {

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(GHOST.get(), WanderingSpirit.createAttributes());
        }
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CloudRevive.MOD_ID , value = Dist.CLIENT)
    static class EventHandler {

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

    @EventBusSubscriber
    static class CommandHandler {

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {

            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

            LiteralCommandNode<CommandSourceStack> cmd1 = dispatcher.register(
                    Commands.literal("killghost")
                            .requires(source -> source.hasPermission(2))
                            .executes(context -> {
                                context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(Vec3.ZERO, 59999968, 59999968, 59999968)).forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
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

            LiteralCommandNode<CommandSourceStack> cmd3 = dispatcher.register(
                    Commands.literal("queueghost")
                            .requires(source -> source.hasPermission(0))
                            .executes(context -> {
                                ServerPlayer player = context.getSource().getPlayer();
                                if(player != null) {
                                    AtomicInteger count = new AtomicInteger(1);
                                    context.getSource().getLevel().getEntitiesOfClass(WanderingSpirit.class, AABB.ofSize(Vec3.ZERO, 59999968, 59999968, 59999968)).forEach(
                                            entity -> {
                                                if(entity.locateTargetUUID().equals(player.getUUID())) {
                                                    player.sendSystemMessage(Component.literal(count + " X:" + entity.getX() + " Y:" + entity.getY() + " Z:" + entity.getZ()));
                                                    count.getAndIncrement();
                                                }
                                            });
                                    if(count.get() == 1) {
                                        player.sendSystemMessage(Component.translatable("command.queueghost.failure"));
                                    }
                                }

                                return 1;
                            })
            );
        }
    }
}
