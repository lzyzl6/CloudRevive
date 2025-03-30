package com.lzyzl6.registry;

import com.lzyzl6.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.lzyzl6.CloudRevive.MOD_ID;
import static com.lzyzl6.registry.ModBlocks.*;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    public static final Supplier<Item> START_CAGE = ITEMS.register(
            "start_cage",
            () -> new StartCage(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .durability(31)
            )
    );
    public static final Supplier<Item> CAGE = ITEMS.register(
            "cage",
            () -> new Cage(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .durability(81)
            )
    );
    public static final Supplier<Item> CHAOS_CAGE = ITEMS.register(
            "chaos_cage",
            () -> new ChaosCage(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .durability(256)
            )
    );

    public static final Supplier<Item> PEARL = ITEMS.register(
            "pearl",
            () -> new AbInitioPearl(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
            )
    );
    public static final Supplier<Item> CHAOS_PEARL = ITEMS.register(
            "chaos_pearl",
            () ->new ChaosPearl(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
            )
    );
    public static final Supplier<Item> SOUL_PEARL = ITEMS.register(
            "soul_pearl",
            () -> new SoulPearl(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
            )
    );
    public static final Supplier<Item> CORE_QI = ITEMS.register(
            "core_qi",
            () -> new CoreQi(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .fireResistant()
                    .stacksTo(16)
            )
    );
    public static final Supplier<Item> CHAOS_QI = ITEMS.register(
            "chaos_qi",
            () -> new ChaosQi(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .stacksTo(16)
            )
    );
    public static final Supplier<Item> SKY_QI = ITEMS.register(
            "sky_qi",
            () -> new SkyQi(new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
            )
    );
    public static final Supplier<Item> GROUND_QI = ITEMS.register(
            "ground_qi",
            () -> new GroundQi(new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
            )
    );
    public static final Supplier<Item> PEOPLE_QI = ITEMS.register(
            "people_qi",
            () -> new PeopleQI(new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
            )
    );
    public static final Supplier<Item> DEAD_QI = ITEMS.register(
            "dead_qi",
            () -> new DeadQi(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant()
                    .stacksTo(16)
            )
    );

    public static final Supplier<Item> QI_FRUIT = ITEMS.register(
            "qi_fruit",
            () -> new QiFruit(new Item.Properties()
                    .food((new FoodProperties.Builder())
                            .nutrition(3).saturationMod(1.5f).alwaysEat().fast()
                            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 200,0, false, false, true), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1500,2, false, false, true), 0.13f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200,2, false, false, true), 0.13f)
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000,2, false, false, true), 0.12f)
                            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 1600,1, false, false, true), 0.11f)
                            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 3000,2, false, false, true), 0.05f)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200,0, false, false, true), 0.15f)
                            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1000,2, false, false, true), 0.08f)
                            .build()
                    )
            )
    );
    public static final Supplier<Item> SOUL_FRUIT = ITEMS.register(
            "soul_fruit",
            () -> new SoulFruit(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)
                    .food((new FoodProperties.Builder())
                    .nutrition(2).saturationMod(2.5f).alwaysEat().fast()
                    .effect(() -> new MobEffectInstance(ModEffects.SOUL_LIKE.get(), 2800,0, false, false, true), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2800,4, false, false, true), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 2800, 4, false, false, true), 1.0f)
                    .build()
                    )
            )
    );

    public static final Supplier<BlockItem> BIRTH_BEACON_ITEM = ITEMS.register(
            "birth_beacon",

            () -> new BirthBeaconItem(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .fireResistant()
            )
    );

    public static final Supplier<BlockItem> QI_BLOCK_CORE_ITEM = ITEMS.register(
            "qi_block_core",
            () ->  new BlockItem(QI_BLOCK_CORE.get(), new Item.Properties())
    );

    public static final Supplier<BlockItem> QI_FRUIT_BUSH_ITEM = ITEMS.register(
            "qi_fruit_bush",
            () ->  new BlockItem(QI_FRUIT_BUSH.get(), new Item.Properties())
    );

    public static final Supplier<BlockItem> QI_BLOCK_ITEM = ITEMS.register(
            "qi_block",
            () ->  new BlockItem(QI_BLOCK.get(), new Item.Properties())
    );


    public static void initialize() {

    }
}
