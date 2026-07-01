package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // vanilla caps loaded motion at 10 because fast things are scary, apparently.
    @ModifyConstant(
        method = "load(Lnet/minecraft/world/level/storage/ValueInput;)V",
        constant = @Constant(doubleValue = 10.0)
    )
    private double valueunlocker$unlimitedMotion(double original) {
        return Double.MAX_VALUE;
    }

    @Redirect(
        method = "load(Lnet/minecraft/world/level/storage/ValueInput;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D")
    )
    private double valueunlocker$unlimitedPosition(double value, double min, double max) {
        // nbt position loading clamps coordinates too, because of course the same bullshit exists twice.
        return value;
    }
}
