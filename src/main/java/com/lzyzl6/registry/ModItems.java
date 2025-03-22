package com.lzyzl6.registry;

import com.lzyzl6.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.Optional;

import static com.lzyzl6.CloudRevive.MOD_ID;


public class ModItems {

    public static final Item START_CAGE = register(
            "start_cage",
            new StartCage(new Item.Properties().rarity(Rarity.UNCOMMON).durability(3))
    );

    public static final Item CAGE = register(
            "cage",
            new Cage(new Item.Properties().rarity(Rarity.RARE).durability(81))
    );

    public static final Item CHAOS_CAGE = register(
            "chaos_cage",
            new ChaosCage(new Item.Properties().rarity(Rarity.EPIC).fireResistant().durability(256))
    );

    public static final Item PEARL = register(
            "pearl",
            new AbInitioPearl(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1))
    );

    public static final Item CHAOS_PEARL = register(
            "chaos_pearl",
            new ChaosPearl(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1))
    );

    public static final Item SOUL_PEARL = register(
            "soul_pearl",
            new SoulPearl(new Item.Properties().rarity(Rarity.RARE).stacksTo(1))
    );

    public static final Item CORE_QI = register(
            "core_qi",
            new CoreQi(new Item.Properties().rarity(Rarity.RARE).fireResistant().stacksTo(16))
    );

    public static final Item CHAOS_QI = register(
            "chaos_qi",
            new ChaosQi(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(16))
    );

    public static final Item SKY_QI = register(
            "sky_qi",
            new SkyQi(new Item.Properties().fireResistant().stacksTo(16))
    );

    public static final Item GROUND_QI = register(
            "ground_qi",
            new GroundQi(new Item.Properties().fireResistant().stacksTo(16))
    );

    public static final Item PEOPLE_QI = register(
            "people_qi",
            new PeopleQI(new Item.Properties().fireResistant().stacksTo(16))
    );

    public static final Item DEAD_QI = register(
            "dead_qi",
            new DeadQi(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant().stacksTo(16))
    );

    public static final Item QI_FRUIT = register(
            "qi_fruit",
            new QiFruit(new Item.Properties().food(new FoodProperties(3, 1.5f, true, 0.95f, Optional.of(ItemStack.EMPTY),List
                    .of(new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.LEVITATION, 200,0, false, false, true), 1.0f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1500,2, false, false, true), 0.13f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200,2, false, false, true), 0.13f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2000,2, false, false, true), 0.12f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1600,1, false, false, true), 0.11f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.LUCK, 3000,2, false, false, true), 0.05f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200,0, false, false, true), 0.15f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000,2, false, false, true), 0.08f))))
    ));

    public static final Item SOUL_FRUIT = register(
            "soul_fruit",
            new SoulFruit(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON).food(new FoodProperties(2, 2.5f, true, 0.95f, Optional.of(ItemStack.EMPTY),List
                    .of(new FoodProperties.PossibleEffect(new MobEffectInstance(ModEffects.SOUL_LIKE, 2800,0, false, false, true), 1.0f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2800,4, false, false, true), 1.0f),
                            new FoodProperties.PossibleEffect(new MobEffectInstance(MobEffects.WEAKNESS, 2800, 4, false, false, true), 1.0f)
                    )))));

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, path), item);
    }


    public static void initialize() {

    }
}
