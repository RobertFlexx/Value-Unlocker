package com.valueunlocker.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEnchantments.Mutable.class)
public abstract class ItemEnchantmentsMutableMixin {
    @Redirect(
        method = "set",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I")
    )
    private int valueunlocker$setExactLevel(int level, int max) {
        return level;
    }

    @Redirect(
        method = "upgrade",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I")
    )
    private int valueunlocker$upgradeExactLevel(int level, int max) {
        return level;
    }
}
