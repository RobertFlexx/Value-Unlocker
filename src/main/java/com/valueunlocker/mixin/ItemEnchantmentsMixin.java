package com.valueunlocker.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin {
    @ModifyConstant(
        method = "<init>(Lit/unimi/dsi/fastutil/objects/Object2IntOpenHashMap;)V",
        constant = @Constant(intValue = 255)
    )
    private int valueunlocker$constructorMaxLevel(int max) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(
        method = "<clinit>",
        constant = @Constant(intValue = 255)
    )
    private static int valueunlocker$codecMaxLevel(int max) {
        return Integer.MAX_VALUE;
    }
}
