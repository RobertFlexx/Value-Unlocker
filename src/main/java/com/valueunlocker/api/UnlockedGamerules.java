package com.valueunlocker.api;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;

public final class UnlockedGamerules {
    private UnlockedGamerules() {
    }

    public static double getDouble(GameRules rules, GameRule<?> rule) {
        if (rules instanceof UnlockedGamerulesAccess access) {
            return access.valueunlocker$getDouble(rule);
        }
        Object value = rules.get((GameRule) rule);
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return 0.0D;
    }

    public static void setDouble(GameRules rules, GameRule<?> rule, double value) {
        if (rules instanceof UnlockedGamerulesAccess access) {
            access.valueunlocker$setDouble(rule, value);
        }
    }
}
