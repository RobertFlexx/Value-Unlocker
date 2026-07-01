package com.valueunlocker.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Unique
    private static float valueunlocker$savedTickRate = 20.0f;

    @Inject(method = "getTickTargetMillis", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$useExactTickMillis(float msPerTick, CallbackInfoReturnable<Float> cir) {
        // client tries to be clever about tick timing. no thanks, use the cursed value the server gave us.
        Minecraft self = (Minecraft)(Object)this;
        if (self.level != null && self.level.tickRateManager().runsNormally()) {
            cir.setReturnValue(self.level.tickRateManager().millisecondsPerTick());
        }
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void valueunlocker$saveTickRate(ClientLevel level, CallbackInfo ci) {
        Minecraft self = (Minecraft)(Object)this;
        if (self.level != null) {
            valueunlocker$savedTickRate = self.level.tickRateManager().tickrate();
        }
    }

    @Inject(method = "setLevel", at = @At("TAIL"))
    private void valueunlocker$restoreTickRate(ClientLevel level, CallbackInfo ci) {
        if (level != null) {
            // changing levels forgets weird tick rates, so shove the bastard back in after the swap.
            level.tickRateManager().setTickRate(valueunlocker$savedTickRate);
        }
    }
}
