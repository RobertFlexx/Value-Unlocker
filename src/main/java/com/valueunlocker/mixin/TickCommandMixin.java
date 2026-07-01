package com.valueunlocker.mixin;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.server.commands.TickCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TickCommand.class)
public abstract class TickCommandMixin {
    @Redirect(
        method = "register",
        at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/arguments/FloatArgumentType;floatArg(FF)Lcom/mojang/brigadier/arguments/FloatArgumentType;")
    )
    private static FloatArgumentType valueunlocker$unboundedTickRateArgument(float min, float max) {
        return FloatArgumentType.floatArg();
    }
}
