package com.valueunlocker.mixin;

import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExtraCodecs.class)
public abstract class ExtraCodecsMixin {
    // minecraft has another pile of range codecs because apparently one pile of bullshit was not enough.
    @ModifyVariable(method = "intRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int valueunlocker$intRangeMin(int min) {
        return Integer.MIN_VALUE;
    }

    @ModifyVariable(method = "intRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int valueunlocker$intRangeMax(int max) {
        return Integer.MAX_VALUE;
    }

    @ModifyVariable(method = "longRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int valueunlocker$longRangeMin(int min) {
        return Integer.MIN_VALUE;
    }

    @ModifyVariable(method = "longRange(II)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int valueunlocker$longRangeMax(int max) {
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
