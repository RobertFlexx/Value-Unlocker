package com.valueunlocker.mixin;

import com.mojang.brigadier.arguments.FloatArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FloatArgumentType.class)
public abstract class FloatArgumentTypeMixin {
    @ModifyVariable(
        method = "floatArg(FF)Lcom/mojang/brigadier/arguments/FloatArgumentType;",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private static float valueunlocker$removeMin(float min) {
        return -Float.MAX_VALUE;
    }

    @ModifyVariable(
        method = "floatArg(FF)Lcom/mojang/brigadier/arguments/FloatArgumentType;",
        at = @At("HEAD"),
        ordinal = 1,
        argsOnly = true
    )
    private static float valueunlocker$removeMax(float max) {
        return Float.MAX_VALUE;
    }
}
