package com.lzyzl6.registry;

import com.lzyzl6.enchantment.BindEnchantmentEffect;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import java.util.function.UnaryOperator;

import static com.lzyzl6.CloudRevive.MOD_ID;

public class ModEnchantments {

    static UnaryOperator<DataComponentType.Builder<Unit>> unaryOperator =builder -> builder.persistent(Unit.CODEC);

    public static DataComponentType<Unit> SOUL_BIND = Registry.<DataComponentType<Unit>>register(
            BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, "soul_bind", ((DataComponentType.Builder)unaryOperator.apply(DataComponentType.builder())).build()
    );

    public static final ResourceKey<Enchantment> BIND = key("bind");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> HOLDER_GETTER_I = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> HOLDER_GETTER_E = context.lookup(Registries.ENCHANTMENT);
        register(
                context,
                BIND,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        HOLDER_GETTER_I.getOrThrow(ModTags.BIND_ENCHANTABLE),
                                        1, 1, Enchantment.constantCost(25), Enchantment.constantCost(50), 8
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.LOCATION_CHANGED,new BindEnchantmentEffect())
                        .exclusiveWith(HOLDER_GETTER_E.getOrThrow(EnchantmentTags.TREASURE))
                        .withEffect(SOUL_BIND)
        );
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    private static void register(BootstrapContext<Enchantment> bootstrapContext, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder) {
        bootstrapContext.register(resourceKey, builder.build(resourceKey.location()));
    }

    public static void initialize() {
    }
}
