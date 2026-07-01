package com.valueunlocker.mixin;

import net.minecraft.world.TickRateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TickRateManager.class)
public abstract class TickRateManagerMixin {
    @Redirect(
        method = "setTickRate",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F")
    )
    private float valueunlocker$allowTinyTickRates(float value, float vanillaMinimum) {
        // this is literally Math.max(1, rate). one line of fun police bullshit.
        return value;
    }
}
