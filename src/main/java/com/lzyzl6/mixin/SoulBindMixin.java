package com.lzyzl6.mixin;

import com.lzyzl6.registry.ModEnchantments;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Inventory.class)
public class SoulBindMixin {
    @ModifyVariable(method = "dropAll", at = @At("STORE"))
    private ItemStack bindDrop(ItemStack itemStack) {
        if(EnchantmentHelper.has(itemStack, ModEnchantments.SOUL_BIND.get()))
        {
            itemStack = ItemStack.EMPTY;
        }
        return itemStack;
    }

}
