package com.valueunlocker.mixin;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DoubleArgumentType.class)
public abstract class DoubleArgumentTypeMixin {
    @ModifyVariable(
        method = "doubleArg(DD)Lcom/mojang/brigadier/arguments/DoubleArgumentType;",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private static double valueunlocker$removeMin(double min) {
        return -Double.MAX_VALUE;
    }

    @ModifyVariable(
        method = "doubleArg(DD)Lcom/mojang/brigadier/arguments/DoubleArgumentType;",
        at = @At("HEAD"),
        ordinal = 1,
        argsOnly = true
    )
    private static double valueunlocker$removeMax(double max) {
        return Double.MAX_VALUE;
    }
}
