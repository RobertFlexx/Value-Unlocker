package com.valueunlocker.mixin;

import com.valueunlocker.api.UnlockedGamerulesAccess;
import com.valueunlocker.util.UnlockedNumbers;
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public abstract class GameRulesMixin implements UnlockedGamerulesAccess {
    @Shadow
    @Final
    private GameRuleMap rules;

    @Unique
    private final Reference2DoubleOpenHashMap<GameRule<?>> valueunlocker$doubles = new Reference2DoubleOpenHashMap<>();

    @Override
    public double valueunlocker$getDouble(GameRule<?> rule) {
        if (this.valueunlocker$doubles.containsKey(rule)) {
            return this.valueunlocker$doubles.getDouble(rule);
        }
        Object value = this.rules.get((GameRule) rule);
        return value instanceof Number number ? number.doubleValue() : 0.0D;
    }

    @Override
    public void valueunlocker$setDouble(GameRule<?> rule, double value) {
        this.valueunlocker$doubles.put(rule, value);
    }

    @Inject(method = "get", at = @At("RETURN"), cancellable = true)
    private <T> void valueunlocker$returnBackendInteger(GameRule<T> rule, CallbackInfoReturnable<T> cir) {
        Object value = cir.getReturnValue();
        if (rule.gameRuleType() == GameRuleType.INT && value instanceof Number number && !(value instanceof Integer)) {
            cir.setReturnValue((T) Integer.valueOf(valueunlocker$toVanillaConsumerInt(rule, number.doubleValue())));
        }
    }

    @Unique
    private static int valueunlocker$toVanillaConsumerInt(GameRule<?> rule, double value) {
        if (rule == GameRules.RANDOM_TICK_SPEED) {
            return UnlockedNumbers.toBackendInt(value);
        }
        return UnlockedNumbers.toThresholdInt(value);
    }

    @Inject(method = "set", at = @At("HEAD"))
    private <T> void valueunlocker$captureNumericSet(GameRule<T> rule, T value, MinecraftServer server, CallbackInfo ci) {
        if (value instanceof Number number) {
            this.valueunlocker$setDouble(rule, number.doubleValue());
        }
    }

    @Inject(method = "setAll(Lnet/minecraft/world/level/gamerules/GameRules;Lnet/minecraft/server/MinecraftServer;)V", at = @At("TAIL"))
    private void valueunlocker$copyDoubleValues(GameRules other, MinecraftServer server, CallbackInfo ci) {
        if (!(other instanceof UnlockedGamerulesAccess access)) {
            return;
        }
        ((GameRules) (Object) this).availableRules().forEach(rule -> {
            double value = access.valueunlocker$getDouble(rule);
            this.valueunlocker$setDouble(rule, value);
        });
    }
}
