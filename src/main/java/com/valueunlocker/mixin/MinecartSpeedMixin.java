package com.valueunlocker.mixin;

import com.valueunlocker.api.UnlockedGamerules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class MinecartSpeedMixin {
    @Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$getUnlockedMaxSpeed(ServerLevel level, CallbackInfoReturnable<Double> cir) {
        double value = UnlockedGamerules.getDouble(level.getGameRules(), GameRules.MAX_MINECART_SPEED);
        cir.setReturnValue(value / 20.0D);
    }
}
