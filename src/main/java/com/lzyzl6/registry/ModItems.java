package com.lzyzl6.registry;

import com.lzyzl6.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.lzyzl6.CloudRevive.MOD_ID;
import static com.lzyzl6.registry.ModBlocks.*;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final Supplier<Item> START_CAGE = ITEMS.registerItem(
            "start_cage",
            StartCage::new, // The factory that the properties will be passed into.
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .durability(3)
    );
    public static final Supplier<Item> CAGE = ITEMS.registerItem(
            "cage",
            Cage::new,
            new Item.Properties()
                    .rarity(Rarity.RARE)
                    .durability(81)
    );
    public static final Supplier<Item> CHAOS_CAGE = ITEMS.registerItem(
            "chaos_cage",
            ChaosCage::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .durability(256)
    );
    public static final Supplier<Item> PEARL = ITEMS.registerItem(
            "pearl",
            AbInitioPearl::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
    );
    public static final Supplier<Item> CHAOS_PEARL = ITEMS.registerItem(
            "chaos_pearl",
            ChaosPearl::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    );
    public static final Supplier<Item> SOUL_PEARL = ITEMS.registerItem(
            "soul_pearl",
            SoulPearl::new,
            new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
    );
    public static final Supplier<Item> CORE_QI = ITEMS.registerItem(
            "core_qi",
            CoreQi::new,
            new Item.Properties()
                    .rarity(Rarity.RARE)
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> CHAOS_QI = ITEMS.registerItem(
            "chaos_qi",
            ChaosQi::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> SKY_QI = ITEMS.registerItem(
            "sky_qi",
            SkyQi::new,
            new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> GROUND_QI = ITEMS.registerItem(
            "ground_qi",
            GroundQi::new,
            new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> PEOPLE_QI = ITEMS.registerItem(
            "people_qi",
            PeopleQI::new,
            new Item.Properties()
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> DEAD_QI = ITEMS.registerItem(
            "dead_qi",
            DeadQi::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant()
                    .stacksTo(16)
    );
    public static final Supplier<Item> QI_FRUIT = ITEMS.registerItem(
            "qi_fruit",
            QiFruit::new,
            new Item.Properties()
                    .food(new FoodProperties(3, 1.5f, true, 0.95f,
                            Optional.of(ItemStack.EMPTY),
                            List.of(
                                    new FoodProperties.PossibleEffect(() -> new MobEffectInstance(MobEffects.LEVITATION, 200,0, false, false, true),1.0f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.NIGHT_VISION, 1500,2, false, false, true),0.13f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200,2, false, false, true),0.13f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.DIG_SPEED, 2000,2, false, false, true),0.12f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.HEALTH_BOOST, 1600,1, false, false, true),0.11f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.LUCK, 3000,2, false, false, true),0.05f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200,0, false, false, true),0.15f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.ABSORPTION, 1000,2, false, false, true),0.08f)
                            )
                    ))
    );
    public static final Supplier<Item> SOUL_FRUIT = ITEMS.registerItem(
            "soul_fruit",
            SoulFruit::new,
            new Item.Properties()
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties(2, 2.5f, true, 0.95f,
                            Optional.of(ItemStack.EMPTY),
                            List.of(
                                    new FoodProperties.PossibleEffect(() -> new MobEffectInstance(ModEffects.SOUL_LIKE, 2800,0, false, false, true),1.0f)
                                    ,new FoodProperties.PossibleEffect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2800,4, false, false, true),1.0f)
                                    ,new FoodProperties.PossibleEffect(() ->  new MobEffectInstance(MobEffects.WEAKNESS, 2800, 4, false, false, true),1.0f)
                            )
                    ))
    );

    public static final Supplier<BlockItem> BIRTH_BEACON_ITEM = ITEMS.registerItem(
            "birth_beacon",
            BirthBeaconItem::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .fireResistant()
    );

    public static final Supplier<BlockItem> QI_BLOCK_CORE_ITEM = ITEMS.registerSimpleBlockItem(
            "qi_block_core",
            QI_BLOCK_CORE,
            new Item.Properties()
    );

    public static final Supplier<BlockItem> QI_FRUIT_BUSH_ITEM = ITEMS.registerSimpleBlockItem(
            "qi_fruit_bush",
            QI_FRUIT_BUSH,
            new Item.Properties()
    );

    public static final Supplier<BlockItem> QI_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "qi_block",
            QI_BLOCK,
            new Item.Properties()
    );


    public static void initialize() {

    }
}
