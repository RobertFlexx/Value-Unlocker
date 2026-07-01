package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RangedAttribute.class)
public abstract class RangedAttributeMixin {
    @Redirect(
        method = "sanitizeValue",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D")
    )
    private double valueunlocker$noClamp(double value, double min, double max) {
        return value;
    }
}
