package com.valueunlocker.mixin;

import com.valueunlocker.util.UnlockedNumbers;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.ToIntFunction;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRule.class)
public abstract class GameRuleMixin<T> {
    @Mutable
    @Shadow
    @Final
    private ArgumentType<T> argument;

    @Mutable
    @Shadow
    @Final
    private Codec<T> valueCodec;

    @Shadow
    @Final
    private GameRuleType gameRuleType;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void valueunlocker$unlockIntegerRules(
        GameRuleCategory category,
        GameRuleType gameRuleType,
        ArgumentType<T> argument,
        GameRules.VisitorCaller<T> visitorCaller,
        Codec<T> valueCodec,
        ToIntFunction<T> commandResultFunction,
        T defaultValue,
        FeatureFlagSet requiredFeatures,
        CallbackInfo ci
    ) {
        if (this.gameRuleType == GameRuleType.INT) {
            this.argument = (ArgumentType<T>) DoubleArgumentType.doubleArg();
            Codec<Object> numericCodec = Codec.DOUBLE.xmap(value -> value, value -> ((Number) value).doubleValue());
            this.valueCodec = (Codec<T>) (Object) numericCodec;
        }
    }

    @Inject(method = "serialize", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$serializeNumericValue(T value, CallbackInfoReturnable<String> cir) {
        if (this.gameRuleType == GameRuleType.INT && value instanceof Number number) {
            cir.setReturnValue(UnlockedNumbers.format(number.doubleValue()));
        }
    }

    @Inject(method = "argument", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$getUnboundedArgument(CallbackInfoReturnable<ArgumentType<T>> cir) {
        if (this.gameRuleType == GameRuleType.INT) {
            cir.setReturnValue((ArgumentType<T>) DoubleArgumentType.doubleArg());
        }
    }

    @Inject(method = "deserialize", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$deserializeDecimal(String value, CallbackInfoReturnable<DataResult<T>> cir) {
        if (this.gameRuleType != GameRuleType.INT) {
            return;
        }
        try {
            double parsed = Double.parseDouble(value);
            cir.setReturnValue(DataResult.success((T) Integer.valueOf(UnlockedNumbers.toThresholdInt(parsed))));
        } catch (NumberFormatException ignored) {
        }
    }
}
