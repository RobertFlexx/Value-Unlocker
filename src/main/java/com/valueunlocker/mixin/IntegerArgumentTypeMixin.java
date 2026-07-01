package com.valueunlocker.mixin;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IntegerArgumentType.class)
public abstract class IntegerArgumentTypeMixin {
    // brigadier is the front door where commands get told "no" before minecraft even sees the number.
    @ModifyVariable(
        method = "integer(II)Lcom/mojang/brigadier/arguments/IntegerArgumentType;",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private static int valueunlocker$removeMin(int min) {
        return Integer.MIN_VALUE;
    }

    @ModifyVariable(
        method = "integer(II)Lcom/mojang/brigadier/arguments/IntegerArgumentType;",
        at = @At("HEAD"),
        ordinal = 1,
        argsOnly = true
    )
    private static int valueunlocker$removeMax(int max) {
        return Integer.MAX_VALUE;
    }
}
