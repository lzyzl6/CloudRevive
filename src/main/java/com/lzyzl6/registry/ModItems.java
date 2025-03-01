package com.lzyzl6.registry;

import com.lzyzl6.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.lzyzl6.CloudRevive.MOD_ID;


public class ModItems {

    public static final Item CAGE = register(
            "cage",
            new Cage(new Item.Properties().durability(81))
    );//灯笼，紫金

    public static final Item PEARL = register(
            "pearl",
            new AbInitioPearl(new Item.Properties().durability(9))
    );//类末影珍珠

    public static final Item CORE_QI = register(
            "core_qi",
            new CoreQi(new Item.Properties())
    );//紫金

    public static final Item SKY_QI = register(
            "sky_qi",
            new SkyQi(new Item.Properties())
    );//白金

    public static final Item GROUND_QI = register(
            "ground_qi",
            new GroundQi(new Item.Properties())
    );//黑赤

    public static final Item PEOPLE_QI = register(
            "people_qi",
            new PeopleQI(new Item.Properties())
    );//青白

    public static final Item DEAD_QI = register(
            "dead_qi",
            new DeadQi(new Item.Properties())
    );//灰黑

    public static final Item QI_FRUIT = register(
            "qi_fruit",
            new QiFruit(new Item.Properties())
    );

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, path), item);
    }


    public static void initialize() {

    }
}
