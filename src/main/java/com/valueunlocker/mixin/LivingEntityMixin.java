package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Redirect(
        method = "setHealth",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F")
    )
    private float valueunlocker$unboundedHealth(float value, float min, float max) {
        return value;
    }
}
