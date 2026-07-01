package com.valueunlocker.mixin;

import net.minecraft.server.commands.EffectCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EffectCommands.class)
public abstract class EffectCommandsMixin {
    @ModifyConstant(method = "register", constant = @Constant(intValue = 255))
    private static int valueunlocker$unlimitedAmplifier(int original) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "register", constant = @Constant(intValue = 1000000))
    private static int valueunlocker$unlimitedSeconds(int original) {
        return Integer.MAX_VALUE;
    }
}
