package com.valueunlocker.mixin;

import net.minecraft.server.commands.WorldBorderCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldBorderCommand.class)
public abstract class WorldBorderCommandMixin {
    @ModifyConstant(
        method = "setSize",
        constant = @Constant(doubleValue = 5.9999968E7)
    )
    private static double valueunlocker$unlimitedBorderSize(double original) {
        return Double.MAX_VALUE;
    }

    @ModifyConstant(
        method = "setSize",
        constant = @Constant(doubleValue = 1.0)
    )
    private static double valueunlocker$unlimitedBorderSizeMin(double original) {
        return -Double.MAX_VALUE;
    }

    @ModifyConstant(
        method = "setCenter",
        constant = @Constant(doubleValue = 2.9999984E7)
    )
    private static double valueunlocker$unlimitedBorderCenter(double original) {
        return Double.MAX_VALUE;
    }
}
