package com.valueunlocker.mixin;

import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkPos.class)
public abstract class ChunkPosMixin {
    @Inject(method = "isValid()Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$instanceAlwaysValid(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isValid(II)Z", at = @At("HEAD"), cancellable = true)
    private static void valueunlocker$coordsAlwaysValid(int x, int z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
