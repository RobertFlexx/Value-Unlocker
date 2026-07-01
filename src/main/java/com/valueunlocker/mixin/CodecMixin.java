package com.valueunlocker.mixin;

import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Codec.class)
public interface CodecMixin {
    // this is the boring datafixer range crap that rejects numbers before the game even gets to be weird.
    @ModifyVariable(method = "intRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int valueunlocker$intRangeMin(int min) {
        return Integer.MIN_VALUE;
    }

    @ModifyVariable(method = "intRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int valueunlocker$intRangeMax(int max) {
        return Integer.MAX_VALUE;
    }

    @ModifyVariable(method = "floatRange(FF)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static float valueunlocker$floatRangeMin(float min) {
        return -Float.MAX_VALUE;
    }

    @ModifyVariable(method = "floatRange(FF)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static float valueunlocker$floatRangeMax(float max) {
        return Float.MAX_VALUE;
    }
}
