package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.border.WorldBorder$StaticBorderExtent")
public abstract class WorldBorderStaticExtentMixin {
    @Redirect(method = "updateBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private double valueunlocker$dontClampExtent(double value, double min, double max) {
        return value;
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$noCollision(CallbackInfoReturnable<VoxelShape> cir) {
        cir.setReturnValue(Shapes.empty());
    }
}
