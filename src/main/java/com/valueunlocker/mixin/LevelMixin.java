package com.valueunlocker.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    // these checks are the 30-million-block hall monitor. tell it everything is fine, even when it is not.
    @Inject(method = "isInWorldBounds", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$noWorldBounds(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isInValidBounds", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$noValidBounds(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isInSpawnableBounds", at = @At("HEAD"), cancellable = true)
    private static void valueunlocker$noSpawnableBounds(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
