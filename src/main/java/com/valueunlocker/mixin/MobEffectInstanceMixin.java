package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Redirect(
        method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I")
    )
    private int valueunlocker$unlimitedAmplifier(int amplifier, int min, int max) {
        return amplifier;
    }
}
