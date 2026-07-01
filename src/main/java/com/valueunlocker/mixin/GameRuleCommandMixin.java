package com.valueunlocker.mixin;

import com.valueunlocker.api.UnlockedGamerules;
import com.valueunlocker.util.UnlockedNumbers;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRuleCommand.class)
public abstract class GameRuleCommandMixin {
    @Inject(method = "setRule", at = @At("HEAD"), cancellable = true)
    private static <T> void valueunlocker$setNumericRule(CommandContext<CommandSourceStack> context, GameRule<T> rule, CallbackInfoReturnable<Integer> cir) {
        if (rule.gameRuleType() != GameRuleType.INT) {
            return;
        }

        CommandSourceStack source = context.getSource();
        GameRules rules = source.getLevel().getGameRules();
        double doubleValue = DoubleArgumentType.getDouble(context, "value");

        rules.set((GameRule) rule, doubleValue, source.getServer());
        UnlockedGamerules.setDouble(rules, rule, doubleValue);
        source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.gamerule.set", rule.id(), UnlockedNumbers.format(doubleValue)), true);
        cir.setReturnValue(UnlockedNumbers.toCommandResult(doubleValue));
    }

    @Inject(method = "queryRule", at = @At("HEAD"), cancellable = true)
    private static <T> void valueunlocker$queryNumericRule(CommandSourceStack source, GameRule<T> rule, CallbackInfoReturnable<Integer> cir) {
        if (rule.gameRuleType() != GameRuleType.INT) {
            return;
        }

        double doubleValue = UnlockedGamerules.getDouble(source.getLevel().getGameRules(), rule);
        source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.gamerule.query", rule.id(), UnlockedNumbers.format(doubleValue)), false);
        cir.setReturnValue(UnlockedNumbers.toCommandResult(doubleValue));
    }
}
